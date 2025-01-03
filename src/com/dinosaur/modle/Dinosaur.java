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
    private boolean doubleJumpState = false;  // 二段跳状态
    private int jumpCount = 0;     // 跳跃次数计数
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
        if (!jumpState) {  // 第一段跳
            Sound.jump();
            jumpState = true;
            jumpCount = 1;
            jumpValue = -4;  // 向上跳
        } else if (!doubleJumpState && jumpCount == 1) {  // 第二段跳
            Sound.jump();
            doubleJumpState = true;
            jumpCount = 2;
            jumpValue = -4;  // 再次向上跳
            y -= 30;  // 立即提升一些高度，让二段跳更明显
        }
    }

    // 移动
    public void move() {
        step();
        if (jumpState) {
            if (y <= LOWEST_Y - JUMP_HIGHT) {  // 到达最高点
                jumpValue = 4;  // 开始下落
            }
            y += jumpValue;
            
            if (y >= LOWEST_Y) {  // 落地
                y = LOWEST_Y;  // 确保不会低于地面
                jumpState = false;
                doubleJumpState = false;
                jumpCount = 0;
                jumpValue = 0;
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


