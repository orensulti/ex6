package oop.ex6.parser;

/**
 * Exception class for IllegalStatementException - when we encountered in illegal line in the file.
 */
public class IllegalStatementException extends ParserException {
    private final static String EXCEPTION_MESSAGE = "this line in the file is illegal";

    public IllegalStatementException() {
        super(EXCEPTION_MESSAGE);
    }
}
