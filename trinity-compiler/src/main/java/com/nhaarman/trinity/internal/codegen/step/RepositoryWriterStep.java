package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.internal.codegen.ProcessingStepResult;
import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClassRepository;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import com.nhaarman.trinity.internal.codegen.data.TableClassRepository;
import com.nhaarman.trinity.internal.codegen.writer.RepositoryTypeSpecCreator;
import com.nhaarman.trinity.internal.codegen.writer.TypeSpecWriter;
import com.squareup.javapoet.TypeSpec;
import java.io.IOException;
import java.util.Collection;
import javax.annotation.processing.Filer;
import javax.annotation.processing.RoundEnvironment;
import org.jetbrains.annotations.NotNull;

import static com.nhaarman.trinity.internal.codegen.ProcessingStepResult.OK;

public class RepositoryWriterStep implements ProcessingStep {

  @NotNull
  private final TableClassRepository mTableClassRepository;

  @NotNull
  private final RepositoryClassRepository mRepositoryClassRepository;

  @NotNull
  private final ColumnMethodRepository mColumnMethodRepository;

  @NotNull
  private final TypeSpecWriter mTypeSpecWriter;

  public RepositoryWriterStep(@NotNull final TableClassRepository tableClassRepository,
                              @NotNull final RepositoryClassRepository repositoryClassRepository,
                              @NotNull final ColumnMethodRepository columnMethodRepository,
                              @NotNull final Filer filer) {
    mTableClassRepository = tableClassRepository;
    mRepositoryClassRepository = repositoryClassRepository;
    mColumnMethodRepository = columnMethodRepository;
    mTypeSpecWriter = new TypeSpecWriter(filer);
  }

  @NotNull
  @Override
  public ProcessingStepResult process(@NotNull final RoundEnvironment roundEnvironment) throws IOException {
    Collection<TableClass> tableClasses = mTableClassRepository.all();
    Collection<RepositoryClass> repositoryClasses = mRepositoryClassRepository.all();
    writeRepositoryClasses(repositoryClasses, tableClasses);

    return OK;
  }

  private void writeRepositoryClasses(@NotNull final Collection<RepositoryClass> repositoryClasses,
                                      @NotNull final Collection<TableClass> tableClasses) throws IOException {
    for (RepositoryClass repositoryClass : repositoryClasses) {
      for (TableClass tableClass : tableClasses) {
        if (repositoryClass.getTableClassName().equals(tableClass.getClassName())
            && repositoryClass.getTableClassPackageName().equals(tableClass.getPackageName())) {
          writeRepositoryClass(repositoryClass, tableClass);
        }
      }
    }
  }

  private void writeRepositoryClass(@NotNull final RepositoryClass repositoryClass, @NotNull final TableClass tableClass) throws IOException {
    TypeSpec repositoryTypeSpec = new RepositoryTypeSpecCreator(repositoryClass, tableClass, mColumnMethodRepository).create();
    mTypeSpecWriter.writeToFile(repositoryClass.getPackageName(), repositoryTypeSpec);
  }
}
