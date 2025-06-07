package Controller;

import Enemy.Chaser;
import Enemy.Enemy;
import Enemy.Mine;
import Enemy.Walker;
import Helper.Constants;
import Model.Location.Tile;
import Model.StateEngine;

import java.util.ArrayList;
import java.util.List;

public class EnemyThread implements Runnable {

    StateEngine engine;

    private volatile boolean pause = false;


    public void checkMoves(Walker enemy) {
        int next_right_x = enemy.getEnemy_pos_x() + Constants.ENEMY_SIZE;
        int next_leftx_x = enemy.getEnemy_pos_x() - Constants.ENEMY_SIZE;
        int next_up_y = enemy.getEnemy_pos_y() - Constants.ENEMY_SIZE;
        int next_down_y = enemy.getEnemy_pos_y() + Constants.ENEMY_SIZE;
        Tile next_right_tile = engine.getPlayer_location().getTile(next_right_x, enemy.getEnemy_pos_y());
        Tile next_left_tile = engine.getPlayer_location().getTile(next_leftx_x + Constants.ENEMY_SIZE, enemy.getEnemy_pos_y());
        Tile next_up_tile = engine.getPlayer_location().getTile(enemy.getEnemy_pos_x(), next_up_y + Constants.ENEMY_SIZE);
        Tile next_down_tile = engine.getPlayer_location().getTile(enemy.getEnemy_pos_x(), next_down_y);
        if ((next_right_x > Constants.RIGHT_WALL) || !next_right_tile.walkable()) {
            enemy.setCan_move_right(false);
            enemy.setCan_move_left(true);
        }
        if ((next_leftx_x < 0) || !next_left_tile.walkable()) {
            enemy.setCan_move_left(false);
            enemy.setCan_move_right(true);
        }
        if ((enemy.getEnemy_pos_y() <= 1) || !next_up_tile.walkable()) {
            enemy.setCan_move_up(false);
            enemy.setCan_move_down(true);
        }
        if ((next_down_y > Constants.BOTTOM_WALL || !next_down_tile.walkable())) {
            enemy.setCan_move_down(false);
            enemy.setCan_move_up(true);
        }

    }

    public boolean checkMoves(Chaser chaser) {
        int chaser_new_pos_x = chaser.getEnemy_target_pos_x();
        int chaser_new_pos_y = chaser.getEnemy_target_pos_y();
        int s  = Constants.ENEMY_SIZE;

        Tile t1 = engine.getPlayer_location()
                .getTile(
                        chaser_new_pos_x          ,
                        chaser_new_pos_y
                );
        Tile t2 = engine.getPlayer_location()
                .getTile(
                        (chaser_new_pos_x + s - 1),
                        (chaser_new_pos_y + s - 1)
                );

        return isInBounds(chaser_new_pos_x, chaser_new_pos_y)
                && t1.walkable()
                && t2.walkable();
    }


    public void updateEnemy() {
        List<Enemy> enemies = engine.getPlayer_location().getLocation_enemies();

        List<Enemy> toRemove = new ArrayList<>();


        for (int i = 0; i < enemies.size(); i++) {
            Enemy a = enemies.get(i);
            for (int j = i + 1; j < enemies.size(); j++) {
                Enemy b = enemies.get(j);
                if (Enemy.intersects(a, b)) {
                    if (a instanceof Walker) ((Walker) a).reverseDirection();
                    if (b instanceof Walker) ((Walker) b).reverseDirection();
                }
            }
        }

        for (Enemy enemy : enemies) {

            intersects(enemy);

            int enemy_current_pos_x = enemy.getEnemy_pos_x();
            int enemy_current_pos_y = enemy.getEnemy_pos_y();

            int player_current_pos_x = engine.getPlayer().getPlayer_pos_x();
            int player_current_pos_y = engine.getPlayer().getPlayer_pos_y();

            if (enemy instanceof Walker) {
                if (((Walker) enemy).getDirection().equals(Constants.Direction.Horizontal)) {

                    int enemy_next_pos_x_right = enemy.getEnemy_pos_x() + Constants.ENEMY_SPEED;
                    int enemy_next_pos_x_left = enemy.getEnemy_pos_x() - Constants.ENEMY_SPEED;

                    if (((Walker) enemy).isCan_move_left() && isInBounds(enemy_next_pos_x_right, enemy_current_pos_y)) {
                        checkMoves((Walker) enemy);
                        enemy.setEnemy_pos_x(enemy_next_pos_x_left);
                    }
                    else if (((Walker) enemy).isCan_move_right() && isInBounds(enemy_next_pos_x_left, enemy_current_pos_y)) {
                        checkMoves((Walker) enemy);
                        enemy.setEnemy_pos_x(enemy_next_pos_x_right);
                    }
                }
                else if (((Walker) enemy).getDirection().equals(Constants.Direction.Vertical)) {
                    int enemy_next_pos_y_down = enemy_current_pos_y + Constants.ENEMY_SPEED;
                    int enemy_next_pos_y_up = enemy_current_pos_y - Constants.ENEMY_SPEED;
                    if (((Walker) enemy).isCan_move_up() && isInBounds(enemy_current_pos_x, enemy_next_pos_y_up)) {
                        checkMoves((Walker) enemy);
                        enemy.setEnemy_pos_y(enemy_next_pos_y_up);
                    }
                    else if (((Walker) enemy).isCan_move_down() && isInBounds(enemy_current_pos_x, enemy_next_pos_y_down)) {
                        checkMoves((Walker) enemy);
                        enemy.setEnemy_pos_y(enemy_next_pos_y_down);
                    }
                }
            }
            else if (enemy instanceof Chaser) {
                Chaser chaser = (Chaser) enemy;

                int detectionRange = 10 * Constants.BLOCK_SIZE;
                if (Math.abs(player_current_pos_x - enemy_current_pos_x) > detectionRange ||
                        Math.abs(player_current_pos_y - enemy_current_pos_y) > detectionRange) {
                    chaser.setIs_active(false);
                    continue;
                }
                chaser.setIs_active(true);

                int dx = Integer.compare(player_current_pos_x, enemy_current_pos_x);
                int dy = Integer.compare(player_current_pos_y, enemy_current_pos_y);

                chaser.setEnemy_target_pos_x(enemy_current_pos_x + dx * Constants.ENEMY_SPEED);
                chaser.setEnemy_target_pos_y(enemy_current_pos_y);

                if (dx != 0
                        && isInBounds(chaser.getEnemy_target_pos_x(), chaser.getEnemy_target_pos_y())
                        && checkMoves(chaser)
                ) {
                    chaser.setEnemy_pos_x(chaser.getEnemy_target_pos_x());
                } else {
                    chaser.setEnemy_target_pos_x(enemy_current_pos_x);
                    chaser.setEnemy_target_pos_y(enemy_current_pos_y + dy * Constants.ENEMY_SPEED);
                    if (dy != 0
                            && isInBounds(chaser.getEnemy_target_pos_x(), chaser.getEnemy_target_pos_y())
                            && checkMoves(chaser)
                    ) {
                        chaser.setEnemy_pos_y(chaser.getEnemy_target_pos_y());
                    }
                }
            }
            else if (enemy instanceof Mine) {
                Mine mine = (Mine) enemy;

                mine.updateHighlight(32);

                int dx = Math.abs(player_current_pos_x - enemy_current_pos_x) / Constants.BLOCK_SIZE;
                int dy = Math.abs(player_current_pos_y - enemy_current_pos_y) / Constants.BLOCK_SIZE;

                if (dx <= 2 && dy <= 2) {
                    mine.setIs_active(true);
                    if (player_current_pos_x == enemy_current_pos_x && player_current_pos_y == enemy_current_pos_y) {
                        engine.setLose_condition(true);
                    }
                } else {
                    if (mine.getHighlightTimeLeft() <= 0) {
                        mine.setIs_active(false);
                        mine.setIs_active(false);
                    }
                }

            }
                if (enemy instanceof Mine) continue;

                for (Enemy other : enemies) {
                    if (other instanceof Mine) {
                        Mine mine = (Mine) other;
                        if (mine.isIs_active() && Enemy.intersects(enemy, mine)) {
                            toRemove.add(enemy);
                            toRemove.add(mine);
                            break;
                        }
                    }
                }
            }
            enemies.removeAll(toRemove);
        }

    public void intersects(Enemy enemy) {
        int player_current_pos_x = engine.getPlayer().getPlayer_pos_x();
        int player_current_pos_y = engine.getPlayer().getPlayer_pos_y();
        int player_width = Constants.PLAYER_SIZE;
        int player_height = Constants.PLAYER_SIZE;

        int enemy_current_pos_x = enemy.getEnemy_pos_x();
        int enemy_current_pos_y = enemy.getEnemy_pos_y();
        int enemy_width = Constants.ENEMY_SIZE;
        int enemy_height = Constants.ENEMY_SIZE;

        if (player_current_pos_x < enemy_current_pos_x + enemy_width &&
                player_current_pos_x + player_width > enemy_current_pos_x &&
                player_current_pos_y < enemy_current_pos_y + enemy_height &&
                player_current_pos_y + player_height > enemy_current_pos_y) engine.setLose_condition(true);
    }


    @Override
    public void run() {
        while (!engine.is_game_over()) {
            while (pause) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            updateEnemy();
            try {
                Thread.sleep(Constants.DELAY);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public boolean isPaused() {
        return pause;
    }

    public boolean isInBounds(int x, int y) {
        return x > 0 && x < Constants.RIGHT_WALL && y >= 0 && y < Constants.BOTTOM_WALL;
    }


    public void pause_loop() {
        this.pause = true;
    }

    public void unpause() {
        this.pause = false;
    }

    public EnemyThread(StateEngine engine) {
        this.engine = engine;
    }
}

