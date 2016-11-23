package oop.ex6.methods;

/**
 * This is an exception class of BadArgsException which extends from MethodException
 */
public class BadArgsException extends MethodException {
    private static final String ILLEGAL_METHOD_ARGS = "Args sent to " +
            "function are illegal";
    public BadArgsException() {
        super(ILLEGAL_METHOD_ARGS);
    }
}
