package ru.sberbank.jschool.homework.classloaders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class EncryptedClassLoader extends ClassLoader {
    private String rootDirectory;
    private int offset;

    public EncryptedClassLoader(String rootDirectory, int offset) {
        this.rootDirectory = rootDirectory;
        this.offset = offset;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] code = loadByteOffset(name);
            Class<?> cl = defineClass(name, code, 0, code.length);
            if (cl == null) {
                throw new ClassNotFoundException();
            }
            return cl;
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return super.findClass(name);
    }

    private byte[] loadByteOffset(String name) throws IOException {
        byte[] current = Files.readAllBytes(Paths.get(rootDirectory + "\\" + name + ".class"));
        byte[] result = new byte[current.length];
        for (int i = 0; i < current.length; i++) {
            result[i] = (byte) (current[i] - offset);
        }
        return result;
    }
}
