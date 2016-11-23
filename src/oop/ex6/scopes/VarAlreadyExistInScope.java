package oop.ex6.scopes;

/**
 * This class is an exception class of VarAlreadyExistInScope which extends ScopeException
 */
public class VarAlreadyExistInScope extends ScopeException{

    // constants:
    private final static String EXCEPTION_MESSAGE = " cannot add a variable to this scope, " +
            "you are trying to add a variable to this scope which is already exist.";

    /** default constructor */
    public VarAlreadyExistInScope(){
        super(EXCEPTION_MESSAGE);
    }
}


