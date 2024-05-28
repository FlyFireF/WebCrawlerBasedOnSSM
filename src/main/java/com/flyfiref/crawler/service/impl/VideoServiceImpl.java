package com.flyfiref.crawler.service.impl;
import com.flyfiref.crawler.mapper.VideoMapper;
import com.flyfiref.crawler.pojo.Video;
import com.flyfiref.crawler.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
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
        //每周必看可能会有重复的视频，如果重复就跳过
        try{
            return videoMapper.insert(v);
        }catch (DuplicateKeyException e){
            return 0;
        }
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
    @Override
    public List<String> queryAllBVID() {
        return videoMapper.selectAllBVID();
    }
}