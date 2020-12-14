package designpattern;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class ShapeFactory2 implements FactoryMethod {
    static final Map<String, Constructor> typeMapping = new HashMap<>();
    static final String prefix = Shape.class.getName().substring(0, Shape.class.getName().length() - 5);

    @Override
    public Shape create(String type) {
        try {
            return (Shape) typeMapping
                    .computeIfAbsent(type, key -> ShapeFactory2.getConstructor(key))
                    .newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Constructor getConstructor(String type) {
        Constructor constructor = null;
        try {
            constructor = Class.forName(prefix + type).getConstructor();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return constructor;
    }

    public static void main(String[] args) {
        ShapeFactory2 shapeFactory2 = new ShapeFactory2();
        FactoryTest.test(shapeFactory2);
    }
}
