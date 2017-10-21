package cn.zifangsky.manager.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zifangsky.manager.LJXiaoQuManager;
import cn.zifangsky.mapper.LianjiaXiaoquxxMapper;
import cn.zifangsky.model.LianjiaXiaoquxx;

@Service("ljXiaoQuManager")
public class LJXiaoQuManagerImpl implements LJXiaoQuManager {

	@Autowired
	private LianjiaXiaoquxxMapper xiaoquxx;
	
	@Override
	public void updateLJXiaoQu(List<LianjiaXiaoquxx> xiaoqulist) {
		for (LianjiaXiaoquxx lianjiaXiaoquxx : xiaoqulist) {
			
			LianjiaXiaoquxx xq = xiaoquxx.selectByPrimaryKey(lianjiaXiaoquxx.getXiaoqubm());
			if(xq!=null){
			   xiaoquxx.updateByPrimaryKeySelective(lianjiaXiaoquxx);
			}else{
			   xiaoquxx.insertSelective(lianjiaXiaoquxx);
			}
		}

	}

}
