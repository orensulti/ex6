package oop.ex6.scopes;

/**
 * This class is an exception class of MethodOverLoadingException which extends from ScopeException
 */
public class MethodOverLoadingException extends ScopeException {
    private static final String OVERLOADING = "Overloading: two functions with the same name";
    public MethodOverLoadingException(String exceptionError) {
        super(exceptionError);
    }

    public MethodOverLoadingException() {
        super(OVERLOADING);
    }
}
