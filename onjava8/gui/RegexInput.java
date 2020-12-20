package gui;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Pattern;

public class RegexInput {

    public static void main(String[] args) {

        Frame frame = new Frame("Regex");
        frame.setBounds(300, 400, 600, 800);
        frame.setLayout(new FlowLayout());
        //创建label标签对象
        Label label = new Label("请输入QQ号 要求是纯数字");
        TextField textField = new TextField(40);
        frame.add(label);
        frame.add(textField);
        textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                System.out.println(e.getKeyChar());
                if (!Pattern.matches("^[0-9]*$",String.valueOf(e.getKeyChar()))) {
                    //取消事件原本的动作
                    e.consume();
                }
            }
        });
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
