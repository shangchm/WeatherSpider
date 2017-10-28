package cn.zifangsky.manager;

public interface CrawlManager {
	
	/**
	 * 天气爬虫
	 * @param stationCode 县城（区）的CODE
	 */
	public void weatherCrawl(String stationCode);
	
	/**
	 * 房屋爬虫
	 * @param station 城区
	 */
	public void houseCrawl(String csdm,String qydm);
	
	
	/**
	 * 房屋爬虫
	 * @param station 城区
	 */
	public void houseCrawlUpdate(String csdm);
	
	
	/**
	 * 房屋爬虫
	 * @param station 城区
	 */
	public void houseCrawlCJ(String csdm,String qydm);
	
	/**
	 * 房屋爬虫
	 * @param station 城区
	 */
	public void houseCrawlXQ(String csdm,String qydm);
	
	/**
	 * 代理IP爬虫，地址：http://www.xicidaili.com
	 */
	public void proxyIPCrawl();
	
	/**
	 * 代理IP爬虫，地址：http://www.kuaidaili.com
	 */
	public void proxyIPCrawl2();
}
