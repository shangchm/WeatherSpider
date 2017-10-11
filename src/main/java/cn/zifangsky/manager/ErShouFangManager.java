package cn.zifangsky.manager;

import cn.zifangsky.model.LianjiaFangwuxx;

public interface ErShouFangManager {
	
	int deleteByPrimaryKey(String fangwubh);

    int insert(LianjiaFangwuxx record);

    int insertSelective(LianjiaFangwuxx record);

    LianjiaFangwuxx selectByPrimaryKey(String fangwubh);

    int updateByPrimaryKeySelective(LianjiaFangwuxx record);

    int updateByPrimaryKey(LianjiaFangwuxx record);

}
