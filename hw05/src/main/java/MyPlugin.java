import ru.sberbank.jschool.homework.classloaders.Plugin;

public class MyPlugin implements Plugin{

    @Override
    public void run(String[] urls) {
        System.out.println("I'm is FistPlugin!");
    }
}
