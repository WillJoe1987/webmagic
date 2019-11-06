package org.tx_samples.bjdttc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class YonghuiApp {

	private String yonghuiUrl = "http://www.yonghui.com.cn/mapi/proajax?act=2&pid=0&ctlgid=0&keyword=&rnd=761.0999147537441&p=";
	
	int totalPage = 167;
	
	private String fileName = "d:/永辉超市数据.txt";
	
	public void createFile() throws Exception{
    	RequestConfig requestConfig = 
                RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).build();    	
    	CloseableHttpClient  hc = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    	CloseableHttpResponse res = null;
    	File target = new File(fileName);
    	if(!target.exists()) {
    		target.createNewFile();
    	}
		FileOutputStream output = new FileOutputStream(target);
		OutputStreamWriter write = new OutputStreamWriter(output,"UTF-8");
    	String head = "title,addr,tel,opendate,bus,logourl\n";
    	write.write(head);
    	
    	int index = 1;
    	
    	for(int i=1;i<=totalPage;i++) {
    		System.out.print("【page】："+yonghuiUrl+i+"\n");
    		HttpGet hg = new HttpGet(yonghuiUrl+i);
    		res = hc.execute(hg);
    		HttpEntity he = res.getEntity();
    		
    		String e =  EntityUtils.toString(he) ;
    		//System.out.println(e.substring(4));
    		Document doc = Jsoup.parse(e.substring(4));
    		//"//li[@class=pplb_item]
    		Elements as = doc.getElementsByTag("li");
    		Iterator<Element> ai = as.iterator();
    		while(ai.hasNext()) {
    			Element li = ai.next();
    			Element img = li.child(0);
    			String logourl = img.attr("src");
    			Element div1 = li.child(1);
    			Element h1 = div1.child(0);
    			String title = h1.text();
    			title = title.split(" ")[1];
    			Element span = div1.child(1);
    			String addr = getAttr(span.text().split(" ")[0]);
    			String tel = getAttr(span.text().split(" ")[1]);
    			String opendate = getAttr(span.text().split(" ")[2]);
    			String bus = getAttr(span.text().split(" ")[3]);
    			String line = title +","+addr+","+tel+","+opendate+","+bus+","+logourl;
    			System.out.println("【"+index+"】"+line);
    			write.write(line+"\n");
    			index ++ ;
    		}
    		Thread.sleep(Math.round(Math.random()*5000));
    	}
    	write.flush();
		write.close();
        //System.out.println(e);
	}
	
	private String getAttr(String string) {
		if(string.indexOf("：")<=0) return "";
		String[] sp = string.split("：");
		if(sp.length<2) {
			return "";
		}
		return sp[1];
	}
	
	
	public static void main(String[] args) throws Exception {
		YonghuiApp ya = new YonghuiApp();
		ya.createFile();
	}
	
}
