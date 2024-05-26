package com.flyfiref.crawler.main;

import com.alibaba.fastjson.JSONObject;
import com.flyfiref.crawler.service.ReplyService;
import com.flyfiref.crawler.util.HTTPUtils;

import java.io.IOException;
import java.util.Map;

public class CrawlComments implements Runnable{
    private ReplyService replyService;
    private String bvid;
    private int intPages;
    private String oid;

    public CrawlComments(String bvid, int intPages,ReplyService replyService) {
        this.replyService = replyService;
        this.bvid = bvid;
        this.intPages = intPages;
        this.oid=getOid(bvid);
    }
    private String getOid(String bvid){
        String r= null;
        try {
            r = HTTPUtils.getRawHtml("https://api.bilibili.com/x/web-interface/view?bvid="+bvid);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JSONObject data = JSONObject.parseObject(r).getJSONObject("data")
       return ;
    }
    @Override
    public void run() {
        Map<String,Object> data = getData();
        Integer replyNum = (Integer) data.get("replyNum");
        JSONObject replies = (JSONObject)data.get("replies");
        getReply(replies);
    }
    public Map<String,Object> getData(){
        return ;
    }
    public void getReply(JSONObject replies){

    }
}
