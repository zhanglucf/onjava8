package chapter1;


import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Test {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static void main(String[] args){
        Calendar calendar=Calendar.getInstance();//当前日期
                int year=calendar.get(Calendar.YEAR);//2010
                int nextyear=calendar.get(Calendar.YEAR)+1;//2011;
                Calendar nowyear=Calendar.getInstance();
                Calendar nexty=Calendar.getInstance();
                nowyear.set(year, 0, 1);//2010-1-1
                nexty.set(nextyear, 0, 1);//2011-1-1

                calendar.add(Calendar.DAY_OF_MONTH, -calendar.get(Calendar.DAY_OF_WEEK));//周六
                Calendar c=(Calendar) calendar.clone();
                for(;calendar.before(nexty)&&calendar.after(nowyear);calendar.add(Calendar.DAY_OF_YEAR, -7)){
                    printf(calendar);
                }
                for(;c.before(nexty)&&c.after(nowyear);c.add(Calendar.DAY_OF_YEAR, 7)){
                    printf(c);
                }
            }

            //打印
            public static void printf(Calendar calendar)
            {
                System.out.println(calendar.get(Calendar.YEAR)+"-"+(1+calendar.get(Calendar.MONTH))+"-"+calendar.get(Calendar.DATE));

    }



    private static void getDays() {
        int year= 2013;
        int m=1;//月份计数
        while (m<13)
        {
            int month=m;
            Calendar cal=Calendar.getInstance();//获得当前日期对象
            cal.clear();//清除信息
            cal.set(Calendar.YEAR,year);
            cal.set(Calendar.MONTH,month-1);//1月从0开始
            cal.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天

            System.out.println("##########___" + sdf.format(cal.getTime()));

            int count=cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            System.out.println("$$$$$$$$$$________" + count);

            for (int j=0;j<=(count - 2);)
            {
                cal.add(Calendar.DAY_OF_MONTH,+1);
                j++;
                System.out.println(sdf.format(cal.getTime()));
            }
            m++;
        }
    }
}
