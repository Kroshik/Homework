package ru.sberbank.jschool.homework.dergun;

import ru.sberbank.jschool.homework.classloaders.CryptoPluginManager;
import ru.sberbank.jschool.homework.classloaders.Plugin;
import ru.sberbank.jschool.homework.classloaders.PluginManager;

import java.io.IOException;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, IOException {
        { // task #2
            /*
            CryptoClass cypto = new CryptoClass(5);
            String input = Paths.get("src", "main", "resources", "third_plugin", "MyPlugin3.class").toAbsolutePath().toString();
            String output = Paths.get("src", "main", "resources", "crypto","third_plugin", "MyPlugin3.class").toAbsolutePath().toString();
            cypto.createFileCrypto(input, output);
            */

            CryptoPluginManager manager = new CryptoPluginManager(Paths.get("src", "main", "resources", "crypto").toAbsolutePath().toString());

            Plugin plugin = manager.loadPlugin("first_plugin");
            plugin.run(args);

            plugin = manager.loadPlugin("second_plugin");
            plugin.run(args);

            plugin = manager.loadPlugin("third_plugin");
            plugin.run(args);
        }
        //----------------------------------//
        { // task #1
            PluginManager manager = new PluginManager(Paths.get("src", "main", "resources").toAbsolutePath().toString());

            Plugin plugin = manager.loadPlugin("first_plugin");
            plugin.run(args);

            plugin = manager.loadPlugin("second_plugin");
            plugin.run(args);

            plugin = manager.loadPlugin("third_plugin");
            plugin.run(args);
        }
    }
}
