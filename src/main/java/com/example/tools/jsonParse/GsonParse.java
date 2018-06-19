package com.example.tools.jsonParse;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JinXing
 * @date 2018/6/19 9:44
 */
public class GsonParse{

    public static final <T> T JsonToBean(String json,Class<T> classType) throws IllegalAccessException, InstantiationException {
        if(json ==null)return null;
        Assert.notNull(classType,"classType 不能为空");

//        T target = BeanUtils.instantiateClass(classType) ;
        T target = new Gson().fromJson(json, classType);
        return target;
    }

    public static <T> List<T> JsonToList(String json, Class<T> classType) throws InvocationTargetException, IllegalAccessException {
        if(json ==null)return null;
        Assert.notNull(classType,"classType 不能为空");

        T target = BeanUtils.instantiateClass(classType) ;
        List<T> jsonList = new Gson().fromJson(json, new TypeToken<List<T>>() {}.getType());
        List<T> targetList=new ArrayList<>();
        for (T t : jsonList) {
            if(t instanceof Map){
                LinkedTreeMap linkedHashMap= (LinkedTreeMap) t;
                BeanUtilsBean.getInstance().populate(target, linkedHashMap);
                targetList.add(target);
            }
        }
        return targetList;
    }

    @Deprecated
    public static <T> List<T> JsonToList2(String json, Class<T> classType) throws InstantiationException, IllegalAccessException {
        if(json ==null)return null;
        Assert.notNull(classType,"classType 不能为空");

        System.out.println("type:"+new TypeToken<List<T>>() {}.getType());
        System.out.println("type:"+new TypeToken<List<Student>>() {}.getType());
        json=json.replace("[","")
        .replace("]","")
        .replace("},","};");
        String[] split = json.split(";");
        List<T> tList=new ArrayList<>();
        for (String splitJson : split) {
            T t = JsonToBean(splitJson, classType);
            tList.add(t);
        }
        return tList;
    }


    public static Map<String,Object> JsonToMap(String json){
        if(json ==null)return null;
        Map<String,Object> map = new Gson().fromJson(json, new TypeToken<Map<String,Object>>() {}.getType());

        return map;
    }



}
