package oop.ex6.variables;
import oop.ex6.main.RegexPatterns;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A concrete variable class for double, contains as data member the variable type,
 * 3 different constructors and overrides methods from abstract class.
 */

public class DoubleVariable extends Variable {

    // data members:
    public VariableTypes.DataTypes variableType = VariableTypes.DataTypes.DOUBLE;


    // constructors: 3 ways to create a double variable:
    // 1. with name only (uninitialized)
    // 2. with name and a specific value (initialized)
    // 3. with name and other variable value

    public DoubleVariable(String name) throws VariableException{
        super(name);
    }

    public DoubleVariable(String name, String value) throws VariableException
    {
        super(name, value);
    }
    public DoubleVariable(String name, Variable otherVariable) throws VariableException{
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
        return VariableTypes.isCompatibleWith(VariableTypes.DataTypes.DOUBLE,
                otherVariable.getVariableType());
    }

    @Override
    public VariableTypes.DataTypes getVariableType(){
        return variableType;
    }

    @Override
    public Pattern getPattern(){
        return RegexPatterns.LEGAL_DOUBLE_PATTERN;
    }
}
