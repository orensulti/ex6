package oop.ex6.variables;

/**
 * This is an exception class for variable package for a case when we try to assign a variable value of
 * other variable, but, the other variable has not been initialized.
 */
public class UnInitializedVariableAssignmentException extends VariableException {
        // constants:
        private final static String EXCEPTION_MESSAGE = "Can not assign the other variable's value because" +
                "it is uninitialized variable";

        /** default constructor */
        public UnInitializedVariableAssignmentException(){
            super(EXCEPTION_MESSAGE);
        }
}

