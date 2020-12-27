package netty.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(6);

        for (int i = 0; i < 6; i++) {
            intBuffer.put(i * 2);
        }

        intBuffer.flip();
        intBuffer.position(3);
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
