package trinityprocessortest.full;

import com.nhaarman.trinity.TypeSerializer;
import com.nhaarman.trinity.annotations.Serializer;

@Serializer(MyObject.class)
public class MyObjectTypeSerializer implements TypeSerializer<MyObject, String> {

  @Override
  public String serialize(final MyObject deserializedObject) {
    return deserializedObject.value;
  }

  @Override
  public MyObject deserialize(final String serializedObject) {
    return new MyObject(serializedObject);
  }

}