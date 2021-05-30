package nettyAdvance.capter05.config;

import nettyAdvance.capter05.protocol.Serializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 配置类，初始化加载配置文件
 */
public abstract class Config {
    private static final Logger log = LoggerFactory.getLogger(Config.class);
    private static Properties properties;
    static {
        try {
            InputStream resource = Config.class.getResourceAsStream("/bootstrap.properties");
            properties = new Properties();
            properties.load(resource);
        } catch (IOException e) {
            log.error("初始化失败");
            throw new ExceptionInInitializerError(e);
        }
    }

    public static int getServerPort(){
        String value = properties.getProperty("server.port");
        if(null == value){
            return 8080;
        }else{
            return Integer.valueOf(value);
        }
    }

    public static Serializer.Algorithm getSerializerAlgorithm(){
        String value = properties.getProperty("serializer.algorithm");
        if(value == null){
            return Serializer.Algorithm.JAVA;
        }else{
            return Serializer.Algorithm.valueOf(value);
        }
    }
}