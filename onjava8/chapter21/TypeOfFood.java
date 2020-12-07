package com.example.onjava8.chapter21;

import static com.example.onjava8.chapter21.Food.*;

// enums/menu/TypeOfFood.java
// {java enums.menu.TypeOfFood}
public class TypeOfFood {
    public static void main(String[] args) {
        Food food = 主菜.布瑞托;
        food = 主菜.烤宽面条;
        food = 甜点.冰淇淋;
        food = 咖啡.卡布奇诺;
        System.out.println(food);
    }
}

