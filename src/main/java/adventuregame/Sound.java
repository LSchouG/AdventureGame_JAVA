/** ******************************************************************************
 * FileName: Sound.java
 * Purpose: Handles sound effect and music playback using Java's Clip API.
 * Author: Lars S Gregersen
 * Date: 21-5-2025
 * Version: 1.0
 * NOTES:
 * - Supports background music and sound effects
 * - Includes methods to load, play, loop, and stop audio clips
 *******************************************************************************/

package adventuregame;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

    Clip clip;
    URL soundURL[] = new URL[30]; // Array of sound file paths

    /**************************************************************************
     * Constructor: Sound()
     * Purpose: Preloads sound file paths into the URL array.
     ***************************************************************************/
    public Sound() {
        soundURL[0] = getClass().getResource("/sound/back-ground-music.wav");
        soundURL[1] = getClass().getResource("/sound/power-up.wav");
        soundURL[2] = getClass().getResource("/sound/coin.wav");
        soundURL[3] = getClass().getResource("/sound/door.wav");
        soundURL[4] = getClass().getResource("/sound/end-game-victory.wav");
        soundURL[5] = getClass().getResource("/sound/hit-monster.wav");
        soundURL[6] = getClass().getResource("/sound/hurt.wav");
        soundURL[7] = getClass().getResource("/sound/slime.wav");
        soundURL[8] = getClass().getResource("/sound/sword-swing.wav");
        soundURL[9] = getClass().getResource("/sound/slash.wav");
        soundURL[10] = getClass().getResource("/sound/slime-1.wav");
        soundURL[11] = getClass().getResource("/sound/level-up.wav");
        soundURL[12] = getClass().getResource("/sound/menu-cursor.wav");
        soundURL[13] = getClass().getResource("/sound/fireball-woosh.wav");
    }

    /**************************************************************************
     * Method: setFile(int i)
     * Purpose: Loads the selected sound file into the Clip object.
     * Inputs: i - index of the sound file in soundURL[]
     * Notes: Prepares the sound for playback.
     ***************************************************************************/
    public void setFile(int i) {
        try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("ERROR: setFile();" + " index in the soundArray = " + i);
            e.printStackTrace();
        }
    }

    /**************************************************************************
     * Method: play()
     * Purpose: Plays the loaded sound clip from the beginning once.
     ***************************************************************************/
    public void play() {
        clip.start();
    }

    /**************************************************************************
     * Method: loop()
     * Purpose: Loops the loaded sound clip continuously.
     ***************************************************************************/
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    /**************************************************************************
     * Method: stop()
     * Purpose: Stops the currently playing sound clip.
     ***************************************************************************/
    public void stop() {
        clip.stop();
    }
}
