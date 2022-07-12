package l2j;

import l2j.network.LoginClient;
import l2j.network.gameserverpackets.ServerStatus;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * @author : Alice
 * @date : 23.09.2021
 * @time : 14:20
 */
public class GameServerInfo {
    // auth
    @Getter @Setter
    private int id;
    @Getter
    private final byte[] hexId;
    @Getter @Setter
    private boolean isAuthed;
    // status
    @Getter @Setter
    private GameServerThread gameServerThread;
    @Getter
    private int status;
    // network
    private final ArrayList<GameServerAddress> _addrs = new ArrayList<>(5);
    @Getter @Setter
    private int port;
    // config
    private static final boolean IS_PVP = true;
    @Getter @Setter
    private int serverType;
    @Getter @Setter
    private int ageLimit;
    @Getter @Setter
    private boolean isShowingBrackets;
    @Getter @Setter
    private int maxPlayers;

    /**
     * Instantiates a new game server info.
     *
     * @param id    the id
     * @param hexId the hex id
     * @param gst   the gst
     */
    public GameServerInfo(int id, byte[] hexId, GameServerThread gst) {
        this.id = id;
        this.hexId = hexId;
        gameServerThread = gst;
        status = ServerStatus.STATUS_DOWN;
    }

    /**
     * Instantiates a new game server info.
     *
     * @param id    the id
     * @param hexId the hex id
     */
    public GameServerInfo(int id, byte[] hexId) {
        this(id, hexId, null);
    }

    public String getName() {
        // this value can't be stored in a private variable because the ID can be changed by setId()
        return GameServerTable.getInstance().getServerNameById(id);
    }

    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(int status) {
        if (LoginServer.getInstance().getStatus() == ServerStatus.STATUS_DOWN) {
            this.status = ServerStatus.STATUS_DOWN;
        } else if (LoginServer.getInstance().getStatus() == ServerStatus.STATUS_GM_ONLY) {
            this.status = ServerStatus.STATUS_GM_ONLY;
        } else {
            this.status = status;
        }
    }

    public String getStatusName() {
        switch (status) {
            case 0: {
                return "Auto";
            }
            case 1: {
                return "Good";
            }
            case 2: {
                return "Normal";
            }
            case 3: {
                return "Full";
            }
            case 4: {
                return "Down";
            }
            case 5: {
                return "GM Only";
            }
            default: {
                return "Unknown";
            }
        }
    }

    /**
     * Gets the current player count.
     *
     * @return the current player count
     */
    public int getCurrentPlayerCount() {
        if (gameServerThread == null) {
            return 0;
        }
        return gameServerThread.getPlayerCount();
    }

    public boolean canLogin(LoginClient client) {
        // DOWN status doesn't allow anyone to login.
        if (status == ServerStatus.STATUS_DOWN) {
            return false;
        }

        // GM_ONLY status or full server only allows superior access levels accounts to login.
        if ((status == ServerStatus.STATUS_GM_ONLY) || (getCurrentPlayerCount() >= getMaxPlayers())) {
            return client.getAccessLevel() > 0;
        }

        // Otherwise, any positive access level account can login.
        return client.getAccessLevel() >= 0;
    }

    /**
     * Gets the external host.
     *
     * @return the external host
     */
    public String getExternalHost() {
        try {
            return getServerAddress(InetAddress.getByName("0.0.0.0"));
        } catch (Exception e) {
            // Ignore.
        }
        return null;
    }

    /**
     * Checks if is pvp.
     *
     * @return true, if is pvp
     */
    public boolean isPvp() {
        return IS_PVP;
    }

    /**
     * Sets the down.
     */
    public void setDown() {
        setAuthed(false);
        setPort(0);
        setGameServerThread(null);
        setStatus(ServerStatus.STATUS_DOWN);
    }

    /**
     * Adds the server address.
     *
     * @param subnet the subnet
     * @param addr   the addr
     * @throws UnknownHostException the unknown host exception
     */
    public void addServerAddress(String subnet, String addr) throws UnknownHostException {
        _addrs.add(new GameServerAddress(subnet, addr));
    }

    /**
     * Gets the server address.
     *
     * @param addr the addr
     * @return the server address
     */
    @SuppressWarnings("unlikely-arg-type")
    public String getServerAddress(InetAddress addr) {
        for (GameServerAddress a : _addrs) {
            if (a.equals(addr)) {
                return a.getServerAddress();
            }
        }
        return null; // should not happen
    }

    /**
     * Gets the server addresses.
     *
     * @return the server addresses
     */
    public String[] getServerAddresses() {
        final String[] result = new String[_addrs.size()];
        for (int i = 0; i < result.length; i++) {
            result[i] = _addrs.get(i).toString();
        }

        return result;
    }

    /**
     * Clear server addresses.
     */
    public void clearServerAddresses() {
        _addrs.clear();
    }
}
