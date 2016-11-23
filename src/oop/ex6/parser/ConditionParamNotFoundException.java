package oop.ex6.parser;

/**
 * This class is an exception class of type ConditionParamNotFoundException which extends from ParserException
 */
public class ConditionParamNotFoundException extends ParserException {
    private final static String EXCEPTION_MESSAGE = "Parameter in condition line not found";
    public ConditionParamNotFoundException() {
        super(EXCEPTION_MESSAGE);
    }
}
