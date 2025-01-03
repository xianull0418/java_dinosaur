/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.view;

import com.dinosaur.service.ScoreRecorder;
import com.dinosaur.service.Sound;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainFrame extends JFrame {
    public MainFrame() {
        this.restart();
        this.setBounds(340, 150, 820, 260);//窗体大小
        this.setTitle("奔跑吧！小恐龙!");
        Sound.backgroud();
        ScoreRecorder.init();
        this.addListener();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    public void restart() {
        Container c = this.getContentPane();
        c.removeAll();
        GamePanel panel = new GamePanel();
        c.add(panel);
        this.addKeyListener(panel);
        c.validate();
    }

    private void addListener() {
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                ScoreRecorder.saveScore();
            }
        });
    }
}
