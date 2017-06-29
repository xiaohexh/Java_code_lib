package com.xh.test;

import java.io.File;

public abstract class FileWatchdog extends Thread {

	public static final long WATCH_INTERVAL = 6000L;
	
	protected String fileName;
	private long interval = WATCH_INTERVAL;
	
	private long lastModified = 0L;
	private boolean warnedAlready = false;
	private boolean interrupted = false;
	
	public FileWatchdog(String fileName) {
		super("FileWatchdog");
		this.fileName = fileName;
		this.setDaemon(true);
	}
	
	public void setInterval(long interval) {
		this.interval = interval;
	}
	
	public abstract void doOnWatch();
	
	public void checkFile() {
		
		boolean fileExists;
		File file = new File(fileName);
		try {
			fileExists = file.exists(); 
		} catch (SecurityException e) {
			this.interrupted = true;
			return;
		}
		
		if (fileExists) {
			long l = file.lastModified();
			if (l > this.lastModified) {
				this.lastModified = l;
				this.doOnWatch();
				this.warnedAlready = false;
			}
		} else {
			// error log
			this.interrupted = true;
			this.warnedAlready = true;
		}
	}
	
	@Override
	public void run() {
		while (!interrupted) {
			this.checkFile();
			try {
				Thread.sleep(WATCH_INTERVAL);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

// usage

//package com.xh.test;
//
//public class AutoUpdateConfigFile extends FileWatchdog {
//	
//	public AutoUpdateConfigFile(String fileName) {
//		super(fileName);
//	}
//	
//	@Override
//	public void doOnWatch() {
//		System.out.println("config file:" + fileName + " has been changed, will reload it");
//	}
//
//	public static void main(String[] args) {
//		
//		String configFile = "config.properties";
//		new AutoUpdateConfigFile(configFile).start();
//	}
//}

