package Enemy;

import Helper.Constants;

import java.awt.*;

public class Chaser extends Enemy {

    private boolean is_active = false;

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Chaser(int enemy_pos_x, int enemy_pos_y) {
        super(enemy_pos_x, enemy_pos_y);

    }


    @Override
    public void draw(Graphics2D painter) {
        painter.setColor(Color.CYAN);
        if (is_active) {
            painter.fillRect(this.enemy_pos_x, this.enemy_pos_y, Constants.ENEMY_SIZE, Constants.ENEMY_SIZE);
        } else if (!is_active) {
            painter.drawRect(this.enemy_pos_x, this.enemy_pos_y, Constants.ENEMY_SIZE, Constants.ENEMY_SIZE);
        }
    }
}
