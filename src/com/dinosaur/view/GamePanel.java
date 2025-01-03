/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.view;

import com.dinosaur.modle.Dinosaur;
import com.dinosaur.modle.Obstacle;
import com.dinosaur.service.FreshThread;
import com.dinosaur.service.ScoreRecorder;
import com.dinosaur.service.Sound;

import java.awt.event.KeyListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

// 游戏主面板
public class GamePanel extends JPanel implements KeyListener {
    private BufferedImage image; // 主图片
    private BackgroundImage background; // 背景图片
    private Dinosaur golden; // 恐龙
    private Graphics2D g2; // 主图片绘图对象
    private int addObstacleTimer = 0; // 添加障碍计时器
    private boolean finish = false; // 游戏结束标志
    private ArrayList<Obstacle> list = new ArrayList<Obstacle>(); // 障碍集合
    private final int FREASH = FreshThread.FREASH; // 刷新时间

    int score = 0; // 得分
    int scoreTimer = 0; // 分数计时器

    private static final int SPEED_UP_SCORE = 200;  // 每200分升级一次
    private static final int MAX_SPEED = 25;        // 最大速度
    private int currentSpeed = BackgroundImage.SPEED;  // 当前速度

    public GamePanel() {
        // 主图片采用宽800高300的彩色图片
        image = new BufferedImage(800, 300, BufferedImage.TYPE_INT_BGR);
        g2 = image.createGraphics(); // 获取主图片绘图对象
        background = new BackgroundImage(); // 初始化滚动背景
        golden = new Dinosaur(); // 初始化小恐龙
        list.add(new Obstacle()); // 添加第一个障碍
        FreshThread t = new FreshThread(this); // 刷新帧线程
        t.start(); // 启动线程
    }

    // 绘制主图片
    private void paintImage() {
        background.roll(); // 背景图片开始滚动
        golden.move(); // 恐龙开始移动
        g2.drawImage(background.image, 0, 0, this); // 绘制滚动背景
        if (addObstacleTimer == 1300) { // 每过1300毫秒
            if (Math.random() * 100 > 40) { // 60%概率出现障碍
                list.add(new Obstacle());
            }
            addObstacleTimer = 0; // 重新计时
        }

        for (int i = 0; i < list.size(); i++) { // 遍历障碍集合
            Obstacle o = list.get(i); // 获取障碍对象
            if (o.isLive()) { // 如果是有效障碍
                o.move(); // 障碍移动
                g2.drawImage(o.image, o.x, o.y, this); // 绘制障碍
                // 如果恐龙头脚碰到障碍
                if (o.getBounds().intersects(golden.getFootBounds())
                        || o.getBounds().intersects(golden.getHeadBounds())) {
                    Sound.hit(); // 播放撞击声音
                    gameOver(); // 游戏结束
                }
            } else { // 如果不是有效障碍
                list.remove(i); // 删除此障碍
                i--; // 循环变量前移
            }
        }
        g2.drawImage(golden.image, golden.x, golden.y, this); // 绘制恐龙
        if (scoreTimer >= 500) { // 每过500毫秒
            score += 10; // 加十分
            scoreTimer = 0; // 重新计时
        }

        g2.setColor(Color.BLACK); // 使用黑色
        g2.setFont(new Font("黑体", Font.BOLD, 24)); // 设置字体
        g2.drawString(String.format("%06d", score), 700, 30); // 绘制分数

        addObstacleTimer += FREASH; // 障碍计时器递增
        scoreTimer += FREASH; // 分数计时器递增

        // 根据分数调整速度
        int targetSpeed = BackgroundImage.SPEED + (score / SPEED_UP_SCORE);
        if (targetSpeed != currentSpeed && targetSpeed <= MAX_SPEED) {
            currentSpeed = targetSpeed;
            background.setCurrentSpeed(currentSpeed);
            // 更新所有障碍物的速度
            for (Obstacle o : list) {
                o.setSpeed(currentSpeed);
            }
        }
        
        // 根据速度调整障碍物生成间隔
        if (addObstacleTimer >= 1300 - (currentSpeed - BackgroundImage.SPEED) * 50) {
            if (Math.random() * 100 > 40) {
                Obstacle obstacle = new Obstacle();
                obstacle.setSpeed(currentSpeed);  // 设置新障碍物的速度
                list.add(obstacle);
            }
            addObstacleTimer = 0;
        }
    }

    // 重写绘制组件方法
    public void paint(Graphics g) {
        paintImage(); // 绘制主图片内容
        g.drawImage(image, 0, 0, this);
    }

    // 游戏是否结束
    public boolean isFinish() {
        return finish;
    }

    // 使游戏结束
    public void gameOver() {
        ScoreRecorder.addNewScore(score); // 记录当前分数
        finish = true;
    }

    // 实现按下键盘按键方法
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode(); // 获取按下的按键值
        if (code == KeyEvent.VK_SPACE || code == KeyEvent.VK_UP) { // 如果是空格或者上键
            golden.jump(); // 恐龙跳跃
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }
}

