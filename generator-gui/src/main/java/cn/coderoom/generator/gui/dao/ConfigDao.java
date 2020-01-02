package cn.coderoom.generator.gui.dao;

import cn.coderoom.generator.base.config.GeneratorConfig;
import cn.coderoom.generator.base.db.entity.DatabaseConfig;
import cn.coderoom.generator.base.db.utils.ConnectionManager;
import cn.coderoom.generator.base.db.utils.DbUtil;
import cn.coderoom.generator.base.db.utils.SqliteHelper;
import cn.coderoom.generator.gui.model.UIAnnotationTableColumnVo;
import cn.coderoom.generator.gui.model.UITableColumnVO;
import com.alibaba.fastjson.JSON;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * @package：cn.coderoom.generator.gui.dao
 * @description:
 * @author: Leesire
 * @email:coderoom.cn@gmail.com
 * @createtime: 2019/12/18
 */
public class ConfigDao {

    private static final Logger logger = LoggerFactory.getLogger(ConfigDao.class);
    private static volatile boolean portForwaring = false;

    public static void saveGeneratorConfig(GeneratorConfig generatorConfig) throws Exception {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stat = conn.createStatement();
            String jsonStr = JSON.toJSONString(generatorConfig);
            String sql = String.format("INSERT INTO generator_config values('%s', '%s')", generatorConfig.getName(),
                    jsonStr);
            SqliteHelper.executeUpdateSql(sql);
        } finally {
            if (rs != null) rs.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        }
    }

    public static GeneratorConfig loadGeneratorConfig(String name) throws Exception {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stat = conn.createStatement();
            String sql = String.format("SELECT * FROM generator_config where name='%s'", name);
            logger.info("sql: {}", sql);
            String result = SqliteHelper.executeQuerySql(sql);
            GeneratorConfig generatorConfig = null;
            generatorConfig = JSON.parseObject(result, GeneratorConfig.class);
            return generatorConfig;
        } finally {
            if (rs != null) rs.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        }
    }

    public static List<GeneratorConfig> loadGeneratorConfigs() throws Exception {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = ConnectionManager.getConnection();
            stat = conn.createStatement();
            String sql = String.format("SELECT * FROM generator_config");
            logger.info("sql: {}", sql);
            List<String> results = SqliteHelper.executeQueryListSql(sql);
            List<GeneratorConfig> configs = new ArrayList<>();
            for (String result:
                    results) {

                configs.add(JSON.parseObject(result, GeneratorConfig.class));
            }
            return configs;
        } finally {
            if (rs != null) rs.close();
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        }
    }

    public static int deleteGeneratorConfig(String name) throws Exception {
        Connection conn = null;
        Statement stat = null;
        try {
            conn = ConnectionManager.getConnection();
            stat = conn.createStatement();
            String sql = String.format("DELETE FROM generator_config where name='%s'", name);
            logger.info("sql: {}", sql);
            return SqliteHelper.executeUpdateSql(sql);
        } finally {
            if (stat != null) stat.close();
            if (conn != null) conn.close();
        }
    }

    public static List<UIAnnotationTableColumnVo> getAnnotationTableColumns(DatabaseConfig dbConfig, String tableName) throws Exception {
        String url = DbUtil.getConnectionUrlWithSchema(dbConfig);
        logger.info("getTableColumns, connection url: {}", url);
        Session sshSession = DbUtil.getSSHSession(dbConfig);
        DbUtil.engagePortForwarding(sshSession, dbConfig);
        Connection conn = DbUtil.getConnection(dbConfig);
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(dbConfig.getSchema(), null, tableName, null);
            List<UIAnnotationTableColumnVo> columns = new ArrayList<>();
            while (rs.next()) {
                UIAnnotationTableColumnVo columnVO = new UIAnnotationTableColumnVo();
                String columnName = rs.getString("COLUMN_NAME");
                columnVO.setColumnName(columnName);
                columns.add(columnVO);
            }
            return columns;
        } finally {
            conn.close();
            DbUtil.shutdownPortForwarding(sshSession);
        }
    }

    public static List<UITableColumnVO> getTableColumns(DatabaseConfig dbConfig, String tableName) throws Exception {
        String url = DbUtil.getConnectionUrlWithSchema(dbConfig);
        logger.info("getTableColumns, connection url: {}", url);
        Session sshSession = DbUtil.getSSHSession(dbConfig);
        DbUtil.engagePortForwarding(sshSession, dbConfig);
        Connection conn = DbUtil.getConnection(dbConfig);
        try {
            DatabaseMetaData md = conn.getMetaData();
            ResultSet rs = md.getColumns(dbConfig.getSchema(), null, tableName, null);
            List<UITableColumnVO> columns = new ArrayList<>();
            while (rs.next()) {
                UITableColumnVO columnVO = new UITableColumnVO();
                String columnName = rs.getString("COLUMN_NAME");
                columnVO.setColumnName(columnName);
                columnVO.setJdbcType(rs.getString("TYPE_NAME"));
                columns.add(columnVO);
            }
            return columns;
        } finally {
            conn.close();
            DbUtil.shutdownPortForwarding(sshSession);
        }
    }

}
