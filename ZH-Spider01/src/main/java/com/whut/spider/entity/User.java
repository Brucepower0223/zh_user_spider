package com.whut.spider.entity;

/**
 * Created by fangjin on 2017/7/6.
 */
public class User {


    private Integer id;
    private String name;
    private Integer age;
    private Department department;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "department=" + department +
                ", age=" + age +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
    }
}
