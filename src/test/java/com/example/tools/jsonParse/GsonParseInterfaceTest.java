package com.example.tools.jsonParse;

import com.example.tools.ToolsApplicationTests;
import com.google.gson.Gson;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author JinXing
 * @date 2018/6/19 9:56
 */
public class GsonParseInterfaceTest extends ToolsApplicationTests {


    @Test
    public void JsonToBean() throws InstantiationException, IllegalAccessException {

        Student student=new Student();
        student.setAge(10);
        student.setGender("aaa");
        student.setGrades("bbb");
        student.setName("jx");
        System.out.println(student);
        String json = new Gson().toJson(student);

        System.out.println("json:"+json);

        Student student1 = GsonParse.JsonToBean(json, Student.class);
        System.out.println("student1:"+student1);

    }

    @Test
    public void jsonToList() throws IllegalAccessException, InstantiationException, InvocationTargetException {

        Student student=new Student();
        student.setAge(10);
        student.setGender("aaa");
        student.setGrades("bbb");
        student.setName("jx");
        List<Student> list=new ArrayList<>();
        list.add(student);

        student=new Student();
        student.setAge(20);
        student.setGender("aaa2");
        student.setGrades("bbb2");
        student.setName("jx2");
        list.add(student);

        System.out.println(list);
        String json = new Gson().toJson(list);

        Student student1=new Student();

        List<Student> students = GsonParse.JsonToList(json, Student.class);
        System.out.println("students:"+students);
        if(!CollectionUtils.isEmpty(students)){
            students.forEach(stu->{
                System.out.println(stu);
            });
        }

    }

    @Test
    public void jsonToMap() {

        Student student=new Student();
        student.setAge(10);
        student.setGender("aaa");
        student.setGrades("bbb");
        student.setName("jx");
        System.out.println(student);
        String json = new Gson().toJson(student);
        Map<String, Object> stringObjectMap = GsonParse.JsonToMap(json);
        System.out.println(stringObjectMap);

    }



}