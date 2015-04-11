package com.nhaarman.trinity.internal.codegen.writer.method;

import com.nhaarman.trinity.internal.codegen.data.ColumnMethodRepository;
import com.nhaarman.trinity.internal.codegen.data.TableClass;
import org.junit.Before;

import static org.mockito.Mockito.*;

public class CreateContentValuesMethodCreatorTest {

  private CreateContentValuesMethodCreator mCreateContentValuesMethodCreator;

  @Before
  public void setUp() {
    mCreateContentValuesMethodCreator = new CreateContentValuesMethodCreator(mock(TableClass.class), mock(ColumnMethodRepository.class));
  }
}