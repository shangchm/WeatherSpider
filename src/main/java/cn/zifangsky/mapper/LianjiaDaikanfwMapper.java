package cn.zifangsky.mapper;

import cn.zifangsky.model.LianjiaDaikanfw;

public interface LianjiaDaikanfwMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LianjiaDaikanfw record);

    int insertSelective(LianjiaDaikanfw record);

    LianjiaDaikanfw selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LianjiaDaikanfw record);

    int updateByPrimaryKey(LianjiaDaikanfw record);
}