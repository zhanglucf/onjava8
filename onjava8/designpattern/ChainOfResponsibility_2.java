package designpattern;

import util.Print;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

class Re {
    public Boolean success;
    public String date;

    public Re(Boolean success, String date) {
        this.success = success;
        this.date = date;
    }

    @Override
    public String toString() {
        return "Re{" +
                "success=" + success +
                ", date='" + date + '\'' +
                '}';
    }
}

public class ChainOfResponsibility_2 {

    public static List<Function<Long, Re>> Strategies = Arrays.asList(
            ChainOfResponsibility_2::findSaiyan,
            ChainOfResponsibility_2::findSuperSaiyanLevelOne,
            ChainOfResponsibility_2::findSuperSaiyanLevelTwo,
            ChainOfResponsibility_2::findSuperSaiyanLevelThree
    );

    private static Re findSaiyan(Long combat) {
        if (combat < 10000L)
            return new Re(true, "常态卡卡罗特");
        return new Re(false, "");
    }

    private static Re findSuperSaiyanLevelOne(Long combat) {
        if (1_000L <= combat && combat < 500_000L)
            return new Re(true, "超一贝吉塔");
        return new Re(false, "");
    }

    private static Re findSuperSaiyanLevelTwo(Long combat) {
        if (500_000L <= combat && combat < 1_000_000L)
            return new Re(true, "超二贝吉塔");
        return new Re(false, "");
    }

    private static Re findSuperSaiyanLevelThree(Long combat) {
        if (1_000_000L <= combat && combat < 3_000_000L)
            return new Re(true, "超三贝吉塔");
        return new Re(false, "");
    }

    public static Re findSuitableSaiyan(Long combat) {
        for (Function<Long, Re> strategy : Strategies) {
            if (strategy.apply(combat).success) {
                return strategy.apply(combat);
            }
        }
        return new Re(false, "战斗力没有合适的赛亚人");
    }
}

class TT {
    public static void main(String[] args) {
        List<Long> combats = Arrays.asList(1000L, 100_000L, 500_000L, 500_000L, 1_000_000l, 10_000_000l);
        long count = combats.stream()
                .map(ChainOfResponsibility_2::findSuitableSaiyan)
                .peek(Print::println)
                .count();
    }
}