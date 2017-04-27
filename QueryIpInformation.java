package com.xh.exercise;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// content format of ip.txt
// ...
// 1928413628|1928506369|x|x|x|x|中国|北京|北京||中国联通
// 1928506370|1928864891|x|x|x|x|中国|北京|北京|海淀区|中国联通
// ...

public class QueryIpInformation {
	
	private static final String IP_FILE_PATH = "C:\\Document\\ip.txt";
	
	private List<Long> ipLists = new ArrayList<Long>();
	private Map<Long, String> ipInfos = new HashMap<Long, String>();
	
	public QueryIpInformation() {
		init();
	}
	
	public void init() {
		
		FileInputStream fis = null;
		InputStreamReader streamReader = null;
		BufferedReader br = null;
		
		try {
			fis = new FileInputStream(IP_FILE_PATH);
			streamReader = new InputStreamReader(fis, "GB2312");
			br = new BufferedReader(streamReader);
			
			String line = null;
			int index;
			
			while ((line = br.readLine()) != null) {
				
				index = line.indexOf('|');
				if (index == -1) {
					continue;
				}
				
				long startIp = Long.valueOf(line.substring(0, index));
				ipLists.add(startIp);
				ipInfos.put(startIp, line + "|M");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String find(String ip) {
		// TODO:verification of ip
		long longIp = str2Ip(ip);
		System.out.println("longip:" + longIp);
		String ipInfo = find(longIp);
		String[] infos = ipInfo.split("\\|");
		String country = infos[6];
		String province = infos[7];
		String city = infos[8];
		String  county = infos[9];
		String operator = infos[10];
		return country + "|" + province + "|" + city + "|" + county + "|" + operator;
	}
	
	private String find(long ip) {
		int start = 0;
		int end = ipLists.size();
		int middle;
		boolean flag = false;
		while (start <= end) {
			middle = (start + end) / 2;
			if (ipLists.get(middle) == ip) {
				return ipInfos.get(ip);
			} else if (ipLists.get(middle) > ip) {
				end = middle - 1;
				flag = true;
			} else {
				start = middle + 1;
				flag = false;
			}
		}
		
//		System.out.println("start:" + ipInfos.get(ipLists.get(start)));
//		System.out.println("end  :" + ipInfos.get(ipLists.get(end)));
//		System.out.println("flag :" + flag);
		
		if (flag) {
			return ipInfos.get(ipLists.get(start));
		} else {
			return ipInfos.get(ipLists.get(end));
		}
	}

    private long str2Ip(String ip)  {
        String[] ss = ip.split("\\.");
        int a, b, c, d;
        a = Integer.parseInt(ss[0]);
        b = Integer.parseInt(ss[1]);
        c = Integer.parseInt(ss[2]);
        d = Integer.parseInt(ss[3]);
        return (a << 24) | (b << 16) | (c << 8) | d;
    }
    
    public static void main(String[] args) {
    	String ip = "114.242.35.162";
    	QueryIpInformation query = new QueryIpInformation();
    	for (int i = 0; i < 1; i++) {
    		System.out.println(query.find(ip));
    	}
    }
}

