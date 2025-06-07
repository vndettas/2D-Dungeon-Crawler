package Items;

import Model.StateEngine;

import java.util.Timer;
import java.util.TimerTask;

public class ImmutablePlayer extends Item{

    StateEngine engine;

    public void setEngine(StateEngine engine) {
        this.engine = engine;
    }

    public ImmutablePlayer(int item_pos_x, int item_pos_y) {
        super(item_pos_x, item_pos_y, "/res/icons/people.png");
    }

    @Override
    public void use() {
        engine.setInvincible(true);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                engine.setInvincible(false);
            }
        }, 10000);


    }

    @Override
    public void draw() {

    }
}
