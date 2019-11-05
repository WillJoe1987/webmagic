package us.codecraft.webmagic.model.samples;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.TargetUrl;

/**
 * @author code4crafter@gmail.com
 */
@TargetUrl("https://www.chinapp.com/pinpai/canyin-0-0-[1-9]*")
//@ExtractBy(value = "//ul[@class=pplb_items_lists]/")
public class QQMeishi {

    @ExtractBy("//li[@class=pplb_item]//a/@title")
    private String shopName;

//    @ExtractBy("//div[@class=info]/a[@class=title]/text()")
//    private String promo;

    public static void main(String[] args) {
        OOSpider.create(Site.me(), new ConsolePageModelPipeline(), QQMeishi.class).addUrl("https://www.chinapp.com/pinpai/canyin-0-0-1").thread(4).run();
    }

}
