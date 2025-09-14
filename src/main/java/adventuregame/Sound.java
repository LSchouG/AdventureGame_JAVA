package adventuregame;

import java.net.URL;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Sound {

    Clip clip;
    URL[] soundURL = new URL[30];
    FloatControl fc;
    int volumeScale = 3; // Default global volume
    float volume;
    float[] baseVolumeOffset = new float[30]; // Per-sound volume balance
    int currentSoundIndex;

    public Sound() {
        soundURL[0] = getClass().getResource("/sound/back-ground-music.wav");
        //baseVolumeOffset[0] = -1f;
        soundURL[1] = getClass().getResource("/sound/power-up.wav");
        //baseVolumeOffset[1] = -1f;
        soundURL[2] = getClass().getResource("/sound/coin.wav");
        //baseVolumeOffset[2] = -1f;
        soundURL[3] = getClass().getResource("/sound/door.wav");
        //baseVolumeOffset[3] = -1f;
        soundURL[4] = getClass().getResource("/sound/end-game-victory.wav");
        //baseVolumeOffset[4] =  -1f;
        soundURL[5] = getClass().getResource("/sound/hit-monster.wav");
        //baseVolumeOffset[5] = -1f;
        soundURL[6] = getClass().getResource("/sound/hurt.wav");
        //baseVolumeOffset[6] =  -1f;
        soundURL[7] = getClass().getResource("/sound/slime.wav");
        //baseVolumeOffset[7] =  -1f;
        soundURL[8] = getClass().getResource("/sound/sword-swing.wav");
        //baseVolumeOffset[8] = -1f;
        soundURL[9] = getClass().getResource("/sound/slash.wav");
        //baseVolumeOffset[9] =  -1f;
        soundURL[10] = getClass().getResource("/sound/slime-1.wav");
        //baseVolumeOffset[10] =  -1f;
        soundURL[11] = getClass().getResource("/sound/level-up.wav");
        //baseVolumeOffset[11] = -1f;
        soundURL[12] = getClass().getResource("/sound/menu-cursor.wav");
        //baseVolumeOffset[12] =  -1f;
        soundURL[13] = getClass().getResource("/sound/fireball-woosh.wav");
        //baseVolumeOffset[13] =  -1f;
        soundURL[14] = getClass().getResource("/sound/game-over.wav");
        //baseVolumeOffset[14] = 1f;
        soundURL[15] = getClass().getResource("/sound/snoring.wav");
        //baseVolumeOffset[15] =  -1f;
        soundURL[16] = getClass().getResource("/sound/tent.wav");
        //baseVolumeOffset[16] = -1f;
        soundURL[17] = getClass().getResource("/sound/parry.wav");
        //baseVolumeOffset[17] =  -1f;
        soundURL[18] = getClass().getResource("/sound/Blocked.wav");
        //baseVolumeOffset[18] = -1f;
        soundURL[19] = getClass().getResource("/sound/bip-letter.wav");
        //baseVolumeOffset[19] = 0f;
        soundURL[20] = getClass().getResource("/sound/background-loop-dungeon.wav");
        //baseVolumeOffset[20] = -1f;
        soundURL[21] = getClass().getResource("/sound/door-open-lock.wav");    // open Iron door
        //baseVolumeOffset[21] = -1f;
        soundURL[22] = getClass().getResource("/sound/pickaxe.wav");
        //baseVolumeOffset[21] = -1f;
        soundURL[23] = getClass().getResource("/sound/bossfight.wav");
        //baseVolumeOffset[21] = -1f;
        soundURL[24] = getClass().getResource("/sound/boss-music.wav");
        //baseVolumeOffset[21] = -1f;
    }
    public void setFile(int i) {
        try {
            currentSoundIndex = i;
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void play() {clip.setFramePosition(0);clip.start();}
    public void loop() {clip.loop(Clip.LOOP_CONTINUOUSLY);}
    public void stop() {clip.stop();}
    public void checkVolume() {
        switch (volumeScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }


        if (fc != null) {
            fc.setValue(volume + baseVolumeOffset[currentSoundIndex]);
        }
    }
    public void setVolumeScale(int scale) {
        volumeScale = scale;
        checkVolume();
    }
}
