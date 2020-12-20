package io.basic;

import java.io.*;
import java.util.*;

/**
 * 复制图片
 */
public class IO_18 {
    public static void main(String[] args) throws IOException {

        TreeSet<Student> students = new TreeSet<>(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                int num = o2.math - o1.math;
                int num2 = num == 0 ? o2.chinese - o1.chinese : num;
                int num3 = num2 == 0 ? o2.english - o1.english : num2;
                int num4 = num3 == 0 ? o2.name.compareTo(o1.name) : num3;
                return num4;
            }
        });
        for (int i = 0; i < 4; i++) {
            Scanner scanner = new Scanner(System.in);
            System.out.println(String.format("请录入第%s个学生的信息", i + 1));
            System.out.println(String.format("姓名"));
            String name = scanner.nextLine();
            System.out.println(String.format("语文成绩"));
            String chinese = scanner.nextLine();
            System.out.println(String.format("数学成绩"));
            String math = scanner.nextLine();
            System.out.println(String.format("英文成绩"));
            String english = scanner.nextLine();
            Student student = new Student(i,
                    name,
                    Integer.valueOf(chinese),
                    Integer.valueOf(math),
                    Integer.valueOf(english));
            students.add(student);
        }
        students.forEach(System.out::println);
    }
}
