package com.example.tools.httpUtils;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.alibaba.dubbo.common.utils.StringUtils;

import java.util.List;

/**
 * 字典排序
 * @author JinXing
 * @date 2018/7/17 17:03
 */
public class DictionaryOrderHelper {


    /**
     * 字典排序
     * 思路：
     * 1.将列表进行自然排序 (升序)
     * //2.将自然排序的结果进行组装并返回
     * @author JinXing
     * @date 2018/7/17 17:13
     *
     */
    public static String getDictionaryOrder(List<String> dictionaryList){

        if(CollectionUtils.isEmpty(dictionaryList))return null;

        //1.将列表进行自然排序 (升序)
        CollectionUtils.sort(dictionaryList);

        //2.将自然排序的结果进行组装并返回
        StringBuffer resultBuffer=new StringBuffer();
        dictionaryList.stream().filter(str-> StringUtils.isNotEmpty(str)).forEach(str->
            resultBuffer.append(str)
        );
        return resultBuffer.toString();

    }



}
