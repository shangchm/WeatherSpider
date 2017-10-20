package cn.zifangsky.mapper;

import cn.zifangsky.model.LianjiaXiaoquxx;

public interface LianjiaXiaoquxxMapper {
    int deleteByPrimaryKey(String xiaoqubm);

    int insert(LianjiaXiaoquxx record);

    int insertSelective(LianjiaXiaoquxx record);

    LianjiaXiaoquxx selectByPrimaryKey(String xiaoqubm);

    int updateByPrimaryKeySelective(LianjiaXiaoquxx record);

    int updateByPrimaryKey(LianjiaXiaoquxx record);
}