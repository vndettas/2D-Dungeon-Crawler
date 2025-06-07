package Model;

import Controller.EnemyThread;
import Controller.GameThread;
import Controller.KeyListener;
import Model.Location.Location;
import View.GameGraphics;
import View.GameWindow;

public class Game {
    public Game() {
        GameWindow game_window = new GameWindow();
        StateEngine state_engine = new StateEngine();
        state_engine.getPlayer_location().setEngine(state_engine);
        Location current_loc = state_engine.getPlayer_location();
        Player player = new Player(current_loc.getStarting_pos_x(), current_loc.getStarting_pos_y());
        GameGraphics game_panel = new GameGraphics(player, state_engine);
        KeyListener listener = new KeyListener(state_engine);
        EnemyThread enemy_loop = new EnemyThread(state_engine);
        Thread enemy_loop_thread = new Thread(enemy_loop);
        GameThread game_loop = new GameThread(game_panel, current_loc, player, listener, state_engine);
        Thread game_loop_thread = new Thread(game_loop);


        state_engine.setPlayer(player);
        state_engine.setGraphics(game_panel);
        state_engine.setThread(game_loop);
        state_engine.setThread(enemy_loop);

        game_panel.addKeyListener(listener);
        game_panel.setFocusable(true);

        game_loop_thread.start();
        enemy_loop_thread.start();

        game_window.add(game_panel);

        game_window.setVisible(true);
    }
}
