package com.mytest.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import com.mytest.common.DataEncypt;
/**
 * @Author wanwei
 * @Date 2020/3/24 15:21
 * @Description
 * @Reviewer
 */
public class doApiRequest {
    private static Logger logger = LoggerFactory.getLogger(doApiRequest.class);

    private static final int CONNECTION_TIME_OUT = 100000;

    private static final int SOCKET_TIME_OUT = 100000;

    private static final RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SOCKET_TIME_OUT)
            .setConnectTimeout(CONNECTION_TIME_OUT).build();

    public static void main(String[] args) throws Exception {
        System.out.println("执行api接口进行压力测试");
        String filePath = "E:\\data_test\\testdata\\data.txt";
//		String filePath="";
		String resp = "";

		//getUrl
//		resp = run("https://blog.csdn.net/a200822146085", filePath, "getUrl");

        //getwithparam
//        resp = run("http://www.baidu.com/s", filePath, "getUrl");

        //POST：application/UrlEncodedFormEntity
//        resp = run("http://sdfg/sdfg", filePath="", "postUrlEncodedFormEntity");

        //POST：application/JSON
        resp = run("http://fsdfg.df.com/df", filePath="", "postUrlEncodedFormEntity");

        System.out.println(resp);
    }

    public static String run(String url, String filePath, String encryptType) throws Exception {

        String resp = null;

        if(encryptType.equals("postJson")){
            Random r = new Random();
            int lineNums = getFileLineCounts(filePath);
            int i = r.nextInt(lineNums);
            List<String> paramsList = readDataFromText(filePath);

            String data = paramsList.get(i).toString();
//		    System.out.println(i+datas);
            System.out.println(filePath + "......line....." + i + " ......resp.....");

            try {
                resp = do_post2(url, data);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(encryptType.equals("postUrlEncodedFormEntity")){
            Map<String, String> datas = new HashMap<String,String>();
            try {
                String aes_data = "2ff10ff50e095cc0fffe939e73a4ae2fd5bfdbe4b3896bbdfd4dcd600b501960ee5d173d383e57bbdf60e0bca7c2492e7108398f71cb47e9e2360b2164e907dc099dba02b9d03f923872fcfe6e0890a0a6d9d40187647b5f8d4fd704555a02ded327464e3fe3cba3fbae92f53451446c403c85cc6e1e3c8f3c0a6445faf57115";
                datas.put("data", aes_data);
                resp = rec_post(url,datas);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(encryptType.equals("getUrl")){
            resp = do_get(url,null);
        }

        if(encryptType.equals("getWithParam")){
            Map<String, String> params = new HashMap<String, String>();
            try {
//			String base64_data = Base64.encodeBase64String(datas.getBytes("utf-8"));
                params.put("wd", "ww");
                params.put("ie", "UTF-8");
            } catch (Exception e) {
                // TODO: handle exception
                e.printStackTrace();
            }
            resp = do_get(url, params);
            }

            return resp;
    }


    //GET请求
    private static String do_get(String url, Map<String, String> params) throws URISyntaxException {
        // TODO Auto-generated method stub
        /*
         * http post 请求
         */
        CloseableHttpClient httpclient = null;
        HttpGet httpGet = null;
        CloseableHttpResponse response = null;
        String result = null;

        // 带url params需要，创建uri
        URIBuilder builder = new URIBuilder(url);
        if (params != null) {
            for (String key : params.keySet()) {
                builder.addParameter(key, params.get(key));
            }
        }
        URI uri = builder.build();

        try {
            httpGet = new HttpGet(uri);
            httpGet.setConfig(requestConfig);
            //addHeaderParam(httpPost, headermap);
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(httpGet, HttpClientContext.create());
            result = EntityUtils.toString(response.getEntity());
            return result;
            // logger.info("Post request url:{}, result : {} ", url, result );
        } catch (Exception e) {
            logger.error("Post url ：【 " + url + " 】请求异常！", e);
        } finally {
            try {
                response.close();
                httpGet.releaseConnection();
            } catch (IOException e) {
                logger.error("Post colse response Exception", e);
            }
        }
        return "wanwei:error";
    }

    //POST请求： 原生form表单UrlEncodedFormEntity
    private static String rec_post(String url, Map<String, String> params) {
        // TODO Auto-generated method stub
        /*
         * http post 请求
         */
        CloseableHttpClient httpclient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String result = null;

        List<NameValuePair> pair = new ArrayList<NameValuePair>();
        Set<String> keySet = params.keySet();
        for (String key : keySet) {
            pair.add(new BasicNameValuePair(key, params.get(key)));
        }

        try {
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            //addHeaderParam(httpPost, headermap);
            httpPost.setEntity(new UrlEncodedFormEntity(pair, "UTF-8"));
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(httpPost, HttpClientContext.create());
            result = EntityUtils.toString(response.getEntity());
            return result;
            // logger.info("Post request url:{}, result : {} ", url, result );
        } catch (Exception e) {
            logger.error("Post url ：【 " + url + "】， params ：【 " + JSONObject.toJSONString(params) + " 】请求异常！", e);
        } finally {
            try {
                response.close();
                httpPost.releaseConnection();
            } catch (IOException e) {
                logger.error("Post colse response Exception", e);
            }
        }

        return "wanwei:error";
    }

    //POST请求： application/json json格式提交
    private static String do_post2(String url, String params) {
        // TODO Auto-generated method stub
        /*
         * http post 请求
         */
        CloseableHttpClient httpclient = null;
        HttpPost httpPost = null;
        CloseableHttpResponse response = null;
        String result = null;

        try {
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            //addHeaderParam(httpPost, headermap);
            StringEntity entity = new StringEntity(params.toString(), "utf-8");
            //设置body编码格式
            entity.setContentEncoding("UTF-8");
            //设置请求头content-type
            entity.setContentType("application/json");
            httpPost.setEntity(entity);
            httpclient = HttpClients.createDefault();
            response = httpclient.execute(httpPost, HttpClientContext.create());
            result = EntityUtils.toString(response.getEntity());
            return result;
            // logger.info("Post request url:{}, result : {} ", url, result );
        } catch (Exception e) {
            logger.error("Post url ：【 " + url + "】， params ：【 " + JSONObject.toJSONString(params) + " 】请求异常！", e);
        } finally {
            try {
                response.close();
                httpPost.releaseConnection();
            } catch (IOException e) {
                logger.error("Post colse response Exception", e);
            }
        }
        return "wanwei:error";
    }

    private static void addHeaderParam(HttpPost httpPost, Map<String, String> headermap) {
        // TODO Auto-generated method stub
        /*
         * 添加信息头
         */

        for (String key : headermap.keySet()) {
            httpPost.addHeader(key, headermap.get(key));
        }
    }


    public static List<String> readDataFromText(String filePath) {
        /*
         * 从本地txt文件中读取测试数据，绝对路径
         *
         *
         */
        List<String> dataList = new ArrayList<String>();
        try {
//			String is = new FileInputStream(filePath);
            String encoding = "utf-8";
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath),"utf-8"));
            String data;
            while((data = br.readLine()) != null) {
                dataList.add(data);
            }
            br.close();
//			is.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

    public static int getFileLineCounts(String filename) {
        int cnt = 0;
        InputStream is = null;
        try {
            is = new BufferedInputStream(new FileInputStream(filename));
            byte[] c = new byte[1024];
            int readChars = 0;
            while ((readChars = is.read(c)) != -1) {
                for (int i = 0; i < readChars; ++i) {
                    if (c[i] == '\n') {
                        ++cnt;
                    }
                }
            }
        } catch (Exception ex) {
            cnt = -1;
            ex.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return cnt;
    }
}
