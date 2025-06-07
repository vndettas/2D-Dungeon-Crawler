package Controller;

import Model.StateEngine;

import java.awt.event.KeyEvent;

public class KeyListener implements java.awt.event.KeyListener {

    private boolean moving_up = false;

    private boolean moving_down = false;

    private boolean moving_left = false;

    private boolean moving_right = false;

    StateEngine engine;

    public KeyListener(StateEngine engine) {
        this.engine = engine;
    }
    public KeyListener() {
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> moving_up = true;
            case KeyEvent.VK_D -> moving_right = true;
            case KeyEvent.VK_S -> moving_down = true;
            case KeyEvent.VK_A -> moving_left = true;
            case KeyEvent.VK_1 -> engine.getInventory().useItem(0);
            case KeyEvent.VK_2 -> engine.getInventory().useItem(1);
            case KeyEvent.VK_3 -> engine.getInventory().useItem(2);
            case KeyEvent.VK_4-> engine.getInventory().useItem(3);
            case KeyEvent.VK_5-> engine.getInventory().useItem(4);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {
            case KeyEvent.VK_W -> moving_up = false;
            case KeyEvent.VK_D -> moving_right = false;
            case KeyEvent.VK_S -> moving_down = false;
            case KeyEvent.VK_A -> moving_left = false;

        }
    }

    public boolean isMoving_up() {
        return moving_up;
    }

    public boolean isMoving_down() {
        return moving_down;
    }

    public boolean isMoving_left() {
        return moving_left;
    }

    public boolean isMoving_right() {
        return moving_right;
    }

}
