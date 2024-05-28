package com.flyfiref.crawler.service.impl;
import com.flyfiref.crawler.mapper.ReplyMapper;
import com.flyfiref.crawler.pojo.Reply;
import com.flyfiref.crawler.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
@Service("replyService")
public class ReplyServiceImpl implements ReplyService {
    @Autowired
    ReplyMapper replyMapper;
    @Override
    public int add(Reply r) {
        //跳过重复
        try{
            return replyMapper.insert(r);
        }catch (DuplicateKeyException e){
            return 0;
        }
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