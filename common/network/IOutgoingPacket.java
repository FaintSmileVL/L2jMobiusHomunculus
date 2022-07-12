package network;

/**
 * @author Nos
 */
public interface IOutgoingPacket
{
	/**
	 * @param packet the packet writer
	 * @return {@code true} if packet was writen successfully, {@code false} otherwise.
	 */
	boolean write(PacketWriter packet);
}
