package com.example.tools.sort;

/**
 * @author JinXing
 * @date 2019/4/30 17:59
 */
public class Student implements Comparable<Student>{

    private String id;

    private String studentId;

    private String stuName;

    public Student(String id, String studentId, String stuName) {
        this.id = id;
        this.studentId = studentId;
        this.stuName = stuName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Override
    public int compareTo(Student studentName) {
        return this.stuName.compareTo(studentName.getStuName());
   }

    @Override
    public String toString() {
        return "Student{" +
                "id='" + id + '\'' +
                ", studentId='" + studentId + '\'' +
                ", stuName='" + stuName + '\'' +
                '}';
    }
}
