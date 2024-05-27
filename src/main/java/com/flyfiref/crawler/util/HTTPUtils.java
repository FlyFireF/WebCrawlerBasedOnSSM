package com.flyfiref.crawler.util;

import java.io.IOException;
import org.apache.http.client.methods.HttpGet;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.AuthSchemes;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
/**
 * @author:合肥工业大学 管理学院 钱洋
 * @ 
 */
public abstract class HTTPUtils {
	static Builder builder = new Builder();
	public static String getRawHtml(String personalUrl) throws ParseException, IOException {
		HttpClient httpClient = HttpClients.custom().build(); 
		//获取响应文件，即html，采用get方法获取响应数据
		HttpGet getMethod = new HttpGet(personalUrl);
		//随机产生User-Agent
		getMethod.setHeader("User-Agent",builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)));
		getMethod.setHeader("Cookie","i-wanna-go-back=-1; buvid4=B55AD30A-B5C7-EE96-9814-E4C022B25B2443760-022012117-IKgYnehUfRYM1Sjdj%2ByQEw%3D%3D; DedeUserID=76705098; DedeUserID__ckMd5=e55b568f86f4f208; buvid_fp_plain=undefined; hit-new-style-dyn=1; LIVE_BUVID=AUTO6216846666312393; i-wanna-go-feeds=2; enable_web_push=DISABLE; blackside_state=0; CURRENT_BLACKGAP=0; header_theme_version=CLOSE; hit-dyn-v2=1; is-2022-channel=1; CURRENT_QUALITY=80; buvid3=BBAF5CA3-E6C1-FE3E-EB5E-7E7D81519EC600411infoc; b_nut=1710341100; _uuid=4952C725-4191-E971-36F9-517331382E7401941infoc; rpdid=|(J~J~Jkk|Ru0J'u~uu|JuJuJ; FEED_LIVE_VERSION=V_WATCHLATER_PIP_WINDOW3; CURRENT_FNVAL=4048; bp_video_offset_76705098=925239047675183187; fingerprint=612b7d1dd4148eefd033b01de1b7effe; buvid_fp=612b7d1dd4148eefd033b01de1b7effe; PVID=2; bp_t_offset_76705098=927153112733450262; home_feed_column=5; browser_resolution=1822-879; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTY4MjE3NjIsImlhdCI6MTcxNjU2MjUwMiwicGx0IjotMX0._c0QCWNOE8sdNA58ZTSBQubgfLlW1bKCtG0EsY_A-0g; bili_ticket_expires=1716821702; b_lsid=7E72A5D1_18FB77C1D75; SESSDATA=9dbe3634%2C1732322460%2Cbc6de%2A52CjCXfW5c4aiDIvZBand69hAUkPB-2n2jWM2gMTvpVyqvEJqfbK9d1fanYFdt_K3SEscSVmJWZGxjZnZ0V2ZrSl9KWG5wYy0xTHpkNDJKUTFGQWMtMnUxLWV1bHA5NWd6eUlDV2N0Z0FMVHJCa2ZERTRPUHB0alYtYVVYdFFVZkpEVmpJczhhS1ZBIIEC; bili_jct=89da6747d7744eb64b86a3df4ef89382; sid=7ozcvbp3");
		HttpResponse response = new BasicHttpResponse(HttpVersion.HTTP_1_1,
				HttpStatus.SC_OK, "OK");
		try {
			//执行get方法
			response = httpClient.execute(getMethod);
		} catch (IOException e) {
			e.printStackTrace();

		} finally {
			// getMethod.abort();
		}
		//获取响应状态码
		int StatusCode = response.getStatusLine().getStatusCode();
		//如果状态响应码为200，则获取html实体内容或者json文件
		String entity = "";
		if(StatusCode == 200){
			entity = EntityUtils.toString (response.getEntity(),"utf-8");
			EntityUtils.consume(response.getEntity());
		}else {
			//否则，消耗掉实体
			EntityUtils.consume(response.getEntity());
		}
		return entity;
	}
	/**
	 * 表头提交数据
	 * 
	 * @param URL
	 * @param nameValuePairList
	 * @return  
	 * @throws UnsupportedEncodingException 
	 * */
	public static String getHttpPost(String URL,List<NameValuePair> nameValuePairList) throws UnsupportedEncodingException  {
		RequestConfig defaultConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT)
				.setExpectContinueEnabled(true)
				.setTargetPreferredAuthSchemes(Arrays.asList(AuthSchemes.NTLM, AuthSchemes.DIGEST))
				.setProxyPreferredAuthSchemes(Arrays.asList(AuthSchemes.BASIC))
				.setConnectionRequestTimeout(20*1000)
				.setConnectTimeout(20*1000)
				.setSocketTimeout(20*1000)
				.build();
		HttpClient httpClient =  HttpClients.custom()
				.setDefaultRequestConfig(defaultConfig)
				.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
				.build();
		HttpPost httpost = new HttpPost(URL); //使用的请求方法
		//表单参数提交
		httpost.setEntity(new UrlEncodedFormEntity(nameValuePairList, HTTP.UTF_8));  
		//随机产生User-Agent
		httpost.setHeader("Accept-Encoding", "gzip, deflate, br");
		httpost.setHeader("Accept-Language", "zh-CN,zh;q=0.9,en;q=0.8");
		httpost.setHeader("Cache-Control", "max-age=0");
		httpost.setHeader("User-Agent", builder.userAgentList.get(new Random().nextInt(builder.userAgentSize)) );
		httpost.setHeader("Cookie","buvid3=BBAF5CA3-E6C1-FE3E-EB5E-7E7D81519EC600411infoc; b_nut=1710341100; _uuid=4952C725-4191-E971-36F9-517331382E7401941infoc; rpdid=|(J~J~Jkk|Ru0J'u~u|JRuuR|; DedeUserID=76705098; DedeUserID__ckMd5=e55b568f86f4f208; enable_web_push=DISABLE; header_theme_version=CLOSE; hit-dyn-v2=1; buvid4=EC00C477-7439-250B-D56F-FF01D18B38DF01552-024012313-fa%2FvZXAH7oFepNcIXhNUKQ%3D%3D; buvid_fp_plain=undefined; FEED_LIVE_VERSION=V_WATCHLATER_PIP_WINDOW3; CURRENT_FNVAL=4048; bp_video_offset_76705098=925239047675183187; CURRENT_QUALITY=64; home_feed_column=5; SESSDATA=aca32993%2C1731164903%2Cba3d2%2A52CjApFDCkYIvigkxA6l_hMsfKk5tY41U0M9lUfRX6uKA_4ovbN_mHfFx5P_7-yiFnIYcSVm5lWnVPQ2xyNVZrSW43VXdqaER4WHFmT1BNY1pFWXpzZWhrTDc4azRGNjhoUVN5Mk1Va0dILW9zeWt5ZW01SUhsdFFvNXNWb2ItWmg4MXhxOUN5TExRIIEC; bili_jct=2a288ff28950e847eb29f35e7ff993cf; sid=6mhyu15l; bili_ticket=eyJhbGciOiJIUzI1NiIsImtpZCI6InMwMyIsInR5cCI6IkpXVCJ9.eyJleHAiOjE3MTU4NzIxMTksImlhdCI6MTcxNTYxMjg1OSwicGx0IjotMX0.ba2tSyES1JtXY9deX8uNMlrwC0cOAUYNd3V6d5oAINI; bili_ticket_expires=1715872059; bp_t_offset_76705098=931436925997285395; PVID=1; b_lsid=443CC84A_18F7C5EF29B; browser_resolution=1828-870; fingerprint=ae7d5492425c6f673a66262a0eb2fe2a; buvid_fp=BBAF5CA3-E6C1-FE3E-EB5E-7E7D81519EC600411infoc");
		HttpResponse response = null;
		String res = "";
		try {  
			response = httpClient.execute(httpost); 
			res = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {  
			e.printStackTrace();  
		} finally {  
			//释放连接
			try {
				EntityUtils.consume(response.getEntity());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} //消耗实体
		}  
//		System.out.println(res);
		return res;
	}
	/**
	 * 封装请求头信息的静态类
	 */
	static class Builder{
		//设置userAgent库;读者根据需求添加更多userAgent
		String[] userAgentStrs = {
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36 Edg/123.0.0.0",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36 Edg/124.0.0.0",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/125.0.0.0 Safari/537.36",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/121.0",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/122.0",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/123.0",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/124.0",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:121.0) Gecko/20100101 Firefox/125.0",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64; rv:126.0) Gecko/20100101 Firefox/126.0",
		};
		List<String> userAgentList = Arrays.asList(userAgentStrs);
		int userAgentSize = userAgentList.size();
	}
}