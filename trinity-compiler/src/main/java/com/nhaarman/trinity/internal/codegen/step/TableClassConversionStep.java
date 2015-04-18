package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Table;
import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.data.TableClassFactory;
import com.nhaarman.trinity.internal.codegen.data.TableClassRepository;
import java.io.IOException;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class TableClassConversionStep implements ProcessingStep {

  @NotNull
  private final TableClassRepository mTableClassRepository;

  @NotNull
  private final TableClassFactory mTableClassFactory;

  public TableClassConversionStep(@NotNull final TableClassRepository tableClassRepository) {
    mTableClassRepository = tableClassRepository;
    mTableClassFactory = new TableClassFactory();
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    Set<? extends TypeElement> tableElements = (Set<? extends TypeElement>) roundEnvironment.getElementsAnnotatedWith(Table.class);
    Set<TableClass> tableClasses = mTableClassFactory.createTableClasses(tableElements);
    mTableClassRepository.save(tableClasses);

    return OK;
  }
}
