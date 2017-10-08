package cn.zifangsky.spider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class LianJiaSpider implements PageProcessor {
	
	private Site site = Site.me().setTimeOut(20000).setRetryTimes(3)
			.setSleepTime(2000).setCharset("UTF-8");
	
	@Override
	public Site getSite() {
		Set<Integer> acceptStatCode = new HashSet<>();
		acceptStatCode.add(200);
		site = site.setAcceptStatCode(acceptStatCode).addHeader("Accept-Encoding", "/")
				.setUserAgent(UserAgentUtils.radomUserAgent());
		return site;
	}

	
	@Override
    public void process(Page page) {
				
        String url =  page.getUrl().toString();
        Pattern pattern1 = Pattern.compile("https://tj.lianjia.com/ershoufang/[a-z]*/pg(\\d*)?");
        Matcher matcher1 = pattern1.matcher(url);
        
        Pattern pattern2 = Pattern.compile("/ershoufang/\\d+.html");
        Matcher matcher2 = pattern2.matcher(url);
        
        //列表页面
        if(matcher1.find()){
            //详情页URL集合
            List<String> housePageUrls = page.getHtml().xpath("//li[@class='clear']/a/@href").all();
            
            if(housePageUrls != null && housePageUrls.size() > 0){
                //将当前列表页的所有房屋页面添加进去
                page.addTargetRequests(housePageUrls);
            }
            
            //当前列表页中的其他列表页的链接
            String pages =  page.getHtml().xpath("//div[@class='page-box house-lst-page-box']/@page-data").toString();
            JSONObject json = JSON.parseObject(pages);
            if("1".equals(json.getInteger("curPage").toString())){
            	int size = json.getIntValue("totalPage");
            	 List<String> listUrls = new ArrayList<String>();
            	for (int i = 0 ;i<size;i++) {
            		i++;
					listUrls.add(url.substring(0,url.indexOf("pg")+2)+i);
				}
            	System.out.println(listUrls);
            	 page.addTargetRequests(listUrls);
            } 
        }else if(matcher2.find()){  //房屋页面    
            //获取总价
            String zongjia = page.getHtml().xpath("//div[@class='price']/span[@class='total']/text()").toString();
            //获取单价
            String dianjia = page.getHtml().xpath("//div[@class='unitPrice']/span[@class='unitPriceValue']/text()").toString();
            System.out.println("zongjia:"+zongjia);
            System.out.println("dianjia:"+dianjia);
            
            
            
            
            //page.putField("movie", movie);  //后面做数据的持久化
        }    
    }
 

	
	public static void main(String[] args) {
		
		  String url = "https://tj.lianjia.com/ershoufang/heping/pg1";
		  
		    Pattern pattern1 = Pattern.compile("https://tj.lianjia.com/ershoufang/[a-z]*/pg(\\d*)?");
	        Matcher matcher1 = pattern1.matcher(url);
	        System.out.println(matcher1.find());
	        Pattern pattern2 = Pattern.compile("/ershoufang/\\d+.html");
	        Matcher matcher2 = pattern2.matcher(url);
	        System.out.println(matcher2.find());
	}
	
}
