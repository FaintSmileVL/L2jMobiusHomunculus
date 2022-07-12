package org.l2jmobius.gameserver.data;

import lombok.extern.slf4j.Slf4j;
import org.l2jmobius.Config;
import org.mariadb.jdbc.MariaDbPoolDataSource;

import java.sql.Connection;

/**
 * @author : Alice
 * @date : 23.09.2021
 * @time : 15:31
 */
@Slf4j
public class DatabaseFactory {
    private static final MariaDbPoolDataSource DATABASE_POOL = new MariaDbPoolDataSource(Config.DATABASE_URL + "&user=" + Config.DATABASE_LOGIN + "&password=" + Config.DATABASE_PASSWORD + "&maxPoolSize=" + Config.DATABASE_MAX_CONNECTIONS);

    public static void init() {
        // Test if connection is valid.
        try {
            DATABASE_POOL.getConnection().close();
            log.info("Database: Initialized.");
        } catch (Exception e) {
            log.info("Database: Problem on initialize. " + e);
        }
    }

    public static Connection getConnection() {
        Connection con = null;
        while (con == null) {
            try {
                con = DATABASE_POOL.getConnection();
            } catch (Exception e) {
                log.error("DatabaseFactory: Cound not get a connection. ", e);
            }
        }
        return con;
    }

    public static void close() {
        try {
            DATABASE_POOL.close();
        } catch (Exception e) {
            log.error("DatabaseFactory: There was a problem closing the data source. ", e);
        }
    }
}
