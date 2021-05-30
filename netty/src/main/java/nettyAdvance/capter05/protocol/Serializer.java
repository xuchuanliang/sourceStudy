package nettyAdvance.capter05.protocol;

import com.google.gson.*;

import java.io.*;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * 用于扩展序列化、反序列化算法
 */
public interface Serializer{
    <T>T deserialize(Class<T> clazz,byte[] bytes);

    <T> byte[] serialize(T object);

    /**
     * 实现序列化算法的具体接口
     */
    enum Algorithm implements Serializer{
        JAVA{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                try {
                    ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                    ObjectInputStream ois = new ObjectInputStream(bis);
                    return (T) ois.readObject();
                } catch (IOException | ClassNotFoundException e) {
                    throw new RuntimeException("反序列化失败",e);
                }
            }

            @Override
            public <T> byte[] serialize(T object) {
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    ObjectOutputStream oos = new ObjectOutputStream(bos);
                    oos.writeObject(object);
                    return bos.toByteArray();
                } catch (IOException e) {
                    throw new RuntimeException("序列化失败",e);
                }
            }
        },

        JSON{
            @Override
            public <T> T deserialize(Class<T> clazz, byte[] bytes) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassJsonAdapter()).create();
                String content = new String(bytes, StandardCharsets.UTF_8);
                return gson.fromJson(content,clazz);
            }

            @Override
            public <T> byte[] serialize(T object) {
                Gson gson = new GsonBuilder().registerTypeAdapter(Class.class, new ClassJsonAdapter()).create();
                return gson.toJson(object).getBytes(StandardCharsets.UTF_8);
            }
        }
    }

    /**
     * Json序列化Class类型的序列化和反序列化适配器，默认情况下Gson不直接支持Class类型的序列化和反序列化，但是我们可以自己实现适配器
     */
    class ClassJsonAdapter implements JsonSerializer<Class<?>>, JsonDeserializer<Class<?>>{

        /**
         * 反序列为Class类型
         * @param json
         * @param typeOfT
         * @param context
         * @return
         * @throws JsonParseException
         */
        @Override
        public Class<?> deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            try {
                String className = json.getAsString();
                return Class.forName(className);
            } catch (ClassNotFoundException e) {
                throw new JsonParseException(e);
            }
        }

        /**
         * 序列化Class类型
         * @param src
         * @param typeOfSrc
         * @param context
         * @return
         */
        @Override
        public JsonElement serialize(Class<?> src, Type typeOfSrc, JsonSerializationContext context) {
            return new JsonPrimitive(src.getName());
        }
    }
}
