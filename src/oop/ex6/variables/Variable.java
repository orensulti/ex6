package oop.ex6.variables;

import oop.ex6.main.RegexPatterns;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This is an abstract class of Variable, the different types of variables should extends from this class
 * and implement the following methods: isValueFits(), isVarFits() - which are abstract methods in this class
 * and has been override in the sub classes.
 */
public abstract class Variable {


    // data members:
    private String variableName;
    private boolean isInitialized;

    // constructors:
    // we have 3 different ways to initialize a constructor:
    // 1. declaration of variable without a value (constructor gets name)
    // 2. declaration of variable with a specific value (constructor gets name and value)
    // 3. declaration of variable with a value of other variable (constructor gets name and other variable)

    /**
     * constructor number 1
     * @param name - String with the name of the variable
     * @throws VariableException if name is bad.
     */
    public Variable(String name) throws VariableException{
        setVariableName(name);
    }

    /**
     * constructor number 2
     * @param name - String with the name of the variable
     * @param value - String which represents the value we want to assign the variable
     * @throws VariableException if name does not fit.
     */
    public Variable(String name, String value) throws VariableException{
        setVariableName(name);
        setVariableValue(value);
    }

    /**
     * constructor number 3
     * @param name - String with the name of the variable
     * @param otherVariable - Object of other Variable which we want to assign our variable
     * @throws VariableException if name/value does not fit.
     */
    public Variable(String name, Variable otherVariable) throws VariableException
         {
        setVariableName(name);
        setVariableOtherVariable(otherVariable);
    }

    /**
     * Variable default constructor
     */
    public Variable(){

    }

    // methods:

    // check variable properties:

    /**
     * This method checks if variable has legal name according to regex string by using matches method.
     * @param name - String for the name of the variable
     * @return - boolean - true if the name of the variable is legal, otherwise, false.
     */
    public boolean isVariableHasLegalName(String name){
        if (name == null){
            return false;
        }
        Matcher m = RegexPatterns.VARIABLE_LEGAL_NAME_PATTERN.matcher(name);
        return m.matches();
    }

    // abstract methods:
    abstract boolean isValueAssignmentFits(String value);
    abstract boolean isOtherVariableAssignmentFits(Variable otherVariable);
    abstract VariableTypes.DataTypes getVariableType();
    abstract Pattern getPattern();

    // concrete methods:

    // getters:

    /**
     * Getter method for isInitialized data member
     * @return - boolean: true if variable has been initialized, otherwise, false.
     * * */
    public boolean getIsInitialized(){
         return this.isInitialized;
     }

    /**
     * Getter method for variableName
     * @return - the name of the variable
     */
    public String getVariableName(){
        return this.variableName;
    }


    /**
     * Getter method for isFinal data member.
     * all of the variable types are not final except final variable which will overrides this method
     * and return true.
     * @return - boolean if this is a final variable(true), otherwise(false).
     */
    public boolean getIsFinal(){
        return false;
    }


    // setters:

    /**
     * This method set the boolean value of isInitialized param to the data member isInitialized
     * @param isInitialized - boolean value
     */
    public void setVariableIsInitialized(boolean isInitialized){
        this.isInitialized = isInitialized;
    }



    /**
     * This method set a name to variable, if the name is legal - set the name, otherwise throw
     * BadVariableNameException exception.
     * @param name - String with the name to set.
     * @throws BadVariableNameException if name is bad.
     */
    protected void setVariableName(String name) throws VariableException{
        if (isVariableHasLegalName(name)){
            setVariableIsInitialized(false);
            this.variableName = name;
        }
        else{
            throw new BadVariableNameException();
        }
    }

    /**
     * This method set a specific value to variable, if the assignment with this value is legal - set
     * this value to variable, otherwise, throw BadVariableValueException exception.
     * @param value - String with the value we want to set to variable
     * @throws BadVariableValueException if name is bad.
     */
    public void setVariableValue(String value) throws VariableException{
        if (isValueAssignmentFits(value)){
            setVariableIsInitialized(true);
        }
        else{
            throw new BadVariableValueException();
        }
    }

    /**
     * This method set a value of other variable to our variable, if the assignment with this value is legal -
     * set this value to variable, otherwise, throw BadVariableValueException.
     * this method first checks if the other variable has been initialized, if not throw unInitialized
     * exception.
     * @param otherVariable - Object of (the other) Variable
     * @throws BadVariableValueException if var is not initialized.
     */
    public void setVariableOtherVariable(Variable otherVariable) throws VariableException{
        if (!otherVariable.getIsInitialized()){
            throw new UnInitializedVariableAssignmentException();
        }
        else{
            if (isOtherVariableAssignmentFits(otherVariable)){
                setVariableIsInitialized(true);
            }
            else{
                throw new BadVariableValueException();
            }
        }
    }

    /**
     * This method creates a copy for a variable, identical to the given variable
     * @param variable the variable to be copied
     * @return variableCopy - Object of the copied identical variable
     * @throws VariableException if var is bad.
     */
    public static Variable deepCopyVariable(Variable variable) throws VariableException {
        // Initiates a new Variable object
       Variable copiedVar = VariableFactory.createVariable(variable.getVariableType(),
               variable.getVariableName());

        if(variable.getIsInitialized()){
            copiedVar.setVariableIsInitialized(true);
        }
        if(variable.getIsFinal()) {
            return new FinalVariableDecorator(variable);
        }
        return copiedVar;
    }

    public VariableTypes.DataTypes getType() {
        return this.getVariableType();
    }



}
