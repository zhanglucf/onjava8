package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuDemo {
    public static void main(String[] args) {
        Frame frame = new Frame("菜单栏");
        frame.setBounds(600,800,600,800);
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("选项");
        MenuItem menuItemA = new MenuItem("退出系统");
        MenuItem menuItemB = new MenuItem("B");
        MenuItem menuItemC = new MenuItem("C");
        menu.add(menuItemA);
        menu.add(menuItemB);
        menu.add(menuItemC);
        menuBar.add(menu);
        menuItemA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        frame.setMenuBar(menuBar);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        frame.setVisible(true);
    }
}
