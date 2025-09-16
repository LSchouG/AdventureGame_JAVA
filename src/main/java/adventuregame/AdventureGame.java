/**
 * *****************************************************************************
 * FileName: AdventureGame.java
 * Purpose: Launches the main game window and sets up the game environment
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Creates the main JFrame window
 * - Initializes GamePanel and adds it to the window
 * - Starts the game loop and sets up initial game configuration
 *******************************************************************************/
package adventuregame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;


public class AdventureGame {

    public static JFrame window;

    public static void main(String[] args) {

        window = new JFrame();  // use lowercase "window" as the variable name

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app on exit
        window.setResizable(false);             // Disable resizing for consistent layout
        window.setTitle("Adventure Game");      // Set the window title
        new AdventureGame().setIcon();

        GamePanel gamePanel = new GamePanel();  // 2. Create and add the main game panel to the window
        window.add(gamePanel);

        gamePanel.config.loadConfig();
        if (gamePanel.fullScreen == true) {
            window.setUndecorated(true);
        }

        window.pack();                          // 3. Automatically size the window based on the gamePanel's preferred size

        window.setLocationRelativeTo(null);     // 4. Center the window on the screen
        window.setVisible(true);              // 5. Make the window visible

        gamePanel.requestFocusInWindow();     // 6. Ensure the game panel receives key input focus

        gamePanel.setupGame();                // 7. Set up game configurations and resources (e.g., tiles, player)
        gamePanel.startGameThread();          // 8. Start the main game thread (runs the game loop)
    }
    public void setIcon() {
        URL resourceUrl = getClass().getClassLoader().getResource("images/player/player-down-still.png");
        BufferedImage image = null;

        if (resourceUrl != null) {
            // Try classpath loading first (for packaged builds)
            try {
                image = ImageIO.read(resourceUrl);
            } catch (IOException e) {
                System.err.println("Failed to load icon from classpath: " + e.getMessage());
            }
        } else {
            // Fallback to file system loading (for development/debugging)
            String filePath = "/Users/lars.s.g/Documents/GitHub/AdventureGame_JAVA1 using Ai to improve Lets see??/src/main/resources/images/player/player-down-still.png";
            File iconFile = new File(filePath);
            if (iconFile.exists()) {
                try {
                    image = ImageIO.read(iconFile);
                } catch (IOException e) {
                    System.err.println("Failed to load icon from file: " + e.getMessage());
                }
            } else {
                System.err.println("Icon file not found at: " + filePath);
            }
        }

        if (image != null) {
            window.setIconImage(image);
        } else {
            System.err.println("Could not load game icon; window will use default.");
        }
    }
}
