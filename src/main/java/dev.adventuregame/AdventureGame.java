/** ******************************************************************************
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
package dev.adventuregame;

import javax.swing.JFrame;

public class AdventureGame {

    /**************************************************************************
     * Method: main(String[] args)
     * Purpose: Program entry point. Creates and displays the game window.
     * Inputs:  args[] - command line arguments (unused)
     * Outputs: Initializes and displays the game GUI
     ***************************************************************************/
    public static void main(String[] args) {

        // 1. Create the main application window
        JFrame window = new JFrame();  // use lowercase "window" as the variable name
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Close app on exit
        window.setResizable(false); // Disable resizing for consistent layout
        window.setTitle("My Farm Game");  // Set the window title

        // 2. Create and add the main game panel to the window
        GamePanel gamePanel = new GamePanel();
        window.add(gamePanel);

        // 3. Automatically size the window based on the gamePanel's preferred size
        window.pack();

        // 4. Center the window on the screen
        window.setLocationRelativeTo(null);

        // 5. Make the window visible
        window.setVisible(true);

        // 6. Ensure the game panel receives key input focus
        gamePanel.requestFocusInWindow();

        // 7. Set up game configurations and resources (e.g., tiles, player)
        gamePanel.setupGame();

        // 8. Start the main game thread (runs the game loop)
        gamePanel.startGameThread();
    }
}
