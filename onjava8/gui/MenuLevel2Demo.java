package gui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MenuLevel2Demo {
    public static void main(String[] args) {
        Frame frame = new Frame("菜单栏");
        frame.setBounds(600, 800, 600, 800);
        MenuBar menuBar = new MenuBar();
        Menu menuA = new Menu("设置");
        Menu menuB = new Menu("功能");

        Menu menuBSub1 = new Menu("字体颜色");
        Menu menuBSub2 = new Menu("字体大小");
        MenuItem menuCSub1 = new MenuItem("打开记事本");
        MenuItem menuCSub2 = new MenuItem("打开计算器");
        MenuItem menuCSub3 = new MenuItem("打开音乐播放器");
        MenuItem menuCSubExit = new MenuItem("退出系统");

        MenuItem menuBSub1Item1 = new MenuItem("颜色1");
        MenuItem menuBSub1Item2 = new MenuItem("颜色2");
        MenuItem menuBSub1Item3 = new MenuItem("颜色3");

        MenuItem menuBSub2Item1 = new MenuItem("16");
        MenuItem menuBSub2Item2 = new MenuItem("26");
        MenuItem menuBSub2Item3 = new MenuItem("36");

        //给二级菜单menuBSub1设置菜单项
        menuBSub1.add(menuBSub1Item1);
        menuBSub1.add(menuBSub1Item2);
        menuBSub1.add(menuBSub1Item3);

        //给二级菜单menuBSub2设置菜单项
        menuBSub2.add(menuBSub2Item1);
        menuBSub2.add(menuBSub2Item2);
        menuBSub2.add(menuBSub2Item3);

        //给一级菜单menuB设置菜单项
        menuA.add(menuBSub1);
        menuA.add(menuBSub2);
        //给一级菜单menuC设置菜单项
        menuB.add(menuCSub1);
        menuB.add(menuCSub2);
        menuB.add(menuCSub3);
        menuB.add(menuCSubExit);

        menuBar.add(menuA);
        menuBar.add(menuB);

        menuCSubExit.addActionListener(new ActionListener() {
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
