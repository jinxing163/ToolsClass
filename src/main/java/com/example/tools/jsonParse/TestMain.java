package com.example.tools.jsonParse;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.esotericsoftware.reflectasm.MethodAccess;
import com.google.gson.*;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @author JinXing
 * @date 2018/6/15 16:54
 */
public class TestMain {

    public static void main(String[] args) throws IllegalAccessException {
        System.out.println("list typeName"+List.class.getTypeName());
        test1();

    }

    private static void test1() throws IllegalAccessException {
        String jsonStr = "{\"name\":\"三班\",\"students\":[{\"age\":25,\"gender\":\"female\",\"grades\":\"三班\",\"name\":\"露西\",\"score\":{\"网络协议\":98,\"JavaEE\":92,\"计算机基础\":93},\"weight\":51.3},{\"age\":26,\"gender\":\"male\",\"grades\":\"三班\",\"name\":\"杰克\",\"score\":{\"网络安全\":75,\"Linux操作系统\":81,\"计算机基础\":92},\"weight\":66.5},{\"age\":25,\"gender\":\"female\",\"grades\":\"三班\",\"name\":\"莉莉\",\"score\":{\"网络安全\":95,\"Linux操作系统\":98,\"SQL数据库\":88,\"数据结构\":89},\"weight\":55}]}";

        //1. 获得 解析者
        JsonParser parser = new JsonParser();
        //2. 获得 根节点元素
        JsonElement element = parser.parse(jsonStr);
        //3.判断根节点的类型。{}为对象类型，[]为数组类型。
//       System.out.println(element.isJsonObject());
//       System.out.println(element.isJsonArray());
        JsonObject root = element.getAsJsonObject();

        Grades grades=new Grades();
        Class<? extends Grades> aClass = grades.getClass();
        MethodAccess access = MethodAccess.get(aClass);
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField == null) {
                continue;
            }

            //字段名称
            String fieldName = declaredField.getName();
            String fieldSetName=getFieldSetName(fieldName);
            if (fieldName.indexOf("serial") != -1) {
                continue;
            }

            //字段类型
            Class<?> type = declaredField.getType();
            //当前类
            Class<?> declaringClass = declaredField.getDeclaringClass();
            String typeName = declaredField.getType().getName();
            System.out.println(fieldName+"-->"+fieldSetName);
            System.out.println("type:"+typeName);
            //判断对象类型，根据不同类型获取对象的指
            //Object为对象类型,其他为基础数据类型
            if (typeName.equals(Object.class.getTypeName())) {
                JsonObject asJsonObject = root.getAsJsonObject(fieldName);
                declaredField.set(fieldName, asJsonObject);
            }else if(typeName.equals(List.class.getTypeName())){
                JsonArray asJsonArray = root.getAsJsonArray(fieldName);
                System.out.println("asJsonArray:"+asJsonArray);
                declaredField.set(fieldName, asJsonArray);
            }else {
                if(jsonPrimitive(root,grades,access,fieldName,fieldSetName)) {
                    continue;
                }
            }
        }

        System.out.println(grades);
    }

    /**获取字段对应的Set方法*/
    private static String getFieldSetName(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        StringBuffer setName = new StringBuffer("set");
        setName.append(name.substring(0, 1).toUpperCase() + name.substring(1, name.length()));

        return setName.toString();
    }

    private static <T> boolean jsonPrimitive(JsonObject root, T remoteTarget, MethodAccess access, String name, String fieldSetName) {
        //Json对应的字段值
        JsonPrimitive asJsonPrimitive = root.getAsJsonPrimitive(name);
        if (asJsonPrimitive.isBoolean()) {
            //boolean 类型
            access.invoke(remoteTarget, fieldSetName, asJsonPrimitive.getAsBoolean());
        } else if (asJsonPrimitive.isNumber()) {
            access.invoke(remoteTarget, fieldSetName, asJsonPrimitive.getAsLong());
        } else if (asJsonPrimitive.isString()) {
            access.invoke(remoteTarget, fieldSetName, asJsonPrimitive.getAsString());
        } else if (asJsonPrimitive.isJsonArray()) {
            JsonArray asJsonArray = asJsonPrimitive.getAsJsonArray();
        } else if (asJsonPrimitive.isJsonObject()) {
            JsonObject asJsonObject = asJsonPrimitive.getAsJsonObject();
        } else {
            return true;
        }
        return false;
    }


}
