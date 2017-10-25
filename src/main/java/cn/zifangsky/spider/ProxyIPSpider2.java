package cn.zifangsky.spider;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.zifangsky.model.ProxyIp;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class ProxyIPSpider2 implements PageProcessor {

	@Override
	public Site getSite() {
		Site site = Site.me().setTimeOut(6000).setRetryTimes(3)
				.setSleepTime(10000).setCharset("UTF-8").addHeader("Accept-Encoding", "/")
				.setUserAgent(UserAgentUtils.radomUserAgent());
		        
		
		return site;
	}

	@Override
	public void process(Page page) {
		
		String s = page.getHtml().get();
		List<String> ipList = page.getHtml().xpath("//table[@class='table table-bordered table-striped']/tbody/tr").all();
		List<ProxyIp> result = new ArrayList<>();
	    System.out.println("本页获取ip数量："+ipList.size());
		if(ipList != null && ipList.size() > 0){
			for(String tmp : ipList){
				Html html = Html.create(tmp);
				ProxyIp proxyIp = new ProxyIp();
				String[] data = html.xpath("//body/text()").toString().trim().split("\\s+");
				String dataStr = html.xpath("//body/text()").toString();
				
				proxyIp.setIp(data[0]);
				proxyIp.setPort(Integer.valueOf(data[1]));
				//System.out.println("::"+proxyIp.getIp()+":"+proxyIp.getPort());
				Pattern pattern = Pattern.compile("HTTPS?\\s(.*)?\\s\\d秒");
				Matcher matcher = pattern.matcher(dataStr);
				if(matcher.find()){
					proxyIp.setAddr(matcher.group(1));
				}

				proxyIp.setType(data[3]);
				
				result.add(proxyIp);
			} 
		}
		page.putField("result", result);
		//String current = page.getHtml().xpath("//div[@class='pagination']/em[@class='current']/text()").toString();
		
		//if(current!=null&&"1".equals(current)){
		//   String pages = page.getHtml().xpath("//div[@class='pagination']/a[11]/text()").toString();
		String pages = "1800";
		   if(pages!=null){
			   int num = Integer.parseInt(pages);
			   for (int i = 0; i < num; i++) {
				   page.addTargetRequest("http://www.kuaidaili.com/free/inha/"+i);
			 }
		   }
		  
		//}
	}

}
