package com.Udemy.tankWar;

import java.awt.*;
import java.util.Random;

public class Blood {
    private final Random random = new Random();
    private int x;
    private int y;
    private boolean alive = true;
    private final Image image;
    public Blood(int x, int y) {
        this.x = x;
        this.y = y;
        this.image = Tools.getImage("blood.png");
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;

    }

    void draw(Graphics g){
        x += random.nextInt(10);
        x -= random.nextInt(10);
        y += random.nextInt(10);
        y -= random.nextInt(10);
        x = Math.max( 0 , Math.min(x , 1000 - image.getWidth(null)  )  );
        y = Math.max( 0 , Math.min(y , 1000 - image.getHeight(null)  )  );
        g.drawImage(image ,x , y, null);
    }

    Rectangle getRectangle(){
        return new Rectangle(x , y , image.getWidth(null) , image.getHeight(null));
    }

}
