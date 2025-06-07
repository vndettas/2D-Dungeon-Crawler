package Controller;

import Helper.Constants;
import Model.Location.Location;
import Model.Location.Tile;
import Model.Player;
import Model.StateEngine;
import View.GameGraphics;

public class GameThread implements Runnable{

    private GameGraphics game_graphics;


    private StateEngine engine;

    private Player player;

    private Location location;

    private volatile boolean pause = false;
    KeyListener input_handler;


    public GameThread(GameGraphics game_graphics, Location location, Player player, KeyListener listener, StateEngine state_engine) {
        this.player = player;
        this.input_handler = listener;
        this.game_graphics = game_graphics;
        this.location = location;
        this.engine = state_engine;
    }

    public void updatePlayer() {

        int player_x = player.getPlayer_pos_x();
        int player_y = player.getPlayer_pos_y();
        Tile current_tile = location.getTile(player_x, player_y);
        pickupItem(current_tile);
        int player_y_down = player.getPlayer_pos_y_down();
        int player_x_right = player.getPlayer_pos_x_right();



        if (input_handler.isMoving_left() && input_handler.isMoving_down()) {
            int new_player_x = player_x - Constants.PLAYER_SPEED;
            int new_player_y = player_y + Constants.PLAYER_SPEED;
            Tile next_tile = location.getTile(new_player_x, new_player_y);
            Tile next_tile_right_bottom = location.getTile(new_player_x+Constants.PLAYER_SIZE, new_player_y+Constants.PLAYER_SIZE);
            if(isInBounds(new_player_x, player_y) && next_tile.walkable() && next_tile_right_bottom.walkable()) player.move(new_player_x, new_player_y);

        } else if (input_handler.isMoving_right() && (input_handler.isMoving_up())) {
            int new_player_x = player_x + Constants.PLAYER_SPEED;
            int new_player_y = player_y - Constants.PLAYER_SPEED;
            Tile next_tile = location.getTile(new_player_x, new_player_y);
            Tile next_tile_right_bottom = location.getTile(new_player_x+Constants.PLAYER_SIZE, new_player_y+Constants.PLAYER_SIZE);
            if(isInBounds(new_player_x, player_y) && next_tile.walkable() && next_tile_right_bottom.walkable()) player.move(new_player_x, new_player_y);

        } else if (input_handler.isMoving_right() && (input_handler.isMoving_down())) {
            int new_player_x = player_x + Constants.PLAYER_SPEED;
            int new_player_y = player_y + Constants.PLAYER_SPEED;
            Tile next_tile = location.getTile(new_player_x, new_player_y);
            Tile next_tile_right_bottom = location.getTile(new_player_x+Constants.PLAYER_SIZE, new_player_y+Constants.PLAYER_SIZE);
            if(isInBounds(new_player_x, player_y) && next_tile.walkable() && next_tile_right_bottom.walkable()) player.move(new_player_x, new_player_y);

        } else if (input_handler.isMoving_left() && (input_handler.isMoving_up())) {
            int new_player_x = player_x - Constants.PLAYER_SPEED;
            int new_player_y = player_y - Constants.PLAYER_SPEED;
            Tile next_tile = location.getTile(new_player_x, new_player_y);
            if(isInBounds(new_player_x, player_y) && next_tile.walkable()) player.move(new_player_x, new_player_y);

        } else if (input_handler.isMoving_down()) {
            int new_player_y = player_y + Constants.PLAYER_SPEED;
            Tile next_tile = location.getTile(player_x, player_y_down + Constants.PLAYER_SPEED);
            Tile next_tile_right_bottom = location.getTile(player_x+Constants.PLAYER_SIZE, new_player_y+Constants.PLAYER_SIZE);
            if(isInBounds(player_x, new_player_y) && next_tile.walkable() && next_tile_right_bottom.walkable()) player.move(player_x, new_player_y);

        } else if (input_handler.isMoving_left()) {
            int new_player_x = player_x - Constants.PLAYER_SPEED;
            Tile next_tile = location.getTile(new_player_x, player_y);
            if(isInBounds(new_player_x, player_y) && next_tile.walkable()) player.move(new_player_x, player_y);

        } else if (input_handler.isMoving_right()) {
            int new_player_x = player_x + Constants.PLAYER_SPEED;
            Tile next_tile = location.getTile(new_player_x, player_y);
            Tile next_tile_right_bottom = location.getTile(new_player_x+Constants.PLAYER_SIZE, player_y+Constants.PLAYER_SIZE);
            if(isInBounds(new_player_x, player_y) &&  next_tile.walkable() && next_tile_right_bottom.walkable()) player.move(new_player_x, player_y);


        } else if (input_handler.isMoving_up()) {
            int new_player_y = player_y - Constants.PLAYER_SPEED;
            Tile next_tile = location.getTile(player_x, new_player_y);
            if(isInBounds(player_x, new_player_y) && next_tile.walkable()) player.move(player_x, new_player_y);

        }

        if(isOnExit(player_x, player_y)){
            engine.goNextLocation();
            this.location = engine.getPlayer_location();

        }
    }

    void pickupItem(Tile tile){
        if (tile.getTile_type() == Constants.Tile_Type.ITEM_TILE && !engine.getInventory().isFull()){
            if(!(tile.getTile_item() == null)) engine.getInventory().addItem(tile.getTile_item());
            tile.removeItem();
        }
    }



        @Override
    public void run() {
        while(!engine.is_game_over()){
            while (pause){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            game_graphics.repaint();

            updatePlayer();
            try {
                Thread.sleep(Constants.DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isOnExit(int x, int y){
        Tile current_tile = location.getTile(x, y);
        return current_tile.getTile_type() == Constants.Tile_Type.CONNECTOR ? true : false;

    }

    public boolean isInBounds(int x, int y){
        return x > 0 && x < Constants.RIGHT_WALL - 22 && y > 0 && y < Constants.BOTTOM_WALL - 15;
    }


    public boolean isPaused() {
        return pause;
    }

    public void pause_loop() {
        this.pause = true;
    }

    public void unpause(){
        this.pause = false;
    }

}
