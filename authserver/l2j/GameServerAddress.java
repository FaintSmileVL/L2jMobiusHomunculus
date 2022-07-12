package l2j;

import util.IPSubnet;

import java.net.UnknownHostException;

/**
 * @author : Alice
 * @date : 23.09.2021
 * @time : 14:21
 */
public class GameServerAddress extends IPSubnet {
    private final String _serverAddress;

    /**
     * Instantiates a new game server address.
     *
     * @param subnet  the subnet
     * @param address the address
     * @throws UnknownHostException the unknown host exception
     */
    public GameServerAddress(String subnet, String address) throws UnknownHostException {
        super(subnet);
        _serverAddress = address;
    }

    /**
     * Gets the server address.
     *
     * @return the server address
     */
    public String getServerAddress() {
        return _serverAddress;
    }

    @Override
    public String toString() {
        return _serverAddress + super.toString();
    }
}