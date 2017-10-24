package cn.zifangsky.mapper;

import java.util.List;

import cn.zifangsky.model.LianjiaFangwuxx;

public interface LianjiaFangwuxxMapper {
    int deleteByPrimaryKey(String fangwubh);

    int insert(LianjiaFangwuxx record);

    int insertSelective(LianjiaFangwuxx record);

    LianjiaFangwuxx selectByPrimaryKey(String fangwubh);

    int updateByPrimaryKeySelective(LianjiaFangwuxx record);

    int updateByPrimaryKey(LianjiaFangwuxx record);
    
    List<LianjiaFangwuxx> getLianjiedz(String flag);
}