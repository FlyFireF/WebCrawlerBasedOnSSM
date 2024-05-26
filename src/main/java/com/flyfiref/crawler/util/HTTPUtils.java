package util;

import java.io.IOException;
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
import org.apache.http.client.methods.HttpGet;
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
	 * @param url
	 * @param params
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
		String[] userAgentStrs = {"Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_8; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
				"Mozilla/5.0 (Windows; U; Windows NT 6.1; en-us) AppleWebKit/534.50 (KHTML, like Gecko) Version/5.1 Safari/534.50",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; Trident/7.0; .NET4.0C; .NET4.0E; .NET CLR 2.0.50727; .NET CLR 3.0.30729; .NET CLR 3.5.30729; InfoPath.3; rv:11.0) like Gecko",
				"Mozilla/5.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)",
				"Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0; Trident/4.0)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 6.0)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; Trident/4.0; SE 2.X MetaSr 1.0; SE 2.X MetaSr 1.0; .NET CLR 2.0.50727; SE 2.X MetaSr 1.0)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; 360SE)",
				"Mozilla/4.0 (compatible; MSIE 7.0; Windows NT 5.1; TencentTraveler 4.0)",
		"Opera/9.80 (Macintosh; Intel Mac OS X 10.6.8; U; en) Presto/2.8.131 Version/11.11"};
		List<String> userAgentList = Arrays.asList(userAgentStrs);
		int userAgentSize = userAgentList.size();
	}
}