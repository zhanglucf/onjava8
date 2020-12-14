package designpattern;

/**
 * 静态工厂
 * 缺点，添加新的类型，create需要修改
 */
public class ShapeFactory1 implements FactoryMethod {

    @Override
    public Shape create(String type) {
        switch (type){
            case "Cricle":
                return new Cricle();
            case "Square":
                return new Square();
            case "Triangle":
                return new Triangle();
            default:
                throw new BadShapeCreation("illegal type");
        }
    }

    public static void main(String[] args) {
        FactoryTest.test(new ShapeFactory1());
    }
}
