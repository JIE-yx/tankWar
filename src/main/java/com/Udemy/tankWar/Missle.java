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
        return direction.getImage("missile");
    }

    public void draw(Graphics g) {
        move();
        if ( x < 0 || x > 800 || y < 0 || y > 600 ){
            return ;
        }
        g.drawImage(getImage() , x, y , null);

    }

    void move(){
        x += direction.xFactor * speed;
        y += direction.yFactor * speed;
    }
}
