package com.whut.spider.utils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by fangjin on 2017/6/29.
 */
public class HttpUtils {


    public static List<String> ipList = new ArrayList<>();

    private static final String PROXY_IP_PATH = "/Users/fangjin/IdeaProjects/ZH-Spider/src/main/resources/proxyip.txt";


    public static String getHtmlFromUrl(String url) throws IOException {
        HttpUtils.setProxy();

        BufferedReader reader = null;
        String result = null;
        try {
            URL requestUrl = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) requestUrl.openConnection();
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_0) AppleWebKit/537.36 " +
                            "(KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36");
            connection.setConnectTimeout(5000);
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }


    public static void setProxy() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(PROXY_IP_PATH)));
            String line;
            while ((line = reader.readLine()) != null) {
                ipList.add(line);
            }

            Random random = new Random();
            String ip = ipList.get(random.nextInt(ipList.size()));

            String proxyIp = ip.split(":")[0];
            String proxyPort = ip.split(":")[1];

            System.getProperties().setProperty("proxySet", "true");
            System.getProperties().setProperty("proxyHost", proxyIp);
            System.getProperties().setProperty("proxyPort", proxyPort);


            System.out.println("proxyHost = " + proxyIp + ",proxyPort = " + proxyPort);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

}
