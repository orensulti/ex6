package oop.ex6.methods;

import oop.ex6.main.SjavaException;

/**
 * An extends Exception method class, all other exceptions extends it.
 */
public class MethodException extends SjavaException {
    public MethodException(String exceptionError) {
        super(exceptionError);
    }

}
