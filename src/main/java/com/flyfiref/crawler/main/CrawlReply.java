package com.flyfiref.crawler.main;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flyfiref.crawler.pojo.Reply;
import com.flyfiref.crawler.service.ReplyService;
import com.flyfiref.crawler.util.HTTPUtils;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
//爬取评论
public class CrawlReply implements Runnable{
    private ReplyService replyService;//replyService用于调用插入数据库的方法
    private String bvid;//BVID
    private int intPages;//要爬取的页数
    private String oid;//调用的评论api使用的不是bvid，而是这个叫作“oid”的变量，应该是B站之前的av号，这需要通过另一个api根据bvid获取
    //构造方法
    public CrawlReply(String bvid, int intPages, ReplyService replyService) {
        this.replyService = replyService;
        this.bvid = bvid;
        this.intPages = intPages;
        this.oid=getOid(bvid);//根据bvid获取oid
    }
    //根据bvid获取oid
    private String getOid(String bvid){
        System.out.println("正在爬取视频：" + bvid);
        String r= null;
        try {
            //访问获取oid的api
            r = HTTPUtils.getRawHtml("https://api.bilibili.com/x/web-interface/view?bvid="+bvid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //获取oid并返回
        JSONObject data = JSONObject.parseObject(r).getJSONObject("data");
        String oid = data.getString("aid");
        System.out.println(bvid+"对应的oid是："+oid);
        return oid;
    }
    //启动的方法
    @Override
    public void run() {
        int page = 1;
        int endPage=0;
        //这个循环根据是否到最后一页或指定的页数跳出
        while (true){
            System.out.println("正在爬取第"+page+"页评论");
            //获取评论JSON和评论数
            Map<String,Object> data = getData(page, oid);
            Integer replyNum = (Integer) data.get("replyNum");
            JSONArray replies = (JSONArray)data.get("replies");
            //从评论JSON提取字段
            getReply(replies);
            //计算评论总页数，取总页数与指定的最大页数这两者的最小值作为终止的页数
            endPage = Math.min(replyNum / 20 + 1, intPages);
            //到最后一页就跳出循环
            if(page==endPage) break;
            page+=1;
        }
        System.out.println("爬取评论完成");
    }
    //获取评论JSON和评论数
    public Map<String,Object> getData(int page, String oid){
        String r = null;
        try {
            //睡一会（2s~3s），防止爬取过于频繁
            Thread.sleep(new Random().nextInt(1000)+2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        try {
            //通过api获取评论JSON和评论数
            r=HTTPUtils.getRawHtml("https://api.bilibili.com/x/v2/reply?jsonp=jsonp&pn="+page+"&type=1&oid="+oid+"&sort=2&_="+System.currentTimeMillis());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //解析JSON提取数据并返回
        JSONObject replyData = JSONObject.parseObject(r);
        Map<String,Object> data = new HashMap<>();
        data.put("replyNum",replyData.getJSONObject("data").getJSONObject("page").getInteger("count"));
        data.put("replies",replyData.getJSONObject("data").getJSONArray("replies"));
        //System.out.println("成功获取oid为"+oid+"的评论。");
        return data;
    }
    //从评论JSON提取字段
    public void getReply(JSONArray replies){
        for(int i =0;i< replies.size();i++){
            JSONObject one = replies.getJSONObject(i);
            String uname = one.getJSONObject("member").getString("uname");//用户名
            String content = one.getJSONObject("content").getString("message");//评论内容
            Integer r_like = one.getInteger("like");//点赞数
            Long ctime = one.getLong("ctime");//评论时间（时间戳）
            //封装成对象，并插入数据库
            Reply r = new Reply(null,bvid,r_like,content,uname,ctime);
            int ret = replyService.add(r);
        }
    }
}