package oop.ex6.parser;

import oop.ex6.main.SjavaException;

/**
 * This class is a ParserException which is the parent of all Parser exceptions.
 */
public class ParserException extends SjavaException{

    /**
     * Constructor
     * @param exceptionError error to forward.
     */
    public ParserException(String exceptionError){
        super(exceptionError);
    }


}
