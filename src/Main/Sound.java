package Main;

import javax.sound.sampled.*;
import java.io.File;

public class Sound {

    private Clip musicClip;
    private final File[] files = new File[10];

    public Sound() {
        // Using relative paths for the sound files
        files[0] = new File("sounds/white-labyrinth-active.wav");
        files[1] = new File("sounds/delete line.wav");
        files[2] = new File("sounds/game over.wav");
        files[3] = new File("sounds/rotation.wav");
        files[4] = new File("sounds/touch floor.wav");
    }

    public void play(int i, boolean music) {
        try {
            if (files[i] != null && files[i].exists()) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(files[i]);
                Clip clip = AudioSystem.getClip();

                if (music) {
                    if (musicClip != null && musicClip.isRunning()) {
                        musicClip.stop();
                        musicClip.close();
                    }
                    musicClip = clip;
                }

                clip.open(ais);
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                });
                clip.start();
            } else {
                System.out.println("Sound file not found or file does not exist for index: " + i);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop() {
        if (musicClip != null) {
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (musicClip != null) {
            musicClip.stop();
            musicClip.close();
        }
    }
}
