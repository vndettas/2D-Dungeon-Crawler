package Enemy;

import Helper.Constants;

import java.awt.*;

public abstract class Enemy {

    protected int enemy_pos_x;
    protected int enemy_pos_y;

    protected int enemy_target_pos_x;

    protected int enemy_target_pos_y;

    public abstract void draw(Graphics2D painter);


    public Enemy(int enemy_pos_x, int enemy_pos_y) {
        this.enemy_pos_x = enemy_pos_x;
        this.enemy_pos_y = enemy_pos_y;
    }

    public void setEnemy_target_pos_x(int enemy_target_pos_x) {
        this.enemy_target_pos_x = enemy_target_pos_x;
    }

    public void setEnemy_target_pos_y(int enemy_target_pos_y) {
        this.enemy_target_pos_y = enemy_target_pos_y;
    }

    public static boolean intersects(Enemy a, Enemy b) {
        return a.getEnemy_pos_x() < b.getEnemy_pos_x() + Constants.ENEMY_SIZE &&
                a.getEnemy_pos_x() + Constants.ENEMY_SIZE > b.getEnemy_pos_x() &&
                a.getEnemy_pos_y() < b.getEnemy_pos_y() + Constants.ENEMY_SIZE &&
                a.getEnemy_pos_y() + Constants.ENEMY_SIZE > b.getEnemy_pos_y();
    }

    public int getEnemy_pos_x() {
        return enemy_pos_x;
    }

    public int getEnemy_pos_y() {
        return enemy_pos_y;
    }

    public int getEnemy_target_pos_x() {
        return enemy_target_pos_x;
    }

    public int getEnemy_target_pos_y() {
        return enemy_target_pos_y;
    }

    public void setEnemy_pos_x(int enemy_pos_x) {
        this.enemy_pos_x = enemy_pos_x;
    }

    public void setEnemy_pos_y(int enemy_pos_y) {
        this.enemy_pos_y = enemy_pos_y;
    }
}
