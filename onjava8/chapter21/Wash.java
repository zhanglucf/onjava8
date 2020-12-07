package chapter21;

import java.util.EnumSet;

public enum Wash {
    打开淋浴("#打开淋浴"),
    把头发打湿("#把头发打湿"),
    使用洗发水("#使用洗发水"),
    轻柔头发("#轻柔头发"),
    冲洗头发("#冲洗头发"),
    关掉淋浴("#关掉淋浴"),
    把头发吹干("#把头发吹干");
    Wash(String description){
        this.description = description;
    }
    private String description;
    void f(){
        System.out.println(description);
    }
}

/**
 * EnumSet中的元素顺序和枚举中声明的顺序保持一致
 * 与后面加入的顺序没有关系
 */
class TestWash{
    EnumSet<Wash> enumSet = EnumSet.noneOf(Wash.class);
    public static void main(String[] args) {
        TestWash testWash = new TestWash();
        testWash.add(Wash.关掉淋浴);
        testWash.add(Wash.把头发吹干);
        testWash.add(Wash.冲洗头发);
        testWash.add(Wash.轻柔头发);
        testWash.add(Wash.轻柔头发);
        testWash.add(Wash.轻柔头发);
        testWash.add(Wash.轻柔头发);
        testWash.add(Wash.使用洗发水);
        testWash.add(Wash.打开淋浴);
        testWash.add(Wash.把头发打湿);
        for (Wash wash : testWash.enumSet) {
            wash.f();
        }
    }
    public void add(Wash wash){
        enumSet.add(wash);
    }

}
