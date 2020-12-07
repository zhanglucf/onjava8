package com.example.onjava8.chapter12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FilesTest {
    public static void main(String[] args) throws IOException {
        FilesTest ft = new FilesTest();
        List<String> text = Files.readAllLines(Paths.get("D:\\study\\OnJava8\\src\\main\\java\\com\\example\\onjava8\\chapter12\\config"));
        List<Student> students = new ArrayList<>();
        for (String line : text) {
            String[] s = line.split(" ");
            Student student = ft.new Student(s[0],s[1],Integer.valueOf(s[2]),s[3]);
            students.add(student);
        }
        System.out.println(students);
    }
    class Student{
        private String name;
        private String sex;
        private Integer age;
        private String adress;

        public Student(String name, String sex, Integer age, String adress) {
            this.name = name;
            this.sex = sex;
            this.age = age;
            this.adress = adress;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getAdress() {
            return adress;
        }

        public void setAdress(String adress) {
            this.adress = adress;
        }

        @Override
        public String toString() {
            return "Student{" +
                    "name='" + name + '\'' +
                    ", sex='" + sex + '\'' +
                    ", age=" + age +
                    ", adress='" + adress + '\'' +
                    '}';
        }
    }

}
