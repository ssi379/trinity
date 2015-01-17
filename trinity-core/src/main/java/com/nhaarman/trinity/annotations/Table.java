package com.nhaarman.trinity.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.CLASS;

@Target(ElementType.TYPE)
@Retention(CLASS)
public @interface Table {

    String name();

}