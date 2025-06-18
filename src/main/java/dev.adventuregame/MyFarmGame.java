package dev.adventuregame;
import javax.swing.JFrame;
public class MyFarmGame {
    public static void main(String[] args) {

        JFrame window = new JFrame();  // use lowercase "window" as the variable name
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("My Farm Game");  // Only set title once

        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        window.pack();

        window.setLocationRelativeTo(null);  // Center the window on the screen
        window.setVisible(true);  // Show the window

        gamePanel.requestFocusInWindow();  // ensure key events work
        gamePanel.setupGame();
        gamePanel.startGameThread();

    }
}
