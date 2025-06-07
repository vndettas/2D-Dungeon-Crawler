package Model;

import Helper.Constants;

import java.awt.*;


//Player class handles player movement, stats
public class Player {

    private int player_pos_x;

    private int player_pos_y;

    public int player_pos_x_right;

    public int player_pos_y_down;

    private Inventory inventory;
    public Player(int player_pos_x, int player_pos_y) {
        this.player_pos_x = player_pos_x;
        this.player_pos_y = player_pos_y;
        this.player_pos_x_right = player_pos_x + Constants.PLAYER_SIZE;
        this.player_pos_y_down = player_pos_y + Constants.PLAYER_SIZE;
        this.inventory = new Inventory();
    }
    public void move(int x, int y){
        this.player_pos_x = x;
        this.player_pos_y = y;
        this.player_pos_x_right = x + Constants.PLAYER_SIZE;
        this.player_pos_y_down = y + Constants.PLAYER_SIZE;
    }

    public void draw(Graphics2D graphics){
        graphics.setColor(Color.BLUE);
        graphics.fillRect(this.player_pos_x, this.player_pos_y, Constants.PLAYER_SIZE, Constants.PLAYER_SIZE);
    }

    public int getPlayer_pos_x() {
        return player_pos_x;
    }

    public int getPlayer_pos_y() {
        return player_pos_y;
    }

    public int getPlayer_pos_x_right() {
        return player_pos_x_right;
    }

    public void setPlayer_pos_x_right(int player_pos_x_right) {
        this.player_pos_x_right = player_pos_x_right;
    }

    public int getPlayer_pos_y_down() {
        return player_pos_y_down;
    }

    public void setPlayer_pos_y_down(int player_pos_y_down) {
        this.player_pos_y_down = player_pos_y_down;
    }



}
