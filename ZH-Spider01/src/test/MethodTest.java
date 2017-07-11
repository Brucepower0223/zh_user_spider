import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.whut.spider.crawler.Crawler;
import com.whut.spider.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by fangjin on 2017/6/29.
 */
public class MethodTest {

    @Test
    public void test1() {


        String html = "<div class='div1'> </div><div class='div1'> </div><div class='div1'> </div>";


        Document document = Jsoup.parse(html);
        Elements elements = document.select(".div1");
        System.out.println("elements = " + elements.size());
    }

    @Test
    public void testSetProxy() {

        HttpUtils.setProxy();

//        String htmlResult = HttpUtils.getHtmlFromUrl("https://www.zhihu.com/people/ninputer/activities");
//        System.out.println("html result = " + htmlResult);


    }


    @Test
    public void testCrawer() throws IOException {
        String url = "https://www.zhihu.com/people/fang-jing-24-57/activitiess";
        Crawler.crawler(url);
    }

    @Test
    public void testGetHrefAttr() {
        String html = "<a class='aclass' href='www.hao123.com'></a>"
                + "<a class='aclass' href='www.baidu.com'></a>"
                + "<a class='aclass' href='www.sina.com'></a>";
        Document document = Jsoup.parse(html);
        Elements elements = document.select(".aclass");
        for (Element element : elements) {
            System.out.println("elements" + element.attr("href"));
        }
    }

    /**
     * Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36
     * public BrowserVersion(String applicationName, String applicationVersion, String userAgent, float browserVersionNumeric) {
     * public static final BrowserVersion FIREFOX_24 = new BrowserVersion("Netscape", "5.0 (Windows)", "Mozilla/5.0 (Windows NT 6.1; rv:24.0) Gecko/20100101 Firefox/24.0", 24.0F, "FF24", (BrowserVersionFeatures[])null);
     */
    @Test
    public void testHttpUnit() {
        WebClient webClient = new WebClient(BrowserVersion.INTERNET_EXPLORER_11);
        // BrowserVersion browserVersion = new BrowserVersion()
//        webClient.getOptions().setCssEnabled(true);
//        webClient.getOptions().setJavaScriptEnabled(true);
        try {
            HtmlPage htmlPage =
                    webClient.getPage("https://www.zhihu.com/people/fang-jing-24-57/following");
            System.out.println(htmlPage.asText());
            webClient.closeAllWindows();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
