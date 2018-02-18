package ru.sberbank.jschool.homework.classloaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CryptoClass extends ClassLoader {
    private int offset;

    public CryptoClass(int offset) {
        this.offset = offset;
    }


    public void createFileCrypto(String input, String output) throws IOException {
        byte [] result = loadByteOffset(input);
        Path path = Paths.get(output);
        Files.write(path, result);
    }


    private byte[] loadByteOffset(String path) throws IOException {
        byte[] current = Files.readAllBytes(Paths.get(path));
        byte[] result = new byte[current.length];
        for (int i = 0; i < current.length; i++) {
            result[i] = (byte) (current[i] + offset);
        }
        return result;
    }
}
