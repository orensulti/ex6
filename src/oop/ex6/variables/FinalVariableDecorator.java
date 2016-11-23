package oop.ex6.variables;
import java.util.regex.Pattern;

/**
 * Decorator class for final variable which behaves different than the regular variables in terms of:
 * initiation - which is prohibited with name only and set value which is prohibited after declaration.
 */
public class FinalVariableDecorator extends Variable {

    // data members:
    private Variable finalVariable;

    // constructors:
    // here comes the difference: final variable behaves different when initiated.
    // for example you can not create final variable without initialization of value in contract to regular
    // variable, in addition, you can not assign an other variable's value to final variable, again, in
    // contract to regular variables which can get values from other variables for example : a = b;
    // 4th constructor is used for a special-deep.copy case.

    public FinalVariableDecorator(String name) throws VariableException{
        throw new MustBeInitializedFinalVariableException();
    }

    public FinalVariableDecorator(String name, String value) throws VariableException{
        super(name, value);

    }

    public FinalVariableDecorator(String name, Variable otherVariable) throws VariableException {
        super(name, otherVariable);
    }

    public FinalVariableDecorator(Variable var) throws VariableException {
        super.setVariableName(var.getVariableName());
        super.setVariableIsInitialized(true);

        if (!var.getIsInitialized()){
            throw new MustBeInitializedFinalVariableException();
        }
        this.finalVariable = var;
    }


    @Override
    public boolean getIsFinal(){
        return true;
    }

    // overrides method of changing variable value - in case of final variable it is not possible.
    @Override

    public void setVariableValue(String value) throws VariableException{
        throw new UnchangeableFinalVariableException();
    }

    @Override
    public void setVariableOtherVariable(Variable otherVariable) throws VariableException{
        throw new UnchangeableFinalVariableException();
    }

    // implementation of methods from abstract variable class:
    // just preserve the functionality of Variable.

    @Override
    public boolean isValueAssignmentFits(String value){
        return finalVariable.isValueAssignmentFits(value);
    }

    @Override
    public boolean isOtherVariableAssignmentFits(Variable otherVariable){
        return finalVariable.isOtherVariableAssignmentFits(otherVariable);
    }

    @Override
    public VariableTypes.DataTypes getVariableType(){
        return finalVariable.getVariableType();
    }

    @Override
    public Pattern getPattern(){
        return finalVariable.getPattern();
    }
}
