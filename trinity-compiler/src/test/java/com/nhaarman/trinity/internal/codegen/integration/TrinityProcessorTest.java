package com.nhaarman.trinity.internal.codegen.integration;

import com.nhaarman.trinity.internal.codegen.TrinityProcessor;
import org.junit.Test;

import static com.google.common.truth.Truth.assert_;
import static com.google.testing.compile.JavaFileObjects.forResource;
import static com.google.testing.compile.JavaSourcesSubjectFactory.javaSources;
import static java.util.Arrays.asList;

public class TrinityProcessorTest {

  @Test
  public void emptyFiles() {
    assert_()
        .about(javaSources())
        .that(asList(
            forResource("trinityprocessortest/empty/MyEntity.java"),
            forResource("trinityprocessortest/empty/MyRepository.java")
        ))
        .processedWith(new TrinityProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(forResource("trinityprocessortest/empty/TrinityMyRepository.java"));
  }

  @Test
  public void fullFiles() {
    assert_()
        .about(javaSources())
        .that(asList(
            forResource("trinityprocessortest/full/MyEntity.java"),
            forResource("trinityprocessortest/full/MyRepository.java"),
            forResource("trinityprocessortest/full/MyObject.java"),
            forResource("trinityprocessortest/full/MyObjectTypeSerializer.java")
        ))
        .processedWith(new TrinityProcessor())
        .compilesWithoutError()
        .and()
        .generatesSources(forResource("trinityprocessortest/full/TrinityMyRepository.java"));
  }
}
