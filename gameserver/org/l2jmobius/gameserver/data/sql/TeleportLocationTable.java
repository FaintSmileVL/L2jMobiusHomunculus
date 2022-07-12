package org.l2jmobius.gameserver.data.sql;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.data.DatabaseFactory;
import org.l2jmobius.gameserver.model.TeleportLocation;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : Alice
 * @date : 28.09.2021
 * @time : 14:13
 */
public class TeleportLocationTable {
    private static final Logger LOGGER = Logger.getLogger(TeleportLocationTable.class.getName());

    private final Map<Integer, TeleportLocation> _teleports = new HashMap<>();

    protected TeleportLocationTable() {
        reloadAll();
    }

    public void reloadAll() {
        _teleports.clear();
        try (Connection con = DatabaseFactory.getConnection();
             Statement s = con.createStatement();
             ResultSet rs = s.executeQuery("SELECT id, loc_x, loc_y, loc_z, price, fornoble, itemId FROM teleport")) {
            TeleportLocation teleport;
            while (rs.next()) {
                teleport = new TeleportLocation();
                teleport.setTeleId(rs.getInt("id"));
                teleport.setLocX(rs.getInt("loc_x"));
                teleport.setLocY(rs.getInt("loc_y"));
                teleport.setLocZ(rs.getInt("loc_z"));
                teleport.setPrice(rs.getInt("price"));
                teleport.setForNoble(rs.getInt("fornoble") == 1);
                teleport.setItemId(rs.getInt("itemId"));

                _teleports.put(teleport.getTeleId(), teleport);
            }
            LOGGER.info(getClass().getSimpleName() + ": Loaded " + _teleports.size() + " teleport location templates.");
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, getClass().getSimpleName() + ": Error loading Teleport Table.", e);
        }

        if (!Config.CUSTOM_TELEPORT_TABLE) {
            return;
        }

        int cTeleCount = _teleports.size();
        try (Connection con = DatabaseFactory.getConnection();
             Statement s = con.createStatement();
             ResultSet rs = s.executeQuery("SELECT id, loc_x, loc_y, loc_z, price, fornoble, itemId FROM custom_teleport")) {
            TeleportLocation teleport;
            while (rs.next()) {
                teleport = new TeleportLocation();
                teleport.setTeleId(rs.getInt("id"));
                teleport.setLocX(rs.getInt("loc_x"));
                teleport.setLocY(rs.getInt("loc_y"));
                teleport.setLocZ(rs.getInt("loc_z"));
                teleport.setPrice(rs.getInt("price"));
                teleport.setForNoble(rs.getInt("fornoble") == 1);
                teleport.setItemId(rs.getInt("itemId"));

                _teleports.put(teleport.getTeleId(), teleport);
            }
            cTeleCount = _teleports.size() - cTeleCount;
            if (cTeleCount > 0) {
                LOGGER.info(getClass().getSimpleName() + ": Loaded " + cTeleCount + " custom teleport location templates.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, getClass().getSimpleName() + ": Error while creating custom teleport table " + e.getMessage(), e);
        }
    }

    /**
     * @param id
     * @return
     */
    public TeleportLocation getTemplate(int id) {
        return _teleports.get(id);
    }

    public static TeleportLocationTable getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        protected static final TeleportLocationTable INSTANCE = new TeleportLocationTable();
    }
}
