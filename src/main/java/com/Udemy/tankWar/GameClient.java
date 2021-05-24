package com.Udemy.tankWar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GameClient extends JComponent {

    private Tank playerTank;

    public GameClient(){
        this.playerTank = new Tank(400 ,100 , Direction.DOWN);
        this.setPreferredSize(new Dimension(800,600));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        playerTank.draw(g);

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("坦克大战1.0");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        GameClient client = new GameClient();
        client.repaint();
        frame.add(client);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                //super.keyPressed(e);
                // 把 键盘 的监听 事件 传给 坦克
                // 让 坦克 根据 键盘来 进行 对应的 操作
                client.playerTank.keyPressed(e);
            }
            @Override
            public void keyReleased(KeyEvent e) {
                //super.keyReleased(e);
                client.playerTank.keyReleased(e);
            }
        });


        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        while ( true ){

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.repaint();
        }

    }
}
