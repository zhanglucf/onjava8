package chapter21;

import java.util.Random;

public enum Game {
    手游(Category.手游.class),
    PC网游(Category.网游.class);
    Category[] values;
    Random random = new Random(47);

    Game(Class<? extends Category> clazz) {
        values = clazz.getEnumConstants();
    }

    public Category randomSelection() {
        return values[random.nextInt(values.length)];
    }

    interface Category {
        enum 手游 implements Category {
            王者荣耀, 刺激战场, 天天酷跑
        }

        enum 网游 implements Category {
            穿越火线, 英雄联盟, 绝地求生
        }
    }

    public static void main(String[] args) {
        System.out.println(Game.values());
        System.out.println("--------");
        for (int i = 0; i < 6; i++) {
            Game game = Enums.random(Game.class);
            System.out.print(game + ":");
            System.out.println(game.randomSelection());
        }
    }
}
