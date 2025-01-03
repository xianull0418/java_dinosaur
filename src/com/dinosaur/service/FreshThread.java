/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.service;

import com.dinosaur.view.GamePanel;
import com.dinosaur.view.MainFrame;
import com.dinosaur.view.ScoreDialog;

import java.awt.*;

public class FreshThread extends Thread {
    public static final int FREASH = 20;
    GamePanel p;

    public FreshThread(GamePanel p) {
        this.p = p;
    }

    public void run() {
        while(!this.p.isFinish()) {
            this.p.repaint();

            try {
                Thread.sleep(FREASH);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Container c=p.getParent();
        while (!(c instanceof MainFrame)){c = c.getParent();}


        MainFrame frame = (MainFrame)c;
        new ScoreDialog(frame);
        frame.restart();
    }
}
