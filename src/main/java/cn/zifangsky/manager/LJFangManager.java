package cn.zifangsky.manager;

import java.util.List;

import cn.zifangsky.model.LianjiaDaikanfw;
import cn.zifangsky.model.LianjiaFangwuxx;
import cn.zifangsky.model.LianjiaTiaojiafw;

public interface LJFangManager {
	
	int deleteByPrimaryKey(String fangwubh);

    int insert(LianjiaFangwuxx record);

    int insertSelective(LianjiaFangwuxx record);

    LianjiaFangwuxx selectByPrimaryKey(String fangwubh);

    int updateByPrimaryKeySelective(LianjiaFangwuxx record);

    int updateByPrimaryKey(LianjiaFangwuxx record);
    
    List<String> getLianjiedz();
    
    void updateDaikanxx(List<LianjiaDaikanfw>  daikanList);
    
    void insertTiaojiaxx(LianjiaTiaojiafw tiaojiaxx);
    
}
