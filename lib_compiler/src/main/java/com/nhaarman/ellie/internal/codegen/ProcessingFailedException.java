package com.nhaarman.ellie.internal.codegen;

public class ProcessingFailedException extends RuntimeException {

    public ProcessingFailedException(final String message) {
        super(message);
    }

    public ProcessingFailedException(final Throwable cause){
        super(cause);
    }
}
