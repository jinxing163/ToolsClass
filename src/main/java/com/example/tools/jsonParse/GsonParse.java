package com.example.tools.jsonParse;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.util.Assert;

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

    public static <T> List<T> JsonToList(String json, Class<T> classType){
        if(json ==null)return null;
        Assert.notNull(classType,"classType 不能为空");

        System.out.println("type:"+new TypeToken<List<T>>() {}.getType());
        System.out.println("type:"+new TypeToken<List<Student>>() {}.getType());


        List<T> tList = new Gson().fromJson(json, new TypeToken<List<T>>() {}.getType());

        return tList;
    }

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
