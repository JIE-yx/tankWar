package com.Udemy.tankWar;

import com.sun.org.apache.bcel.internal.generic.SWITCH;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Tank {

    private int x;
    private int y;
    private Direction direction;
    private boolean enemy; // 坦克 是敌是友 ？

    public Tank(int x, int y, Direction direction) {
        this( x , y , direction , false );
    }

    public Tank(int x, int y, Direction direction, boolean enemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public void setY(int y) {
        this.y = y;
    }

    void move(){
        if ( this.stopped ){
            return;
        }

        switch (direction){
            case UP:
                y -= 5;
                break;
            case DOWN:
                y += 5;
                break;
            case LEFT:
                x -= 5;
                break;
            case RIGHT:
                x += 5;
                break;
            case UPLEFT:
                x -= 5;
                y -= 5;
                break;
            case UPRIGHT:
                x += 5;
                y -= 5;
                break;
            case DOWNLEFT:
                x -= 5;
                y += 5;
                break;
            case DOWNRIGHT:
                x += 5;
                y += 5;
                break;
        }
    }

    Image getImage(){
        String prefix = this.enemy ? "e" : "";
        switch (direction){
            case UP:
                return new ImageIcon("assets/images/" + prefix +"tankU.gif").getImage();
            case DOWN:
                return new ImageIcon("assets/images/"+ prefix +"tankD.gif").getImage();
            case LEFT:
                return new ImageIcon("assets/images/"+ prefix +"tankL.gif").getImage();
            case RIGHT:
                return new ImageIcon("assets/images/"+ prefix +"tankR.gif").getImage();
            case UPLEFT:
                return new ImageIcon("assets/images/"+ prefix +"tankLU.gif").getImage();
            case UPRIGHT:
                return new ImageIcon("assets/images/"+ prefix +"tankRU.gif").getImage();
            case DOWNLEFT:
                return new ImageIcon("assets/images/"+ prefix +"tankLD.gif").getImage();
            case DOWNRIGHT:
                return new ImageIcon("assets/images/"+ prefix +"tankRD.gif").getImage();
        }
        return null;
    }

    private boolean up , down , left , right;

    public void keyPressed(KeyEvent e) {

        switch ( e.getKeyCode() ){
            case KeyEvent.VK_UP:
                up = true;
                break;
            case KeyEvent.VK_DOWN:
                down = true;
                break;
            case KeyEvent.VK_LEFT:
                left = true;
                break;
            case KeyEvent.VK_RIGHT:
                right = true;
                break;
        }

    }

    public void keyReleased(KeyEvent e) {

        switch ( e.getKeyCode() ){
            case KeyEvent.VK_UP:
                up = false;
                break;
            case KeyEvent.VK_DOWN:
                down = false;
                break;
            case KeyEvent.VK_LEFT:
                left = false;
                break;
            case KeyEvent.VK_RIGHT:
                right = false;
                break;
        }
    }

    void draw(Graphics g){
        // 画 坦克 之前 先要确定 坦克得方向
        // 然后 确定坦克 的 新位置
        // 然后 在 对应的 位置 画上 对应 朝向 的坦克
        this.determineDirection();
        this.move();
        g.drawImage(this.getImage(), this.x,this.y, null);
    }

    private boolean stopped;

    private void determineDirection(){

        if ( !up && !down && !left && !right ){
            this.stopped = true;
        }
        else{

            this.stopped = false;

            // 定义 8 个 方向
            // 上左
            if ( up && left && !down && !right ){
                this.direction = Direction.UPLEFT;
            }
            // 上右
            else if ( up && !left && !down && right ){
                this.direction = Direction.UPRIGHT;
            }
            // 上
            else if ( up && !left && !down && !right  ){
                this.direction = Direction.UP;
            }
            // 左
            else if ( !up && left && !down && !right  ){
                this.direction = Direction.LEFT;
            }
            // 右
            else if ( !up && !left && !down && right ){
                this.direction = Direction.RIGHT;
            }
            // 下
            else if ( !up && !left && down && !right ){
                this.direction = Direction.DOWN;
            }
            // 下左
            else if ( !up && left && down && !right ){
                this.direction = Direction.DOWNLEFT;
            }
            // 下右
            else if ( !up && !left && down && right ){
                this.direction = Direction.DOWNRIGHT;
            }

        }


    }

}
