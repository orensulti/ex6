package oop.ex6.variables;

/**
 * This is an exception class for variable package for a case when a final variable has been created
 * without a value - prohibited in final variable - Must be initialized when declared.
 */
public class MustBeInitializedFinalVariableException extends FinalVariableException {
    // constants:
    private final static String EXCEPTION_MESSAGE = "can not declare a final variable without initialization";

    /** default constructor */
    public MustBeInitializedFinalVariableException(){
        super(EXCEPTION_MESSAGE);
    }
}