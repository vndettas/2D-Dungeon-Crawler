package Items;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public abstract class Item {

    protected int item_pos_x;

    protected int item_pos_y;

    public Image getIcon() {
        return icon;
    }

    private Image icon;

    public Item(int item_pos_x, int item_pos_y, String iconPath) {
        this.item_pos_x = item_pos_x;
        this.item_pos_y = item_pos_y;
        try {
            this.icon = ImageIO.read(getClass().getResource(iconPath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getItem_pos_x() {
        return item_pos_x;
    }

    public void setItem_pos_x(int item_pos_x) {
        this.item_pos_x = item_pos_x;
    }

    public int getItem_pos_y() {
        return item_pos_y;
    }

    public void setItem_pos_y(int item_pos_y) {
        this.item_pos_y = item_pos_y;
    }

    public abstract void use();

    public abstract void draw();
}
