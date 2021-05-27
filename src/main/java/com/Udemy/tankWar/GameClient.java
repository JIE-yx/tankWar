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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class GameClient extends JComponent {

    // 单例模式
    private static final GameClient Instance = new GameClient();

    private boolean gameOver = false;

    public static GameClient getInstance(){
        return Instance;
    }

    private AtomicInteger enemyTankKilled = new AtomicInteger();

    private Tank playerTank;

    Tank getPlayerTank() {
        return playerTank;
    }

    private List<Tank> enemyTanks;
    private List<Wall> walls ;// 一共 4 面 墙 ，一面墙 由若干 砖块 组成
    private List<Missle> missles;
    private List<Explosion> explosions;

    public List<Wall> getWalls() {
        return walls;
    }

    public List<Tank> getEnemyTanks() {
        return enemyTanks;
    }

    void addExplosion(Explosion e){
        explosions.add(e);
    }





    public List<Missle> getMissles() {
        return missles;
    }

    void addMissle(Missle missle){
        missles.add(missle);
    }

//    synchronized void removeMissle(Missle missle){
//        missles.remove(missle);
//    }

    public GameClient(){
        this.explosions = new ArrayList<>();
        this.playerTank = new Tank(400 ,100 , Direction.DOWN);
        this.walls = Arrays.asList(
                new Wall(300 ,150 ,true , 8),
                new Wall(300 ,300 ,true , 8),
                new Wall( 150,120 ,false , 8),
                new Wall( 650,120 ,false , 8),

                new Wall( 800,600 ,true , 4),
                new Wall( 150,120 ,false , 4),
                new Wall( 800,300 ,true , 4),
                new Wall( 150,700 ,false , 4)
        );
        this.missles = new CopyOnWriteArrayList<>();
        this.initialEnemyTanks();

        this.setPreferredSize(new Dimension(1000,1000));
    }

    private void initialEnemyTanks() {
        this.enemyTanks = new CopyOnWriteArrayList<>();
        // 敌方 坦克 共 12 辆
        // 排放 成 3 排 4 列
        for (int row = 0 ; row < 6; row++){
            for (int col = 0; col < 5 ; col ++){
                this.enemyTanks.add( new Tank(300 + 100 * col , 400 + 100 * row
                        ,Direction.UP , true) );
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0,0,1000,1000);
        if ( !playerTank.isAlive() ){
            g.setColor(Color.RED);
            g.setFont(new Font(null , Font.BOLD , 100));
            g.drawString("GameOver" , 125 , 300);
            g.setFont(new Font(null , Font.BOLD , 60));
            g.drawString("Press F2 to restart" , 125 , 400);
            gameOver = true;
            return ;
        }
        playerTank.draw(g);
        int count = enemyTanks.size();
        enemyTanks.removeIf(t -> !t.isAlive());
        enemyTankKilled.addAndGet( count - enemyTanks.size() );

        g.setColor(Color.WHITE);
        g.setFont(new Font(null , Font.BOLD , 17));
        g.drawString("Player HP: " + playerTank.getHp() , 10 , 25);
        g.drawString("Enemy Left: " + enemyTanks.size() , 10 , 50);
        g.drawString("Enemy killed: " + enemyTankKilled.get() , 10 , 75);
        g.drawImage(Tools.getImage("tree.png") , 920 , 920 , null);




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

        explosions.removeIf(t -> !t.isAlive());
        for (Explosion explosion : explosions){
            explosion.draw(g);
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
            client.repaint();
            if (client.gameOver  ){
                continue;
            }
            for(Tank tank : client.enemyTanks){
                tank.actRandomly();
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    void restart() {
        if ( ! playerTank.isAlive() ){
            this.playerTank = new Tank(400 ,100 , Direction.DOWN);;
            this.initialEnemyTanks();
            this.gameOver = false;
        }
    }
}
