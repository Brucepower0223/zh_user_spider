package com.whut.spider.crawler;

import com.whut.spider.algorithm.BloomFilter;
import com.whut.spider.entity.ZhUser;
import com.whut.spider.utils.HttpUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.concurrent.*;

/**
 * Created by fangjin on 2017/6/29.
 */
public class Crawler {

    private static final String URL_PREFIX = "https://www.zhihu.com";

    public static String INIT_URL = "https://www.zhihu.com/people/fang-jing-24-57/activities";

    public static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static BlockingQueue<String> urlQuene = new LinkedBlockingQueue<>();

    //布隆去重算法
    public static BloomFilter bloomFilter = new BloomFilter();


    public static void main(String[] args) {
        try {
            startCrawl();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void startCrawl() throws InterruptedException {

        System.out.println("-------------start crawler---------------");
        urlQuene.put(INIT_URL);


        //开启5个线程，用来爬取用户
        for (int i = 0; i < 5; i++) {
            Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        String url = getUrlFromUrlQuene();
                        System.out.println("getUrlFromQuene url = " + url);
                        if (!bloomFilter.contains(url)) {
                            bloomFilter.add(url);
                            try {
                                crawler(url);
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("Exception");
                            }
                        } else {
                            System.out.println("the url has been crawled");
                        }
                    }
                }
            });
            threadPool.execute(t1);
        }

        Runnable listenRunnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int runningThread = ((ThreadPoolExecutor) threadPool).getActiveCount();
                    if (runningThread < 5) {
                        System.out.println("getUrlFromQuene url = ");
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String url = getUrlFromUrlQuene();
                                System.out.println("getUrlFromQuene url = " + url);
                                try {
                                    crawler(url);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }
        };

        Thread listenThread = new Thread(listenRunnable);
    }


    /**
     * 从url队列中拿到一个待抓取的url
     *
     * @return
     */
    public static String getUrlFromUrlQuene() {
        try {
            return urlQuene.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 将待抓取的用户页信息存入数据库
     *
     * @param url: 待抓去的url
     */
    public static void crawler(String url) throws IOException {
        System.out.println("start crawurl = " + url);
        String result = HttpUtils.getHtmlFromUrl(url);
        System.out.println("result = " + result);
        ZhUser zhUser = new ZhUser();

        Document document = Jsoup.parse(result);

        Elements elements;
        elements = document.select(".ProfileHeader-name");
        String name = elements.first().text();
        zhUser.setName(name);

        elements = document.select(".ProfileHeader-infoItem");
        int size = elements.size();
        if (size == 2) {
            String str1 = elements.first().text();
            //deal str1  互联网 茁壮 工程师
            if (str1 != null) {
                String[] s1 = str1.split(" ");

                if (s1.length > 0) {
                    String bussiness = s1[0];
                    zhUser.setBusiness(bussiness);
                }

                if (s1.length > 1) {
                    String company = s1[1];
                    zhUser.setCompany(company);
                }

                if (s1.length > 2) {
                    String position = s1[2];
                    zhUser.setPosition(position);
                }
            }
            //deal str2 武汉理工大学 电子商务
            String str2 = elements.get(1).text();
            if (str2 != null) {
                String[] s2 = str2.split(" ");
                if (s2.length > 0) {
                    zhUser.setUniversity(s2[0]);
                }

                if (s2.length > 1) {
                    zhUser.setMajor(s2[1]);
                }
            }
        }
        elements = document.select("a.Button.NumberBoard-item.Button--plain");

        for (Element element : elements) {
            String href = element.attr("href");
            if (href.contains("following")) {
                zhUser.setFollowingnum(element.child(1).text());
            }

            if (href.contains("followers")) {
                zhUser.setFollowersnum(element.child(1).text());

            }
        }


        elements = document.select("span.Tabs-meta");

        //answer
        zhUser.setAnswernum(elements.get(0).text());
        //ask
        zhUser.setAsknum(elements.get(1).text());
        //columns
        zhUser.setColumnnum(elements.get(2).text());
        //collection
        zhUser.setCollectionnum(elements.get(3).text());

        int maleSize = document.select("svg.Icon.Icon--male").size();
        int femaleSize = document.select("svg.Icon.Icon--female").size();


        //男性
        if (maleSize == 1 && femaleSize == 0) {
            zhUser.setSex(1);
        } else if (maleSize == 0 && femaleSize == 1) {
            //女性
            zhUser.setSex(0);
        } else {
            zhUser.setSex(1);
        }


        //将该用户关注人信息的url放入队列
        getUserFollowUrl(url, zhUser);

        System.out.println("用户抓取成功");

        // TODO: 2017/6/29 :未完待续，将获取的信息存入数据库

        System.out.println("zhUser =  " + zhUser.toString());
    }


    /**
     * 将每个人关注对象的url放入堵塞队列  https://www.zhihu.com/people/fang-jing-24-57/activities
     *
     * @param url    https://www.zhihu.com/people/tai-zai-yan/
     * @param zhUser 从url通过 crawler爬取的信息
     */
    public static void getUserFollowUrl(String url, ZhUser zhUser) throws IOException {
        String followingUrl = url.replace("activities", "following");

        String followingPage = HttpUtils.getHtmlFromUrl(followingUrl);
        Document document = Jsoup.parse(followingPage);
        Elements elements = null;

        int pageCount = 20;
        String userFollowing = zhUser.getFollowingnum();
        int userFollowingNum = 0;
        if (userFollowing != null && !userFollowing.equals(" ")) {
            userFollowingNum = Integer.valueOf(userFollowing);
        }

        int pageSize =
                (userFollowingNum % pageCount) > 0 ? (userFollowingNum / pageCount) + 1 : userFollowingNum / pageCount;
        for (int i = 0; i < pageSize; i++) {
            followingUrl += "?page=" + i;
            String result = HttpUtils.getHtmlFromUrl(followingUrl);
            Document document1 = Jsoup.parse(result);
            Elements listItem = document1.select(".List-item");
            for (Element item : listItem) {
                Elements links = item.select("a.UserLink-link");
                String peopleUrl = links.first().attr("href");
                peopleUrl = URL_PREFIX + peopleUrl + "/activities";
                try {
                    urlQuene.put(peopleUrl);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
