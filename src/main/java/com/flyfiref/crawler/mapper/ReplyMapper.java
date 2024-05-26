package com.flyfiref.crawler.mapper;
import com.flyfiref.crawler.pojo.Reply;
import java.util.List;
import java.util.Map;
public interface ReplyMapper {
    int insert(Reply r);
    List<Reply> selectRepliesWithCondition(Map<String,Object> map);
    int count(Map<String,Object> map);
}