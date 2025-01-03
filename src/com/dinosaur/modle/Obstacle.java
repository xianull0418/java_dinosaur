/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.modle;

import com.dinosaur.view.BackgroundImage;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Obstacle {
    public int x;
    public int y;
    public BufferedImage image;
    private BufferedImage stone;
    private BufferedImage cacti;
    private int currentSpeed;

    public Obstacle() {
        try {
            this.stone = ImageIO.read(new File("image/石头.png"));
            this.cacti = ImageIO.read(new File("image/仙人掌.png"));
        } catch (IOException var2) {
            var2.printStackTrace();
        }

        Random r = new Random();
        if (r.nextInt(2) == 0) {
            this.image = this.cacti;
        } else {
            this.image = this.stone;
        }

        this.x = 800;
        this.y = 200 - this.image.getHeight();
        this.currentSpeed = BackgroundImage.SPEED;
    }

    public void setSpeed(int speed) {
        this.currentSpeed = speed;
    }

    //移动
    public void move() {
        this.x -= this.currentSpeed;
    }

    //消除
    public boolean isLive() {
        if(x<=-image.getWidth()) return false;

        return true;
    }

    public Rectangle getBounds() {
        return this.image == this.cacti ? new Rectangle(this.x + 7, this.y, 15, this.image.getHeight()) : new Rectangle(this.x + 5, this.y + 4, 23, 21);
    }

}
