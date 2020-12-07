package chapter17;

// validating/GuavaPreconditions.java
// Demonstrating Guava Preconditions
import org.springframework.scheduling.support.CronSequenceGenerator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.*;
import static com.google.common.base.Preconditions.*;
public class GuavaPreconditions {
    static void test(Consumer<String> c, String s) {
        try {
            System.out.println(s);
            c.accept(s);
            System.out.println("Success");
        } catch(Exception e) {
            String type = e.getClass().getSimpleName();
            String msg = e.getMessage();
            System.out.println(type +
                    (msg == null ? "" : ": " + msg));
        }
    }

    public static void main(String[] args) throws ParseException {
//        test(s -> s = checkNotNull(s), "X");
//        test(s -> s = checkNotNull(s), null);
//        test(s -> s = checkNotNull(s, "s was null"), null);
//        test(s -> s = checkNotNull(s, "s was null, %s %s", "arg2", "arg3"), null);
//        test(s -> checkArgument(s == "Fozzie"), "Fozzie");
//        test(s -> checkArgument(s == "Fozzie"), "X");
//        test(s -> checkArgument(s == "Fozzie"), null);
//        test(s -> checkArgument(s == "Fozzie", "Bear Left!"), null);
//        test(s -> checkArgument(s == "Fozzie", "Bear Left! %s Right!", "Frog"), null);
//        test(s -> checkState(s.length() > 6), "Mortimer");
//        test(s -> checkState(s.length() > 6), "Mort");
//        test(s -> checkState(s.length() > 6), null);
//        test(s -> checkElementIndex(6, s.length()), "Robert");
//        test(s -> checkElementIndex(6, s.length()), "Bob");
//        test(s -> checkElementIndex(6, s.length()), null);
//        test(s -> checkPositionIndex(6, s.length()), "Robert");
//        test(s -> checkPositionIndex(6, s.length()), "Bob");
//        test(s -> checkPositionIndex(6, s.length()), null);
//        test(s -> checkPositionIndexes(0, 6, s.length()), "Hieronymus");
//        test(s -> checkPositionIndexes(0, 10, s.length()), "Hieronymus");
//        test(s -> checkPositionIndexes(0, 11, s.length()), "Hieronymus");
//        test(s -> checkPositionIndexes(-1, 6, s.length()), "Hieronymus");
//        test(s -> checkPositionIndexes(7, 6, s.length()), "Hieronymus");
//        test(s -> checkPositionIndexes(0, 6, s.length()), null);


        long a = System.currentTimeMillis();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse("2020-03-31 15:30:00");
        String cron = "00 30 15 31 * ?";
        CronSequenceGenerator cronSequenceGenerator = new CronSequenceGenerator(cron);

        for (int i = 0; i < 3660 ; i++) {
            date = cronSequenceGenerator.next(date);
            System.out.println(format.format(date));
        }
        System.out.println("--------------------------------------");
        System.out.println("take hour: " + (System.currentTimeMillis()-a));
    }
}

