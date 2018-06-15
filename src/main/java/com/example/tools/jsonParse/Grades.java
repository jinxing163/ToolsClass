package com.example.tools.jsonParse;

import java.util.List;

/**
 * @author JinXing
 * @date 2018/6/15 16:52
 */
public class Grades {

    private String name;//班级名称
    private List<Student> students;//班里的所有学生

    @Override
    public String toString() {
        return "Grades{" +
                "name='" + name + '\'' +
                ", students=" + students +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
