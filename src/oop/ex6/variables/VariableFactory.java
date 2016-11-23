package oop.ex6.variables;

/**
 * Factory class for variables to create a concrete variable.
 */
public class VariableFactory {

    public static Variable createVariable(VariableTypes.DataTypes type, String name) throws VariableException{
        switch(type){
            case CHAR:
                return new CharVariable(name);
            case INTEGER:
                return new IntegerVariable(name);
            case DOUBLE:
                return new DoubleVariable(name);
            case STRING:
                return new StringVariable(name);
            case BOOLEAN:
                return new BooleanVariable(name);
            default:
                throw new UnsupportedVariableException();
        }

    }
    public static Variable createVariable(VariableTypes.DataTypes type, String name, String value)
            throws VariableException{
        switch (type){
            case CHAR:
                return new CharVariable(name, value);
            case INTEGER:
                return new IntegerVariable(name, value);
            case DOUBLE:
                return new DoubleVariable(name, value);
            case STRING:
                return new StringVariable(name, value);
            case BOOLEAN:
                return new BooleanVariable(name, value);
            default:
                throw new UnsupportedVariableException();
        }
    }

}
