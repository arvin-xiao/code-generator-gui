package cn.coderoom.generator.base.db.utils;

import cn.coderoom.generator.base.db.entity.DatabaseConfig;
import cn.coderoom.generator.base.db.entity.DbTypeEnum;
import cn.coderoom.generator.base.exceptions.DbDriverLoadingException;
import cn.coderoom.generator.base.exceptions.ServiceException;
import cn.coderoom.generator.base.exceptions.enums.CoreExceptionEnum;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.mybatis.generator.internal.util.ClassloaderUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/** 
 * 
 * @class DbUtil 
 * @package cn.coderoom.generator.gui.util
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/17 13:08 
*/ 
public class DbUtil {

    private static final Logger _LOG = LoggerFactory.getLogger(DbUtil.class);
    private static final int DB_CONNECTION_TIMEOUTS_SECONDS = 1;

    private static Map<DbTypeEnum, Driver> drivers = new HashMap<>();

	private static ExecutorService executorService = Executors.newSingleThreadExecutor();
	private static volatile boolean portForwaring = false;
	private static Map<Integer, Session> portForwardingSession = new ConcurrentHashMap<>();

    public static Session getSSHSession(DatabaseConfig databaseConfig) {
		if (StringUtils.isBlank(databaseConfig.getSshHost())
				|| StringUtils.isBlank(databaseConfig.getSshPort())
				|| StringUtils.isBlank(databaseConfig.getSshUser())
				|| StringUtils.isBlank(databaseConfig.getSshPassword())
		) {
			return null;
		}
		Session session = null;
		try {
			//Set StrictHostKeyChecking property to no to avoid UnknownHostKey issue
			Properties config = new Properties();
			config.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			Integer sshPort = NumberUtils.createInteger(databaseConfig.getSshPort());
			int port = sshPort == null ? 22 : sshPort;
			session = jsch.getSession(databaseConfig.getSshUser(), databaseConfig.getSshHost(), port);
			session.setPassword(databaseConfig.getSshPassword());
			session.setConfig(config);
		}catch (JSchException e) {
			//Ignore
		}
		return session;
	}

	public static void engagePortForwarding(Session sshSession, DatabaseConfig config) {
		if (sshSession != null) {
			AtomicInteger assinged_port = new AtomicInteger();
			Future<?> result = executorService.submit(() -> {
				try {
					Integer localPort = NumberUtils.createInteger(config.getLport());
					Integer RemotePort = NumberUtils.createInteger(config.getRport());
					int lport = localPort == null ? Integer.parseInt(config.getPort()) : localPort;
					int rport = RemotePort == null ? Integer.parseInt(config.getPort()) : RemotePort;
					Session session = portForwardingSession.get(lport);
					if (session != null && session.isConnected()) {
						String s = session.getPortForwardingL()[0];
						String[] split = StringUtils.split(s, ":");
						boolean portForwarding = String.format("%s:%s", split[0], split[1]).equals(lport + ":" + config.getHost());
						if (portForwarding) {
							return;
						}
					}
					sshSession.connect();
					assinged_port.set(sshSession.setPortForwardingL(lport, config.getHost(), rport));
					portForwardingSession.put(lport, sshSession);
					portForwaring = true;
					_LOG.info("portForwarding Enabled, {}", assinged_port);
				} catch (JSchException e) {
					_LOG.error("Connect Over SSH failed", e);
					if (e.getCause() != null && e.getCause().getMessage().equals("Address already in use: JVM_Bind")) {
						throw new RuntimeException("Address already in use: JVM_Bind");
					}
					throw new RuntimeException(e.getMessage());
				}
			});
			try {
				result.get(5, TimeUnit.SECONDS);
			}catch (Exception e) {
				shutdownPortForwarding(sshSession);
				if (e.getCause() instanceof RuntimeException) {
					throw (RuntimeException)e.getCause();
				}
				if (e instanceof TimeoutException) {
					throw new RuntimeException("OverSSH 连接超时：超过5秒");
				}

				_LOG.info("executorService isShutdown:{}", executorService.isShutdown());
				throw new ServiceException(CoreExceptionEnum.SSH_CONNECTION_ERROR);
				//TODO
				//AlertUtil.showErrorAlert("OverSSH 失败，请检查连接设置:" + e.getMessage());
			}
		}
	}

	public static void shutdownPortForwarding(Session session) {
		portForwaring = false;
		if (session != null && session.isConnected()) {
			session.disconnect();
			_LOG.info("portForwarding turn OFF");
		}
//		executorService.shutdown();
	}

    public static Connection getConnection(DatabaseConfig config) throws ClassNotFoundException, SQLException {
		DbTypeEnum dbTypeEnum = DbTypeEnum.valueOf(config.getDbType());
		if (drivers.get(dbTypeEnum) == null){
			loadDbDriver(dbTypeEnum);
		}

		String url = getConnectionUrlWithSchema(config);
	    Properties props = new Properties();

	    props.setProperty("user", config.getUsername()); //$NON-NLS-1$
	    props.setProperty("password", config.getPassword()); //$NON-NLS-1$

		DriverManager.setLoginTimeout(DB_CONNECTION_TIMEOUTS_SECONDS);
	    Connection connection = drivers.get(dbTypeEnum).connect(url, props);
        _LOG.info("getConnection, connection url: {}", connection);
        return connection;
    }

    public static List<String> getTableNames(DatabaseConfig config) throws Exception {
		Session sshSession = getSSHSession(config);
		engagePortForwarding(sshSession, config);
		Connection connection = getConnection(config);
	    try {
		    List<String> tables = new ArrayList<>();
		    DatabaseMetaData md = connection.getMetaData();
		    ResultSet rs;
		    if (DbTypeEnum.valueOf(config.getDbType()) == DbTypeEnum.SQL_Server) {
			    String sql = "select name from sysobjects  where xtype='u' or xtype='v' order by name";
			    rs = connection.createStatement().executeQuery(sql);
			    while (rs.next()) {
				    tables.add(rs.getString("name"));
			    }
		    } else if (DbTypeEnum.valueOf(config.getDbType()) == DbTypeEnum.Oracle){
			    rs = md.getTables(null, config.getUsername().toUpperCase(), null, new String[] {"TABLE", "VIEW"});
		    } else if (DbTypeEnum.valueOf(config.getDbType())== DbTypeEnum.Sqlite){
		    	String sql = "Select name from sqlite_master;";
			    rs = connection.createStatement().executeQuery(sql);
			    while (rs.next()) {
				    tables.add(rs.getString("name"));
			    }
		    } 
		    else {
			    // rs = md.getTables(null, config.getUsername().toUpperCase(), null, null);


				rs = md.getTables(config.getSchema(), null, "%", new String[] {"TABLE", "VIEW"});			//针对 postgresql 的左侧数据表显示
		    }
		    while (rs.next()) {
			    tables.add(rs.getString(3));
		    }

		    if (tables.size()>1) {
		    	Collections.sort(tables);
			}
		    return tables;
	    } finally {
	    	connection.close();
			shutdownPortForwarding(sshSession);
	    }
	}

    public static String getConnectionUrlWithSchema(DatabaseConfig dbConfig) throws ClassNotFoundException {
		DbTypeEnum dbTypeEnum = DbTypeEnum.valueOf(dbConfig.getDbType());
		String connectionUrl = String.format(dbTypeEnum.getConnectionUrlPattern(),
				portForwaring ? "127.0.0.1" : dbConfig.getHost(), portForwaring ? dbConfig.getLport() : dbConfig.getPort(), dbConfig.getSchema(), dbConfig.getEncoding());
        _LOG.info("getConnectionUrlWithSchema, connection url: {}", connectionUrl);
        return connectionUrl;
    }

	/**
	 * 加载数据库驱动
	 * @param dbTypeEnum 数据库类型
	 */
	private static void loadDbDriver(DbTypeEnum dbTypeEnum){
		List<String> driverJars = SqliteHelper.getAllJDBCDriverJarPaths();
		ClassLoader classloader = ClassloaderUtility.getCustomClassloader(driverJars);
		try {
			Class clazz = Class.forName(dbTypeEnum.getDriverClass(), true, classloader);
			Driver driver = (Driver) clazz.newInstance();
			_LOG.info("load driver class: {}", driver);
			drivers.put(dbTypeEnum, driver);
		} catch (Exception e) {
			_LOG.error("load driver error", e);
			throw new DbDriverLoadingException("找不到"+ dbTypeEnum.getConnectorJarFile()+"驱动");
		}
	}

	public void dataSourceConfig(String dbType,String username,String password) {

		DataSourceConfig dataSourceConfig = new DataSourceConfig();

		DbType typeEnum = DbType.valueOf(dbType);
		switch (typeEnum){

			case MYSQL:
				dataSourceConfig.setDbType(DbType.MYSQL);
				dataSourceConfig.setDriverName(DbTypeEnum.MySQL.getDriverClass());
				dataSourceConfig.setUsername(username);
				dataSourceConfig.setPassword(password);
				dataSourceConfig.setUrl(DbTypeEnum.MySQL.getConnectionUrlPattern());
				break;

			case ORACLE:
				break;
		}

	}

}
