package com.Udemy.tankWar;

import java.awt.*;

public class Wall {

    private int x;
    private int y;
    private boolean horizontal ;
    private int brickNum;

    public Wall(int x, int y, boolean horizontal, int brickNum) {
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
        this.brickNum = brickNum;
    }

    public void draw(Graphics g){
        Image brickImage = Tools.getImage("brick.png");
        if ( horizontal ){
            for ( int i = 0 ; i < brickNum ; i ++ ){
                g.drawImage(brickImage , x + i * brickImage.getWidth(null)
                        , y , null);
            }
            return ;
        }
        for ( int i = 0 ; i < brickNum ; i ++ ){
            g.drawImage(brickImage , x
                    , y + i * brickImage.getHeight(null) , null);
        }
    }
}
