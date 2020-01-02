package cn.coderoom.generator.base.db.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

/** 
 * 
 * @class ConnectionManager
 * @package cn.coderoom.generator.gui.util
 * @author lim
 * @email coderoom.cn@gmail.com
 * @date 2019/12/18 14:22
*/ 
public class ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
    private static final String DB_URL = "jdbc:sqlite:config/sqlite3.db";

    public static Connection getConnection() throws Exception {
        Class.forName("org.sqlite.JDBC");
        File file = new File(DB_URL.substring("jdbc:sqlite:".length())).getAbsoluteFile();
        logger.info("database FilePath :{}", file.getAbsolutePath());
        Connection conn = DriverManager.getConnection(DB_URL);
        return conn;
    }
}
