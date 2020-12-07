package chapter21;

// enums/menu/Food.java
// Subcategorization of enums within interfaces
//对于 enum 而言，实现接口是使其子类化的唯一办法
public interface Food {
    enum 开胃菜 implements Food {
        沙拉, 汤, 春卷;
    }
    enum 主菜 implements Food {
        烤宽面条, 布瑞托, PAD_THAI,
        LENTILS, HUMMOUS, VINDALOO;
    }
    enum 甜点 implements Food {
        TIRAMISU, 冰淇淋, BLACK_FOREST_CAKE,
        FRUIT, CREME_CARAMEL;
    }
    enum 咖啡 implements Food {
        BLACK_COFFEE, DECAF_COFFEE, ESPRESSO,
        LATTE, 卡布奇诺, TEA, HERB_TEA;
    }
}

