package cn.zifangsky.spider;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.zifangsky.manager.ErShouFangManager;
import cn.zifangsky.model.LianjiaFangwuxx;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 自定义Pipeline处理抓取的数据
 * @author zifangsky
 *
 */
@Component("lianjianCJPipeline")
public class LianjianCJPipeline implements Pipeline {
	
	
	@Resource(name="erShouFangManager")
	private ErShouFangManager erShouFangManager;
	
	/**
	 * 保存数据
	 */
	@Override
	public void process(ResultItems resultItems, Task task) {
		LianjiaFangwuxx fangwuxx = resultItems.get("fangwuxx");
		LianjiaFangwuxx CJfangwuxx = resultItems.get("CJfangwuxx");
		if (fangwuxx != null&&CJfangwuxx!=null) {
			LianjiaFangwuxx oldfangwuxx = erShouFangManager.selectByPrimaryKey(fangwuxx.getFangwubh());

			if (oldfangwuxx == null) {
				erShouFangManager.insertSelective(fangwuxx);
			} else {
				erShouFangManager.updateByPrimaryKeySelective(CJfangwuxx);
			}
		}
	}
}
