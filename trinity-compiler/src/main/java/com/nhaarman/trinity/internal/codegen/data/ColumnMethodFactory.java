package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.annotations.Column;
import com.nhaarman.trinity.annotations.PrimaryKey;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethod.Builder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.type.TypeKind;
import org.jetbrains.annotations.NotNull;

public class ColumnMethodFactory {

  public Collection<ColumnMethod> createColumnMethods(final Set<? extends ExecutableElement> columnElements) {
    Collection<ColumnMethod> results = new ArrayList<>();

    for (ExecutableElement columnElement : columnElements) {
      results.add(createColumnMethod(columnElement));
    }

    return results;
  }

  public ColumnMethod createColumnMethod(@NotNull final ExecutableElement element) {
    Builder builder = new Builder();

    builder.withName(element.getSimpleName().toString());
    builder.withFullyQualifiedTableClassName(element.getEnclosingElement().toString());
    builder.withColumnName(element.getAnnotation(Column.class).value());

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
