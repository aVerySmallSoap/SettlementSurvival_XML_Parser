import java.io.File;

public class Main {
    public static void main(String[] args) {
        File b = new File("test");
        Loader d = new Loader(b);
        Thread thread2 = new Thread(d);
        thread2.start();
    }
}
