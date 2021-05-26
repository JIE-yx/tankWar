package com.Udemy.tankWar;

import java.awt.*;

public class Explosion {

    private int x;
    private int y;
    private int step = 0;
    private boolean alive = true;

    public Explosion(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    void draw(Graphics g){
        if ( step > 10 ){
            alive = false;
            return ;
        }
        g.drawImage(Tools.getImage(step + ".gif") ,x , y , null);
        step++;
    }
}
