package com.example.tools.httpUtils;

import com.alibaba.dubbo.common.utils.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author JinXing
 * @date 2018/7/17 15:06
 */
public class HttpRequest {

    //默认编码格式
    private static final String DEFAULT_CHARSET="utf-8";

    //超时时间
    private static final Integer TIME_OUT=3000;

    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param map 请求Map参数，请求参数应该是 {"name1":"value1","name2":"value2"}的形式。
     * @param charset 发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, Map<String,Object> map,String charset){
        StringBuffer sb=new StringBuffer();
        //构建请求参数
        if(map!=null&&map.size()>0){
            Iterator it=map.entrySet().iterator(); //定义迭代器
            while(it.hasNext()){
                Map.Entry  er= (Map.Entry) it.next();
                sb.append(er.getKey());
                sb.append("=");
                sb.append(er.getValue());
                sb.append("&");
            }
        }
        return  sendGet(url,sb.toString(), charset);
    }


    /**
     * 向指定URL发送POST方法的请求
     * @param url 发送请求的URL
     * @param map 请求Map参数，请求参数应该是 {"name1":"value1","name2":"value2"}的形式。
     * @param charset 发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendPost(String url, Map<String,Object> map,String charset){
        StringBuffer sb=new StringBuffer();
        //构建请求参数
        if(map!=null&&map.size()>0){
            for (Map.Entry<String, Object> e : map.entrySet()) {
                sb.append(e.getKey());
                sb.append("=");
                sb.append(e.getValue());
                sb.append("&");
            }
        }
        return  sendPost(url,sb.toString(),charset);
    }


    /**
     * 向指定URL发送GET方法的请求
     * @param url 发送请求的URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset 发送和接收的格式
     * @return URL 所代表远程资源的响应结果
     */
    public static String sendGet(String url, String param,String charset) {

        if(StringUtils.isEmpty(charset))charset=DEFAULT_CHARSET;

        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(TIME_OUT);
            conn.setReadTimeout(TIME_OUT);
            // 建立实际的连接
            conn.connect();
            // 定义 BufferedReader输入流来读取URL的响应,设置接收格式
            in = new BufferedReader(new InputStreamReader(
                    conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定 URL 发送POST方法的请求
     * @param url 发送请求的 URL
     * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @param charset 发送和接收的格式
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param,String charset) {

        if(StringUtils.isEmpty(charset))charset=DEFAULT_CHARSET;

        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        String line;
        StringBuffer sb=new StringBuffer();
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 设置请求格式
            conn.setRequestProperty("contentType", charset);
            conn.setRequestProperty("content-type", "application/x-www-form-urlencoded");
            //设置超时时间
            conn.setConnectTimeout(TIME_OUT);
            conn.setReadTimeout(TIME_OUT);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应    设置接收格式
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(),charset));
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            result=sb.toString();
        } catch (Exception e) {
            System.out.println("发送 POST请求出现异常!"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static void main(String[] args) {
        String getUrl="http://120.27.148.181:8787/appteacher/teacher/courseqa/getComtentDetailFromAnswer";
        String postUrl="http://120.27.148.181:8787/appteacher/teacher/courseqa/getComtentDetailFromAnswer";
        String param="format=json&ip=218.4.255.255";
        String param1="a=苏州市";
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("pageIndex", "0");
        map.put("answerId", "1146258");
        map.put("pageSize", "10");
        map.put("uuid", "X2JyZ7YX");
        Map<String,Object> map1=new HashMap<String,Object>();
        map1.put("a", "苏州市");
//        System.out.println("Get请求1:"+HttpRequest.sendGet(getUrl, param,"utf-8"));
        System.out.println("Get请求2:"+HttpRequest.sendGet(getUrl, map,"utf-8"));
//        System.out.println("Post请求1:"+HttpRequest.sendPost(postUrl, param1,"utf-8"));
        System.out.println("Post请求2:"+HttpRequest.sendPost(postUrl, map,"utf-8"));

    }

}
