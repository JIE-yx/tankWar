package com.Udemy.tankWar;
import java.io.File;
import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;


public class Tank {

    private final int max_hp = 100;
    private int speed = 5;
    private int x;
    private int y;
    private Direction direction;
    private boolean enemy; // 坦克 是敌是友 ？
    private boolean alive = true;
    private int hp = max_hp;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    boolean isAlive() {
        return alive;
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isEnemy() {
        return enemy;
    }

    public Tank(int x, int y, Direction direction) {
        this( x , y , direction , false );
    }

    public Tank(int x, int y, Direction direction, boolean enemy) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
    }




    public void setDirection(Direction direction) {
        this.direction = direction;
    }



    void move(){

        if ( this.stopped ){
            return;
        }

        x += direction.xFactor * speed;
        y += direction.yFactor * speed;

    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    Image getImage(){
        String prefix = this.enemy ? "e" : "";
        return direction.getImage(prefix + "tank");
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
            case KeyEvent.VK_CONTROL:
                fire();
                break;
            case KeyEvent.VK_A:
                superFire();
                break;
            case KeyEvent.VK_F2: GameClient.getInstance().restart();
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




    void fire(){
        Missle missle = new Missle(x + getImage().getWidth(null) / 2 - 6
                        ,y + getImage().getHeight(null) / 2 - 6
                        , enemy , direction);
        GameClient.getInstance().addMissle(missle);
        Tools.playAudio("shoot.wav");

    }
    void superFire(){
        for (Direction direction : Direction.values()){
            Missle missle = new Missle(x + getImage().getWidth(null) / 2 - 6
                    ,y + getImage().getHeight(null) / 2 - 6
                    , enemy , direction);
            GameClient.getInstance().getMissles().add(missle);
        }
        Tools.playAudio("shoot.wav");

    }

    public boolean isDying(){
        return this.hp <= max_hp * 0.25;
    }

    void draw(Graphics g){
        // 先保存 坦克 上一次的位置
        // 如果发生碰撞， 就 不更新坦克的位置
        int oldX = x;
        int oldY = y;
        // 画 坦克 之前 先要确定 坦克得方向
        // 然后 确定坦克 的 新位置
        // 然后 在 对应的 位置 画上 对应 朝向 的坦克

        if ( !enemy ){
            this.determineDirection();
        }
        this.move();

        //  0 <= x <= 800 - tankWidth
        //  防止 坦克 越界
        x = Math.max( 0 , Math.min(x , 1000 - getImage().getWidth(null)  )  );
        y = Math.max( 0 , Math.min(y , 1000 - getImage().getHeight(null)  )  );

        // 坦克 和 墙壁 的碰撞检测
        Rectangle tankRec = this.getRectangle();
        for (Wall wall : GameClient.getInstance().getWalls()){
            if ( tankRec.intersects(wall.getRectangel()) ){
                x = oldX;
                y = oldY;
                break ;
            }
        }


        // this可能时友方坦克 也可能是 敌方坦克
        // 用来 防止 坦克 间 的碰撞
        for (Tank enemyTank : GameClient.getInstance().getEnemyTanks()){
            if ( enemyTank != this && tankRec.intersects(enemyTank.getRectangle()) ){
                x = oldX;
                y = oldY;
                break;
            }
        }

        if ( enemy && this.getRectangle().intersects
                (GameClient.getInstance().getPlayerTank().getRectangle()) ){
            x = oldX;
            y = oldY;
        }

        if ( !enemy ){
            Blood blood = GameClient.getInstance().getBlood();
            if ( blood.isAlive() && tankRec.intersects
                    (GameClient.getInstance().getBlood().getRectangle()) ){
                this.hp = max_hp;
                Tools.playAudio("revive.wav");
                blood.setAlive(false);
            }
            g.setColor(Color.WHITE);
            g.fillRect(x , y - 10 ,
                    this.getImage().getWidth(null) ,10);
            g.setColor(Color.RED);
            int width = hp * this.getImage().getWidth(null) / max_hp ;
            g.fillRect(x , y - 10 , width , 10 );

        }



        g.drawImage(this.getImage(), this.x,this.y, null);
    }

    public Rectangle getRectangle(){
        return new Rectangle(x , y
                , getImage().getWidth(null) ,getImage().getHeight(null));
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
                this.direction = Direction.LEFT_UP;
            }
            // 上右
            else if ( up && !left && !down && right ){
                this.direction = Direction.RIGHT_UP;
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
                this.direction = Direction.LEFT_DOWN;
            }
            // 下右
            else if ( !up && !left && down && right ){
                this.direction = Direction.RIGHT_DOWN;
            }

        }


    }

    private final Random random = new Random();

    private int interval = random.nextInt(10) + 3;


    void actRandomly() {
        Direction[] dir = Direction.values();
        if ( interval == 0 ) {
            interval = random.nextInt(10) + 3;
            direction = dir[random.nextInt(8)];
            if (random.nextBoolean()) {
                fire();
            }
        }
        interval--;
    }
}
