package org.tx_samples.bjdttc;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class BJDTTCProcessor implements PageProcessor{

    private Site site = Site.me().setCycleRetryTimes(5).setRetryTimes(5).setSleepTime(500).setTimeOut(3 * 60 * 1000)
            .setUserAgent("Mozilla/5.0 (Windows NT 6.1; WOW64; rv:38.0) Gecko/20100101 Firefox/38.0")
            .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8")
            .addHeader("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
            .setCharset("UTF-8");
	
    static List<String> avoidTag = new ArrayList<String>();
    static{
    	avoidTag.add("script");
    	avoidTag.add("link");
    }
	@Override
	public void process(Page page) {
		// TODO Auto-generated method stub
		page.putField("texts", page.getHtml().xpath("//section//text()").all());
		Element doc = page.getHtml().getDocument().body();
		JSONObject jo = parse2JSON(doc);
		
		page.putField("2json", jo);
	}
	
	public JSONObject parse2JSON(Element element){
		JSONObject json = new JSONObject();
		buildAttribute(json, element, "id");
		buildAttribute(json, element, "class");
		//buildAttribute(json, element, "style");
		buildAttribute(json, element, "data-src");
		json.put("tagName", element.tagName());
		String ownText = element.ownText();
		//if(!"".equals(ownText)){
			json.put("owntext", element.text());
		//}
		Elements children = element.children();
		if(children.size()<=0){
			return json;
		}else{
			JSONArray childrenJson = new JSONArray();
			Iterator<Element> it = children.iterator();
			while(it.hasNext()){
				Element child = it.next();
				if(avoidTag.contains(child.tagName().toLowerCase())){
					continue;
				}else{
					childrenJson.add(parse2JSON(child));
				}
			}
			json.put("children", childrenJson);
		}
		
		return json;
	}
	
	public void buildAttribute(JSONObject json, Element element, String attr){
		
		String attrVal = element.attr(attr);
		if(StringUtils.isEmpty(attrVal)){
			return ;
		}
		json.put(attr, attrVal);
		return ;
	}

	@Override
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	
	public static void main(String[] args) {
		//Spider.create(new BJDTTCProcessor())
		//.addUrl("https://mp.weixin.qq.com/s/eAEDQDCRkFs_GAkK_8wIZw").thread(1).run();
		Spider.create(new BJDTTCProcessor())
		.addUrl("https://mp.weixin.qq.com/s/mPvSejf35kgXYrzV0Q2__A").thread(1).run();
		
	}

	@Override
	public String toString() {
		return "BJDTTCProcessor [site=" + site + "]";
	}
	
}
