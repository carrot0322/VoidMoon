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

import static me.carrot0322.voidmoon.util.client.FileUtil.unpackFile;
import static me.carrot0322.voidmoon.util.client.Util.mc;

public class SoundManager extends Feature {
    File dir = new File(mc.mcDataDir, "Voidmoon");
    File soundsDir = new File(dir, "sounds");

    public SoundUtil enableSound;
    public SoundUtil disableSound;

    public void init(){
        File enableSoundFile = new File(soundsDir, "enable.wav");
        File disableSoundFile = new File(soundsDir, "disable.wav");

        if (!enableSoundFile.exists()) {
            try {
                if(!soundsDir.exists()) soundsDir.mkdirs();
                unpackFile(enableSoundFile, "assets/minecraft/voidmoon/sound/enable.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!disableSoundFile.exists()) {
            try {
                if(!soundsDir.exists()) soundsDir.mkdirs();
                unpackFile(disableSoundFile, "assets/minecraft/voidmoon/sound/disable.wav");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        enableSound = new SoundUtil(enableSoundFile);
        disableSound = new SoundUtil(disableSoundFile);
    }
}
