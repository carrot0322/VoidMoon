package me.carrot0322.voidmoon.util.client;

import me.carrot0322.voidmoon.VoidMoon;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class SoundUtil {
    private final File file;

    public SoundUtil(File file) {
        this.file = file;
    }

    public void asyncPlay() {
        new Thread(this::playSound).start();
    }

    public void playSound() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception ex) {
            VoidMoon.logger.error("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}
