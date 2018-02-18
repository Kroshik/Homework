package ru.sberbank.jschool.homework.classloaders;

import org.apache.commons.io.FilenameUtils;

import java.io.File;

public class CryptoPluginManager {

    // directory that contains plugin folders
    private final String rootDirectory;

    public CryptoPluginManager(String rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    /**
     * method takes as a parameter a folder name in the root plugin directory,
     * loads the plugin .class file from the folder if present,
     * and returns a Plugin object
     *
     * @param pluginName - name of the plugin folder
     * @return Plugin
     * @throws PluginNotFoundException - when folder named 'pluginName' is missing,
     *                                 or it contains no .class files
     */
    public Plugin loadPlugin(String pluginName) throws PluginNotFoundException {
        try {
            File root = new File(rootDirectory + "/" + pluginName);
            if (root.listFiles().length != 1) {
                throw new PluginNotFoundException("couldn't locate plugin " + pluginName);
            }
            EncryptedClassLoader plugin = new EncryptedClassLoader(rootDirectory + "/" + pluginName, 5);
//            System.out.println(rootDirectory + "\\" + pluginName + "\\" + root.listFiles()[0].getName());
            Class<?> compiledClass = plugin.findClass(FilenameUtils.getBaseName(root.listFiles()[0].getName()));

            if (compiledClass == null) {
                throw new PluginNotFoundException("couldn't locate plugin " + pluginName);
            }
            return (Plugin) compiledClass.newInstance();
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new PluginNotFoundException(e.getMessage(), e);
        }
    }
}
