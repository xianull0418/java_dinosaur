/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.view;


import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackgroundImage {
    public BufferedImage image;
    private BufferedImage image1;
    private BufferedImage image2;
    private Graphics2D g;
    public int x1;
    public int x2;
    public static final int SPEED = 10;//滚动速度
    private int currentSpeed = SPEED; // 当前速度

    public BackgroundImage() {
        try {
            this.image1 = ImageIO.read(new File("image/背景.png"));
            this.image2 = ImageIO.read(new File("image/背景.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        this.image = new BufferedImage(800, 300, 1);
        this.g = this.image.createGraphics();
        this.x1 = 0;
        this.x2 = 800;
        this.g.drawImage(this.image1, this.x1, 0, (ImageObserver)null);
    }

    public void setCurrentSpeed(int speed) {
        this.currentSpeed = speed;
    }

    public void roll() {
        this.x1 -= this.currentSpeed;
        this.x2 -= this.currentSpeed;
        if (this.x1 <= -800) {
            this.x1 = 800;
        }

        if (this.x2 <= -800) {
            this.x2 = 800;
        }

        this.g.drawImage(this.image1, this.x1, 0, (ImageObserver)null);
        this.g.drawImage(this.image2, this.x2, 0, (ImageObserver)null);
    }
}
