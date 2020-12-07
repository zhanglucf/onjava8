package chapter13;

import java.util.function.BiConsumer;

class In1{}
class In2{}
public class MethodConversion {

    public static void f1(In1 in1,In2 in2){
        System.out.println("f1()");
    }
    public static void f2(In1 in1,In2 in2){
        System.out.println("f2()");
    }

    public static void main(String[] args) {
        BiConsumer<In1,In2> biConsumer = MethodConversion::f1;
        BiConsumer<In1,In2> biConsumer2 = MethodConversion::f2;
        biConsumer.accept(new In1(),new In2());
        biConsumer2.accept(new In1(),new In2());
    }
}
