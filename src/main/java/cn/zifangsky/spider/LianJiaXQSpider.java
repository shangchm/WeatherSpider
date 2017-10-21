package cn.zifangsky.spider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.zifangsky.model.LianjiaXiaoquxx;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

@Component("lianJiaXQSpider")
public class LianJiaXQSpider implements PageProcessor {
	
	
	private Site site = Site.me().setTimeOut(30000).setRetryTimes(3)
			.setSleepTime(10000).setCharset("UTF-8");
	
	
	
	private  String URI = "https://tj.lianjia.com";;
	
	
	
	
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
		List<String> urlList = ConfigUitl.getLink();
		System.out.println("已有链接个数："+urlList.size());
        String url =  page.getUrl().toString();
        Pattern pattern1 = Pattern.compile(URI+"/xiaoqu/[a-z]+/pg(\\d*)?");
        Matcher matcher1 = pattern1.matcher(url);
        
        Pattern pattern2 = Pattern.compile(URI+"/xiaoqu/[a-z]+/$");
        Matcher matcher2 = pattern2.matcher(url);
        
        Pattern pattern3 = Pattern.compile(URI+"/xiaoqu/\\d+/$");
        Matcher matcher3 = pattern3.matcher(url);
        
        
        //列表页面
        if(matcher1.find()||matcher2.find()){
              //列表页面信息获取
             
              
              List<String> xiaoqubm = page.getHtml().xpath("//li[@class='clear xiaoquListItem']/@data-housecode").all();
              List<String> xiaoqumc = page.getHtml().xpath("//li[@class='clear xiaoquListItem']/div[@class='info']/div[@class='title']/a/text()").all();
              List<String> ditiexx = page.getHtml().xpath("//li[@class='clear xiaoquListItem']/div[@class='info']/div[@class='tagList']/span/text()").all();
              //?
              List<String> jinqicjts = page.getHtml().xpath("//li[@class='clear xiaoquListItem']/div[@class='info']/div[@class='houseInfo']/a[contains(@href,'chengjiao')]/text()").all();
              List<String> chuzhuts = page.getHtml().xpath("//li[@class='clear xiaoquListItem']/div[@class='info']/div[@class='houseInfo']/a[contains(@href,'zufang')]/text()").all();
              
              List<String> suozaicq = page.getHtml().xpath("//li[@class='clear xiaoquListItem']/div[@class='info']/div[@class='positionInfo']/a[1]/text()").all();
              List<String> suozaisq = page.getHtml().xpath("//li[@class='clear xiaoquListItem']/div[@class='info']/div[@class='positionInfo']/a[2]/text()").all();
              
              List<String> zaishouts = page.getHtml().xpath("//li[@class='clear xiaoquListItem']/div[@class='xiaoquListItemRight']/div[@class='xiaoquListItemSellCount']/a/span/text()").all();
              List<String> lianjiedz = page.getHtml().xpath("//li[@class='clear xiaoquListItem']/div[@class='xiaoquListItemRight']/div[@class='xiaoquListItemSellCount']/a/@href").all();
            
            List<LianjiaXiaoquxx> xqlist = new ArrayList<LianjiaXiaoquxx>();
            
            for (int i = 0; i < xiaoqubm.size(); i++) {
            	 LianjiaXiaoquxx xq = new LianjiaXiaoquxx();
            	 xq.setXiaoqubm(xiaoqubm.get(i));
            	 xq.setXiaoqumc(xiaoqumc.get(i));
            	 xq.setSuozaics("tj");
            	 xq.setSuozaicq(suozaicq.get(i));
            	 xq.setSuozaisq(suozaisq.get(i));
            	 if(zaishouts.get(i)!=null)
            	 xq.setZaishouts(Integer.parseInt(zaishouts.get(i)));
            	 xq.setLianjiedz(lianjiedz.get(i));
            	 xq.setGengxinrq(ConfigUitl.getDate());
				 if (ditiexx.size() == xiaoqubm.size()) {
					xq.setDitiexx(ditiexx.get(i));
				 }
				 
				if (jinqicjts.size() == xiaoqubm.size()) {
					String cjts = jinqicjts.get(i);
					if (cjts != null) {
						cjts = cjts.trim().replaceAll("90天成交", "").replaceAll("套", "");
						xq.setJinqicjts(Integer.parseInt(cjts));
					}
				}
				
				if (chuzhuts.size() == xiaoqubm.size()) {
					String chuzuts = chuzhuts.get(i);
					if (chuzuts != null) {
						chuzuts = chuzuts.trim().replaceAll("正在出租", "").replaceAll("套", "");
						xq.setChuzhuts(Integer.parseInt(chuzuts));
					}
				}
				 xqlist.add(xq);
			}
            page.putField("xqlist", xqlist);
            
            
            //当前列表页中其它列表链接添加进去
            String pages =  page.getHtml().xpath("//div[@class='page-box house-lst-page-box']/@page-data").toString();
            String pageurl =  page.getHtml().xpath("//div[@class='page-box house-lst-page-box']/@page-url").toString();
            JSONObject json = JSON.parseObject(pages);
            if("1".equals(json.getInteger("curPage").toString())){//避免重复添加，只在第一次的时候添加
            	//各区的链接
            	List<String> PageListUrls = page.getHtml().xpath("//div[@data-role='ershoufang']/div[2]/a/@href").all();
                
                if(PageListUrls != null && PageListUrls.size() > 0){
                    //将当前列表页的所有房屋页面添加进去
                 
                    List<String> list = new ArrayList<String>();
                    for (String purl : PageListUrls) {
    					list.add(URI+purl);
    				}
                    page.addTargetRequests(list);
                 }
            	
            	
            	
            	//翻页连接
				if (!ConfigUitl.linkContains(url)) {
					int size = json.getIntValue("totalPage");
					List<String> listUrls = new ArrayList<String>();
					for (int i = 1; i <= size; i++) {
						listUrls.add(URI + pageurl.substring(0, pageurl.indexOf("pg") + 2) + i);
					}
					System.out.println(listUrls);
					page.addTargetRequests(listUrls);
				}
            } 
        }else if(matcher3.find()){ //小区详情页面不处理
        	
        	page.setSkip(true);
        }    
    }
 

	
	public static void main(String[] args) {
		
		  String url = "https://tj.lianjia.com/xiaoqu/nankai/pg1";
		  
		    Pattern pattern1 = Pattern.compile("https://tj.lianjia.com/xiaoqu/[a-z]+/pg(\\d*)?");
	        Matcher matcher1 = pattern1.matcher(url);
	        System.out.println(matcher1.find());
	       
	        
	        Pattern pattern2 = Pattern.compile("https://tj.lianjia.com/xiaoqu/[a-z]+/$");
	        Matcher matcher2 = pattern2.matcher(url);
	        System.out.println(matcher2.find());
	        
	        Pattern pattern3= Pattern.compile("/xiaoqu/\\w+.html$");
	        Matcher matcher3 = pattern3.matcher(url);
	        System.out.println(matcher3.find());
	        
	        
	        String chengjiaosj = "2017.09.27 链家成交";
	        chengjiaosj = chengjiaosj.split(" ")[0];
	        chengjiaosj = chengjiaosj.replaceAll("\\.", "");
	        System.out.println( Math.round((Integer.parseInt("100")*10000)/Double.parseDouble("33.3")));
	}
	
}
