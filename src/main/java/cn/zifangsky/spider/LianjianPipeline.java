package cn.zifangsky.spider;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.zifangsky.manager.LJFangManager;
import cn.zifangsky.model.LianjiaFangwuxx;
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
		if (fangwuxx != null) {
			LianjiaFangwuxx oldfangwuxx = erShouFangManager.selectByPrimaryKey(fangwuxx.getFangwubh());

			if (oldfangwuxx == null) {
				fangwuxx.setShifouxz("1");
				erShouFangManager.insertSelective(fangwuxx);
			} else {
				fangwuxx.setFangwubh(oldfangwuxx.getFangwubh());
				fangwuxx.setShifouxz("0");
				erShouFangManager.updateByPrimaryKeySelective(fangwuxx);
			}
		}
	}
	
	
}
