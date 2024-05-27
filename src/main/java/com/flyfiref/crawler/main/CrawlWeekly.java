package com.flyfiref.crawler.main;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flyfiref.crawler.pojo.Video;
import com.flyfiref.crawler.service.ReplyService;
import com.flyfiref.crawler.service.VideoService;
import com.flyfiref.crawler.util.HTTPUtils;
import java.io.IOException;
import java.util.Random;

//爬取每周必看的主程序，实现Runnable接口以开启多线程。
public class CrawlWeekly implements Runnable{
    private VideoService videoService;//videoService用于调用插入数据库的方法
    private ReplyService replyService;//同上，操作的是Reply数据库
    private String isReverse;//是否倒序爬取
    private int intPages;//要爬取的页数
    private int intAllPages;//总页数
    private String isAutoCrawlComments;//是否自动爬取评论
    private int autoCrawlCommentsPages;//自动爬取评论页数
    //构造方法
    public CrawlWeekly(String isReverse, int intPages, int intAllPages, String isAutoCrawlComments, int autoCrawlCommentsPages, VideoService videoService,ReplyService replyService) {
        this.isReverse = isReverse;
        this.intPages = intPages;
        this.intAllPages = intAllPages;
        this.isAutoCrawlComments = isAutoCrawlComments;
        this.autoCrawlCommentsPages = autoCrawlCommentsPages;
        this.videoService=videoService;
        this.replyService=replyService;
    }
    //启动的方法
    @Override
    public void run() {
        //如果倒序，就从最大页数开始；否则从第一页（最早的）开始
        if("true".equals(isReverse)){
            for(int i = 0;i<intPages;i++){
                crawl(intAllPages-i);
            }
        } else if ("false".equals(isReverse)) {
            for(int i = 1;i<=intPages;i++){
                crawl(i);
            }
        }
        System.out.println("爬取每周必看完成");
    }
    //爬取的程序
    private void crawl(int page){
        System.out.println("正在爬取第"+page+"期每周必看");
        String videoInfo= null;
        /*try {
            //睡一会（2s~3s），防止爬取过于频繁
            Thread.sleep(new Random().nextInt(1000)+2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/
        try {
            //访问api获取JSON信息
            videoInfo = HTTPUtils.getRawHtml("https://api.bilibili.com/x/web-interface/popular/series/one?number="+page);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //从JSON信息提取视频列表
        try{
            JSONArray list = JSONObject.parseObject(videoInfo).getJSONObject("data").getJSONArray("list");
            for(int i =0;i< list.size();i++) {
                //获取每一个字段
                JSONObject one = list.getJSONObject(i);
                String bvid = one.getString("bvid");//BV号
                String title = one.getString("title");//标题
                String upName = one.getJSONObject("owner").getString("name");//UP主
                String rcmd_reason = one.getString("rcmd_reason");//推荐原因
                String description = one.getString("desc");//视频简介
                JSONObject vidInfo = one.getJSONObject("stat");//视频信息，后面的字段都是这里面的
                Integer v_view = vidInfo.getInteger("view");//播放量
                Integer danmaku = vidInfo.getInteger("danmaku");//弹幕数
                Integer reply = vidInfo.getInteger("reply");//评论数
                Integer v_like = vidInfo.getInteger("like");//点赞数
                Integer coin = vidInfo.getInteger("coin");//投币数
                Integer fav = vidInfo.getInteger("favorite");//收藏数
                Integer share = vidInfo.getInteger("share");//转发量
                //封装成视频对象，并插入数据库
                Video v = new Video(bvid, title, upName, rcmd_reason, description, v_view, danmaku, reply, v_like, coin, fav, share);
                int r = videoService.add(v);
                //自动爬取视频评论
                if("true".equals(isAutoCrawlComments)){
                    new CrawlReply(bvid, autoCrawlCommentsPages, replyService).run();
                }
            }
        }catch (NullPointerException e){
            System.out.println("获取第"+page+"期每周必看失败，正在重试……");
            crawl(page);
        }
    }
}