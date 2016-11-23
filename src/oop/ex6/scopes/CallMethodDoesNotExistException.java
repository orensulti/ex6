package oop.ex6.scopes;

/**
 * This class is an exception class of CallMethodDoesNotExistException which extends from ScopeException
 */
public class CallMethodDoesNotExistException extends ScopeException {
    private final static String EXCEPTION_MESSAGE = " Method is not found in any scope, thus cannot call " +
            "method ";
    public CallMethodDoesNotExistException() {
        super(EXCEPTION_MESSAGE);
    }
}
