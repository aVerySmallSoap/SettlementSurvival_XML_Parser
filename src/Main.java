import java.io.File;

public class Main {
    public static void main(String[] args) {
        XMLLoader xmlLoader = XMLLoader.getInstance();
        xmlLoader.addLoader(new Loader(new File("./xml/test")));
        xmlLoader.addLoader(new Loader(new File("./xml/test2")));
        xmlLoader.addLoader(new Loader(new File("./xml/NewGameMapAnimNumItem.xml")));
        xmlLoader.threadLoaders();
        xmlLoader.test_func();
    }
}
