package com.test;

import org.junit.Test;
import com.mytest.controller.doApiRequest;


/**
 * @Author wanwei
 * @Date 2020/3/24 15:51
 * @Description
 * @Reviewer
 */
public class testdo {
    @Test
    public void testdoo(){
        System.out.println("执行api接口进行压力测试");
        String filePath = "E:\\data_test\\testdata\\data.txt";
//		String filePath="";
        String resp = "";

        try {
            //getUrl
		    resp = doApiRequest.run("https://blog.csdn.net/a200822146085", filePath, "getUrl");

            //getwithparam
//            resp = doApiRequest.run("http://www.baidu.com/s", filePath, "getUrl");

            //POST：application/UrlEncodedFormEntity
//            resp = doApiRequest.run("http://sdfg/sdfg", filePath="", "postUrlEncodedFormEntity");

            //POST：application/JSON
//            resp = doApiRequest.run("http://fsdfg.df.com/df", filePath="", "postUrlEncodedFormEntity");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println(resp);
    }
}

