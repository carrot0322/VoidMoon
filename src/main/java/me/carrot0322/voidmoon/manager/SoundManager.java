package me.carrot0322.voidmoon.manager;

import me.carrot0322.voidmoon.feature.Feature;
import me.carrot0322.voidmoon.util.client.SoundUtil;
import net.minecraft.client.main.Main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import static me.carrot0322.voidmoon.util.client.Util.mc;

public class SoundManager extends Feature {
    File dir = new File(mc.mcDataDir, "voidmoon");
    File soundsDir = new File(dir, "sound");

    public SoundUtil enableSound;
    public SoundUtil disableSound;

    public void init(){
        File enableSoundFile = new File(soundsDir, "enable.wav");
        File disableSoundFile = new File(soundsDir, "disable.wav");

        if (!enableSoundFile.exists()) {
            try {
                unpackFile(enableSoundFile, "assets/minecraft/voidmoon/sound/enable.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!disableSoundFile.exists()) {
            try {
                unpackFile(disableSoundFile, "assets/minecraft/voidmoon/sound/disable.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        enableSound = new SoundUtil(enableSoundFile);
        disableSound = new SoundUtil(disableSoundFile);
    }

    private void unpackFile(File file, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(name);
        try {
            Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } finally {
            fos.close();
            inputStream.close();
        }
    }
}
