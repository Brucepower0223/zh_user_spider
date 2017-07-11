package com.whut.spider.utils;

import java.io.*;

/**
 * Created by fangjin on 2017/6/30.
 */
public class FileUtils {


    /**
     * 从本地的html获取待抓取的信息
     *
     * @param path
     * @return
     */
    public static String getStringFromFile(String path) {
        String result = null;
        BufferedReader reader = null;
        try {

            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
            String line;
            while ((line = reader.readLine()) != null) {
                result += line;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
        return result;
    }


}
