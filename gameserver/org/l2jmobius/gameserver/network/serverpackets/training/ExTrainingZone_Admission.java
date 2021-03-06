package org.l2jmobius.gameserver.network.serverpackets.training;

import org.l2jmobius.Config;
import network.PacketWriter;
import org.l2jmobius.gameserver.data.xml.ExperienceData;
import org.l2jmobius.gameserver.network.OutgoingPackets;
import org.l2jmobius.gameserver.network.serverpackets.IClientOutgoingPacket;

/**
 * @author Sdw
 */
public class ExTrainingZone_Admission implements IClientOutgoingPacket
{
	private final long _timeElapsed;
	private final long _timeRemaining;
	private final double _maxExp;
	private final double _maxSp;
	
	public ExTrainingZone_Admission(int level, long timeElapsed, long timeRemaing)
	{
		_timeElapsed = timeElapsed;
		_timeRemaining = timeRemaing;
		_maxExp = Config.TRAINING_CAMP_EXP_MULTIPLIER * ((ExperienceData.getInstance().getExpForLevel(level) * ExperienceData.getInstance().getTrainingRate(level)) / Config.TRAINING_CAMP_MAX_DURATION);
		_maxSp = Config.TRAINING_CAMP_SP_MULTIPLIER * (_maxExp / 250d);
	}
	
	@Override
	public boolean write(PacketWriter packet)
	{
		OutgoingPackets.EX_TRAINING_ZONE_ADMISSION.writeId(packet);
		packet.writeD((int) _timeElapsed); // Training time elapsed in minutes, max : 600 - 10hr RU / 300 - 5hr NA
		packet.writeD((int) _timeRemaining); // Time remaining in seconds, max : 36000 - 10hr RU / 18000 - 5hr NA
		packet.writeF(_maxExp); // Training time elapsed in minutes * this value = acquired exp IN GAME DOESN'T SEEM LIKE THE FIELD IS LIMITED
		packet.writeF(_maxSp); // Training time elapsed in minutes * this value = acquired sp IN GAME LIMITED TO INTEGER.MAX_VALUE SO THE MULTIPLY WITH REMAINING TIME CANT EXCEED IT (so field max value can't exceed 3579139.0 for 10hr) !! // Note real sp gain is exp gained / 250
		return true;
	}
}
