package com.example.onjava8.chapter1;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarDemo {
    public static void main(String[] args) {
    }



    public static int getTotalDaysOfMonth(Calendar calendar) {
        int month = calendar.get(Calendar.MONTH);
//        printCalendar(calendar);
        Calendar copy = Calendar.getInstance();
        copy.setTime(calendar.getTime());
        copy.set(Calendar.MONTH, month);
        copy.set(Calendar.DATE, 1);
        copy.roll(Calendar.DATE, -1);

//        printCalendar(copy);

        return copy.get(Calendar.DATE);
    }

    public static void  printCalendar(Calendar calendar){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(calendar.getTime());
        System.out.println(str);
    }
}
