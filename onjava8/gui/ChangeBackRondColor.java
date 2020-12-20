package gui;

import concurrent.Nap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class ChangeBackRondColor {
    static final Color[] colors = {
            Color.gray,
            Color.green,
            Color.RED,
            Color.YELLOW,
            Color.BLUE,
            Color.CYAN,
            Color.DARK_GRAY,
            Color.lightGray,
    };
    static final Random rand = new Random();

    public static void main(String[] args) {

        Frame frame = new Frame("Hello World");
        frame.setBounds(300, 400, 600, 800);
        frame.setLayout(new FlowLayout());
        Button button = new Button("变色");
        frame.add(button);
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
                frame.setBackground(colors[rand.nextInt(colors.length)]);
            }
        });
        for (int i = 0; i < 100; i++) {
            new Nap(1000);
            frame.setBackground(colors[rand.nextInt(colors.length)]);
        }
    }
}
