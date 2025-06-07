package Enemy;

import Helper.Constants;

import java.awt.*;

public class Walker extends Enemy{

    private Constants.Direction direction;

    private boolean can_move_right = true;

    private boolean can_move_left = true;

    private boolean can_move_up = true;

    private boolean can_move_down = true;



    public Walker(int enemy_pos_x, int enemy_pos_y) {
        super(enemy_pos_x, enemy_pos_y);
        if(Constants.rnd()){
            this.direction = Constants.Direction.Horizontal;
        } else {
            this.direction = Constants.Direction.Vertical;
        }
    }

    public void reverseDirection() {
        if (this.direction == Constants.Direction.Horizontal) {
            if(can_move_left){
                can_move_left = false;
                can_move_right = true;
            }
            else {
                can_move_right = false;
                can_move_left = true;
            }
        } else if (this.direction == Constants.Direction.Vertical) {
            if(can_move_up){
                can_move_up = false;
                can_move_down = true;
            }
            else {
                can_move_down = false;
                can_move_up = true;
            }

        }
    }

        @Override
    public void draw(Graphics2D painter) {
        painter.setColor(Color.RED);
        painter.drawRect(this.enemy_pos_x, this.enemy_pos_y, Constants.ENEMY_SIZE, Constants.ENEMY_SIZE);

    }

    public boolean isCan_move_right() {
        return can_move_right;
    }

    public void setCan_move_right(boolean can_move_right) {
        this.can_move_right = can_move_right;
    }

    public boolean isCan_move_left() {
        return can_move_left;
    }

    public void setCan_move_left(boolean can_move_left) {
        this.can_move_left = can_move_left;
    }

    public boolean isCan_move_up() {
        return can_move_up;
    }

    public void setCan_move_up(boolean can_move_up) {
        this.can_move_up = can_move_up;
    }

    public boolean isCan_move_down() {
        return can_move_down;
    }

    public void setCan_move_down(boolean can_move_down) {
        this.can_move_down = can_move_down;
    }


    public Constants.Direction getDirection() {
        return direction;
    }

}
