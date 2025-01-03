/**
 * @author 杨晨
 * @studentId 1230204886
 */
package com.dinosaur.view;

import com.dinosaur.service.ScoreRecorder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// 成绩对话窗
public class ScoreDialog extends JDialog {
    public ScoreDialog(JFrame frame) {
        super(frame, true); // 调用父类构造方法,阻塞父窗体
        int scores[] = ScoreRecorder.getScores(); // 获取当前前三名成绩
        JPanel scoreP = new JPanel(new GridLayout(4, 1)); // 成绩面板，4行1列
        scoreP.setBackground(Color.WHITE); // 白色背景
        JLabel title = new JLabel("得分排行榜", JLabel.CENTER); // 标题标签，居中
        title.setFont(new Font("黑体", Font.BOLD, 20)); // 设置字体
        title.setForeground(Color.RED); // 红色体字
        JLabel first = new JLabel("第一名：" + scores[2], JLabel.CENTER); // 第一名标签
        JLabel second = new JLabel("第二名：" + scores[1], JLabel.CENTER); // 第二名标签
        JLabel third = new JLabel("第三名：" + scores[0], JLabel.CENTER); // 第三名标签
        JButton restart = new JButton("重新开始"); // 重新开始按钮
        restart.addActionListener(new ActionListener() { // 按钮添加事件监听
            @Override
            public void actionPerformed(ActionEvent e) { // 当点击时
                dispose(); // 销毁对话框
            }
        });

        scoreP.add(title); // 成绩面板添加标签
        scoreP.add(first);
        scoreP.add(second);
        scoreP.add(third);

        Container c = getContentPane(); // 获取主容器
        c.setLayout(new BorderLayout()); // 使用边界布局
        c.add(scoreP, BorderLayout.CENTER); // 成绩面板放中间
        c.add(restart, BorderLayout.SOUTH); // 按钮放底部

        setTitle("游戏结束"); // 对话框标题

        int width, height; // 对话框宽高
        width = height = 200; // 对话框宽高均为200
        // 获得主窗体中居中位置的横坐标
        int x = frame.getX() + (frame.getWidth() - width) / 2;
        // 获得主窗体中居中位置的纵坐标
        int y = frame.getY() + (frame.getHeight() - height) / 2;
        setBounds(x, y, width, height); // 设置坐标和宽高
        setVisible(true); // 显示对话框
    }

}

