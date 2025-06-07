package Items;

import Model.StateEngine;

import java.util.Timer;
import java.util.TimerTask;

public class Freeze extends Item{

    StateEngine engine;

    public Freeze(int item_pos_x, int item_pos_y) {
        super(item_pos_x, item_pos_y, "/res/icons/cool.png");

    }

    public void setEngine(StateEngine engine) {
        this.engine = engine;
    }

    @Override
    public void use() {
        engine.getEnemy_thread().pause_loop();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                engine.getEnemy_thread().unpause();
            }
        }, 3000);

    }

    @Override
    public void draw() {

    }
}
