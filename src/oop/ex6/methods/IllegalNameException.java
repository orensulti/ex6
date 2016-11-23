package oop.ex6.methods;

/**
 * This is an exception class of IllegalNameException which extends from MethodException
 */
public class IllegalNameException extends MethodException {
    private static final String ILLEGAL_NAME = "Name is illegal";
    public IllegalNameException() {
        super(ILLEGAL_NAME);
    }
}
