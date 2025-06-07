package Model.Location;

import Helper.Constants;
import Items.Item;

import java.awt.*;


//Tile represents a block in coordinate system
public class Tile {

    public int x_pos;

    public int y_pos;

    public int x_pos_left_down;

    private boolean has_item = true;

    public int y_pos_right_down;

    private Item tile_item;
    int width = Constants.BLOCK_SIZE;
    int height = Constants.BLOCK_SIZE;
    private Constants.Tile_Type tile_type;
    public boolean walkable(){
        if(tile_type == Constants.Tile_Type.WALL){
            return false;
        } else {
            return true;
        }
    }

    public void setTile_type(Constants.Tile_Type tile_type) {
        if ((this.tile_type != Constants.Tile_Type.START) && (this.tile_type != Constants.Tile_Type.CONNECTOR)){
            this.tile_type = tile_type;
        }
    }

    public void setCoordinates(int x_pos, int y_pos){
        this.x_pos = x_pos;
        this.y_pos = y_pos;
    }

    public Constants.Tile_Type getTile_type() {
        return tile_type;
    }

    public Tile() {
        this.tile_type = Constants.Tile_Type.DEFAULT;
    }

    public int getX_pos() {
        return x_pos;
    }

    public Rectangle getBounds(){
        return new Rectangle(x_pos, y_pos, width, height);
    }


    public int getY_pos() {
        return y_pos;
    }

    public boolean isWall(){
        return tile_type == Constants.Tile_Type.WALL;
    }

    public Item getTile_item() {
        return tile_item;
    }

    public boolean isHas_item() {
        return has_item;
    }

    public void removeItem(){
        this.tile_item = null;
        has_item = false;
    }

    public void setTile_item(Item tile_item) {
        this.tile_item = tile_item;
    }

}
