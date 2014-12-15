package com.nhaarman.lib_setup;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.CLASS;

@Target(ElementType.TYPE)
@Retention(CLASS)
public @interface Table {

    String name();

    Class<?> repository();

    int sinceVersion() default 1;
}