import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.FilePipeline;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class Door implements PageProcessor{
	private Site site = Site.me().setRetryTimes(3).setSleepTime(200);

    @Override
    public void process(Page page) {
    	page.addTargetRequests(page.getHtml().xpath("//a[@class='j_th_tit']").links().all());
    	page.addTargetRequests(page.getHtml().xpath("//div[@id='frs_list_pager']/a[@class='next']").links().all());
        //page.addTargetRequests(page.getHtml().links().regex("(http://tieba\\.baidu\\.com/\\w+/\\w+)").all());
    	page.putField("link", page.getUrl().toString());
        page.putField("title", page.getHtml().xpath("//div[@class='core_title core_title_theme_bright']/h1/text()").toString());
        page.putField("ID", page.getHtml().xpath("//li[@class='d_name']/a/text()"));
        if (page.getResultItems().get("ID")==null || page.getResultItems().get("title")==null ){
            //skip this page
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return site;
    }

    public static void main(String[] args) {
        Spider.create(new Door()).addUrl("http://tieba.baidu.com/f?ie=utf-8&kw=湖南科技大学吧&fr=search")
        	.addPipeline(new TTJsonPipeline("D:\\testdata\\"))
        	.thread(10)
        	.run();
    }
}
