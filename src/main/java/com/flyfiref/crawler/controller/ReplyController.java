package com.flyfiref.crawler.controller;
import com.flyfiref.crawler.main.CrawlReply;
import com.flyfiref.crawler.pojo.Reply;
import com.flyfiref.crawler.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class ReplyController {
    @Autowired
    ReplyService replyService;
    /**
     * 根据bvid获取评论
     * @param bvid
     * @param pages 评论页数
     * @return 返回1作为信号给前端，前端收到后弹框说明后台正在爬取
     */
    @RequestMapping("/getComments.do")
    public @ResponseBody int getComments(
            @RequestParam("bvid") String bvid,
            @RequestParam("pages") String pages){
        int intPages = Integer.parseInt(pages);
        new Thread(new CrawlReply(bvid,intPages,replyService)).start();
        return 1;
    }
    /**
     * 根据分页信息和bvid查询评论
     * @param pageNo
     * @param pageSize
     * @param bvid
     * @return 评论集
     */
    @RequestMapping("/queryRepliesWithCondition.do")
    public @ResponseBody Object queryRepliesWithCondition(
            @RequestParam("pageNo") int pageNo,
            @RequestParam("pageSize") int pageSize,
            @RequestParam("bvid") String bvid){
        //封装查询条件和分页条件
        Map<String,Object> map=new HashMap<>();
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        map.put("bvid",bvid);
        //查询和计数
        List<Reply> replyList = replyService.findRepliesWithCondition(map);
        int count = replyService.count(map);
        //封装结果集
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("replyList",replyList);
        retMap.put("count",count);
        return retMap;
    }
}