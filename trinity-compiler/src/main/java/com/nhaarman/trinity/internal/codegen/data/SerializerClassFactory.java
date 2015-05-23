package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.annotations.Serializer;
import java.util.HashSet;
import java.util.Set;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;
import org.jetbrains.annotations.NotNull;

public class SerializerClassFactory {

  @NotNull
  public Set<SerializerClass> createSerializerClasses(final Set<? extends TypeElement> serializerElements) {
    Set<SerializerClass> tableClasses = new HashSet<>(serializerElements.size());

    for (TypeElement serializerElement : serializerElements) {
      tableClasses.add(createSerializerClass(serializerElement));
    }

    return tableClasses;
  }

  public SerializerClass createSerializerClass(@NotNull final TypeElement serializerElement) {
    SerializerClass.Builder builder = new SerializerClass.Builder();

    String className = serializerElement.getSimpleName().toString();
    String packageName = "";
    if (!serializerElement.getQualifiedName().toString().equals(className)) {
      packageName = serializerElement.getQualifiedName().toString().substring(0, serializerElement.getQualifiedName().toString().indexOf(className) - 1);
    }

    builder.withClassName(className);
    builder.withPackageName(packageName);
    builder.withElement(serializerElement);

    TypeMirror serializableType = getSerializerValue(serializerElement);
    builder.withFullyQualifiedSerializableTypeName(serializableType.toString());

    return builder.build();
  }

  @NotNull
  private TypeMirror getSerializerValue(final TypeElement typeElement) {
    try {
      typeElement.getAnnotation(Serializer.class).value();
      throw new IllegalStateException("Could not get serializer value");
    } catch (MirroredTypeException e) {
      return e.getTypeMirror();
    }
  }
}
