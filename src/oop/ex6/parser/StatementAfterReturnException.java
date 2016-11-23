package oop.ex6.parser;

/**
 * Exception class for StatementAfterReturnException, in scenario when there are more statements
 * after the return in a method's code.
 */
public class StatementAfterReturnException extends ParserException {

    private final static String EXCEPTION_MESSAGE = " statements after the return statement are illegal ";

    public StatementAfterReturnException() {
        super(EXCEPTION_MESSAGE);
    }

}
