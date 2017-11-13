package cn.zifangsky.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.zifangsky.model.LianjiaFangwuxx;

public interface LianjiaFangwuxxMapper {
    int deleteByPrimaryKey(String fangwubh);

    int insert(LianjiaFangwuxx record);

    int insertSelective(LianjiaFangwuxx record);

    LianjiaFangwuxx selectByPrimaryKey(String fangwubh);

    int updateByPrimaryKeySelective(LianjiaFangwuxx record);

    int updateByPrimaryKey(LianjiaFangwuxx record);
    
    List<LianjiaFangwuxx> getLianjiedz(String flag);
    
    List<LianjiaFangwuxx> getLianjieUpdatedz(String datetime);
    
    /**
     * 根据日期获取近日涨跌房屋数量信息
     * @param riqi  大于riqi
     * @param tiaojialx 调价类型 up or down
     * @return
     */
    List<Map<String,Object>> getUpxx(String riqi);
    
    /**
     * @param riqi
     * @return
     */
    List<Map<String,Object>> getDownxx(String riqi);
   
    /**
     * 根据日期获取近日带看数量信息
     * @param riqi
     * @return
     */
    List<Map<String,Object>> getDaiKanxx(String riqi);
    
    /**
     * 根据日期获取近日成交数量信息
     * @param riqi
     * @return
     */
    List<Map<String,Object>> getChengJiaoxx(String riqi);
    
    /**
     * 根据日期获该日挂牌数量信息
     * @param riqi
     * @return
     */
    List<Map<String,Object>> getGuaPaixx(String riqi);
    
    
    /**
     * 根据日期获该日总体信息
     * @param riqi
     * @return
     */
    List<Map<String,Object>> getZongTixx(Map map);
    
    
}