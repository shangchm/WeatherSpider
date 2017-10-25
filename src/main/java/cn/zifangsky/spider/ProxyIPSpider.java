package cn.zifangsky.spider;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.zifangsky.model.ProxyIp;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.SimpleHttpClient;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.JsonPathSelector;

public class ProxyIPSpider implements PageProcessor {

	@Override
	public Site getSite() {
		Site site = Site.me().setTimeOut(6000).setRetryTimes(3)
				.setSleepTime(20000).setCharset("UTF-8").addHeader("Accept-Encoding", "/")
				.setUserAgent(UserAgentUtils.radomUserAgent());
		
		return site;
	}

	/*@Override
	public void process(Page page) {
		String s = page.getHtml().get();
		
		List<String> ipList = page.getHtml().xpath("//table[@id='ip_list']/tbody/tr").all();
		List<ProxyIp> result = new ArrayList<>();
	
		if(ipList != null && ipList.size() > 0){
			ipList.remove(0);  //移除表头
			for(String tmp : ipList){
				Html html = Html.create(tmp);
				ProxyIp proxyIp = new ProxyIp();
				String[] data = html.xpath("//body/text()").toString().trim().split("\\s+");
				
				proxyIp.setIp(data[0]);
				proxyIp.setPort(Integer.valueOf(data[1]));
				proxyIp.setAddr(html.xpath("//a/text()").toString());
				proxyIp.setType(data[3]);
				
				result.add(proxyIp);
			} 
		}
		page.putField("result", result);
		String current = page.getHtml().xpath("//div[@class='pagination']/em[@class='current']/text()").toString();
		if(current!=null&&"1".equals(current)){
		   String pages = page.getHtml().xpath("//div[@class='pagination']/a[10]/text()").toString();
		   if(pages!=null){
			   int num = Integer.parseInt(pages);
			   for (int i = 0; i < num; i++) {
				   
				   page.addTargetRequest("http://www.xicidaili.com/wn/"+i);
			 }
		   }
		  
		}
	}*/
	
	
	@Override
	public void process(Page page) {
		SimpleHttpClient simplehttp = new SimpleHttpClient(); 
        Page jsonpage = simplehttp.get("http://http-api.taiyangruanjian.com/getip?num=100&type=1&pro=&city=0&yys=0&port=11&pack=5415&ts=0&ys=0&cs=0&lb=1&sb=0&pb=4&mr=1");
        List<String> ids = new JsonPathSelector("$.RESULT").selectList(jsonpage.getRawText());
		List<ProxyIp> result = new ArrayList<>();
		
		if(ids != null && ids.size() > 0){
			
			for(String tmp : ids){
				JSONObject json = JSON.parseObject(tmp);
				ProxyIp proxyIp = new ProxyIp();
				
				proxyIp.setIp(json.getString("ip"));
				proxyIp.setPort(json.getIntValue("port"));
				proxyIp.setAddr("");
				proxyIp.setType("http");
				
				result.add(proxyIp);
			} 
		}
		page.putField("result", result);
	}

}
