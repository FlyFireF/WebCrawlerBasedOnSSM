package com.flyfiref.crawler.service;
import com.flyfiref.crawler.pojo.Reply;
import java.util.List;
import java.util.Map;
public interface ReplyService {
    //插入评论到数据库
    int add(Reply r);
    //根据条件查询评论
    List<Reply> findRepliesWithCondition(Map<String,Object> map);
    //查询评论总数
    int count(Map<String,Object> map);
}