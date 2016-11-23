package oop.ex6.parser;

/**
 * This class is an exception class of type BadTypeConditionParamException which extends from ParserException
 */
public class BadTypeConditionParamException extends ParserException {
    private final static String EXCEPTION_MESSAGE = "Condition type parameter is not valid for condition";
    public BadTypeConditionParamException() {
        super(EXCEPTION_MESSAGE);
    }
}
