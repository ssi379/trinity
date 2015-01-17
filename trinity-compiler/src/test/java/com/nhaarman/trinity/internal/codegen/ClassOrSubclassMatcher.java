package com.nhaarman.trinity.internal.codegen;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;

public class ClassOrSubclassMatcher<T> extends BaseMatcher<Class<T>> {

    private final Class<T> mTargetClass;

    public ClassOrSubclassMatcher(final Class<T> targetClass) {
        mTargetClass = targetClass;
    }

    @Override
    public boolean matches(final Object item) {
        if (item != null) {
            if (item instanceof Class) {
                return mTargetClass.isAssignableFrom((Class<T>) item);
            }
        }
        return false;
    }

    @Override
    public void describeTo(final Description description) {
        description.appendText("Matches a class or subclass");
    }

    public static <T> ClassOrSubclassMatcher<T> anySubClass(final Class<T> targetClass) {
        return new ClassOrSubclassMatcher<>(targetClass);
    }
}