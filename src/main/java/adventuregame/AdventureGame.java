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

import javax.swing.JFrame;


public class AdventureGame {

    public static JFrame window;

    /**************************************************************************
     * Method: main(String[] args)
     * Purpose: Program entry point. Creates and displays the game window.
     * Inputs:  args[] - command line arguments (unused)
     * Outputs: Initializes and displays the game GUI
     ***************************************************************************/
    public static void main(String[] args) {

        window = new JFrame();  // use lowercase "window" as the variable name

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app on exit
        window.setResizable(false);             // Disable resizing for consistent layout
        window.setTitle("Adventure Game");      // Set the window title

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
}
