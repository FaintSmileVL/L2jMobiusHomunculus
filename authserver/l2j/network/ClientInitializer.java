package l2j.network;

import java.nio.ByteOrder;

import javax.crypto.SecretKey;

import network.codecs.CryptCodec;
import network.codecs.LengthFieldBasedFrameEncoder;
import network.codecs.PacketDecoder;
import network.codecs.PacketEncoder;
import util.crypt.LoginCrypt;
import l2j.LoginController;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author Nos
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel>
{
	private static final LengthFieldBasedFrameEncoder LENGTH_ENCODER = new LengthFieldBasedFrameEncoder();
	private static final PacketEncoder PACKET_ENCODER = new PacketEncoder(0x8000 - 2);
	
	@Override
	protected void initChannel(SocketChannel ch)
	{
		final SecretKey newKey = LoginController.getInstance().generateBlowfishKey();
		final LoginClient client = new LoginClient(newKey);
		ch.pipeline().addLast(new BannedIpFilter());
		ch.pipeline().addLast("length-decoder", new LengthFieldBasedFrameDecoder(ByteOrder.LITTLE_ENDIAN, 0x8000 - 2, 0, 2, -2, 2, false));
		ch.pipeline().addLast("length-encoder", LENGTH_ENCODER);
		ch.pipeline().addLast("crypt-codec", new CryptCodec(new LoginCrypt(newKey)));
		// ch.pipeline().addLast(new LoggingHandler(LogLevel.INFO));
		ch.pipeline().addLast("packet-decoder", new PacketDecoder<>(IncomingPackets.PACKET_ARRAY, client));
		ch.pipeline().addLast("packet-encoder", PACKET_ENCODER);
		ch.pipeline().addLast(client);
	}
}
