package Model.Location;

import Enemy.Chaser;
import Enemy.Enemy;
import Enemy.Mine;
import Enemy.Walker;
import Helper.Constants;
import Items.*;
import Model.Inventory;
import Model.StateEngine;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Location {

    public final String location_name;

    private Inventory inventory;

    private Location exit;

    StateEngine engine;

    public Tile[][] tiles;

    private final int starting_pos_x;

    private final int starting_pos_y;

    private List<Enemy> location_enemies;

    private List<Item> location_items;

    public void setEngine(StateEngine engine) {
        this.engine = engine;
        for (Item item : location_items) {
            if (item instanceof Freeze) {
                ((Freeze) item).setEngine(engine);
            } else if (item instanceof ClearBlocks) {
                ((ClearBlocks) item).setEngine(engine);
            } else if (item instanceof ImmutablePlayer) {
                ((ImmutablePlayer) item).setEngine(engine);
            }
        }

    }

    public Tile getTile(int x, int y) {
        int tile_index_x = x / Constants.BLOCK_SIZE > 38 ? 0 : x / Constants.BLOCK_SIZE;
        int tile_index_y = y / Constants.BLOCK_SIZE > 21 ? 0 : y / Constants.BLOCK_SIZE;
        return tiles[tile_index_x][tile_index_y];
    }

    public void draw(Graphics2D painter) {
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles[i].length; ++j) {
                if (tiles[i][j].getTile_type() == Constants.Tile_Type.WALL) {
                    painter.setColor(Color.BLACK);
                    painter.fillRect(tiles[i][j].x_pos, tiles[i][j].y_pos, tiles[i][j].width, tiles[i][j].height);
                } else if (tiles[i][j].getTile_type() == Constants.Tile_Type.CONNECTOR) {
                    painter.setColor(Color.YELLOW);
                    painter.fillRect(tiles[i][j].x_pos, tiles[i][j].y_pos, tiles[i][j].width, tiles[i][j].height);
                    painter.setColor(Color.BLACK);
                } else if (tiles[i][j].getTile_type() == Constants.Tile_Type.ITEM_TILE) {
                    if (tiles[i][j].isHas_item()) {
                        painter.setColor(Color.BLACK);
                        int centerX = tiles[i][j].x_pos + tiles[i][j].width / 2;
                        int centerY = tiles[i][j].y_pos + tiles[i][j].height / 2;
                        int offset = 5;
                        painter.drawLine(centerX - offset, centerY, centerX + offset, centerY);
                        painter.drawLine(centerX, centerY - offset, centerX, centerY + offset);

                    }
                } else {
                }
            }
        }
        painter.setColor(Color.RED);
        location_enemies.forEach(enemy -> enemy.draw(painter));
    }

    public Location(String locationName, int starting_pos_x, int starting_pos_y, Inventory inventory) {
        this.location_items = new ArrayList<>();
        this.inventory = inventory;
        this.tiles = new Tile[Constants.WINDOW_WIDTH / Constants.BLOCK_SIZE][Constants.WINDOW_HEIGHT / Constants.BLOCK_SIZE];
        this.starting_pos_x = starting_pos_x;
        this.starting_pos_y = starting_pos_y;
        createTiles();
        this.location_enemies = new ArrayList<>();
        tiles[starting_pos_x / Constants.BLOCK_SIZE][starting_pos_y / Constants.BLOCK_SIZE].setTile_type(Constants.Tile_Type.START);
        tiles[Constants.WINDOW_WIDTH / Constants.BLOCK_SIZE - 1][1].setTile_type(Constants.Tile_Type.CONNECTOR);
        fillMap();
        location_name = locationName;
    }


    public boolean hasNeighbours(Tile tile) {
        int up = tile.y_pos > 0 ? ((tile.y_pos - 1) / Constants.BLOCK_SIZE) : 0;
        int down = tile.y_pos < Constants.BOTTOM_WALL ? ((tile.y_pos + 1) / Constants.BLOCK_SIZE) : Constants.BOTTOM_WALL;
        int left = tile.x_pos > 0 ? ((tile.x_pos - 1) / Constants.BLOCK_SIZE) : 0;
        int right = tile.x_pos < Constants.RIGHT_WALL ? ((tile.x_pos + 1) / Constants.BLOCK_SIZE) : 0;
        return tiles[tile.getX_pos() / Constants.BLOCK_SIZE][up].isWall() || tiles[tile.getX_pos() / Constants.BLOCK_SIZE][down].isWall() || tiles[left][tile.getY_pos() / Constants.BLOCK_SIZE].isWall() || tiles[right][tile.getY_pos() / Constants.BLOCK_SIZE].isWall();
    }

    public void createTiles() {
        int y = 0;
        int x = 0;
        for (int i = 0; i < tiles.length; ++i) {
            for (int j = 0; j < tiles[i].length; ++j) {
                tiles[i][j] = new Tile();
                tiles[i][j].setCoordinates(x, y);
                y += Constants.BLOCK_SIZE;
            }
            x += Constants.BLOCK_SIZE;
            y = 0;
        }
    }

    public void fillMap() {
        Random random = new Random();

        for (int i = 0; i < tiles.length; ++i) {
            int y = i * Constants.BLOCK_SIZE;
            for (int j = 0; j < tiles[i].length; ++j) {
                int x = j * Constants.BLOCK_SIZE;

                int num = random.nextInt(101);
                int probability_of_spawn = hasNeighbours(tiles[i][j]) ? 3 : 17;

                boolean isPlayerArea = (
                        y >= starting_pos_y && y < starting_pos_y + Constants.BLOCK_SIZE &&
                                x >= starting_pos_x && x < starting_pos_x + Constants.BLOCK_SIZE
                );

                if (num % probability_of_spawn == 0 && !isPlayerArea) {
                    tiles[i][j].setTile_type(Constants.Tile_Type.WALL);

                } else if (num % 10 == 0 && !isPlayerArea && tiles[i][j].walkable()) {
                    int enemies = random.nextInt(11);
                    if (enemies % 7 == 0) {
                        //Enemy Chaser = new Chaser(y, x);
                        //location_enemies.add(Chaser);
                        Enemy enemy = new Walker(y, x);
                        location_enemies.add(enemy);

                    } else if ((enemies % 2 == 0) && tiles[i][j].walkable()) {
                        Enemy enemy = new Walker(y, x);
                        location_enemies.add(enemy);
                    } else {
                        Enemy enemy = new Walker(y, x);
                        location_enemies.add(enemy);
                    }
                } else if (num % 75 == 0) {
                    int item_num = random.nextInt(6);
                    tiles[i][j].setTile_type(Constants.Tile_Type.ITEM_TILE);
                    if (item_num % 5 == 0) {
                        Item item = new Freeze(y, x);
                        location_items.add(item);
                        tiles[i][j].setTile_item(item);
                    } else if (item_num % 4 == 0) {
                        Item item = new KillEnemies(y, x, location_enemies);
                        tiles[i][j].setTile_item(item);

                    } else if (item_num % 3 == 0) {
                        Item item = new ImmutablePlayer(y, x);
                        tiles[i][j].setTile_item(item);
                        location_items.add(item);

                    } else if (item_num % 2 == 0) {
                        Item item = new SeeMines(y, x, location_enemies);
                        tiles[i][j].setTile_item(item);


                    } else if (item_num % 1 == 0) {
                        Item item = new ClearBlocks(y, x);
                        tiles[i][j].setTile_item(item);
                        location_items.add(item);
                    } else {
                        tiles[i][j].setTile_type(Constants.Tile_Type.DEFAULT);
                    }
                }
            }
        }
    }

    public String getLocation_name() {
        return location_name;
    }

    public Location getExit() {
        return exit;
    }

    public void setExit(Location exit) {
        this.exit = exit;
    }

    public int getStarting_pos_x() {
        return starting_pos_x;
    }

    public int getStarting_pos_y() {
        return starting_pos_y;
    }

    public List<Enemy> getLocation_enemies() {
        return location_enemies;
    }

    public StateEngine getEngine() {
        return engine;
    }
}
