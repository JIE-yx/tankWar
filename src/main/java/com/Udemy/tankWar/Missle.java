package com.Udemy.tankWar;

import java.awt.*;

public class Missle {

    private static final int speed = 12;
    private int x;
    private int y;
    private final boolean enemy;
    private final Direction direction;

    public Missle(int x, int y, boolean enemy, Direction direction) {
        this.x = x;
        this.y = y;
        this.enemy = enemy;
        this.direction = direction;
    }

    Image getImage(){
        switch (direction){
            case UP:
                return Tools.getImage( "missileU.gif");
            case DOWN:
                return Tools.getImage(  "missileD.gif");
            case LEFT:
                return Tools.getImage( "missileL.gif");
            case RIGHT:
                return Tools.getImage("missileR.gif");
            case UPLEFT:
                return Tools.getImage( "missileLU.gif");
            case UPRIGHT:
                return Tools.getImage("missileRU.gif");
            case DOWNLEFT:
                return Tools.getImage( "missileLD.gif");
            case DOWNRIGHT:
                return Tools.getImage( "missileRD.gif");
        }
        return null;
    }

    public void draw(Graphics g) {
        move();
        if ( x < 0 || x > 800 || y < 0 || y > 600 ){
            return ;
        }
        g.drawImage(getImage() , x, y , null);

    }

    void move(){

        switch (direction){
            case UP:
                y -= speed;
                break;
            case DOWN:
                y += speed;
                break;
            case LEFT:
                x -= speed;
                break;
            case RIGHT:
                x += speed;
                break;
            case UPLEFT:
                x -= speed;
                y -= speed;
                break;
            case UPRIGHT:
                x += speed;
                y -= speed;
                break;
            case DOWNLEFT:
                x -= speed;
                y += speed;
                break;
            case DOWNRIGHT:
                x += speed;
                y += speed;
                break;
        }
    }
}
