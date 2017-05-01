package com.xh.netty.udp.logevent.monitor;

import java.net.InetSocketAddress;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class LogEventMonitor 
{
	
	private static final Logger logger = LoggerFactory.getLogger(LogEventMonitor.class);
	
	private final EventLoopGroup group;
	private final Bootstrap b;
	
	public LogEventMonitor(InetSocketAddress address) {
		group = new NioEventLoopGroup();
		b = new Bootstrap();
		b.group(group)
		 .channel(NioDatagramChannel.class)
		 .option(ChannelOption.SO_BROADCAST, true)
		 .handler(new ChannelInitializer<Channel>() {

			@Override
			protected void initChannel(Channel ch) throws Exception {
				ChannelPipeline pipeline = ch.pipeline();
				pipeline.addLast(new LogEventDecoder());
				pipeline.addLast(new LogEventHandler());
			}
		 })
		 .localAddress(address);
	}
	
	public Channel bind() {
		return b.bind().syncUninterruptibly().channel();
	}
	
	public void stop() {
		group.shutdownGracefully();
	}
	
    public static void main(String[] args) throws InterruptedException {
    	
    	 // initial log
    	 String logPath = System.getProperty("configPath") + "/log4j.properties";
    	 PropertyConfigurator.configureAndWatch(logPath, 5 * 60000);

    	LogEventMonitor monitor = new LogEventMonitor(new InetSocketAddress(10080));
    	try {
    		Channel ch = monitor.bind();
    		ch.closeFuture().sync();
    	} finally {
    		monitor.stop();
    	}
    }
}
