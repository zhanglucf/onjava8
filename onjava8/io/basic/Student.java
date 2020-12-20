package io.basic;

public class Student {
    public Integer id;
    public String name;
    public Integer chinese;
    public Integer math;
    public Integer english;

    public Student(Integer id, String name, Integer chinese, Integer math, Integer english) {
        this.id = id;
        this.name = name;
        this.chinese = chinese;
        this.math = math;
        this.english = english;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", chinese=" + chinese +
                ", math=" + math +
                ", english=" + english +
                '}';
    }
}
