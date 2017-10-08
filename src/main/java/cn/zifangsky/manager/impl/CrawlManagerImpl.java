package cn.zifangsky.manager.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zifangsky.manager.CrawlManager;
import cn.zifangsky.manager.ProxyIpManager;
import cn.zifangsky.model.ProxyIp;
import cn.zifangsky.spider.CustomPipeline;
import cn.zifangsky.spider.LianJiaSpider;
import cn.zifangsky.spider.ProxyIPPipeline;
import cn.zifangsky.spider.ProxyIPSpider;
import cn.zifangsky.spider.ProxyIPSpider2;
import cn.zifangsky.spider.WeatherSpider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.proxy.Proxy;
import us.codecraft.webmagic.proxy.SimpleProxyProvider;

@Service("crawlManager")
public class CrawlManagerImpl implements CrawlManager {
	
	@Resource(name="customPipeline")
	private CustomPipeline customPipeline;
	
	@Resource(name="proxyIPPipeline")
	private ProxyIPPipeline proxyIPPipeline;
	
	@Resource(name="proxyIpManager")
	private ProxyIpManager proxyIpManager;
	
	@Override
	public void weatherCrawl(String stationCode) {
		
		OOSpider.create(new WeatherSpider()).addPipeline(customPipeline)
		.addUrl("http://www.weather.com.cn/weather/" + stationCode + ".shtml")
		.thread(1)
		.run();
	}
	
	
	@Override
	public void houseCrawl(String station) {
		//--------使用代理池--start-------
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader(); 
		List<ProxyIp> ips = proxyIpManager.selectAll();
		Proxy[] ipList = new Proxy[ips.size()];
		for (int i =0;i<ips.size();i++) {
			ProxyIp proxyIp = ips.get(i);
			if(proxyIp.getType().equals("HTTPS")){
				ipList[i] = new Proxy(proxyIp.getIp(),proxyIp.getPort());
			}
		}
		System.out.println("proxyList+++++++"+ipList);
		SimpleProxyProvider proxyProvider = SimpleProxyProvider.from(ipList);  
		httpClientDownloader.setProxyProvider(proxyProvider);
		OOSpider.create(new LianJiaSpider()).setDownloader(httpClientDownloader)
		//--------使用代理池--end-------
		//OOSpider.create(new LianJiaSpider())
		.addUrl("https://tj.lianjia.com/ershoufang/" + station+"/pg1")
		.thread(1)
		.run();
	}

	@Override
	public void proxyIPCrawl() {
		OOSpider.create(new ProxyIPSpider())
		.addUrl("http://www.xicidaili.com/nn/1").addPipeline(proxyIPPipeline)
		.thread(3)
		.run();
	}

	@Override
	public void proxyIPCrawl2() {
		OOSpider.create(new ProxyIPSpider2())
		.addUrl("http://www.kuaidaili.com/free/inha/1/").addPipeline(proxyIPPipeline)
		.thread(2)
		.run();
	}

	

}
