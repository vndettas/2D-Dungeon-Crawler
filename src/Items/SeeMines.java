package Items;

import Enemy.Enemy;
import Enemy.Mine;

import java.util.List;

public class SeeMines extends Item{

List<Enemy> list;

    public SeeMines(int item_pos_x, int item_pos_y, List<Enemy> list) {
        super(item_pos_x, item_pos_y, "/res/icons/minki.png");
        this.list = list;
    }

    @Override
    public void use() {
        for (Enemy enemy : list){
            if (enemy instanceof Mine){
                ((Mine) enemy).setHighlightTimeLeft(3000);
            }
        }

    }

    @Override
    public void draw() {

    }
}
