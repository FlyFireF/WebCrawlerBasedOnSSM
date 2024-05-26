package com.flyfiref.crawler.service;
import com.flyfiref.crawler.pojo.Video;
import java.util.List;
import java.util.Map;
public interface VideoService {
    //插入视频到数据库
    int add(Video v);
    //根据分页条件查询视频列表
    List<Video> findVideosWithCondition(Map<String,Object> map);
    //查询视频总数
    int count(Map<String,Object> map);
    //根据bvid查询视频详情
    Video queryVideoByBvid(String bvid);
}