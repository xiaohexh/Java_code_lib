package com.xh.netty.udp.logevent.monitor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class LogEventHandler extends SimpleChannelInboundHandler<LogEvent> {

	private static final Logger logger = LoggerFactory.getLogger(LogEventHandler.class);
	
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LogEvent msg)
			throws Exception {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(msg.getReceived());
		sb.append("][");
		sb.append(msg.getSource());
		sb.append("][");
		sb.append(msg.getLogfile());
		sb.append("]:");
		sb.append(msg.getMsg());
		logger.error(sb.toString());
	}
}
