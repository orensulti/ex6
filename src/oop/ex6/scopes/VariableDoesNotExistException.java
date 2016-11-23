package oop.ex6.scopes;

/**
 * This class is an exception VariableDoesNotExistException which extends from ScopeException
 */
public class VariableDoesNotExistException extends ScopeException {
    private final static String EXCEPTION_MESSAGE = " variable is not found in any scope ";
    public VariableDoesNotExistException() {
        super(EXCEPTION_MESSAGE);
    }
}

