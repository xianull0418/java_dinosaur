/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.modle;
import com.dinosaur.service.FreshThread;
import com.dinosaur.service.Sound;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// 恐龙类
public class Dinosaur {
    public BufferedImage image; // 主图片
    private BufferedImage image1, image2, image3; // 跑步图片
    public int x, y; // 坐标
    private int jumpValue = 0; // 跳跃的增变量
    private boolean jumpState = false; // 跳跃状态
    private int stepTimer = 0; // 踏步计时器
    private final int JUMP_HIGHT = 100; // 跳起最大高度
    private final int LOWEST_Y = 120; // 落地最低坐标
    private final int FREASH = FreshThread.FREASH; // 刷新时间--刷新帧线程

    public Dinosaur() {
        x = 50;         // 恐龙的横坐标
        y = LOWEST_Y;   // 恐龙的纵坐标
        try {
            image1 = ImageIO.read(new File("image/恐龙1.png")); // 读取恐龙的图片
            image2 = ImageIO.read(new File("image/恐龙2.png"));
            image3 = ImageIO.read(new File("image/恐龙3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 踏步
    private void step() {
        // 每过250毫秒，更换一张图片。因为共有3图片，所以除以3取余，轮流展示这三张
        int tmp = stepTimer / 250 % 3;
        switch (tmp) {
            case 1 :
                image = image1;
                break;
            case 2 :
                image = image2;
                break;
            default :
                image = image3;
        }
        stepTimer += FREASH; // 计时器递增
    }

    // 跳跃
    public void jump() {
        if (!jumpState) { // 如果没处于跳跃状态
            Sound.jump(); // 播放跳跃音效
        }
        jumpState = true; // 处于跳跃状态
    }

    // 移动
    public void move() {
        step(); // 不断踏步
        if (jumpState) { // 如果正在跳跃
            if (y == LOWEST_Y) { // 如果纵坐标大于等于最低点---（越往上坐标越小）
                jumpValue = -4; // 增变量为负值--向上跳
            }
            if (y <= LOWEST_Y - JUMP_HIGHT) {// 如果跳过最高点
                jumpValue = 4; // 增变量为正值--向下跳
            }
            y += jumpValue; // 纵坐标发生变化
            if (y == LOWEST_Y) { // 如果再次落地
                jumpState = false; // 停止跳跃
            }
        }
    }

    public Rectangle getFootBounds() { // 获取恐龙的脚部边界对象

        return new Rectangle(x + 30, y + 59, 29, 18); // 用于后续做碰撞检测
    }

    public Rectangle getHeadBounds() { // 获取恐龙的头部边界对象
        return new Rectangle(x + 66, y + 25, 32, 22);
    }

}


