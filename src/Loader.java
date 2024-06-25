import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;

public class Loader implements Runnable {
    private final HashMap<Integer, ArrayList<String>> AdjacencyList = new HashMap<>();
    protected File parsable;
    protected BufferedReader reader;
    protected FileReader fileReader;
    Charset utf8 = StandardCharsets.UTF_8;

    public Loader(File parsable) {
        this.parsable = parsable;
    }

    @Override
    public void run() {
        long _start = System.currentTimeMillis();
        ArrayList<String> values = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        int c, i = 0; // ASCII character value ; adjacency key
        try {
            fileReader = new FileReader(parsable, utf8);
            reader = new BufferedReader(fileReader);
            while (reader.ready()) {
                c = reader.read();
                if (Character.isWhitespace(c)) continue;
                if (c == 33) {
                    reader.readLine();
                    continue;
                }
                if (c == 62) {
                    builder.append((char) c);
                    values.add(builder.toString());
                    builder = new StringBuilder();
                    reader.mark(1);
                    c = reader.read();
                    if (c != 60 && !Character.isWhitespace(c) && c != -1) {
                        builder.append((char) c);
                        while (reader.ready()) {
                            reader.mark(1);
                            c = reader.read();
                            if (c == 60) {
                                reader.reset();
                                values.add(builder.toString());
                                builder = new StringBuilder();
                                break;
                            } else {
                                builder.append((char) c);
                            }
                        }
                    } else {
                        AdjacencyList.put(i, values);
                        ++i;
                        values = new ArrayList<>();
                    }
                } else {
                    builder.append((char) c);
                }
            }
            AdjacencyList.forEach((k, v) -> System.out.printf("Key: %s, Value: %s\n", k, v));
            long _end = System.currentTimeMillis();
            System.out.printf("\nSpeed: %d ms\n\n", _end - _start);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}