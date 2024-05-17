package me.carrot0322.voidmoon.util.client;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Objects;

public class FileUtil {
    public static void unpackFile(File file, String name) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);
        IOUtils.copy(Objects.requireNonNull(FileUtils.class.getClassLoader().getResourceAsStream(name)), fos);
        fos.close();
    }
}
