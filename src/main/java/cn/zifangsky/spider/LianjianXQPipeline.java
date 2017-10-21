package cn.zifangsky.spider;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import cn.zifangsky.manager.LJXiaoQuManager;
import cn.zifangsky.model.LianjiaXiaoquxx;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

/**
 * 自定义Pipeline处理抓取的数据
 * @author zifangsky
 *
 */
@Component("lianjianXQPipeline")
public class LianjianXQPipeline implements Pipeline {
	
	
	@Resource(name="ljXiaoQuManager")
	private LJXiaoQuManager ljXiaoQuManager;
	
	/**
	 * 保存数据
	 */
	@Override
	public void process(ResultItems resultItems, Task task) {
		List<LianjiaXiaoquxx> xqlist = resultItems.get("xqlist");
		if (xqlist != null) {
			ljXiaoQuManager.updateLJXiaoQu(xqlist);
		}
	}
}
