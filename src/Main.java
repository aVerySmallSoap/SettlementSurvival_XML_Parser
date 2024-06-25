import java.io.File;

public class Main {
    public static void main(String[] args) {
        File a = new File("test");
        File b = new File("test2");
        Loader c = new Loader(a);
        Loader d = new Loader(b);
        Thread thread1 = new Thread(c);
        Thread thread2 = new Thread(d);
        thread1.start();
        thread2.start();
    }
}
