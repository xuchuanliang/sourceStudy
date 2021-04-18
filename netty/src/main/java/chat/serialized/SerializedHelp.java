package chat.serialized;

import java.util.HashMap;
import java.util.Map;

/**
 * 根据序列化类型，进行反序列化
 */
public final class SerializedHelp {
    /**
     * 不同类型的序列化解码器
     */
    private static final Map<Byte,AbstractDeserialized> DESERIALIZED_MAP = new HashMap<>();
    static {
        DESERIALIZED_MAP.put(SerializeType.JDK,new JdkDeserialized());
        DESERIALIZED_MAP.put(SerializeType.HESSIAN,new HessianDeserialized());
        DESERIALIZED_MAP.put(SerializeType.JSON,new JSONDeserialized());
        DESERIALIZED_MAP.put(SerializeType.PROTOBUF,new ProtoBufDeserialized());
    }

    /**
     * 根据解码类型进行解码
     * @param serializedType
     * @param contentBytes
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T deserialized(byte serializedType,byte[] contentBytes){
        return DESERIALIZED_MAP.get(serializedType).deserialized(contentBytes);
    }

    public static <T> T d(int serializedType,byte[] contentBytes){
        return null;
    }
}
