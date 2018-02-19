package ru.sberbank.homework.dergun;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;


public class Parser {
    public static double parseValue(String s) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, IOException {
        String source = "public class ValueParser { \n" +
                "   public static double value = " + s + "; \n" +
                "}\n";

        File root = File.createTempFile("calculator", "");
        root.delete();
        root.mkdirs();
        root.deleteOnExit();

        File sourceFile = new File(root, "ValueParser.java");
        sourceFile.getParentFile().mkdirs();
        Files.write(sourceFile.toPath(), source.getBytes(StandardCharsets.UTF_8));

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, null, new OutputStream() {
            @Override
            public void write(int b) throws IOException {

            }
        }, sourceFile.getPath());

        URLClassLoader classLoader = URLClassLoader.newInstance(new URL[]{root.toURI().toURL()});
        Class<?> cls = Class.forName("ValueParser", true, classLoader);

        Field value = cls.getField("value");
        return value.getDouble(null);
    }
}