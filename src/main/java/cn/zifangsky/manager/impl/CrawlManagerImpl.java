package cn.zifangsky.manager.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.zifangsky.manager.CrawlManager;
import cn.zifangsky.manager.LJFangManager;
import cn.zifangsky.manager.ProxyIpManager;
import cn.zifangsky.model.ProxyIp;
import cn.zifangsky.spider.CheckIPUtils;
import cn.zifangsky.spider.ConfigUitl;
import cn.zifangsky.spider.CustomPipeline;
import cn.zifangsky.spider.LianJiaCJSpider;
import cn.zifangsky.spider.LianJiaSpider;
import cn.zifangsky.spider.LianJiaUpdateSpider;
import cn.zifangsky.spider.LianJiaXQSpider;
import cn.zifangsky.spider.LianjianCJPipeline;
import cn.zifangsky.spider.LianjianPipeline;
import cn.zifangsky.spider.LianjianXQPipeline;
import cn.zifangsky.spider.MyProxyProvider;
import cn.zifangsky.spider.ProxyIPPipeline;
import cn.zifangsky.spider.WeatherSpider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.proxy.Proxy;

@Service("crawlManager")
public class CrawlManagerImpl implements CrawlManager {
	
	@Resource(name="customPipeline")
	private CustomPipeline customPipeline;
	
	@Resource(name="proxyIPPipeline")
	private ProxyIPPipeline proxyIPPipeline;
	
	@Resource(name="proxyIpManager")
	private ProxyIpManager proxyIpManager;
	
	@Resource(name="lianjianPipeline")
	private LianjianPipeline lianjianPipeline;
	
	@Resource(name="lianjianCJPipeline")
	private LianjianCJPipeline lianjianCJPipeline;
	
	@Resource(name="lianJiaCJSpider")
	private LianJiaCJSpider lianJiaCJSpider;
	
	@Resource(name="lianJiaXQSpider")
	private LianJiaXQSpider lianJiaXQSpider;
	
	@Resource(name="lianjianXQPipeline")
	private LianjianXQPipeline lianjianXQPipeline;
	
	@Resource(name="erShouFangManager")
	private LJFangManager erShouFangManager;

	
	@Override
	public void weatherCrawl(String stationCode) {
		
		OOSpider.create(new WeatherSpider()).addPipeline(customPipeline)
		.addUrl("http://www.weather.com.cn/weather/" + stationCode + ".shtml")
		.thread(1)
		.run();
	}
	
	@Override
	public void houseCrawl(String csdm,String qydm) {
		ConfigUitl.setWCJLink(erShouFangManager.getLianjiedz("0"));
		//HttpClientDownloader httpClientDownloader = proxyDownloader("https://tj.lianjia.com/ershoufang");
		OOSpider.create(new LianJiaSpider(csdm))
		//.setDownloader(httpClientDownloader)
		//--------使用代理池--end-------
		.addUrl("https://"+csdm+".lianjia.com/ershoufang/"+qydm+"/pg1co32")
		.addPipeline(lianjianPipeline)
		.thread(5)
		.run();
	}
	
	@Override
	public void houseCrawlUpdate(String csdm) {
		ConfigUitl.setWCJLink(erShouFangManager.getLianjiedz("0"));
		//HttpClientDownloader httpClientDownloader = proxyDownloader("https://tj.lianjia.com/ershoufang");
		OOSpider.create(new LianJiaUpdateSpider(csdm))
		//.setDownloader(httpClientDownloader)
		//--------使用代理池--end-------
		.addUrl("https://"+csdm+".lianjia.com/ershoufang/")
		.addPipeline(lianjianPipeline)
		.thread(10)
		.run();
		
	}
	@Override
	public void houseCrawlCJ(String csdm,String qydm) {
	     
		ConfigUitl.setCJLink(erShouFangManager.getLianjiedz("1"));
		//HttpClientDownloader httpClientDownloader = proxyDownloader("https://tj.lianjia.com/chengjiao");
		OOSpider.create(lianJiaCJSpider)
		//.setDownloader(httpClientDownloader)
		//--------使用代理池--end-------
		.addUrl("https://"+csdm+".lianjia.com/chengjiao/"+qydm+"/pg1")
		.addPipeline(lianjianCJPipeline)
		.thread(5)
		.run();
	}

	@Override
	public void houseCrawlXQ(String csdm, String qydm) {
		OOSpider.create(lianJiaXQSpider)
		//.setDownloader(httpClientDownloader)
		//--------使用代理池--end-------
		.addUrl("https://"+csdm+".lianjia.com/xiaoqu/"+qydm+"/pg1")
		.addPipeline(lianjianXQPipeline)
		.thread(5)
		.run();
		
	}

	@Override
	public void proxyIPCrawl() {
		/*//HttpClientDownloader httpClientDownloader = proxyDownloader("http://www.xicidaili.com");
		OOSpider.create(new ProxyIPSpider())
		//.setDownloader(httpClientDownloader)
		.addUrl("http://www.xicidaili.com/wn/").addPipeline(proxyIPPipeline)
		.thread(1)
		.run();*/
	}

	@Override
	public void proxyIPCrawl2() {
		/*//HttpClientDownloader httpClientDownloader = proxyDownloader("http://www.kuaidaili.com");
		OOSpider.create(new ProxyIPSpider2())
		//.setDownloader(httpClientDownloader)
		.addUrl("http://www.kuaidaili.com/free/inha/1/")
		.addPipeline(proxyIPPipeline)
		.thread(1)
		.run();*/
	}


	

	
	/**
	 * 使用代理 下载资源
	 * @return
	 */
	private HttpClientDownloader proxyDownloader(String testUrl) {
		//--------使用代理池--start-------
		  System.out.println("-------使用代理池--start-------");
		   HttpClientDownloader httpClientDownloader = new HttpClientDownloader(); 
			List<ProxyIp> ips = proxyIpManager.selectAll();
			System.out.println("代理IP资源池总IP地址数:"+ips.size());
			List<ProxyIp> validList = new ArrayList<ProxyIp>();
			for (int i =0;i<ips.size();i++) {
				ProxyIp proxyIp = ips.get(i);
				if(CheckIPUtils.checkLJValidIP(proxyIp.getIp().trim(),proxyIp.getPort(),testUrl)){
					System.out.println(i+" type:"+proxyIp.getType()+" "+proxyIp.getIp()+":"+proxyIp.getPort()+" is ok!");
					validList.add(proxyIp);
				}
			}
			
			Proxy[] ipList = new Proxy[validList.size()];
			for (int i =0;i<validList.size();i++) {
				ProxyIp proxyIp = validList.get(i);
				ipList[i] = new Proxy(proxyIp.getIp().trim(),proxyIp.getPort());
			}
		System.out.println("【代理IP资源池可用IP地址数】："+ipList.length);
		MyProxyProvider proxyProvider = MyProxyProvider.from(ipList);  
		httpClientDownloader.setProxyProvider(proxyProvider);
		return httpClientDownloader;
	}

	

	
}
