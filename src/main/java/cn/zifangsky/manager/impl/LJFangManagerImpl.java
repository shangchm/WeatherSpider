package cn.zifangsky.manager.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.zifangsky.manager.LJFangManager;
import cn.zifangsky.mapper.LianjiaDaikanfwMapper;
import cn.zifangsky.mapper.LianjiaFangwuxxMapper;
import cn.zifangsky.mapper.LianjiaTiaojiafwMapper;
import cn.zifangsky.model.LianjiaDaikanfw;
import cn.zifangsky.model.LianjiaFangwuxx;
import cn.zifangsky.model.LianjiaTiaojiafw;
import cn.zifangsky.spider.ConfigUitl;

@Service("erShouFangManager")
public class LJFangManagerImpl implements LJFangManager {

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

	@Override
	public List<String> getLianjiedz(String flag) {
		List<String> dzlist = new ArrayList<String>();
		List<LianjiaFangwuxx> fwxxList = null;
		if("1".equals(flag)){
		    fwxxList = fangwuxx.getLianjiedz(flag);
		}else if("0".equals(flag)){
			 fwxxList = fangwuxx.getLianjiedz(flag);
		}else{
			 fwxxList = fangwuxx.getLianjieUpdatedz(flag);
		}
		for (LianjiaFangwuxx lianjiaFangwuxx : fwxxList) {
			dzlist.add(lianjiaFangwuxx.getLianjiedz());
		}
		return dzlist;
	}

	@Override
	public void updateDaikanxx(List<LianjiaDaikanfw> daikanList) {
		for (LianjiaDaikanfw daikanxx : daikanList) {

			LianjiaDaikanfw oldxq = daikanfw.selectOne(daikanxx);
			if (oldxq != null) {
				//什么也不做
				//System.out.println("已有带看记录："+oldxq.getId());
			} else {
				//System.out.println("新增带看记录："+daikanxx.getId());
				daikanfw.insertSelective(daikanxx);
			}
		}

	}

	@Override
	public void insertTiaojiaxx(LianjiaTiaojiafw tiaojiaxx) {
		System.out.println("新增调价记录成功："+tiaojiaxx.getFangwubh()+" "+tiaojiaxx.getTiaojialx());
		tiaojiaofw.insertSelective(tiaojiaxx);
	}

	@Override
	public String getRibao() {
		
		List<Map<String,Object>> zjxx = fangwuxx.getUpxx(ConfigUitl.getBeforDate(15));
		
		List<Map<String,Object>> jjxx = fangwuxx.getDownxx(ConfigUitl.getBeforDate(15));
		
		List<Map<String,Object>> cjxx = fangwuxx.getChengJiaoxx(ConfigUitl.getBeforDate(30));
		
		List<Map<String,Object>> dkxx = fangwuxx.getDaiKanxx(ConfigUitl.getBeforDate(15));
		
		List<Map<String,Object>> gpxx = fangwuxx.getGuaPaixx(ConfigUitl.getDate());
		Map map = new HashMap();
		map.put("tjsj", ConfigUitl.getDate());
		map.put("gpsj", ConfigUitl.getBeforDate(1));
		map.put("cjsj", ConfigUitl.getBeforDate(15));
		List<Map<String,Object>> ztxx = fangwuxx.getZongTixx(map);
		
		return null;
	}

}
