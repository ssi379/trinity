package com.nhaarman.trinity.internal.codegen;

import java.util.EnumSet;
import java.util.Set;

import javax.lang.model.element.Modifier;

public class Modifiers {

    public static final Set<Modifier> PUBLIC = EnumSet.of(Modifier.PUBLIC);

    public static final Set<Modifier> PRIVATE_FINAL = EnumSet.of(Modifier.PRIVATE, Modifier.FINAL);
}
