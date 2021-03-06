package oop.ex6.variables;

import oop.ex6.main.RegexPatterns;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A concrete variable class for String, contains as data member the variable type,
 * 3 different constructors and overrides methods from abstract class.
 */
public class StringVariable extends Variable{

    // data members:
    public VariableTypes.DataTypes variableType = VariableTypes.DataTypes.STRING;

    // constructors - 3 constructors - we have 3 ways to initiate a concrete variable
    // 1. with name only
    // 2. with name and specific value
    // 3. with name and other variable value

    public StringVariable(String name) throws VariableException{
        super(name);
    }
    public StringVariable(String name, String value) throws VariableException{
        super(name, value);
    }
    public StringVariable(String name, Variable otherVariable) throws VariableException
          {
        super(name, otherVariable);
    }

    // implementation of methods from abstract variable class:
    @Override
    public boolean isValueAssignmentFits(String value){
        Matcher m = getPattern().matcher(value);
        return m.matches();
    }

    @Override
    public boolean isOtherVariableAssignmentFits(Variable otherVariable){
        return VariableTypes.isCompatibleWith(VariableTypes.DataTypes.STRING,
                otherVariable.getVariableType());
    }

    @Override
    public VariableTypes.DataTypes getVariableType(){
        return variableType;
    }

    @Override
    public Pattern getPattern(){
        return RegexPatterns.LEGAL_STRING_PATTERN;
    }
}
