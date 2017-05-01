package com.xh.netty.udp.logevent.broadcast;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class LogEventBroadcaster {
	
	private static final Logger logger = LoggerFactory.getLogger(LogEventBroadcaster.class);
	
	private final EventLoopGroup group;
	private final Bootstrap bootstrap;
	private final File file;
	
	public LogEventBroadcaster(InetSocketAddress address, File file) {
		group = new NioEventLoopGroup();
		bootstrap = new Bootstrap();
		bootstrap.group(group)
				 .channel(NioDatagramChannel.class)
				 .option(ChannelOption.SO_BROADCAST, true)
				 .handler(new LogEventEncoder(address));
		this.file = file;
	}
	
	public void run() throws Exception {
		Channel ch = bootstrap.bind(0).syncUninterruptibly().channel();
		logger.error("LogEventBroadcaster: server has been started");
		
		long pointer = 0;
		for (;;) {
			long len = file.length();
			
			if (pointer > len) {
				pointer = len;
			} else {
				RandomAccessFile raf = new RandomAccessFile(file, "r");
				raf.seek(pointer);
				String line;
				while ((line = raf.readLine()) != null) {
					logger.error("LogEventBroadcaster:run line:" + line);
					ch.write(new LogEvent(null, -1, file.getAbsolutePath(), line));
				}
				ch.flush();
				pointer = raf.getFilePointer();
				raf.close();
			}
			
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch (InterruptedException e) {
				Thread.interrupted();
				break;
			}
		}
	}
	
	public void stop() {
		group.shutdownGracefully();
	}
	
	public static void main(String[] args) throws Exception {
		
		 // initial log
   	 	String logPath = System.getProperty("configPath") + "/log4j.properties";
   	 	PropertyConfigurator.configureAndWatch(logPath, 5 * 60000);
		
		int port = 10080;
		String logfile = System.getProperty("user.dir") + "/log.txt";
		LogEventBroadcaster broadcaster = new LogEventBroadcaster(
				new InetSocketAddress("255.255.255.255", port), new File(logfile));
		try {
			broadcaster.run();
		} finally {
			broadcaster.stop();
		}
	}
}
