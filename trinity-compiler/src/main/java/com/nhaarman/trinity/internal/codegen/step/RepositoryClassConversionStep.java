package com.nhaarman.trinity.internal.codegen.step;

import com.nhaarman.trinity.annotations.Repository;
import com.nhaarman.trinity.internal.codegen.ProcessingException;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClass;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClassFactory;
import com.nhaarman.trinity.internal.codegen.data.RepositoryClassRepository;
import java.util.Collection;
import java.util.Set;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.TypeElement;
import org.jetbrains.annotations.NotNull;

public class RepositoryClassConversionStep implements ProcessingStep {

  @NotNull
  private final RepositoryClassRepository mRepositoryClassRepository;

  @NotNull
  private final RepositoryClassFactory mRepositoryClassFactory;

  public RepositoryClassConversionStep(@NotNull final RepositoryClassRepository repositoryClassRepository) {
    mRepositoryClassRepository = repositoryClassRepository;
    mRepositoryClassFactory = new RepositoryClassFactory();
  }

  @Override
  public void process(@NotNull final RoundEnvironment roundEnvironment) throws ProcessingException {
    Set<? extends TypeElement> repositoryElements = (Set<? extends TypeElement>) roundEnvironment.getElementsAnnotatedWith(Repository.class);
    Collection<RepositoryClass> repositoryClasses = mRepositoryClassFactory.createRepositoryClasses(repositoryElements);
    mRepositoryClassRepository.save(repositoryClasses);
  }
}
