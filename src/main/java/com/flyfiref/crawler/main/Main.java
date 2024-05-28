package com.flyfiref.crawler.main;
import com.flyfiref.crawler.service.ReplyService;
import com.flyfiref.crawler.service.VideoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import java.util.List;
//手动爬取数据
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        //获取操作数据库的Service对象
        VideoService videoService = context.getBean("videoService", VideoService.class);
        ReplyService replyService = context.getBean("replyService", ReplyService.class);
        //new Thread(new CrawlWeekly("true",170,170,"false",0,videoService,replyService)).start();
        List<String> bvidList = videoService.queryAllBVID();
        System.out.println("len(bvidList)->"+bvidList.size());
        //获取数据库所有视频的评论
        for(String bvid : bvidList){
            new CrawlReply(bvid,10000,replyService).run();
        }
    }
}