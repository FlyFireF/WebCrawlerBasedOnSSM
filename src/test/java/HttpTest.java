import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flyfiref.crawler.main.CrawlReply;
import com.flyfiref.crawler.pojo.Video;
import com.flyfiref.crawler.service.VideoService;
import com.flyfiref.crawler.service.impl.ReplyServiceImpl;
import com.flyfiref.crawler.util.HTTPUtils;
import jakarta.annotation.Resource;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

public class HttpTest {
    @Test
    public void testGetAndParse() throws IOException {
        String weeklyInfo = HTTPUtils.getRawHtml("https://api.bilibili.com/x/web-interface/popular/series/list");
        JSONObject json = JSONObject.parseObject(weeklyInfo);
        JSONObject data = (JSONObject) json.get("data");
        JSONArray list = (JSONArray)  data.get("list");
        JSONObject each = (JSONObject) list.get(0);
        System.out.println(each.get("number"));
    }
    @Test
    public void testCrawlWeekly() throws IOException {
        String videoInfo=HTTPUtils.getRawHtml("https://api.bilibili.com/x/web-interface/popular/series/one?number=1");
//        System.out.println(videoInfo);
        JSONObject json = JSONObject.parseObject(videoInfo);
        JSONObject data = json.getJSONObject("data");
        JSONArray list = data.getJSONArray("list");
        JSONObject one = list.getJSONObject(0);
        String bvid = one.getString("bvid");
        String title = one.getString("title");
        JSONObject upInfo = one.getJSONObject("owner");
        String upName = upInfo.getString("name");
        String rcmd_reason = one.getString("rcmd_reason");
        String description = one.getString("desc");
        JSONObject vidInfo = one.getJSONObject("stat");
        Integer v_view = vidInfo.getInteger("view");
        Integer danmaku = vidInfo.getInteger("danmaku");
        Integer reply = vidInfo.getInteger("reply");
        Integer v_like = vidInfo.getInteger("like");
        Integer coin = vidInfo.getInteger("coin");
        Integer fav = vidInfo.getInteger("favorite");
        Integer share = vidInfo.getInteger("share");
        Video v = new Video(bvid,title,upName,rcmd_reason,description,v_view,danmaku,reply,v_like,coin,fav,share);
//        System.out.println(v);
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        VideoService videoService = context.getBean("videoService",VideoService.class);
        videoService.add(v);
    }
    @Test
    public void testCrawlReply() throws IOException {

    }
}
