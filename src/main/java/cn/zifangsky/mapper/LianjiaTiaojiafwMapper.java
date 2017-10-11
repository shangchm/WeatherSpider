package cn.zifangsky.mapper;

import cn.zifangsky.model.LianjiaTiaojiafw;

public interface LianjiaTiaojiafwMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LianjiaTiaojiafw record);

    int insertSelective(LianjiaTiaojiafw record);

    LianjiaTiaojiafw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LianjiaTiaojiafw record);

    int updateByPrimaryKey(LianjiaTiaojiafw record);
}