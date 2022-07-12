package org.l2jmobius.gameserver.model.actor.instance;

import org.l2jmobius.Config;
import org.l2jmobius.gameserver.enums.InstanceType;
import org.l2jmobius.gameserver.model.actor.Npc;
import org.l2jmobius.gameserver.model.actor.templates.NpcTemplate;
import org.l2jmobius.gameserver.model.sevensigns.SevenSigns;
import org.l2jmobius.gameserver.model.sevensigns.SevenSignsFestival;
import org.l2jmobius.gameserver.network.serverpackets.ActionFailed;
import org.l2jmobius.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author : Alice
 * @date : 27.09.2021
 * @time : 21:44
 */
public class FestivalGuideInstance  extends Npc
{
    private final int _festivalType;
    private final int _festivalOracle;
    private final int _blueStonesNeeded;
    private final int _greenStonesNeeded;
    private final int _redStonesNeeded;

    /**
     * Creates a festival guide.
     * @param template the festival guide NPC template
     */
    public FestivalGuideInstance(NpcTemplate template)
    {
        super(template);
        setInstanceType(InstanceType.FestivalGiudeInstance);

        switch (getId())
        {
            case 31127:
            case 31132:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_31;
                _festivalOracle = SevenSigns.CABAL_DAWN;
                _blueStonesNeeded = 900;
                _greenStonesNeeded = 540;
                _redStonesNeeded = 270;
                break;
            }
            case 31128:
            case 31133:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_42;
                _festivalOracle = SevenSigns.CABAL_DAWN;
                _blueStonesNeeded = 1500;
                _greenStonesNeeded = 900;
                _redStonesNeeded = 450;
                break;
            }
            case 31129:
            case 31134:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_53;
                _festivalOracle = SevenSigns.CABAL_DAWN;
                _blueStonesNeeded = 3000;
                _greenStonesNeeded = 1800;
                _redStonesNeeded = 900;
                break;
            }
            case 31130:
            case 31135:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_64;
                _festivalOracle = SevenSigns.CABAL_DAWN;
                _blueStonesNeeded = 4500;
                _greenStonesNeeded = 2700;
                _redStonesNeeded = 1350;
                break;
            }
            case 31131:
            case 31136:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_NONE;
                _festivalOracle = SevenSigns.CABAL_DAWN;
                _blueStonesNeeded = 6000;
                _greenStonesNeeded = 3600;
                _redStonesNeeded = 1800;
                break;
            }
            case 31137:
            case 31142:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_31;
                _festivalOracle = SevenSigns.CABAL_DUSK;
                _blueStonesNeeded = 900;
                _greenStonesNeeded = 540;
                _redStonesNeeded = 270;
                break;
            }
            case 31138:
            case 31143:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_42;
                _festivalOracle = SevenSigns.CABAL_DUSK;
                _blueStonesNeeded = 1500;
                _greenStonesNeeded = 900;
                _redStonesNeeded = 450;
                break;
            }
            case 31139:
            case 31144:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_53;
                _festivalOracle = SevenSigns.CABAL_DUSK;
                _blueStonesNeeded = 3000;
                _greenStonesNeeded = 1800;
                _redStonesNeeded = 900;
                break;
            }
            case 31140:
            case 31145:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_64;
                _festivalOracle = SevenSigns.CABAL_DUSK;
                _blueStonesNeeded = 4500;
                _greenStonesNeeded = 2700;
                _redStonesNeeded = 1350;
                break;
            }
            case 31141:
            case 31146:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_NONE;
                _festivalOracle = SevenSigns.CABAL_DUSK;
                _blueStonesNeeded = 6000;
                _greenStonesNeeded = 3600;
                _redStonesNeeded = 1800;
                break;
            }
            default:
            {
                _festivalType = SevenSignsFestival.FESTIVAL_LEVEL_MAX_NONE;
                _festivalOracle = SevenSigns.CABAL_NULL;
                _blueStonesNeeded = 0;
                _greenStonesNeeded = 0;
                _redStonesNeeded = 0;
            }
        }
    }

    public int getFestivalType()
    {
        return _festivalType;
    }

    public int getFestivalOracle()
    {
        return _festivalOracle;
    }

    public int getStoneCount(int stoneType)
    {
        switch (stoneType)
        {
            case SevenSigns.SEAL_STONE_BLUE_ID:
            {
                return _blueStonesNeeded;
            }
            case SevenSigns.SEAL_STONE_GREEN_ID:
            {
                return _greenStonesNeeded;
            }
            case SevenSigns.SEAL_STONE_RED_ID:
            {
                return _redStonesNeeded;
            }
            default:
            {
                return -1;
            }
        }
    }

    public void showChatWindow(PlayerInstance player, int value, String suffix, boolean isDescription)
    {
        String filename = SevenSigns.SEVEN_SIGNS_HTML_PATH + "festival/";
        filename += (isDescription) ? "desc_" : "festival_";
        filename += (suffix != null) ? value + suffix + ".htm" : value + ".htm";

        // Send a Server->Client NpcHtmlMessage containing the text of the NpcInstance to the PlayerInstance
        final NpcHtmlMessage html = new NpcHtmlMessage(getObjectId());
        html.setFile(player, filename);
        html.replace("%objectId%", String.valueOf(getObjectId()));
        html.replace("%festivalType%", SevenSignsFestival.getFestivalName(_festivalType));
        html.replace("%cycleMins%", String.valueOf(SevenSignsFestival.getInstance().getMinsToNextCycle()));
        if (!isDescription && "2b".equals(value + suffix))
        {
            html.replace("%minFestivalPartyMembers%", String.valueOf(Config.ALT_FESTIVAL_MIN_PLAYER));
        }

        // If the stats or bonus table is required, construct them.
        if (value == 5)
        {
            html.replace("%statsTable%", getStatsTable());
        }
        if (value == 6)
        {
            html.replace("%bonusTable%", getBonusTable());
        }

        // festival's fee
        if (value == 1)
        {
            html.replace("%blueStoneNeeded%", String.valueOf(_blueStonesNeeded));
            html.replace("%greenStoneNeeded%", String.valueOf(_greenStonesNeeded));
            html.replace("%redStoneNeeded%", String.valueOf(_redStonesNeeded));
        }

        player.sendPacket(html);

        // Send a Server->Client ActionFailed to the PlayerInstance in order to avoid that the client wait another packet
        player.sendPacket(ActionFailed.STATIC_PACKET);
    }

    private static String getStatsTable()
    {
        final StringBuilder tableHtml = new StringBuilder(1000);

        // Get the scores for each of the festival level ranges (types).
        for (int i = 0; i < 5; i++)
        {
            final int dawnScore = SevenSignsFestival.getInstance().getHighestScore(SevenSigns.CABAL_DAWN, i);
            final int duskScore = SevenSignsFestival.getInstance().getHighestScore(SevenSigns.CABAL_DUSK, i);
            final String festivalName = SevenSignsFestival.getFestivalName(i);
            String winningCabal = "Children of Dusk";
            if (dawnScore > duskScore)
            {
                winningCabal = "Children of Dawn";
            }
            else if (dawnScore == duskScore)
            {
                winningCabal = "None";
            }

            tableHtml.append("<tr><td width=\"100\" align=\"center\">" + festivalName + "</td><td align=\"center\" width=\"35\">" + duskScore + "</td><td align=\"center\" width=\"35\">" + dawnScore + "</td><td align=\"center\" width=\"130\">" + winningCabal + "</td></tr>");
        }

        return tableHtml.toString();
    }

    private static String getBonusTable()
    {
        final StringBuilder tableHtml = new StringBuilder(500);

        // Get the accumulated scores for each of the festival level ranges (types).
        for (int i = 0; i < 5; i++)
        {
            final int accumScore = SevenSignsFestival.getInstance().getAccumulatedBonus(i);
            final String festivalName = SevenSignsFestival.getFestivalName(i);
            tableHtml.append("<tr><td align=\"center\" width=\"150\">" + festivalName + "</td><td align=\"center\" width=\"150\">" + accumScore + "</td></tr>");
        }
        return tableHtml.toString();
    }
}