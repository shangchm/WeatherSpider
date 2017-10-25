package cn.zifangsky.spider;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

public class CheckIPUtils {
	
	/**
	 * 校验代理IP的有效性，测试地址为：http://www.ip138.com
	 * @param ip 代理IP地址
	 * @param port  代理IP端口
	 * @return  此代理IP是否有效
	 */
	public static boolean checkValidIP(String ip,Integer port){
		URL url = null;
		HttpURLConnection connection = null;
		try {
			url = new URL("http://www.ip138.com");
			//代理服务器
			InetSocketAddress proxyAddr = new InetSocketAddress(ip, port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
			connection = (HttpURLConnection) url.openConnection(proxy);
			connection.setReadTimeout(4000);
			connection.setConnectTimeout(4000);
			connection.setRequestMethod("GET");

			if(connection.getResponseCode() == 200){
				connection.disconnect();
				return true;
			}
			
		} catch (Exception e) {
			connection.disconnect();
			return false;
		}
		return false;
	}
	
	
	public static boolean checkLJValidIP(String ip,Integer port,String testurl){
		
		HttpURLConnection connection = null;
		 int code = 0;
		try {
			URL url = new URL(testurl);
			//url = new URL("https://tj.lianjia.com/ershoufang");
			//代理服务器
			InetSocketAddress proxyAddr = new InetSocketAddress(ip, port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP, proxyAddr);
			connection = (HttpURLConnection) url.openConnection(proxy);
			connection.setReadTimeout(3000);
			connection.setConnectTimeout(3000);
			connection.setRequestMethod("GET");
            code = connection.getResponseCode() ;
			if(code== 200){
				connection.disconnect();
				return true;
			}
			
		} catch (Exception e) {
			//e.printStackTrace();
			//System.out.println(ip+":"+port+ " return "+code);
			connection.disconnect();
			return false;
		}
		return false;
	}
}
