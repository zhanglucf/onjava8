package io.itheima;

import concurrent.Nap;
import util.Timer;

/**
 * 不死神兔子
 * 1 1 2 3 5 8 13
 */
public class Recursion_6 {


    public static void main(String[] args) {
        Timer timer = new Timer();
        long rabbitNum = getRabbitNum(50);
        //int rabbitNum2 = getRabbitNum2(9);
        System.out.println(rabbitNum);
        //System.out.println(rabbitNum2);
        System.out.println(timer.duration());
    }

    static int getRabbitNum(long monthNum) {
        if (monthNum == 1 || monthNum == 2) {
            return 1;
        } else {
            return getRabbitNum(monthNum - 2) + getRabbitNum(monthNum - 1);
        }
    }

    static int getRabbitNum2(long monthNum) {
        if (monthNum == 1 || monthNum == 2) {
            return 1;
        } else {
            int a = 1;
            int b = 1;
            int num = 0;
            for (int i = 0; i < monthNum - 2; i++) {
                num = a + b;
                a = b;
                b = num;
            }
            return num;
        }
    }

}

