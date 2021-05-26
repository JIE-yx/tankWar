package com.Udemy.tankWar;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GameClient extends JComponent {

    // 单例模式
    private static final GameClient Instance = new GameClient();

    public static GameClient getInstance(){
        return Instance;
    }


    private Tank playerTank;

    Tank getPlayerTank() {
        return playerTank;
    }

    private List<Tank> enemyTanks;
    private List<Wall> walls ;// 一共 4 面 墙 ，一面墙 由若干 砖块 组成
    private List<Missle> missles;

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    public List<Missle> getMissles() {
        return missles;
    }

    synchronized void addMissle(Missle missle){
        missles.add(missle);
    }

//    synchronized void removeMissle(Missle missle){
//        missles.remove(missle);
//    }

    public GameClient(){
        this.playerTank = new Tank(400 ,100 , Direction.DOWN);
        this.walls = Arrays.asList(
                new Wall(300 ,150 ,true , 8),
                new Wall(300 ,300 ,true , 8),
                new Wall( 150,120 ,false , 8),
                new Wall( 650,120 ,false , 8)
        );
        this.missles = new ArrayList<>();
        this.initialEnemyTanks();

        this.setPreferredSize(new Dimension(800,600));
    }

    private void initialEnemyTanks() {
        this.enemyTanks = new ArrayList<>(12);
        // 敌方 坦克 共 12 辆
        // 排放 成 3 排 4 列
        for (int row = 0 ; row < 2; row++){
            for (int col = 0; col < 3 ; col ++){
                this.enemyTanks.add( new Tank(300 + 100 * col , 400 + 100 * row
                        ,Direction.UP , true) );
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,800,600);
        playerTank.draw(g);
        enemyTanks.removeIf(t -> !t.isAlive());
        if ( enemyTanks.isEmpty() ){
            this.initialEnemyTanks();
        }
        for (Tank enemyTank : enemyTanks){
            enemyTank.draw(g);
        }
        for (Wall wall : walls){
            wall.draw(g);
        }
        missles.removeIf(t -> !t.isAlive());
        for (Missle missle : missles){
            missle.draw(g);
        }

    }

    public static void main(String[] args) {


        com.sun.javafx.application.PlatformImpl.startup(()->{});
        JFrame frame = new JFrame();
        frame.setTitle("坦克大战1.0");
        frame.setIconImage(new ImageIcon("assets/images/icon.png").getImage());
        GameClient client = GameClient.getInstance();
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
