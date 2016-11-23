package oop.ex6.parser;

/**
 * Exception class for UnbalancedNumberOfBracketsException.
 */
public class UnbalancedNumberOfBracketsException extends ParserException {

    private final static String EXCEPTION_MESSAGE = " the open brackets and close brackets " +
            "is not match";

    public UnbalancedNumberOfBracketsException() {
        super(EXCEPTION_MESSAGE);
    }

}
