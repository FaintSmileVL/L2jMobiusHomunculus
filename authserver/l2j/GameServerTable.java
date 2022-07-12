package l2j;

import l2j.data.DatabaseFactory;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import util.Rnd;

import java.io.File;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.spec.RSAKeyGenParameterSpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author : Alice
 * @date : 23.09.2021
 * @time : 13:57
 */

@Slf4j
public class GameServerTable {
    @Getter(lazy = true)
    private static final GameServerTable instance = new GameServerTable();

    public static final Map<Integer, String> SERVER_NAMES = new HashMap<Integer, String>();
    // Game Server Table
    private static final Map<Integer, GameServerInfo> GAME_SERVER_TABLE = new HashMap<>();
    // RSA Config
    private static final int KEYS_SIZE = 10;
    private KeyPair[] _keyPairs;
    public static final String SERVER_NAMES_FILE = "config/servername.xml";

    private GameServerTable() {
        SERVER_NAMES.clear();

        try {
            SAXReader reader = new SAXReader(true);
            Document document = reader.read(new File(SERVER_NAMES_FILE));

            Element root = document.getRootElement();

            for (@SuppressWarnings("rawtypes")
                 Iterator itr = root.elementIterator(); itr.hasNext(); ) {
                Element node = (Element) itr.next();
                if (node.getName().equalsIgnoreCase("server")) {
                    Integer id = Integer.valueOf(node.attributeValue("id"));
                    String name = node.attributeValue("name");
                    SERVER_NAMES.put(id, name);
                }
            }
            log.info("Loaded " + SERVER_NAMES.size() + " server names");
        } catch (Exception e) {
            log.error("", e);
        }

        loadRegisteredGameServers();
        log.info("Loaded " + GAME_SERVER_TABLE.size() + " registered Game Servers.");

        initRSAKeys();
        log.info("Cached " + _keyPairs.length + " RSA keys for Game Server communication.");
    }

    /**
     * Inits the RSA keys.
     */
    private void initRSAKeys() {
        try {
            final KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
            keyGen.initialize(new RSAKeyGenParameterSpec(512, RSAKeyGenParameterSpec.F4));
            _keyPairs = new KeyPair[KEYS_SIZE];
            for (int i = 0; i < KEYS_SIZE; i++) {
                _keyPairs[i] = keyGen.genKeyPair();
            }
        } catch (Exception e) {
            log.error("Error loading RSA keys for Game Server communication!");
        }
    }

    /**
     * Load registered game servers.
     */
    private void loadRegisteredGameServers() {
        try (Connection con = DatabaseFactory.getConnection();
             Statement ps = con.createStatement();
             ResultSet rs = ps.executeQuery("SELECT * FROM gameservers")) {
            int id;
            while (rs.next()) {
                id = rs.getInt("server_id");
                GAME_SERVER_TABLE.put(id, new GameServerInfo(id, stringToHex(rs.getString("hexid"))));
            }
        } catch (Exception e) {
            log.error("Error loading registered game servers!");
        }
    }

    /**
     * Gets the registered game servers.
     *
     * @return the registered game servers
     */
    public Map<Integer, GameServerInfo> getRegisteredGameServers() {
        return GAME_SERVER_TABLE;
    }

    /**
     * Gets the registered game server by id.
     *
     * @param id the game server Id
     * @return the registered game server by id
     */
    public GameServerInfo getRegisteredGameServerById(int id) {
        return GAME_SERVER_TABLE.get(id);
    }

    /**
     * Checks for registered game server on id.
     *
     * @param id the id
     * @return true, if successful
     */
    public boolean hasRegisteredGameServerOnId(int id) {
        return GAME_SERVER_TABLE.containsKey(id);
    }

    /**
     * Register with first available id.
     *
     * @param gsi the game server information DTO
     * @return true, if successful
     */
    public boolean registerWithFirstAvailableId(GameServerInfo gsi) {
        // avoid two servers registering with the same "free" id
        synchronized (GAME_SERVER_TABLE) {
            for (Integer serverId : SERVER_NAMES.keySet()) {
                if (!GAME_SERVER_TABLE.containsKey(serverId)) {
                    GAME_SERVER_TABLE.put(serverId, gsi);
                    gsi.setId(serverId);
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Register a game server.
     *
     * @param id  the id
     * @param gsi the gsi
     * @return true, if successful
     */
    public boolean register(int id, GameServerInfo gsi) {
        // avoid two servers registering with the same id
        synchronized (GAME_SERVER_TABLE) {
            if (!GAME_SERVER_TABLE.containsKey(id)) {
                GAME_SERVER_TABLE.put(id, gsi);
                return true;
            }
        }
        return false;
    }

    /**
     * Wrapper method.
     *
     * @param gsi the game server info DTO.
     */
    public void registerServerOnDB(GameServerInfo gsi) {
        registerServerOnDB(gsi.getHexId(), gsi.getId(), gsi.getExternalHost());
    }

    /**
     * Register server on db.
     *
     * @param hexId        the hex id
     * @param id           the id
     * @param externalHost the external host
     */
    public void registerServerOnDB(byte[] hexId, int id, String externalHost) {
        register(id, new GameServerInfo(id, hexId));
        try (Connection con = DatabaseFactory.getConnection();
             PreparedStatement ps = con.prepareStatement("INSERT INTO gameservers (hexid,server_id,host) values (?,?,?)")) {
            ps.setString(1, hexToString(hexId));
            ps.setInt(2, id);
            ps.setString(3, externalHost);
            ps.executeUpdate();
        } catch (Exception e) {
            log.error("Error while saving gameserver!");
        }
    }

    /**
     * Gets the server name by id.
     *
     * @param id the id
     * @return the server name by id
     */
    public String getServerNameById(int id) {
        return SERVER_NAMES.get(id);
    }

    /**
     * Gets the server names.
     *
     * @return the game server names map.
     */
    public Map<Integer, String> getServerNames() {
        return SERVER_NAMES;
    }

    /**
     * Gets the key pair.
     *
     * @return a random key pair.
     */
    public KeyPair getKeyPair() {
        return _keyPairs[Rnd.get(10)];
    }

    /**
     * String to hex.
     *
     * @param string the string to convert.
     * @return return the hex representation.
     */
    private byte[] stringToHex(String string) {
        return new BigInteger(string, 16).toByteArray();
    }

    /**
     * Hex to string.
     *
     * @param hex the hex value to convert.
     * @return the string representation.
     */
    private String hexToString(byte[] hex) {
        if (hex == null) {
            return "null";
        }
        return new BigInteger(hex).toString(16);
    }
}
