package Items;

import Enemy.Enemy;

import java.util.List;

public class KillEnemies extends Item{

    private List<Enemy> list;


    public KillEnemies(int item_pos_x, int item_pos_y, List<Enemy> list) {
        super(item_pos_x, item_pos_y, "/res/icons/killall.png");
        this.list = list;

    }

    @Override
    public void use() {
        list.clear();

    }

    @Override
    public void draw() {

    }
}
