package chapter21;

// enums/menu/Course.java
public enum Course {
    开胃菜(Food.开胃菜.class),
    主菜(Food.主菜.class),
    甜点(Food.甜点.class),
    咖啡(Food.咖啡.class);
    private Food[] values;
    private Course(Class<? extends Food> kind) {
        values = kind.getEnumConstants();
    }
    public Food randomSelection() {
        return Enums.random(values);
    }

    public static void main(String[] args) {
        for (Course course : Course.values()) {
            System.out.println(course.randomSelection());
        }
    }
}

