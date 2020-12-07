package chapter16;

class SimpleException extends Exception{}
public class InheritingException {
    public void f() throws SimpleException{
        System.out.println("Throw SimpleException from f()");
        throw new SimpleException();
    }

    public static void main(String[] args) throws SimpleException {
        InheritingException ie = new InheritingException();
        try {
            ie.f();
        } catch (SimpleException e) {
            System.out.printf("Caught it!");
        }
    }
}
