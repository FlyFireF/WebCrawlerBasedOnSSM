package com.flyfiref.crawler.service.impl;
import com.flyfiref.crawler.mapper.VideoMapper;
import com.flyfiref.crawler.pojo.Video;
import com.flyfiref.crawler.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
//VideoService接口方法的实现，就是调用videoMapper执行具体操作
@Service("videoService")
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoMapper videoMapper;
    @Override
    public int add(Video v) {
        return videoMapper.insert(v);
    }
    @Override
    public List<Video> findVideosWithCondition(Map<String, Object> map) {
        return videoMapper.selectVideosWithCondition(map);
    }
    @Override
    public int count(Map<String,Object> map) {
        return videoMapper.count(map);
    }
    @Override
    public Video queryVideoByBvid(String bvid) {
        return videoMapper.selectVideoByBvid(bvid);
    }
}