package cn.zifangsky.spider;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.zifangsky.model.LianjiaFangwuxx;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

public class LianJiaCJSpider implements PageProcessor {
	
	private Site site = Site.me().setTimeOut(20000).setRetryTimes(3)
			.setSleepTime(2000).setCharset("UTF-8");
	
	private  String URI;
	
	private  String CSDM;
	
	public LianJiaCJSpider(String csdm){
		CSDM = csdm;
		URI = "https://"+CSDM+".lianjia.com";
	}
	
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
        Pattern pattern1 = Pattern.compile(URI+"/chengjiao/[a-z]+/pg(\\d*)?");
        Matcher matcher1 = pattern1.matcher(url);
        
        Pattern pattern2 = Pattern.compile(URI+"/chengjiao/[a-z]+/$");
        Matcher matcher2 = pattern2.matcher(url);
        
        Pattern pattern3 = Pattern.compile(URI+"/chengjiao/\\d+.html$");
        Matcher matcher3 = pattern3.matcher(url);
        
        //列表页面
        if(matcher1.find()){
            //详情页URL集合
            List<String> housePageUrls = page.getHtml().xpath("//li/a[@class='img']/@href").all();
            
            if(housePageUrls != null && housePageUrls.size() > 0){
                //将当前列表页的所有房屋页面添加进去
                page.addTargetRequests(housePageUrls);
            }
            
            //当前列表页中其它列表链接添加进去
            String pages =  page.getHtml().xpath("//div[@class='page-box house-lst-page-box']/@page-data").toString();
            JSONObject json = JSON.parseObject(pages);
            if("1".equals(json.getInteger("curPage").toString())){//避免重复添加，只在第一次的时候添加
            	//各区的链接
            	/*List<String> PageListUrls = page.getHtml().xpath("//div[@data-role='ershoufang']/div/a/@href").all();
                
                if(PageListUrls != null && PageListUrls.size() > 0){
                    //将当前列表页的所有房屋页面添加进去
                    page.addTargetRequests(PageListUrls);
                }*/
            	
            	
            	
            	//翻页连接
            	int size = json.getIntValue("totalPage");
            	 List<String> listUrls = new ArrayList<String>();
            	for (int i = 0 ;i<size;i++) {
            		i++;
					listUrls.add(url.substring(0,url.indexOf("pg")+2)+i);
				}
            	System.out.println(listUrls);
            	 page.addTargetRequests(listUrls);
            } 
        }else if(matcher3.find()){  //房屋页面   
        	
        	//编号
        	String fangwubh = page.getHtml().xpath("//div[@class='transaction']/div[@class='content']/ul/li[1]/text()").toString();
        	
            //挂牌价格
            String zongjia = page.getHtml().xpath("//div[@class='overview']/div[@class='info fr']/div[@class='msg']/span[1]/label/text()").toString();
            //挂牌单价
            String danjia ="0";
            
            //小区名称
            String xiaoqumc = page.getHtml().xpath("//div[@class='house-title']/div[@class='wrapper']/text()").toString();
            if(xiaoqumc!=null)
            	xiaoqumc = xiaoqumc.split(" ")[0];
            //成交时间
            String chengjiaosj = page.getHtml().xpath("//div[@class='house-title']/div[@class='wrapper']/span/text()").toString();
            if(chengjiaosj!=null)
            	chengjiaosj = chengjiaosj.replaceAll("\\.", "").split(" ")[0];
            //所在区
            String suozaiq = page.getHtml().xpath("//section[@class='wrapper']/div[@class='deal-bread']/a[3]/text()").toString();
            if(suozaiq!=null)
            	suozaiq = suozaiq.replaceAll("二手房成交价格", "");
            //所在商圈
            String suozaisq = page.getHtml().xpath("//section[@class='wrapper']/div[@class='deal-bread']/a[4]/text()").toString();
            if(suozaisq!=null)
            	suozaisq = suozaisq.replaceAll("二手房成交价格", "");
            //所在地铁线路
            String suozaidtxl = "";
            //看房次数--
            String kaifangcs = page.getHtml().xpath("//div[@class='overview']/div[@class='info fr']/div[@class='msg']/span[4]/label/text()").toString();
            //预约次数--
            String yueyuecs = page.getHtml().xpath("//div[@class='overview']/div[@class='info fr']/div[@class='msg']/span[5]/label/text()").toString();
            
            //成交周期
            String chenjiaozq = page.getHtml().xpath("//div[@class='overview']/div[@class='info fr']/div[@class='msg']/span[2]/label/text()").toString();
            //成交总价
            String chenjiaozj = page.getHtml().xpath("//div[@class='overview']/div[@class='info fr']/div[@class='price']/span/i/text()").toString();
            //成交均价
            String chenjiaojj = page.getHtml().xpath("//div[@class='overview']/div[@class='info fr']/div[@class='price']/b/text()").toString();
            
            //---------------------------------------基本信息-------base-----------------------------------------
            //房屋户型
            String fangwuhx = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[1]/text()").toString();
            //所在楼层
            String suozailc = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[2]/text()").toString();
            //建筑面积
            String jianzhumj = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[3]/text()").toString();
            //户型结构
            String huxingjg = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[4]/text()").toString();
            //套内面积
            String taoneimj = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[5]/text()").toString();
            //建筑类型
            String jianzhulx = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[6]/text()").toString();
            //房屋朝向
            String fangwucx = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[7]/text()").toString();
            //建筑年代
            String jianzhund = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[8]/text()").toString();
            //建筑结构
            String jianzhujg = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[9]/text()").toString();
            //装修情况
            String zhuangxiuqk = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[10]/text()").toString();
            //梯户比例
            String tihubl = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[11]/text()").toString();
            //供暖方式
            String gongnuanfs = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[12]/text()").toString();
            //配备电梯
            String peibeidt = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[13]/text()").toString();
            //产权年限
            String chanquannx = page.getHtml().xpath("//div[@class='base']/div[@class='content']/ul/li[14]/text()").toString();
            
           //---------------------------------------交易属性-------transaction-----------------------------------------
            //上次交易时间
            String shangcijy = chengjiaosj;
            //交易权属
            String jiaoyiqs = page.getHtml().xpath("//div[@class='transaction']/div[@class='content']/ul/li[2]/text()").toString();
            //挂牌时间
            String guapaisj = page.getHtml().xpath("//div[@class='transaction']/div[@class='content']/ul/li[3]/text()").toString();
            //房屋用途
            String fangwuyt = page.getHtml().xpath("//div[@class='transaction']/div[@class='content']/ul/li[4]/text()").toString();
            //房屋年限
            String fangwunx = page.getHtml().xpath("//div[@class='transaction']/div[@class='content']/ul/li[5]/text()").toString();
            //产权所属
            String chanquanss = page.getHtml().xpath("//div[@class='transaction']/div[@class='content']/ul/li[6]/text()").toString();
           
            
            
           
            
            
            
            LianjiaFangwuxx fw = new LianjiaFangwuxx();
           
            
            fw.setFangwubh(fangwubh);
            if(zongjia!=null&&!"暂无数据".equals(zongjia))
            fw.setZongjia(Integer.parseInt(zongjia.trim()));
            fw.setDanjia((int)Math.round(fw.getZongjia()*10000/Double.parseDouble(jianzhumj.trim().replaceAll("㎡", ""))));
            fw.setXiaoqumc(xiaoqumc);
            fw.setSuozaics(CSDM);
            fw.setSuozaiq(suozaiq);
            fw.setSuozaisq(suozaisq);
            fw.setSuozaidtxl(suozaidtxl);
            if(kaifangcs!=null)
            fw.setKanfangcs(Integer.parseInt(kaifangcs));
            if(yueyuecs!=null)
            fw.setYuyuecs(Integer.parseInt(yueyuecs));
            //基本信息
            fw.setFangwuhx(fangwuhx);
            fw.setSuozailc(suozailc);
            if(jianzhumj!=null&&!"暂无数据".equals(jianzhumj.trim()))
            fw.setJianzhumj(new BigDecimal(jianzhumj.trim().replaceAll("㎡", "")));
            fw.setHuxingjg(huxingjg);
            if(taoneimj!=null&&!"暂无数据".equals(taoneimj.trim())){
            	System.out.println("taoneimj:"+taoneimj);
            fw.setTaoneimj(new BigDecimal(taoneimj.trim().replaceAll("㎡", "")));
            }
            fw.setJianzhulx(jianzhulx);
            fw.setFangwucx(fangwucx);
            fw.setJianzhujg(jianzhujg);
            fw.setZhuangxiuqk(zhuangxiuqk);
            fw.setTihubl(tihubl);
            fw.setGongnuanfs(gongnuanfs);
            fw.setPeibeidt(peibeidt);
            fw.setChanquannx(chanquannx);
            //交易信息
            if(guapaisj!=null)
            fw.setGuapaisj(guapaisj.trim().replaceAll("-", ""));
            fw.setJiaoyiqs(jiaoyiqs);
            if(shangcijy!=null)
            fw.setShangcijy(shangcijy.trim().replaceAll("-", ""));
            fw.setFangwuyt(fangwuyt);
            fw.setFangwunx(fangwunx);
            fw.setChanquanss(chanquanss);
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
            fw.setCaozuosj(format.format(new Date(System.currentTimeMillis())));
            fw.setLianjiedz(url);
            //成交信息
            fw.setShifoucj("1");
            if(chenjiaojj!=null)
            fw.setChengjiaodj(Integer.parseInt(chenjiaojj));
            if(chenjiaozj!=null)
            fw.setChengjiaozj(Integer.parseInt(chenjiaozj));
            fw.setChengjiaosj(chengjiaosj);
            if(chenjiaozq!=null)
            fw.setChengjiaozq(Integer.parseInt(chenjiaozq));
            
            page.putField("fangwuxx", fw);  //后面做数据的持久化
            
            
            
            
            //成交信息
            LianjiaFangwuxx updatefw = new LianjiaFangwuxx();
            updatefw.setFangwubh(fangwubh);
            updatefw.setShifoucj(fw.getShifoucj());
            updatefw.setChengjiaodj(fw.getChengjiaodj());
            updatefw.setChengjiaozj(fw.getChengjiaozj());
            updatefw.setChengjiaosj(fw.getChengjiaosj());
            updatefw.setChengjiaozq(fw.getChengjiaozq());
            updatefw.setLianjiedz(fw.getLianjiedz());
            updatefw.setZongjia(fw.getZongjia());//挂牌总价
            updatefw.setDanjia(fw.getDanjia());
            page.putField("updateFangwuxx", updatefw);  //后面做数据的持久化
        }    
    }
 

	
	public static void main(String[] args) {
		
		  String url = "https://tj.lianjia.com/chengjiao/101101878810.html";
		  
		    Pattern pattern1 = Pattern.compile("https://tj.lianjia.com/chengjiao/[a-z]+/pg(\\d*)?");
	        Matcher matcher1 = pattern1.matcher(url);
	        System.out.println(matcher1.find());
	       
	        
	        Pattern pattern2 = Pattern.compile("https://tj.lianjia.com/chengjiao/[a-z]+/$");
	        Matcher matcher2 = pattern2.matcher(url);
	        System.out.println(matcher2.find());
	        
	        Pattern pattern3= Pattern.compile("/chengjiao/\\d+.html$");
	        Matcher matcher3 = pattern3.matcher(url);
	        System.out.println(matcher3.find());
	        
	        
	        String chengjiaosj = "2017.09.27 链家成交";
	        chengjiaosj = chengjiaosj.split(" ")[0];
	        chengjiaosj = chengjiaosj.replaceAll("\\.", "");
	        System.out.println( Math.round((Integer.parseInt("100")*10000)/Double.parseDouble("33.3")));
	}
	
}