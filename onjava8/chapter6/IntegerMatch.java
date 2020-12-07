package chapter6;

// strings/IntegerMatch.java

public class IntegerMatch {
    /**
     * ? 零或者一个
     * + 一个或者多个
     * (-|\+)?  -或者+ 有一个或者零个
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("-1234".matches("-?\\d+"));
        System.out.println("5678".matches("-?\\d+"));
        System.out.println("+911".matches("-?\\d+"));
        System.out.println("+911".matches("(-|\\+)?\\d+"));
    }
}