package adventuregame;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Config {
    GamePanel gp;
    public List<String> errors = new ArrayList<>(); // COLLECT ALL ERRORS

    public Config(GamePanel gp) {
        this.gp = gp;
    }

    public void saveConfig() {
        errors.clear(); // RESET ERROR LIST

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("config.txt"))) {

            // FULL SCREEN
            try {
                writer.write("fullScreen=" + gp.fullScreen);
                writer.newLine();
            } catch (IOException e) {
                errors.add("Error writing fullScreen: ");
            }

            // MUSIC VOLUME
            try {
                writer.write("musicVolume=" + gp.music.volumeScale);
                writer.newLine();
            } catch (IOException e) {
                errors.add("Error writing musicVolume: ");
            }

            // SE VOLUME
            try {
                writer.write("seVolume=" + gp.se.volumeScale);
                writer.newLine();
            } catch (IOException e) {
                errors.add("Error writing seVolume: ");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadConfig() {
        errors.clear(); // RESET ERROR LIST

        try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {

            // FULL SCREEN
            String configData = reader.readLine();
            if (configData != null && configData.contains("=")) {
                String value = configData.substring(configData.indexOf("=") + 1).trim();
                gp.fullScreen = Boolean.parseBoolean(value);
            } else {
                errors.add("Invalid or missing fullscreen setting");
            }

            // MUSIC VOLUME
            configData = reader.readLine();
            if (configData != null && configData.contains("=")) {
                try {
                    String value = configData.substring(configData.indexOf("=") + 1).trim();
                    gp.music.volumeScale = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    errors.add("Invalid musicVolume value");
                }
            } else {
                errors.add("Missing or invalid musicVolume setting");
            }

            // SE VOLUME
            configData = reader.readLine();
            if (configData != null && configData.contains("=")) {
                try {
                    String value = configData.substring(configData.indexOf("=") + 1).trim();
                    gp.se.volumeScale = Integer.parseInt(value);
                } catch (NumberFormatException e) {
                    errors.add("Invalid seVolume value");
                }
            } else {
                errors.add("Missing or invalid seVolume setting");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
