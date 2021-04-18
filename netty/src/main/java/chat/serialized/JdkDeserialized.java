package chat.serialized;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class JdkDeserialized extends AbstractDeserialized{
    @Override
    public <T> T deserialized(byte[] bytes) {
        try(ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));) {
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
