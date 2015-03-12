package com.nhaarman.trinity.internal.codegen.data;

import com.nhaarman.trinity.annotations.Table;
import com.nhaarman.trinity.internal.codegen.data.TableClass.Builder;
import java.util.Collection;
import javax.lang.model.element.TypeElement;
import org.jetbrains.annotations.NotNull;

public class TableClassTransformer {

  @NotNull
  private final TableClassRepository mTableClassRepository;

  public TableClassTransformer(@NotNull final TableClassRepository tableClassRepository) {
    mTableClassRepository = tableClassRepository;
  }

  public void createFrom(@NotNull final Collection<? extends TypeElement> tableElements) {
    for (TypeElement tableElement : tableElements) {
      createFrom(tableElement);
    }
  }

  private void createFrom(@NotNull final TypeElement tableElement) {
    Builder builder = new Builder();

    String className = tableElement.getSimpleName().toString();
    String packageName = tableElement.getQualifiedName().toString().substring(0, tableElement.getQualifiedName().toString().indexOf(className) - 1);

    builder.withTableName(tableElement.getAnnotation(Table.class).name());
    builder.withClassName(className);
    builder.withPackageName(packageName);
    builder.withElement(tableElement);

    TableClass tableClass = builder.build();
    mTableClassRepository.save(tableClass);
  }
}
