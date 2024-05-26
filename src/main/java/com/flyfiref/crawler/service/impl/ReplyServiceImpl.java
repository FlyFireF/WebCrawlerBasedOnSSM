package com.flyfiref.crawler.service.impl;
import com.flyfiref.crawler.mapper.ReplyMapper;
import com.flyfiref.crawler.pojo.Reply;
import com.flyfiref.crawler.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
@Service
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyMapper replyMapper;
    @Override
    public int add(Reply r) {
        return replyMapper.insert(r);
    }
    @Override
    public List<Reply> findRepliesWithCondition(Map<String, Object> map) {
        return replyMapper.selectRepliesWithCondition(map);
    }
    @Override
    public int count(Map<String, Object> map) {
        return replyMapper.count(map);
    }
}