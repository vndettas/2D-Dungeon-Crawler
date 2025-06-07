package Model;

import Controller.EnemyThread;
import Controller.GameThread;
import Helper.Constants;
import Helper.Debugger;
import Model.Location.Location;
import View.GameGraphics;


//Class responsible for Game state
public class StateEngine {

    private Player player;

    GameThread game_thread;

    EnemyThread enemy_thread;

    Location player_location;

    Inventory inventory;

    boolean lose_condition;

    private boolean invincible = false;


    boolean win_condition;


    private int levels_count = 1;

    GameGraphics graphics;

    public StateEngine() {
        this.inventory = new Inventory();
        Location first_loc = new Location("BEMS-DEMS", Constants.BLOCK_SIZE * 2, Constants.BLOCK_SIZE*2, inventory);
        this.player_location = first_loc;
        Location second_loc = new Location("second", Constants.BLOCK_SIZE * 2, Constants.BLOCK_SIZE*2, inventory);
        Location third_loc = new Location("third", Constants.BLOCK_SIZE * 2, Constants.BLOCK_SIZE*2, inventory);
        first_loc.setExit(second_loc);
        second_loc.setExit(third_loc);
    }

    public void goNextLocation() {
        enemy_thread.pause_loop();
        game_thread.pause_loop();
        if (player_location.getExit() == null) {
            win_condition = true;
        } else {
            StateEngine engine= player_location.getEngine();
            this.player_location = player_location.getExit();
            this.player_location.setEngine(engine);
            this.player.move(player_location.getStarting_pos_x(), player_location.getStarting_pos_y());
            levels_count++;
            game_thread.unpause();
            enemy_thread.unpause();

        }
    }


    public void setLose_condition(boolean lose_condition) {
        Debugger.log("Invicibility: " + this.invincible);
        if (!invincible) {
            Debugger.log("If executed");
            this.lose_condition = lose_condition;
        }
    }

    public boolean isInvincible() {
        return invincible;
    }

    public void setInvincible(boolean invincible) {
        Debugger.log("invincible set: " + invincible);
        this.invincible = invincible;
    }

    public void setPlayer_location(Location player_location) {
        this.player_location = player_location;
    }

    public void setGraphics(GameGraphics graphics) {
        this.graphics = graphics;
    }

    public int getLevels_count() {
        return levels_count;
    }

    public Inventory getInventory() {
        return inventory;
    }


    public void setPlayer(Player player) {
        this.player = player;
    }

    public Location getPlayer_location() {
        return player_location;
    }

    public Player getPlayer() {
        return player;
    }

    public void setThread(GameThread thread){
        this.game_thread = thread;
    }

    public void setThread(EnemyThread thread) {
        this.enemy_thread = thread;
    }

    public boolean is_game_over() {
        return lose_condition;
    }

    public GameThread getGame_thread() {
        return game_thread;
    }

    public EnemyThread getEnemy_thread() {
        return enemy_thread;
    }


}
