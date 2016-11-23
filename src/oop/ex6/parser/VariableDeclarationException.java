package oop.ex6.parser;

/**
 * This class is VariableDeclarationException which extends ParserException
 */
public class VariableDeclarationException extends ParserException {
    private final static String EXCEPTION_MESSAGE = " There was a problem to parse the variable " +
            "declaration line";

    public VariableDeclarationException() {
        super(EXCEPTION_MESSAGE);
    }

}
