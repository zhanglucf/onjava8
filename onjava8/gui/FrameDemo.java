package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class FrameDemo {
    public static void main(String[] args) {
        Frame frame = new Frame("Hello World");
        frame.setBounds(300,400,600,800);
        frame.setLayout(new FlowLayout());
        Button button = new Button("按钮");
        //创建文本框
        TextField textField = new TextField(10);
        //创建文本域
        TextArea textArea = new TextArea(10, 40);
        frame.add(textField);
        frame.add(button);
        frame.add(textArea);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
        button.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //获取文本框的值
                String text = textField.getText();
                //清空文本框
                textField.setText(null);
                //自动换行
                textArea.append(text + "\r\n");
                //获取光标
                textField.requestFocus();
            }
        });

    }
}
