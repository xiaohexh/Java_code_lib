package com.xh.netty.udp.logevent.monitor;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.util.CharsetUtil;

public class LogEventDecoder extends MessageToMessageDecoder<DatagramPacket> {

	private static final Logger logger = LoggerFactory.getLogger(LogEventDecoder.class);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, DatagramPacket msg,
			List<Object> out) throws Exception {
		ByteBuf buf = msg.content();
		int index = buf.indexOf(0, buf.readableBytes(), LogEvent.SEPERATOR);
		String fileName = buf.slice(0, index).toString(CharsetUtil.UTF_8);
		String logMsg = buf.slice(index + 1, buf.readableBytes()).toString(CharsetUtil.UTF_8);
		LogEvent event= new LogEvent(msg.sender(), System.currentTimeMillis(), fileName, logMsg);
		out.add(event);
 	}
}
