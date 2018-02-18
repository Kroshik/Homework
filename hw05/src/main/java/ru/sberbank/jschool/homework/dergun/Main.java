package ru.sberbank.jschool.homework.dergun;

import ru.sberbank.jschool.homework.classloaders.Plugin;
import ru.sberbank.jschool.homework.classloaders.PluginManager;
import ru.sberbank.jschool.homework.classloaders.PluginNotFoundException;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws PluginNotFoundException, IOException {
        PluginManager manager = new PluginManager(Paths.get("src", "main", "resources").toAbsolutePath().toString());
        
        Plugin plugin = manager.loadPlugin("first_plugin");
        plugin.run(args);

        plugin = manager.loadPlugin("second_plugin");
        plugin.run(args);

        plugin = manager.loadPlugin("third_plugin");
        plugin.run(args);
    }
}
