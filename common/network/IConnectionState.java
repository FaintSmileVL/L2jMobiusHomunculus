package network;

import io.netty.util.AttributeKey;

/**
 * @author Nos
 */
public interface IConnectionState
{
	AttributeKey<IConnectionState> ATTRIBUTE_KEY = AttributeKey.valueOf(IConnectionState.class, "");
}
