package oop.ex6.variables;

/**
 * This is an abstract exception class for all exceptions which belong to final variable.
 */
public abstract class FinalVariableException extends VariableException {
    public FinalVariableException(String exceptionError){
        super(exceptionError);
    }
}
