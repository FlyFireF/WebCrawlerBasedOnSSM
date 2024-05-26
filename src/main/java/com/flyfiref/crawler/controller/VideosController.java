package com.flyfiref.crawler.controller;
import com.alibaba.fastjson.JSONObject;
import com.flyfiref.crawler.main.CrawlWeekly;
import com.flyfiref.crawler.pojo.Video;
import com.flyfiref.crawler.service.VideoService;
import com.flyfiref.crawler.util.HTTPUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
public class VideosController {
    @Autowired
    private VideoService videoService;
    /**
     * 获取每周必看总页数，请求api并解析JSON数据
     * @return 总页数
     * @throws IOException
     */
    @RequestMapping("/getAllWeeklyNum.do")
    public @ResponseBody int getAllWeeklyNum() throws IOException {
        String weeklyInfo = HTTPUtils.getRawHtml("https://api.bilibili.com/x/web-interface/popular/series/list");
        return JSONObject.parseObject(weeklyInfo).getJSONObject("data").getJSONArray("list").getJSONObject(0).getInteger("number");
    }
    /**
     * 开始爬取，先将前端传来的页数、总页数转换成int，然后开启新的线程爬取
     * 开启新的线程目的是防止前端阻塞，同时返回1作为信号给前端，前端收到后弹框说明后台正在爬取。
     * @param pages 要爬取的页数
     * @param isReverse 是否倒序爬取
     * @param allPages 总页数
     * @return 返回1作为信号给前端，前端收到后弹框说明后台正在爬取
     */
    @RequestMapping("/startCrawl.do")
    public @ResponseBody int startCrawl(
            @RequestParam("pages") String pages,
            @RequestParam("isReverse") String isReverse,
            @RequestParam("allPages") String allPages){
        int intPages = Integer.parseInt(pages);
        int intAllPages = Integer.parseInt(allPages);
        new Thread(new CrawlWeekly(isReverse,intPages,intAllPages,videoService)).start();
        return 1;
    }
    /**
     * 跳转页面
     * @return JSP页面
     */
    @RequestMapping("/showVideos")
    public String videos(){
        return "videos";
    }
    /**
     * 分页查询视频
     * @param pageNo 页号
     * @param pageSize 总页数
     * @return 结果集，包含总数和视频列表
     */
    @RequestMapping("/queryVideosWithCondition.do")
    public @ResponseBody Object queryVideosWithCondition(
            @RequestParam("pageNo") int pageNo,
            @RequestParam("pageSize") int pageSize){
        //封装查询条件和分页条件
        Map<String,Object> map=new HashMap<>();
        map.put("beginNo",(pageNo-1)*pageSize);
        map.put("pageSize",pageSize);
        //调用videoService的查询以及计数
        List<Video> videos = videoService.findVideosWithCondition(map);
        int count = videoService.count(map);
        //返回结果集，存储总数和视频列表
        Map<String,Object> retMap = new HashMap<>();
        retMap.put("videos",videos);
        retMap.put("count",count);
        return retMap;
    }
    /**
     * 查询视频详情
     * @param bvid BV号
     * @return 视频对象
     */
    @RequestMapping("/queryVideoByBvid.do")
    public @ResponseBody Object queryVideoByBvid(@RequestParam("bvid") String bvid){
        return videoService.queryVideoByBvid(bvid);
    }
    /**
     * 跳转到查看评论
     * @param bvid BV号
     * @param m Model对象，用于传递数据
     * @return JSP页面
     */
    @RequestMapping("/viewComments/{bvid}")
    public String viewComments(@PathVariable("bvid") String bvid, Model m){
        //把要查询的视频BV号传递给JSP
        m.addAttribute("bvid",bvid);
        return "reply";
    }
}