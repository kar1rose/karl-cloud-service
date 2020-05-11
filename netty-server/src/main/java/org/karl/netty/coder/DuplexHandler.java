package org.karl.netty.coder;

import io.netty.channel.CombinedChannelDuplexHandler;
import org.karl.netty.decoder.ByteToStrDecoder;
import org.karl.netty.encoder.ModelToStrEncoder;

/**
 * @author KARL ROSE
 * @date 2020/5/11 14:46
 **/
public class DuplexHandler extends CombinedChannelDuplexHandler<ByteToStrDecoder, ModelToStrEncoder> {

    public DuplexHandler() {
        super(new ByteToStrDecoder(), new ModelToStrEncoder());
    }
}
