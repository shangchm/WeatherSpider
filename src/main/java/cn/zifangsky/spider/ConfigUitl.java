package cn.zifangsky.spider;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class ConfigUitl {
	
	private static Set<String> cjSet = new TreeSet<String>();
	
	private static Set<String> wcjSet =  new TreeSet<String>();
	
	public static List<String> getRegion(String csmc){
		List<String> list = new ArrayList<String>();
		if("tj".equals(csmc)){
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
		}else if("bj".equals(csmc)){
		list.add("dongcheng");
		list.add("xicheng");
		list.add("haidian");
		list.add("fengtai");
		list.add("shijingshan");
		list.add("tongzhou");
		list.add("changping");
		list.add("daxing");
		list.add("yizhuangkaifaqu");
		list.add("shunyi");
		list.add("fangshan");
		list.add("mentougou");
		
		list.add("pinggu");
		list.add("huairou");
		list.add("miyun");
		list.add("yanqing");
		list.add("yanjiao");
		list.add("xianghe");
		}
		
		return list;
	}
	
	public static boolean linkContains(String url,String csmc){
		boolean ok = false;
		List<String> list = getRegion(csmc);
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
