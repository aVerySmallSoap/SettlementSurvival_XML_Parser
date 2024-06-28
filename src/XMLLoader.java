import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

public class XMLLoader {
    protected static final Queue<Thread> THREAD_POOL = new LinkedList<>();
    protected static final Queue<Loader> LOADER_POOL = new LinkedList<>();
    protected static XMLLoader instance = null;

    public static XMLLoader getInstance() {
        if (instance == null) {
            instance = new XMLLoader();
        }
        return instance;
    }

    public void addLoader(Loader loader) {
        LOADER_POOL.add(loader);
    }

    public void addLoader(File f) {
        LOADER_POOL.add(new Loader(f));
    }

    public void threadLoaders() {
        LOADER_POOL.forEach(loader -> THREAD_POOL.add(new Thread(loader)));
    }

    public void test_func() {
        long _start = System.currentTimeMillis();
        THREAD_POOL.forEach(thread -> {
            thread.start();
            try {
                thread.join();
                if (THREAD_POOL.peek() != null && thread.isAlive()) {
                    THREAD_POOL.remove();
                }
            } catch (InterruptedException e) {
                System.out.println("Something went wrong:\n" + e.getMessage());
            }
        });
        LOADER_POOL.forEach(loader -> System.out.println(loader.getXMLTree()));
        long _end = System.currentTimeMillis();
        System.out.println("Total time: " + (_end - _start) + "ms");
    }
}
