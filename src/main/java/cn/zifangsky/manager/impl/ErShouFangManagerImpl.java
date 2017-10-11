package cn.zifangsky.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zifangsky.manager.ErShouFangManager;
import cn.zifangsky.mapper.LianjiaDaikanfwMapper;
import cn.zifangsky.mapper.LianjiaFangwuxxMapper;
import cn.zifangsky.mapper.LianjiaTiaojiafwMapper;
import cn.zifangsky.model.LianjiaFangwuxx;

@Service("erShouFangManager")
public class ErShouFangManagerImpl implements ErShouFangManager {

	@Autowired
	private LianjiaDaikanfwMapper  daikanfw;
	
	@Autowired
	private LianjiaFangwuxxMapper  fangwuxx;
	
	@Autowired
	private LianjiaTiaojiafwMapper  tiaojiaofw;
	
	@Override
	public int deleteByPrimaryKey(String fangwubh) {
		return fangwuxx.deleteByPrimaryKey(fangwubh);
	}

	@Override
	public int insert(LianjiaFangwuxx record) {
		return fangwuxx.insert(record);
	}

	@Override
	public int insertSelective(LianjiaFangwuxx record) {
		return fangwuxx.insertSelective(record);
	}

	@Override
	public LianjiaFangwuxx selectByPrimaryKey(String fangwubh) {
		return fangwuxx.selectByPrimaryKey(fangwubh);
	}

	@Override
	public int updateByPrimaryKeySelective(LianjiaFangwuxx record) {
		return fangwuxx.updateByPrimaryKeySelective(record);
	}

	@Override
	public int updateByPrimaryKey(LianjiaFangwuxx record) {
		return fangwuxx.updateByPrimaryKey(record);
	}

}
