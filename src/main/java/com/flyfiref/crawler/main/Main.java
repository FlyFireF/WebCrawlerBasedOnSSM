package com.flyfiref.crawler.main;

import com.flyfiref.crawler.service.ReplyService;
import com.flyfiref.crawler.service.VideoService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

//手动爬取数据
public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        VideoService videoService = context.getBean("videoService", VideoService.class);
        ReplyService replyService = context.getBean("replyService", ReplyService.class);
        new Thread(new CrawlWeekly("true",270,270,"false",0,videoService,replyService)).start();
    }
}
