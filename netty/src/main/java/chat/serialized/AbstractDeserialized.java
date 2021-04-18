package chat.serialized;

/**
 * 将字节数组反序列化的抽象类
 */
public abstract class AbstractDeserialized {

    public abstract <T> T deserialized(byte[] bytes);
}
