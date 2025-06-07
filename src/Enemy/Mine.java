package Enemy;

import Helper.Constants;

import java.awt.*;

public class Mine extends Enemy{

    private boolean is_active;

    private long highlightTimeLeft = 0;

    public boolean isIs_active() {
        return is_active;
    }

    public void setIs_active(boolean is_active) {
        this.is_active = is_active;
    }

    public Mine(int enemy_pos_x, int enemy_pos_y) {
        super(enemy_pos_x, enemy_pos_y);
    }

    @Override
    public void draw(Graphics2D painter) {
        painter.setColor(Color.YELLOW);
        if (is_active) {
            painter.fillRect(this.enemy_pos_x, this.enemy_pos_y, Constants.ENEMY_SIZE, Constants.ENEMY_SIZE);
        } else if (!is_active) {
        }
    }

    public void updateHighlight(long deltaTime) {
        if (highlightTimeLeft > 0) {
            highlightTimeLeft -= deltaTime;
            if (highlightTimeLeft < 0) {
                highlightTimeLeft = 0;
            }
            setIs_active(true);
        } else {
            setIs_active(false);
        }
    }

    public void setHighlightTimeLeft(int i) {
        highlightTimeLeft = i;
    }

    public long getHighlightTimeLeft() {
        return highlightTimeLeft;
    }
}
