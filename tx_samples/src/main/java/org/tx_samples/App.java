package org.tx_samples;

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

/**
 * Hello world!
 *
 */
public class App 
{
	
	//MAIGOO
	static String cataUrl = "https://www.maigoo.com/ajaxstream/category/?action=searchblowcategory&catid=7&block=brand";
	//www.chinapp.com
	static String pinpai = "https://www.chinapp.com/pinpai/muyingyongpinlei-0-0-";
	static String catagory = "母婴用品";
	static int totalPage = 174		;
	
    public static void main( String[] args ) throws Exception
    {
    	RequestConfig requestConfig = 
                RequestConfig.custom().setConnectTimeout(5000).setConnectionRequestTimeout(1000).build();  
    	
    	CloseableHttpClient  hc = HttpClientBuilder.create().setDefaultRequestConfig(requestConfig).build();
    	CloseableHttpResponse res = null;
    	File target = new File("D:/chinaapp_pinpai_"+catagory+".sql");
		FileOutputStream output = new FileOutputStream(target);
		OutputStreamWriter write = new OutputStreamWriter(output,"UTF-8");
    	if(!target.exists()) {
			target.createNewFile();
		}
    	int index = 1;
    	
    	for(int i=1;i<=totalPage;i++) {
    		System.out.print("【page】："+pinpai+i);
    		HttpGet hg = new HttpGet(pinpai+i);
    		res = hc.execute(hg);
    		HttpEntity he = res.getEntity();
    		
    		String e =  EntityUtils.toString(he) ;
    		Document doc = Jsoup.parse(e);
    		//"//li[@class=pplb_item]
    		Elements as = doc.getElementsByClass("pplb_item");
    		Iterator<Element> ai = as.iterator();
    		while(ai.hasNext()) {
    			Element li = ai.next();
    			Element a = li.child(0);
    			String href = a.attr("href");
    			String name = a.attr("title");
    			Element logoimg = a.child(0);
    			String logourl = logoimg.attr("src");
    			String sql = "Insert into chinaapp_pinpai (name, href, logourl, gatagory) values "
    					+ "('"+name+"','"+href+"','"+logourl+"','"+catagory+"');\n";
    			
    			System.out.print("【"+index+"】："+sql);
    			index ++ ;
    			write.write(sql);
    		}
    		Thread.sleep(Math.round(Math.random()*5000));
    	}
    	write.flush();
		write.close();
        //System.out.println(e);
    }
    
}
