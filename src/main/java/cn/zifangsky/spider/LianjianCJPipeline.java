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
@Component("lianjianCJPipeline")
public class LianjianCJPipeline implements Pipeline {
	
	
	@Resource(name="erShouFangManager")
	private LJFangManager erShouFangManager;
	
	/**
	 * 保存数据
	 */
	@Override
	public void process(ResultItems resultItems, Task task) {
		LianjiaFangwuxx fangwuxx = resultItems.get("fangwuxx");
		LianjiaFangwuxx updateFangwuxx = resultItems.get("updateFangwuxx");
		if (fangwuxx != null&&updateFangwuxx!=null) {
			LianjiaFangwuxx oldfangwuxx = erShouFangManager.selectByPrimaryKey(fangwuxx.getFangwubh());

			if (oldfangwuxx == null) {
				fangwuxx.setShifouxz("1");
				System.out.println("新增记录成功："+fangwuxx.getLianjiedz());
				erShouFangManager.insertSelective(fangwuxx);
			} else {
				updateFangwuxx.setShifouxz("0");
				System.out.println("更新记录成功："+fangwuxx.getLianjiedz());
				erShouFangManager.updateByPrimaryKeySelective(updateFangwuxx);
			}
		}
	}
}
