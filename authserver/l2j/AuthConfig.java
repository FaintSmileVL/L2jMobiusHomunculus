package l2j;

import lombok.extern.slf4j.Slf4j;
import util.PropertiesParser;

import java.io.File;
import java.io.IOException;

/**
 * @author : Alice
 * @date : 23.09.2021
 * @time : 15:01
 */
@Slf4j
public class AuthConfig {
    private static final String LOGIN_CONFIG_FILE = "./config/LoginServer.ini";

    public static boolean SHOW_LICENCE;
    public static boolean SHOW_PI_AGREEMENT;
    public static boolean ACCEPT_NEW_GAMESERVER;
    public static boolean AUTO_CREATE_ACCOUNTS;
    public static boolean FLOOD_PROTECTION;
    public static int FAST_CONNECTION_LIMIT;
    public static int NORMAL_CONNECTION_TIME;
    public static int FAST_CONNECTION_TIME;
    public static int MAX_CONNECTION_PER_IP;
    public static boolean ENABLE_CMD_LINE_LOGIN;
    public static boolean ONLY_CMD_LINE_LOGIN;

    public static int GAME_SERVER_LOGIN_PORT;
    public static String GAME_SERVER_LOGIN_HOST;
    public static int PORT_LOGIN;
    public static String LOGIN_BIND_ADDRESS;
    public static int LOGIN_TRY_BEFORE_BAN;
    public static int LOGIN_BLOCK_AFTER_BAN;
    public static File DATAPACK_ROOT;
    public static boolean LOGIN_SERVER_SCHEDULE_RESTART;
    public static long LOGIN_SERVER_SCHEDULE_RESTART_TIME;
    public static String DATABASE_DRIVER;
    public static String DATABASE_URL;
    public static String DATABASE_LOGIN;
    public static String DATABASE_PASSWORD;
    public static int DATABASE_MAX_CONNECTIONS;
    public static boolean BACKUP_DATABASE;
    public static String MYSQL_BIN_PATH;
    public static String BACKUP_PATH;
    public static int BACKUP_DAYS;
    public static int IO_PACKET_THREAD_CORE_SIZE;

    public static void load() {
        final PropertiesParser ServerSettings = new PropertiesParser(LOGIN_CONFIG_FILE);
        GAME_SERVER_LOGIN_HOST = ServerSettings.getString("LoginHostname", "127.0.0.1");
        GAME_SERVER_LOGIN_PORT = ServerSettings.getInt("LoginPort", 9013);
        LOGIN_BIND_ADDRESS = ServerSettings.getString("LoginserverHostname", "0.0.0.0");
        PORT_LOGIN = ServerSettings.getInt("LoginserverPort", 2106);
        try {
            DATAPACK_ROOT = new File(ServerSettings.getString("DatapackRoot", ".").replaceAll("\\\\", "/")).getCanonicalFile();
        } catch (IOException e) {
            log.warn("Error setting datapack root!", e);
            DATAPACK_ROOT = new File(".");
        }
        ACCEPT_NEW_GAMESERVER = ServerSettings.getBoolean("AcceptNewGameServer", true);
        LOGIN_TRY_BEFORE_BAN = ServerSettings.getInt("LoginTryBeforeBan", 5);
        LOGIN_BLOCK_AFTER_BAN = ServerSettings.getInt("LoginBlockAfterBan", 900);
        LOGIN_SERVER_SCHEDULE_RESTART = ServerSettings.getBoolean("LoginRestartSchedule", false);
        LOGIN_SERVER_SCHEDULE_RESTART_TIME = ServerSettings.getLong("LoginRestartTime", 24);
        DATABASE_DRIVER = ServerSettings.getString("Driver", "org.mariadb.jdbc.Driver");
        DATABASE_URL = ServerSettings.getString("URL", "jdbc:mariadb://localhost/l2jls");
        DATABASE_LOGIN = ServerSettings.getString("Login", "root");
        DATABASE_PASSWORD = ServerSettings.getString("Password", "");
        DATABASE_MAX_CONNECTIONS = ServerSettings.getInt("MaximumDbConnections", 10);
        BACKUP_DATABASE = ServerSettings.getBoolean("BackupDatabase", false);
        MYSQL_BIN_PATH = ServerSettings.getString("MySqlBinLocation", "C:/xampp/mysql/bin/");
        BACKUP_PATH = ServerSettings.getString("BackupPath", "../backup/");
        BACKUP_DAYS = ServerSettings.getInt("BackupDays", 30);
        SHOW_LICENCE = ServerSettings.getBoolean("ShowLicence", true);
        SHOW_PI_AGREEMENT = ServerSettings.getBoolean("ShowPIAgreement", false);
        AUTO_CREATE_ACCOUNTS = ServerSettings.getBoolean("AutoCreateAccounts", true);
        FLOOD_PROTECTION = ServerSettings.getBoolean("EnableFloodProtection", true);
        FAST_CONNECTION_LIMIT = ServerSettings.getInt("FastConnectionLimit", 15);
        NORMAL_CONNECTION_TIME = ServerSettings.getInt("NormalConnectionTime", 700);
        FAST_CONNECTION_TIME = ServerSettings.getInt("FastConnectionTime", 350);
        MAX_CONNECTION_PER_IP = ServerSettings.getInt("MaxConnectionPerIP", 50);
        ENABLE_CMD_LINE_LOGIN = ServerSettings.getBoolean("EnableCmdLineLogin", false);
        ONLY_CMD_LINE_LOGIN = ServerSettings.getBoolean("OnlyCmdLineLogin", false);
        IO_PACKET_THREAD_CORE_SIZE = ServerSettings.getInt("UrgentPacketThreadCoreSize", 20);
    }
}
