package cn.zifangsky.spider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConfigUitl {
	
	private static List<String> linkList = new ArrayList<String>();
	
	public static List<String> getRegion(){
		List<String> list = new ArrayList<String>();
		list.add("heping");
		list.add("kaifaqutj");
		list.add("nankai");
		list.add("xiqing");
		list.add("beichen");
		list.add("dongli");
		list.add("jinnan");
		list.add("tanggu");
		list.add("hexi");
		list.add("hebei");
		list.add("hedong");
		list.add("hongqiao");
		
		return list;
	}
	
	public static boolean linkContains(String url){
		boolean ok = false;
		List<String> list = getRegion();
		for (String s : list) {
			if(url.indexOf(s)!=-1){
				ok =true;
				break;
			}
		}
		return ok;
	}
	
	public static List<String> getLink(){
		return linkList;
	}
	
	public static void setLink(List<String> list){
		linkList.addAll(list);
	}
	
	
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	

}
