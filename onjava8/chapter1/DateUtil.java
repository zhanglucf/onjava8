package chapter1;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 获取两个日期之间的所有周四 并计算每个周四在当月是第几周
 *
 * @author lzw
 * @Date 2019年3月6日
 */
public class DateUtil {

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
//        String week = "星期二";
//        List<String> test = getWeekly("2020-01-01 14:19:29", "2021-01-01 00:00:00", week);
//        List<Long> collect = test.stream()
//                .map(x -> {
//                    String str = x.replace("-", "").substring(0, 8);
//                    return Long.valueOf(str);
//                })
//                .collect(Collectors.toList());
//        collect.sort(Comparator.comparingLong(x -> x));
//        System.err.println(collect);
        List<Calendar> weekOnRang = getSomeDayOnRang("2020-02-29 15:30:00", "2033-01-01 00:00:00",3);
        for (Calendar calendar : weekOnRang) {
            CalendarDemo.printCalendar(calendar);
        }
    }

    /**
     * 按年重复
     */
    public static List<Calendar> getYearOnRang(String dBegin, String dEnd) throws ParseException {
        Calendar calBegin = str2Calendar(dBegin);
        Calendar calEnd = str2Calendar(dEnd);
        int num = calBegin.get(Calendar.DATE);
        List<Calendar> filter = new ArrayList<>();
        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.YEAR, 1);
            Calendar copy = Calendar.getInstance();
            copy.setTime(calBegin.getTime());
            if (copy.before(calEnd)) {
                int days = CalendarDemo.getTotalDaysOfMonth(copy);
                if (days >= num) {
                    copy.set(Calendar.DATE, num);
                    if (calEnd.after(copy)) {
                        filter.add(copy);
                    }
                }
            }
        }
        return filter;
    }


    /**
     * 按月重复
     */
    public static List<Calendar> getDayOfMonthOnRang(String dBegin, String dEnd) throws ParseException {
        Calendar calBegin = str2Calendar(dBegin);
        Calendar calEnd = str2Calendar(dEnd);
        int num = calBegin.get(Calendar.DATE);
        List<Calendar> filter = new ArrayList<>();
        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.MONTH, 1);
            Calendar copy = Calendar.getInstance();
            copy.setTime(calBegin.getTime());
            if (copy.before(calEnd)) {
                int days = CalendarDemo.getTotalDaysOfMonth(copy);
                if (days >= num) {
                    copy.set(Calendar.DATE, num);
                    if (calEnd.after(copy)) {
                        filter.add(copy);
                    }
                }
            }
        }
        return filter;
    }

    /**
     * 按周重复
     */
    public static List<Calendar> getWeekOnRang(String dBegin, String dEnd) throws ParseException {
        Calendar calBegin = str2Calendar(dBegin);
        Calendar calEnd = str2Calendar(dEnd);
        List<Calendar> filter = new ArrayList<>();
        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.DATE, 7);
            Calendar copy = Calendar.getInstance();
            copy.setTime(calBegin.getTime());
            if (copy.before(calEnd)) {
                filter.add(copy);
            }
        }
        return filter;
    }

    /**
     * 按天重复
     */
    public static List<Calendar> getDayOnRang(String dBegin, String dEnd) throws ParseException {
        Calendar calBegin = str2Calendar(dBegin);
        Calendar calEnd = str2Calendar(dEnd);
        List<Calendar> filter = new ArrayList<>();
        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.DATE, 1);
            Calendar copy = Calendar.getInstance();
            copy.setTime(calBegin.getTime());
            if (copy.before(calEnd)) {
                filter.add(copy);
            }
        }
        return filter;
    }

    /**
     * 按N天重复
     */
    public static List<Calendar> getSomeDayOnRang(String dBegin, String dEnd,int num) throws ParseException {
        Calendar calBegin = str2Calendar(dBegin);
        Calendar calEnd = str2Calendar(dEnd);
        List<Calendar> filter = new ArrayList<>();
        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.DATE, num);
            Calendar copy = Calendar.getInstance();
            copy.setTime(calBegin.getTime());
            if (copy.before(calEnd)) {
                filter.add(copy);
            }
        }
        return filter;
    }

    /**
     * 获取每月的第几天
     */
    public static Calendar getDayOfMonthOnNum(int num, Calendar calendar) {
        CalendarDemo.printCalendar(calendar);
        int days = CalendarDemo.getTotalDaysOfMonth(calendar);
        if (days < num) {
            return null;
        } else {
            Calendar copy = Calendar.getInstance();
            copy.setTime(calendar.getTime());
            copy.set(Calendar.DATE, num);
            CalendarDemo.printCalendar(copy);
            return copy;
        }
    }

    public static Calendar str2Calendar(String dateStr) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(format.parse(dateStr));
        return cal;
    }

    /**
     * 获取指定日期范围内，所有月份中和dBegin属于同一天的时间
     *
     * @param dBegin
     * @param dEnd
     * @return
     * @throws ParseException
     */
    public static List<Calendar> findCalendars2(String dBegin, String dEnd) throws ParseException {
        Calendar calBegin = str2Calendar(dBegin);
        Calendar calEnd = str2Calendar(dEnd);
        List<Calendar> calendarList = new ArrayList<Calendar>();
        while (calEnd.after(calBegin)) {
            calBegin.add(Calendar.MONTH, 1);
            Calendar copy = Calendar.getInstance();
            copy.setTime(calBegin.getTime());
            if (copy.before(calEnd)) {
                calendarList.add(copy);
            }
        }
        return calendarList;
    }

    public static List<Calendar> findCalendars(String dBegin, String dEnd) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(format.parse(dBegin));
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(format.parse(dEnd));
        List<Calendar> calendarList = new ArrayList<Calendar>();
        while (format.parse(dEnd).after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            Calendar copy = Calendar.getInstance();
            copy.setTime(calBegin.getTime());
            calendarList.add(copy);
        }
        calendarList.add(calBegin);
        return calendarList;
    }


    public static boolean weekdayOrNot(String date, String week) throws ParseException {
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = sdf.parse(date);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE");
        String currSun = simpleDateFormat.format(currentDate);
        //判断当前是星期几
        if (currSun.equals(week)) {
            return true;
        }
        return false;
    }


    public static String getWeek(Date date) throws Exception {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = sdf.format(date);
        String substring = format.substring(0, 7);
        int number = calendar.get(Calendar.WEEK_OF_MONTH);
        String week = substring + "-0" + number;
        return week;
    }

    /**
     * 根据开始时间和结束时间计算之间的星期
     *
     * @param beginDate
     * @param endDate
     * @return
     * @throws Exception
     */
    public static List<String> getWeekly(String beginDate, String endDate, String week) throws Exception {
        //获取俩个日期之间的日期
        List<String> list = findDates(beginDate, endDate);
        List<String> weekDateList = new ArrayList<>();
        //遍历
        for (String date : list) {
            //判断当前是星期几
            boolean thursday = weekdayOrNot(date, week);
            if (thursday == true) {
                weekDateList.add(date);
            }

        }
        return weekDateList;
    }

    public static List<String> findDates(String dBegin, String dEnd) throws ParseException {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(format.parse(dBegin));
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(format.parse(dEnd));
        List<String> Datelist = new ArrayList<String>();
        while (format.parse(dEnd).after(calBegin.getTime())) {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);
            Datelist.add(format.format(calBegin.getTime()));
        }
        Datelist.add(dBegin);
        return Datelist;
    }

    /**
     * 日期转星期
     *
     * @param datetime
     * @return
     */
    public static String currentDataToWeek(String datetime) {
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        // 获得一个日历
        Calendar cal = Calendar.getInstance();
        Date datet = null;
        try {
            datet = f.parse(datetime);
            cal.setTime(datet);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        // 指示一个星期中的某天。
        int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (w < 0)
            w = 0;
        return weekDays[w];
    }

    public static void  printCalendar(Calendar calendar){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String str = format.format(calendar.getTime());
        System.out.println(str);
    }
}
