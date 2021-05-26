package com.Udemy.tankWar;

import java.awt.*;

public class Missle {

    private static final int speed = 12;
    private int x;
    private int y;
    private final boolean enemy;
    private final Direction direction;
    private boolean alive = true;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Missle(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    Image getImage(){
        return direction.getImage("missile");
    }

    public void draw(Graphics g) {
        move();
        // 如果 子弹 越界
        // 或者 子弹 与 其他 坦克 碰撞
        // 子弹 消失 ， 并且 从 GameClient 的 missles 列表重 移除
        if ( x < 0 || x > 800 || y < 0 || y > 600 ){
            this.setAlive(false);
            return ;
        }
        Rectangle rectangle = this.getRectangle();

        for (Wall wall : GameClient.getInstance().getWalls()){
            if ( rectangle.intersects(wall.getRectangel()) ){
                this.setAlive(false);
                return;
            }
        }

        // 检测 子弹 与 坦克 发生碰撞

        // 如果 是 敌方 的子弹， 只需要检测 是否 与 我方坦克发生 碰撞
        if ( enemy ){
            Tank playerTank = GameClient.getInstance().getPlayerTank();
            if ( rectangle.intersects(playerTank.getRectangle()) ){
                playerTank.setHp(playerTank.getHp() - 20);
                if ( playerTank.getHp() <= 0 ){
                    playerTank.setAlive(false);
                }
                GameClient.getInstance().getPlayerTank().setAlive(false);
                this.setAlive(false);
                return;
            }
        }
        else{
            for (Tank tank : GameClient.getInstance().getEnemyTanks()){
                if ( rectangle.intersects(tank.getRectangle()) ){
                    tank.setAlive(false);
                    this.setAlive(false);
                    return;
                }
            }
        }



        g.drawImage(getImage() , x, y , null);

    }

    void move(){
        x += direction.xFactor * speed;
        y += direction.yFactor * speed;
    }

    Rectangle getRectangle(){
        return new Rectangle(x ,y
                , getImage().getWidth(null),getImage().getHeight(null));
    }
}
