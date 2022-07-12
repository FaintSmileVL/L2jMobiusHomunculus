package org.l2jmobius.gameserver.network.serverpackets;

import network.PacketWriter;
import org.l2jmobius.gameserver.model.sevensigns.SevenSigns;
import org.l2jmobius.gameserver.network.OutgoingPackets;

/**
 * @author : Alice
 * @date : 28.09.2021
 * @time : 14:23
 */
public class SSQInfo implements IClientOutgoingPacket
{
    private int _state = 0;

    public SSQInfo()
    {
        final int compWinner = SevenSigns.getInstance().getCabalHighestScore();
        if (SevenSigns.getInstance().isSealValidationPeriod())
        {
            if (compWinner == SevenSigns.CABAL_DAWN)
            {
                _state = 2;
            }
            else if (compWinner == SevenSigns.CABAL_DUSK)
            {
                _state = 1;
            }
        }
    }

    public SSQInfo(int state)
    {
        _state = state;
    }

    @Override
    public boolean write(PacketWriter packet)
    {
        OutgoingPackets.SSQ_INFO.writeId(packet);
        packet.writeH(256 + _state);
        return true;
    }
}
