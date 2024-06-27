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
        ArrayList<String> values = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        int c, i = 0; // ASCII character value ; adjacency key
        try {
            fileReader = new FileReader(parsable, utf8);
            reader = new BufferedReader(fileReader);
            while (reader.ready()) {
                reader.mark(1);
                c = reader.read();
                if (c == 10 || c == 13 || c == 32 || c == 9) continue;
                //On < check if next is !
                if (c == 60) {
                    int fc = reader.read();
                    if (fc == 33) { // if ! remove line
                        while (reader.ready()) {
                            fc = reader.read();
                            if (fc == 62) {
                                reader.readLine();
                                break;
                            }
                        }
                    } else {
                        builder.append((char) c);
                        builder.append((char) fc);
                        continue;
                    }
                }
                //Assume that it's always tag
                if (c == 62) { // when it ends>
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
                } else if (c != 60) {
                    builder.append((char) c);
                }
            }
            AdjacencyList.forEach((k, v) -> System.out.printf("Key: %s, Value: %s\n", k, v));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}