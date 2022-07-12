package org.l2jmobius.gameserver.network.clientpackets.homunculus;

import network.PacketReader;
import util.Chronos;
import org.l2jmobius.gameserver.model.actor.instance.PlayerInstance;
import org.l2jmobius.gameserver.model.variables.PlayerVariables;
import org.l2jmobius.gameserver.network.GameClient;
import org.l2jmobius.gameserver.network.clientpackets.IClientIncomingPacket;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExHomonculusBirthInfo;
import org.l2jmobius.gameserver.network.serverpackets.homunculus.ExHomunculusInsertHpSpVp;

/**
 * @author Mobius
 */
public class ExHomunculusInsert implements IClientIncomingPacket
{
	private int _type;
	
	@Override
	public boolean read(GameClient client, PacketReader packet)
	{
		_type = packet.readD();
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
		
		final int time = player.getVariables().getInt(PlayerVariables.HOMUNCULUS_TIME, 0);
		if (((Chronos.currentTimeMillis() / 1000) - time) < 86400)
		{
			player.sendMessage("Waiting time has not passed.");
			return;
		}
		
		switch (_type)
		{
			case 0: // hp
			{
				if (player.getCurrentHp() >= 10000)
				{
					player.setCurrentHp(player.getCurrentHp() - 10000);
					final int hp = player.getVariables().getInt(PlayerVariables.HOMUNCULUS_HP, 0);
					if (hp < 99)
					{
						player.getVariables().set(PlayerVariables.HOMUNCULUS_HP, hp + 1);
					}
					else
					{
						player.getVariables().set(PlayerVariables.HOMUNCULUS_HP, 100);
						
						final int status = player.getVariables().getInt(PlayerVariables.HOMUNCULUS_STATUS, 0);
						if (status == 1)
						{
							player.getVariables().set(PlayerVariables.HOMUNCULUS_STATUS, 2);
						}
					}
					player.getVariables().set(PlayerVariables.HOMUNCULUS_TIME, Chronos.currentTimeMillis() / 1000);
				}
				else
				{
					return;
				}
				break;
			}
			case 1: // sp
			{
				if (player.getSp() >= 5000000000L)
				{
					player.setSp(player.getSp() - 5000000000L);
					final int sp = player.getVariables().getInt(PlayerVariables.HOMUNCULUS_SP, 0);
					if (sp < 9)
					{
						player.getVariables().set(PlayerVariables.HOMUNCULUS_SP, sp + 1);
					}
					else
					{
						player.getVariables().set(PlayerVariables.HOMUNCULUS_SP, 10);
						
						final int status = player.getVariables().getInt(PlayerVariables.HOMUNCULUS_STATUS, 0);
						if (status == 1)
						{
							player.getVariables().set(PlayerVariables.HOMUNCULUS_STATUS, 2);
						}
					}
					player.getVariables().set(PlayerVariables.HOMUNCULUS_TIME, Chronos.currentTimeMillis() / 1000);
				}
				else
				{
					return;
				}
				break;
			}
			case 2: // vp
			{
				if (player.getVitalityPoints() >= 35000)
				{
					player.setVitalityPoints(player.getVitalityPoints() - 35000, true);
					final int vp = player.getVariables().getInt(PlayerVariables.HOMUNCULUS_VP, 0);
					if (vp < 4)
					{
						player.getVariables().set(PlayerVariables.HOMUNCULUS_VP, vp + 1);
					}
					else
					{
						player.getVariables().set(PlayerVariables.HOMUNCULUS_VP, 5);
						
						final int status = player.getVariables().getInt(PlayerVariables.HOMUNCULUS_STATUS, 0);
						if (status == 1)
						{
							player.getVariables().set(PlayerVariables.HOMUNCULUS_STATUS, 2);
						}
					}
					player.getVariables().set(PlayerVariables.HOMUNCULUS_TIME, Chronos.currentTimeMillis() / 1000);
				}
				else
				{
					return;
				}
				break;
			}
		}
		
		player.sendPacket(new ExHomunculusInsertHpSpVp(player));
		player.sendPacket(new ExHomonculusBirthInfo(player));
	}
}
