package com.example.tools.httpUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @author JinXing
 * @date 2018/7/17 19:26
 */
public class Random {

    //随机6位数
    private static String getSixRandomNumber(){

        //1.声明0-9 十位数
        List<Integer> list = Arrays.asList(0,1,2,3,4,5,6,7,8,9);

        //2.调用Collections.shuffle(list)打乱顺序
        Collections.shuffle(list);
        StringBuffer numberBuffer=new StringBuffer();

        //3.获取前6位数字组成字符串
        list.subList(0,6).stream().forEach(number->numberBuffer.append(number));

        return numberBuffer.toString();

    }

}
