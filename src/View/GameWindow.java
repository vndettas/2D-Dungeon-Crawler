package View;

import Helper.Constants;

import javax.swing.*;
import java.awt.*;


//Class responsible mainly for creating window

public class GameWindow extends JFrame {


    public GameWindow() throws HeadlessException {
       setTitle("im crazy");
       setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
       setLocationRelativeTo(null);
       setResizable(false);
    }
}
