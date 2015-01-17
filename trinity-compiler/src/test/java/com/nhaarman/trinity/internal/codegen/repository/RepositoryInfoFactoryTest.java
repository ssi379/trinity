package com.nhaarman.trinity.internal.codegen.repository;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;

public class RepositoryInfoFactoryTest {


    private RepositoryInfoFactory mRepositoryInfoFactory;

    @Before
    public void setUp() throws Exception {
        mRepositoryInfoFactory = new RepositoryInfoFactory();
    }

    @Test
    public void an_interface_class() {
//        /* When */
//        RepositoryInfo result = mRepositoryInfoFactory.createRepositoryInfo(TestInterface.class);
//
//        /* Then */
//        assertThat(result.isInterface(), is(true));
    }
//
//    public void an_abstract_class() {
//        /* When */
//        RepositoryInfo result = mRepositoryInfoFactory.createRepositoryInfo(TestAbstractClass.class);
//
//        /* Then */
//        assertThat(result.isInterface(), is(false));
//    }

    public interface TestInterface {

    }

    public abstract class TestAbstractClass {

    }
}