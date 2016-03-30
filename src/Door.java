import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.selenium.Global;
import us.codecraft.webmagic.downloader.selenium.SeleniumDownloader;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Door implements PageProcessor{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(200);

    @Override
    public void process(Page page) {
    	String QQ = page.getUrl().regex("[0-9]+").toString();
    	//System.out.println(page.getHtml());
    	//page.addTargetRequests(page.getHtml().xpath("//div[@class='f-nick']/a[@class='f-name']").links().all());
        //page.addTargetRequests(page.getHtml().links().regex("(http://tieba\\.baidu\\.com/\\w+/\\w+)").all());
    	page.putField("QQ", QQ);
    	if(QQ.equals(Global.hostQQ)){
    		page.addTargetRequests(page.getHtml().xpath("//div[@class='f-nick']/a[@class='f-name']").links().all());
        	page.putField("nick-name", page.getHtml().xpath("//div[@class='f-nick']/a[@class='f-name']/text()").all());
            page.putField("qq-number", page.getHtml().xpath("//div[@class='f-nick']/a[@class='f-name']").links().regex("[0-9]+").all());
    	}
    	else{
    		page.putField("nick-name", page.getHtml().xpath("//img[@class='q_namecard']/@alt").all());
            page.putField("qq-number", page.getHtml().xpath("//img[@class='q_namecard']/@link").regex("d_[0-9]+").regex("[0-9]+").all());
    	}
        /*if (page.getResultItems().get("ID")==null || page.getResultItems().get("title")==null ){
            //skip this page
            page.setSkip(true); 
        }*/
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
    	/*System.setProperty("webdriver.chrome.driver", "D:\\java\\chromedriver.exe");
    	WebDriver driver = new ChromeDriver();
    	driver.get("http://www.baidu.com/");
    	WebElement webElement = driver.findElement(By.xpath("/html"));
        System.out.println(webElement.getAttribute("outerHTML"));*/
    	Global.hostQQ = "8359251";
        Spider.create(new Door()).addUrl("http://user.qzone.qq.com/8359251")
        	.addPipeline(new TTJsonPipeline("D:\\QQSpider\\"))
        	.downloader(new SeleniumDownloader("D:\\java\\chromedriver.exe"))
        	.thread(1)
        	.run();
    }
}
