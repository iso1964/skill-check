package q007.maze;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public abstract class MazeProperties {

    protected static Integer maxLineSize;

    public MazeProperties() {
        Properties properties = load();

        maxLineSize = Integer.valueOf(properties.getProperty("MAX_LINE_SIZE", "100"));
    }

    private Properties load() {
        Properties properties = new Properties();

        try {
            String path = "/q007/config.properties";
            InputStream in = MazeData.class.getResourceAsStream(path);
            if (in == null) {
                System.out.println(path + " not found.");
                throw new RuntimeException();
            }
            properties.load(in);

        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            throw new RuntimeException();
        }

        return properties;
    }
}
