package Configs;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Config {
    private final Properties properties;
    public Config() throws IOException {
        properties = new Properties();
        properties.load(new FileInputStream("src/Configs/Config"));
    }
    public Integer getIntConfig(String path){
        return Integer.parseInt(properties.getProperty(path));
    }
    public String getStrConfig(String path){
        return properties.getProperty(path);
    }
}
