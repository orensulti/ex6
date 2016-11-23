package oop.ex6.variables;

import oop.ex6.main.SjavaException;

/**
 * This is an abstract exception class for all exceptions which belong to a variable.
 */
public abstract class VariableException extends SjavaException {
    public VariableException(String exceptionError){
        super(exceptionError);
    }
}
