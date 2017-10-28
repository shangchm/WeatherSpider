package cn.zifangsky.test.spider;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.zifangsky.manager.CrawlManager;
import cn.zifangsky.spider.ConfigUitl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath*:context/context*.xml"})
public class TestSpider{
	@Resource(name="crawlManager")
	private CrawlManager crawlManager;

	@Test
	public void testWeatherCrawl(){
		//crawlManager.weatherCrawl("101041500");
		//crawlManager.proxyIPCrawl();
		//crawlManager.houseCrawl("tj","nankai");
		//crawlManager.houseCrawlXQ("tj","xiqing");
	   /* List<String> list = ConfigUitl.getRegion();
		if(list != null && list.size() > 0){
			for(String station : list){
				crawlManager.houseCrawl("tj",station);
			}
			
		}*/
		crawlManager.houseCrawlUpdate("tj");
	}


	
}
