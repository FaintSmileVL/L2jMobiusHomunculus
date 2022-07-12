package util.crypt;

import javax.crypto.SecretKey;

import network.ICrypt;
import util.Rnd;

import io.netty.buffer.ByteBuf;

/**
 * @author NosBit
 */
public class LoginCrypt implements ICrypt
{
	private static final byte[] STATIC_BLOWFISH_KEY =
	{
		(byte) 0x6b,
		(byte) 0x60,
		(byte) 0xcb,
		(byte) 0x5b,
		(byte) 0x82,
		(byte) 0xce,
		(byte) 0x90,
		(byte) 0xb1,
		(byte) 0xcc,
		(byte) 0x2b,
		(byte) 0x6c,
		(byte) 0x55,
		(byte) 0x6c,
		(byte) 0x6c,
		(byte) 0x6c,
		(byte) 0x6c
	};
	
	private static final BlowfishEngine STATIC_BLOWFISH_ENGINE = new BlowfishEngine();
	
	static
	{
		STATIC_BLOWFISH_ENGINE.init(STATIC_BLOWFISH_KEY);
	}
	
	private final BlowfishEngine _blowfishEngine = new BlowfishEngine();
	private boolean _static = true;
	
	public LoginCrypt(SecretKey blowfishKey)
	{
		_blowfishEngine.init(blowfishKey.getEncoded());
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.l2jserver.commons.network.ICrypt#encrypt(io.netty.buffer.ByteBuf)
	 */
	@Override
	public void encrypt(ByteBuf buf)
	{
		// Checksum & XOR Key or Checksum only
		buf.writeZero(_static ? 16 : 12);
		
		// Padding
		buf.writeZero(8 - (buf.readableBytes() % 8));
		
		if (_static)
		{
			_static = false;
			
			int key = Rnd.nextInt();
			buf.skipBytes(4); // The first 4 bytes are ignored
			while (buf.readerIndex() < (buf.writerIndex() - 8))
			{
				int data = buf.readIntLE();
				key += data;
				data ^= key;
				buf.setIntLE(buf.readerIndex() - 4, data);
			}
			buf.setIntLE(buf.readerIndex(), key);
			
			buf.resetReaderIndex();
			
			final byte[] block = new byte[8];
			while (buf.isReadable(8))
			{
				buf.readBytes(block);
				STATIC_BLOWFISH_ENGINE.encryptBlock(block, 0);
				buf.setBytes(buf.readerIndex() - block.length, block);
			}
		}
		else
		{
			int checksum = 0;
			while (buf.isReadable(8))
			{
				checksum ^= buf.readIntLE();
			}
			buf.setIntLE(buf.readerIndex(), checksum);
			
			buf.resetReaderIndex();
			
			final byte[] block = new byte[8];
			while (buf.isReadable(8))
			{
				buf.readBytes(block);
				_blowfishEngine.encryptBlock(block, 0);
				buf.setBytes(buf.readerIndex() - block.length, block);
			}
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.l2jserver.commons.network.ICrypt#decrypt(io.netty.buffer.ByteBuf)
	 */
	@Override
	public void decrypt(ByteBuf buf)
	{
		// Packet size must be multiple of 8
		if ((buf.readableBytes() % 8) != 0)
		{
			buf.clear();
			return;
		}
		
		final byte[] block = new byte[8];
		while (buf.isReadable(8))
		{
			buf.readBytes(block);
			_blowfishEngine.decryptBlock(block, 0);
			buf.setBytes(buf.readerIndex() - block.length, block);
		}
		
		// TODO: verify checksum also dont forget!
	}
}
