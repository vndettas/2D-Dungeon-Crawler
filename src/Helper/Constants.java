package Helper;

import java.util.Random;

public class Constants {

    public static final int WINDOW_WIDTH = 1260;

    public static final int WINDOW_HEIGHT = 720;

    public static final int BLOCK_SIZE = 32;

    public static final int PLAYER_SPEED = 2;

    public static final int ENEMY_SPEED = 1;

    public static final int PLAYER_SIZE = 10;

    public static final int BOTTOM_WALL = Constants.WINDOW_HEIGHT - 35;

    public static final int RIGHT_WALL = Constants.WINDOW_WIDTH;
    public static final int FPS = 90;

    public static final int ENEMY_SIZE = 12;

    public static final long DELAY = 1000 / FPS;

    public enum Tile_Type {
        DEFAULT, WALL, START, CONNECTOR, ITEM_TILE;
    }

    public enum Direction {
        Horizontal, Vertical;
    }

    public static boolean rnd() {
        Random random = new Random();
        int num = random.nextInt(100);
        return num % 2 == 0;
    }
}
