package org.l2jmobius.gameserver.network.clientpackets.autoplay;

import org.l2jmobius.Config;
import network.PacketReader;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.autoplay.ExAutoPlaySettingSend;
import org.l2jmobius.gameserver.taskmanager.AutoPlayTaskManager;

/**
 * @author Mobius
 */
public class ExAutoPlaySetting implements IClientIncomingPacket
{
	private int _options;
	private boolean _active;
	private boolean _pickUp;
	private int _nextTargetMode;
	private boolean _longRange;
	private int _potionPercent;
	private boolean _respectfulHunting;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_options = packet.readH();
		_active = packet.readC() == 1;
		_pickUp = packet.readC() == 1;
		_nextTargetMode = packet.readH();
		_longRange = packet.readC() != 0;
		_potionPercent = packet.readD();
		packet.readD(); // 272
		_respectfulHunting = packet.readC() == 1;
		return true;
	}
	
	@Override
	public void run(GameClient client)
	{
		final PlayerInstance player = client.getPlayer();
		if (player == null)
		{
			return;
		}
		
		player.sendPacket(new ExAutoPlaySettingSend(_options, _active, _pickUp, _nextTargetMode, _longRange, _potionPercent, _respectfulHunting));
		player.getAutoPlaySettings().setAutoPotionPercent(_potionPercent);
		
		if (!Config.ENABLE_AUTO_PLAY)
		{
			return;
		}
		
		player.getAutoPlaySettings().setPickup(_pickUp);
		player.getAutoPlaySettings().setLongRange(_longRange);
		player.getAutoPlaySettings().setRespectfulHunting(_respectfulHunting);
		
		if (_active)
		{
			AutoPlayTaskManager.getInstance().doAutoPlay(player);
		}
		else
		{
			AutoPlayTaskManager.getInstance().stopAutoPlay(player);
		}
	}
}
