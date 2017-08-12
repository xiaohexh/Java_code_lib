package com.xh.parse.command.line.param;

import org.apache.commons.lang3.StringUtils;

public class ParseParam {

	private static final String PARAM_KEY_PORT = "-p";
	private static final String PARAM_KEY_REPLICA = "-replica";
	private static final String PARAM_KEY_LOG_DIR = "-dir";
	private static final String PARAM_KEY_HOST = "-h";
	private static final String PARAM_KEY_CFG = "-cfg";
	private static final String PARAM_KEY_INFO = "-info";
	
	public void ParseCLParam(String[] args) {
		int port = 9999;
		String replica = null;
		String dir = null;
		String host = null;
		String cfg = null;
		String info = null;
		
		int argsSize = args.length;
		if (argsSize > 0) {
			if (argsSize % 2 != 0) {
				System.out.println("number of param is incorrect");
				System.exit(1);
			}
			
			int i = 0;
			while (i < argsSize) {
				if (StringUtils.isBlank(args[i]) || StringUtils.isBlank(args[i + 1])) {
					System.out.println("args[" + i + "] is blank");
					continue;
				}
				if (args[i].equals(PARAM_KEY_PORT)) {
					port = Integer.valueOf(args[i + 1].trim());
				} else if (args[i].equals(PARAM_KEY_REPLICA)) {
					replica = String.valueOf(args[i + 1].trim());
				} else if (args[i].equals(PARAM_KEY_LOG_DIR)) {
					dir = String.valueOf(args[i + 1].trim());
				} else if (args[i].equals(PARAM_KEY_HOST)) {
					host = String.valueOf(args[i + 1].trim());
				} else if (args[i].equals(PARAM_KEY_CFG)) {
					cfg = String.valueOf(args[i + 1].trim());
				} else if (args[i].equals(PARAM_KEY_INFO)) {
					info = String.valueOf(args[i + 1].trim());
				}
				
				i += 2;
			}
		}
		
		System.out.println("port:" + port);
		System.out.println("replica:" + replica);
		System.out.println("host:" + host);
		System.out.println("cfg:" + cfg);
		System.out.println("info:" + info);
	}
	
	public static void main(String[] args) {
		new ParseParam().ParseCLParam(args);
	}
}

