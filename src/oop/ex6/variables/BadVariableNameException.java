package oop.ex6.variables;

/**
 * This is an exception class for variable package for a bad variable name.
 */
public class BadVariableNameException extends VariableException {
    // constants:
    private final static String EXCEPTION_MESSAGE = "this is an illegal name to variable!";

    /** default constructor */
    public BadVariableNameException(){
        super(EXCEPTION_MESSAGE);
    }
}


