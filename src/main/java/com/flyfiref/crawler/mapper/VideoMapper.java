package com.flyfiref.crawler.mapper;
import com.flyfiref.crawler.pojo.Video;
import java.util.List;
import java.util.Map;
//方法作用同Service的，不再重复注释了
public interface VideoMapper {
    int insert(Video v);
    List<Video> selectVideosWithCondition(Map<String,Object> map);
    int count(Map<String,Object> map);
    Video selectVideoByBvid(String bvid);
    List<String> selectAllBVID();
}