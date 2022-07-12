package org.l2jmobius.gameserver.network;

import network.ICrypt;
import org.l2jmobius.gameserver.model.events.EventDispatcher;
import org.l2jmobius.gameserver.model.events.impl.server.OnPacketReceived;
import org.l2jmobius.gameserver.model.events.impl.server.OnPacketSent;

import io.netty.buffer.ByteBuf;

/**
 * @author UnAfraid, Nos
 */
public class Crypt implements ICrypt
{
	private final GameClient _client;
	private final byte[] _inKey = new byte[16];
	private final byte[] _outKey = new byte[16];
	private boolean _isEnabled;
	
	public Crypt(GameClient client)
	{
		_client = client;
	}
	
	public void setKey(byte[] key)
	{
		System.arraycopy(key, 0, _inKey, 0, 16);
		System.arraycopy(key, 0, _outKey, 0, 16);
	}
	
	@Override
	public void encrypt(ByteBuf buf)
	{
		if (!_isEnabled)
		{
			_isEnabled = true;
			onPacketSent(buf);
			return;
		}
		
		onPacketSent(buf);
		
		int a = 0;
		while (buf.isReadable())
		{
			final int b = buf.readByte() & 0xFF;
			a = b ^ _outKey[(buf.readerIndex() - 1) & 15] ^ a;
			buf.setByte(buf.readerIndex() - 1, a);
		}
		
		shiftKey(_outKey, buf.writerIndex());
	}
	
	@Override
	public void decrypt(ByteBuf buf)
	{
		if (!_isEnabled)
		{
			onPacketReceive(buf);
			return;
		}
		
		int a = 0;
		while (buf.isReadable())
		{
			final int b = buf.readByte() & 0xFF;
			buf.setByte(buf.readerIndex() - 1, b ^ _inKey[(buf.readerIndex() - 1) & 15] ^ a);
			a = b;
		}
		
		shiftKey(_inKey, buf.writerIndex());
		
		onPacketReceive(buf);
	}
	
	private void onPacketSent(ByteBuf buf)
	{
		final byte[] data = new byte[buf.writerIndex()];
		buf.getBytes(0, data);
		EventDispatcher.getInstance().notifyEvent(new OnPacketSent(_client, data));
	}
	
	private void onPacketReceive(ByteBuf buf)
	{
		final byte[] data = new byte[buf.writerIndex()];
		buf.getBytes(0, data);
		EventDispatcher.getInstance().notifyEvent(new OnPacketReceived(_client, data));
	}
	
	private void shiftKey(byte[] key, int size)
	{
		int old = key[8] & 0xff;
		old |= (key[9] << 8) & 0xff00;
		old |= (key[10] << 0x10) & 0xff0000;
		old |= (key[11] << 0x18) & 0xff000000;
		
		old += size;
		
		key[8] = (byte) (old & 0xff);
		key[9] = (byte) ((old >> 0x08) & 0xff);
		key[10] = (byte) ((old >> 0x10) & 0xff);
		key[11] = (byte) ((old >> 0x18) & 0xff);
	}
}
