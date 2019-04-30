package com.example.tools.sort;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author JinXing
 * @date 2019/4/30 17:49
 */
public class TestMain {

    private final static Comparator<Object> CHINA_COMPARE = Collator.getInstance(java.util.Locale.CHINA);

    public static void main(String[] args) {

        //获取商户list集合

        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("10", "101", "曹操-c"));
        studentList.add(new Student("20", "201", "阿杜-a"));
        studentList.add(new Student("30", "301", "钉钉-d"));
        studentList.add(new Student("40", "401", "版本-b"));

        //Collections工具类的sort()方法对list集合元素排序

        System.out.println("排序前：" + studentList);

        Collections.sort(studentList, (info1, info2) -> {

            //获取中文环境
            Comparator<Object> com = Collator.getInstance(java.util.Locale.CHINA);
            return com.compare(info1.getStuName(), info2.getStuName());
        });


        System.out.println("排序后：" + studentList);

    }

}
