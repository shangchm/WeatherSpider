package cn.zifangsky.spider;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.zifangsky.manager.LJFangManager;
import cn.zifangsky.model.LianjiaDaikanfw;
import cn.zifangsky.model.LianjiaFangwuxx;
import cn.zifangsky.model.LianjiaTiaojiafw;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 自定义Pipeline处理抓取的数据
 * @author zifangsky
 *
 */
@Component("lianjianPipeline")
public class LianjianPipeline implements Pipeline {
	
	
	@Resource(name="erShouFangManager")
	private LJFangManager erShouFangManager;
	
	
	/**
	 * 保存数据
	 */
	@Override
	public void process(ResultItems resultItems, Task task) {
		LianjiaFangwuxx fangwuxx = resultItems.get("fangwuxx");
		List<LianjiaDaikanfw> dkfwList = resultItems.get("dkfwList");
		if (fangwuxx != null) {
			LianjiaFangwuxx oldfangwuxx = erShouFangManager.selectByPrimaryKey(fangwuxx.getFangwubh());

			if (oldfangwuxx == null) {
				fangwuxx.setShifouxz("1");
				erShouFangManager.insertSelective(fangwuxx);
				System.out.println("新增记录成功："+fangwuxx.getLianjiedz());
			} else {
				fangwuxx.setFangwubh(oldfangwuxx.getFangwubh());
				//fangwuxx.setShifouxz("0");
				erShouFangManager.updateByPrimaryKeySelective(fangwuxx);
				System.out.println("更新记录成功："+fangwuxx.getLianjiedz());
				int oldfj = oldfangwuxx.getZongjia();
				int newfj = fangwuxx.getZongjia();
				if(oldfj!=newfj){
					LianjiaTiaojiafw tiaojiaxx = new LianjiaTiaojiafw();
					tiaojiaxx.setFangwubh(fangwuxx.getFangwubh());
					tiaojiaxx.setCaozuosj(ConfigUitl.getDate());
					tiaojiaxx.setZongjia(fangwuxx.getZongjia());
					tiaojiaxx.setDangjia(fangwuxx.getDanjia());
				    tiaojiaxx.setTiaojiasj(ConfigUitl.getDate());
				    tiaojiaxx.setTiaojiaje(newfj-oldfj);
				   
				    if(newfj-oldfj<0){
				      tiaojiaxx.setTiaojialx("down");//降价
				    }else{
				      tiaojiaxx.setTiaojialx("up");//涨价
				    }
				    System.out.println("调价记录插入成功：调价"+(newfj-oldfj)+" 万");
				    erShouFangManager.insertTiaojiaxx(tiaojiaxx);
				}
					
			  
			}
			
			erShouFangManager.updateDaikanxx(dkfwList);
		}
	}
	
	
}
