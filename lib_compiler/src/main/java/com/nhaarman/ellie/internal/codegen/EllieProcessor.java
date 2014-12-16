/*
 * Copyright (C) 2014 Michael Pardo
 * Copyright (C) 2014 Niek Haarman
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nhaarman.ellie.internal.codegen;

import com.nhaarman.ellie.internal.codegen.table.TableProcessor;
import com.nhaarman.lib_setup.annotations.Table;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes(
        "com.nhaarman.lib_setup.*"
)
public class EllieProcessor extends AbstractProcessor {

    private final Map<String, TableProcessor> mProcessors;

    public EllieProcessor() {
        mProcessors = new HashMap<>();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public synchronized void init(final ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        mProcessors.put(Table.class.getCanonicalName(), new TableProcessor(processingEnv));
    }

    @Override
    public boolean process(final Set<? extends TypeElement> annotations, final RoundEnvironment roundEnv) {
        try {
            for (TypeElement annotation : annotations) {
                TableProcessor processor = mProcessors.get(annotation.getQualifiedName().toString());
                if (processor != null) {
                    processor.process(roundEnv);
                }
            }
        } catch (ProcessingFailedException | IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, e.getLocalizedMessage());
        }

        return true;
    }
}
