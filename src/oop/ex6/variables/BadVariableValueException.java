package oop.ex6.variables;

/**
 * This is an exception class for variable package for a bad variable value assignment.
 */
public class BadVariableValueException extends VariableException{
    // constants:
    private final static String EXCEPTION_MESSAGE = "this is an illegal value to variable, " +
            "assignment is illegal";

    /** default constructor */
    public BadVariableValueException(){
        super(EXCEPTION_MESSAGE);
    }
}
