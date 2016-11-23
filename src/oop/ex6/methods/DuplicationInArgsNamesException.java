package oop.ex6.methods;


/**
 * This is an exception class of DuplicationInArgsNamesException which extends from MethodException
 */
public class DuplicationInArgsNamesException extends MethodException{
    private static final String ILLEGAL_METHOD_ARGS = "There was a duplication of " +
            "names in method arguments," +
            "the same name to two or more arguments is illegal";
    public DuplicationInArgsNamesException() {
        super(ILLEGAL_METHOD_ARGS);
    }
}
