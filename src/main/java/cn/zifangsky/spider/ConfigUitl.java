package cn.zifangsky.spider;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.activemq.console.Main;

public class ConfigUitl {
	
	private static Set<String> cjSet = new TreeSet<String>();
	
	private static Set<String> wcjSet =  new TreeSet<String>();
	
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
	
	public static Set<String> getCJLink(){
		return cjSet;
	}
	
	public static void setCJLink(List<String> list){
		cjSet.addAll(list);
	}
	
	
	
	public static Set<String> getWCJLink(){
		return wcjSet;
	}
	
	public static void setWCJLink(List<String> list){
		wcjSet.addAll(list);
	}
	
	
	public static String getDate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return sdf.format(new Date());
	}
	
	public static String getBeforDate(int datenum){
		SimpleDateFormat dft = new SimpleDateFormat("yyyyMMdd");
		Date beginDate = new Date();
		Calendar date = Calendar.getInstance();
		date.setTime(beginDate);
		date.set(Calendar.DATE, date.get(Calendar.DATE) - datenum);
		return dft.format(date.getTime());
	}
	
	
	public static void main(String[] args) {
		String s = getBeforDate(7);
		System.out.println(s);
	}
	

}
