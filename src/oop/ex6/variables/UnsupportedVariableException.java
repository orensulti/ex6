package oop.ex6.variables;

/**
 * This is an exception class for variable package for a case when we try to create unsupported variable type
 * that is mean a variable which is not defined in S-java - which is not in out DataTypes enum.
 */
public class UnsupportedVariableException extends VariableException {
    // constants:
    private final static String EXCEPTION_MESSAGE = "unsupported operation - unrecognized variable type";

    /** default constructor */
    public UnsupportedVariableException(){
        super(EXCEPTION_MESSAGE);
    }
}
