package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.annotations.PrimaryKey;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod.Builder;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import org.jetbrains.annotations.NotNull;

public class ColumnMethodFactory {

  public ColumnMethod createColumnMethod(@NotNull final ExecutableElement element) {
    Builder builder = new Builder();

    builder.withName(element.getSimpleName().toString());

    if (element.getReturnType().getKind() == TypeKind.VOID) {
      builder.isSetter();
      builder.withType(element.getParameters().iterator().next().asType());
    } else {
      builder.isGetter();
      builder.withType(element.getReturnType());
    }

    if (element.getAnnotation(PrimaryKey.class) != null) {
      builder.isPrimary();
    }

    builder.withElement(element);

    return builder.build();
  }
}
