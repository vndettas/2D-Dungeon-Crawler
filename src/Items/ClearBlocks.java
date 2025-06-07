package Items;

import Helper.Constants;
import Model.Location.Tile;
import Model.StateEngine;

public class ClearBlocks extends Item{

    StateEngine engine;

    public void setEngine(StateEngine engine) {
        this.engine = engine;
    }

    public ClearBlocks(int item_pos_x, int item_pos_y) {
        super(item_pos_x, item_pos_y, "/res/icons/shape.png");
    }

    @Override
    public void use() {
        Tile[][] tiles = engine.getPlayer_location().tiles;
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles[i].length; ++j) {
                tiles[i][j].setTile_type(Constants.Tile_Type.DEFAULT);
            }


        }
    }

    @Override
    public void draw() {

    }
}
