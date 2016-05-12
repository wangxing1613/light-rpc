/*
 * 版权所有 (c) 2016. 这是我的第一个测试项目，未经许可不准商用
 */

/*
 * 版权所有 (c) 2016. 这是我的第一个测试项目，未经许可不准商用
 */

/*
 * 版权所有 (c) 2016. 这是我的第一个测试项目，未经许可不准商用
 */

package my.message.coder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import my.message.utils.SerializationUtil;

import java.util.List;

/**
 * Created by wangxing on 2016/4/16.
 */
public class RpcDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public RpcDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object obj = SerializationUtil.deserialize(data, genericClass);
        out.add(obj);
    }
}
