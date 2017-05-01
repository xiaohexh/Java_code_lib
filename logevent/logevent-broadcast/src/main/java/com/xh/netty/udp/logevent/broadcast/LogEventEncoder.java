package com.xh.netty.udp.logevent.broadcast;

import java.net.InetSocketAddress;
import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;
import io.netty.util.CharsetUtil;

public class LogEventEncoder extends MessageToMessageEncoder<LogEvent> {

	public final InetSocketAddress remoteAddress;
	
	public LogEventEncoder(InetSocketAddress remoteAddress) {
        this.remoteAddress = remoteAddress;
    }

	@Override
	protected void encode(ChannelHandlerContext ctx, LogEvent msg, List<Object> out)
			throws Exception {
		ByteBuf buf = ctx.alloc().buffer();
		buf.writeBytes(msg.getLogfile().getBytes(CharsetUtil.UTF_8));
		buf.writeByte(LogEvent.SEPERATOR);
		buf.writeBytes(msg.getMsg().getBytes(CharsetUtil.UTF_8));
		out.add(new DatagramPacket(buf, remoteAddress));
	}
}
