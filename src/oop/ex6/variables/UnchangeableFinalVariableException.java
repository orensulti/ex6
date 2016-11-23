package oop.ex6.variables;


/**
 * This is an exception class for variable package for a case when we try to set a value into final variable
 * after his declaration - it is prohibited - because final variable as a final value (unchangeable)
 */
public class UnchangeableFinalVariableException extends FinalVariableException{
    // constants:
    private final static String EXCEPTION_MESSAGE = "can not change final variable value";

    /** default constructor */
    public UnchangeableFinalVariableException(){
        super(EXCEPTION_MESSAGE);
    }
}