package com.example.tools.world;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author JinXing
 * @date 2018/9/5 18:36
 */
public class SensitiveWorldFilter {

    private static Pattern pattern = null;
    //铭感词库个数
    private static int keywordsCount = 0;

    // 从words.properties初始化正则表达式字符串
    private static void initPattern() {
        StringBuffer patternBuffer = new StringBuffer();
        try {

            InputStream in = SensitiveWorldFilter.class.getClassLoader().getResourceAsStream("keywords.properties");
            InputStreamReader inputStreamReader = new InputStreamReader(in, "utf-8");

            Properties property = new Properties();
            property.load(inputStreamReader);
            Enumeration<?> enu = property.propertyNames();
            while (enu.hasMoreElements()) {
                String content = (String) enu.nextElement();
                patternBuffer.append(content + "|");
                keywordsCount ++;
            }
            patternBuffer.deleteCharAt(patternBuffer.length() - 1);

            //System.out.println(patternBuffer);
            // unix换成UTF-8
            // pattern = Pattern.compile(new
            // String(patternBuf.toString().getBytes("ISO-8859-1"), "UTF-8"));
            // win下换成gb2312
            // pattern = Pattern.compile(new String(patternBuf.toString()
            // .getBytes("ISO-8859-1"), "gb2312"));
            // 装换编码
            pattern = Pattern.compile(patternBuffer.toString());
        } catch (IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    private static String doFilter(String str) {
        Matcher m = pattern.matcher(str);
//        while (m.find()) {// 查找符合pattern的字符串
//            System.out.println("The result is here :" + m.group());
//        }
        // 选择替换方式，这里以* 号代替
        str = m.replaceAll("****");
        return str;
    }

    public static void main(String[] args) {

        System.out.println("当前铭感词库pattern="+pattern);
        initPattern();

        String str = "汽车行走的艺术网课帮做拿高分，学长姐亲手人工带做，扣【⑨②⑦①①①②⑦③】，靠谱，包过";
        System.out.println("原始内容："+str);
        str = doFilter(str);
        System.out.println("替换后内容："+str);

    }

}
