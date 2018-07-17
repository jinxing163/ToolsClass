package com.example.tools.DictionaryOrder;

import java.util.ArrayList;
import java.util.Collections;

/**
 * 字典排序
 * @author JinXing
 * @date 2018/7/16 11:31
 */
public class Order {

    public static void main(String[] args){
        ArrayList<String> arl = new ArrayList<String>();
        arl.add("788");//userId
        arl.add("787878");//随机6位数
        Long timeMillis = System.currentTimeMillis();
        arl.add(timeMillis.toString());//时间戳

        Collections.sort(arl, (o1, o2) -> {
            try {
                // 取得比较对象的汉字编码，并将其转换成字符串
                String s1 = new String(o1.toString().getBytes("GB2312"), "ISO-8859-1");
                String s2 = new String(o2.toString().getBytes("GB2312"), "ISO-8859-1");
                // 运用String类的 compareTo（）方法对两对象进行比较
                return s1.compareTo(s2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        });// 根据元素的自然顺序 对指定列表按升序进行排序。

        StringBuffer sortBuffer=new StringBuffer();
        for (int i = 0; i < arl.size(); i++) {
            System.out.println(arl.get(i));
            sortBuffer.append(arl.get(i));
        }
        System.out.println(sortBuffer);
    }

}
