package com.Udemy.tankWar;

import java.awt.*;

public class Wall {

    private int x;
    private int y;
    private boolean horizontal ;
    private int brickNum;
    private final Image brickImage;

    public Wall(int x, int y, boolean horizontal, int brickNum) {
        this.brickImage = Tools.getImage("brick.png");
        this.x = x;
        this.y = y;
        this.horizontal = horizontal;
        this.brickNum = brickNum;
    }

    // 返回 墙 所在的矩形
    // new Rectangel(x_start , y_start , x_len , y_len)
    public Rectangle getRectangel(){
        return horizontal ?
                new Rectangle(x , y
                        , brickNum * brickImage.getWidth(null)
                        , brickImage.getHeight(null)) :
                new Rectangle(x , y
                        , brickImage.getWidth(null)
                        , brickNum * brickImage.getHeight(null));
    }

    public void draw(Graphics g){
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
