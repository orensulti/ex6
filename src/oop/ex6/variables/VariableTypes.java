package oop.ex6.variables;
import oop.ex6.main.RegexPatterns;
/**
 * This class contains enum of the different data types and contains a method which checks if
 * two data types are compatible or not according constants which save for each data type, his compatible
 * data types (by using enum constants)
 */
public class VariableTypes {

    public enum DataTypes {
        INTEGER("int"),
        DOUBLE("double"),
        STRING("String"),
        BOOLEAN("boolean"),
        CHAR("char");

        private final String stringRepresentation;

        DataTypes(String stringRepresentation) {
            this.stringRepresentation = stringRepresentation;
        }

        // return the String representation
        public String toString() {
            return this.stringRepresentation;
        }
    }

    // constants:
    private static final DataTypes[] CHAR_COMPATIBLE_TYPES = new DataTypes[]{DataTypes.CHAR};

    private static final DataTypes[] DOUBLE_COMPATIBLE_TYPES = new DataTypes[]
            {DataTypes.INTEGER, DataTypes.DOUBLE};

    private static final DataTypes[] INT_COMPATIBLE_TYPES = new DataTypes[]{DataTypes.INTEGER};

    private static final DataTypes[] STRING_COMPATIBLE_TYPES = new DataTypes[]{DataTypes.STRING};

    private static final DataTypes[] BOOLEAN_COMPATIBLE_TYPES =
            new DataTypes[]{DataTypes.BOOLEAN, DataTypes.INTEGER, DataTypes.DOUBLE};

    // methods:

    /**
     * This method gets two Data Types and return boolean if they are compatible or not.
     * @param sourceDataType - Data type of variable from the left side
     * @param targetDataType - Data type of variable from the right side
     * @return
     */
    public static boolean isCompatibleWith(DataTypes sourceDataType, DataTypes targetDataType){
        if (sourceDataType.equals(DataTypes.INTEGER)){
            for(DataTypes type: INT_COMPATIBLE_TYPES){
                if (type.equals(targetDataType)){
                    return true;
                }
            }
            return false;
        }
        else if(sourceDataType.equals(DataTypes.DOUBLE)){
            for(DataTypes type: DOUBLE_COMPATIBLE_TYPES){
                if (type.equals(targetDataType)){
                    return true;
                }
            }
            return false;
        }
        else if (sourceDataType.equals(DataTypes.STRING)){
            for (DataTypes type: STRING_COMPATIBLE_TYPES){
                if (type.equals(targetDataType)){
                    return true;
                }
            }
            return false;
        }
        else if (sourceDataType.equals(DataTypes.BOOLEAN)){
            for (DataTypes type: BOOLEAN_COMPATIBLE_TYPES){
                if (type.equals(targetDataType)){
                    return true;
                }
            }
            return false;
        }
        else{
            for (DataTypes type: CHAR_COMPATIBLE_TYPES){
                if (type.equals(targetDataType)){
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * Returns a DataType.type from a given string, used mainly by Commands classes.
     * @param line to be grabbing a DataType from.
     * @return a valid DataType (int,bool,string,etc..).
     * @throws UnsupportedVariableException type does not fit.
     */
    public static DataTypes getTypeFromLine(String line) throws UnsupportedVariableException {
            if (line.equals(DataTypes.BOOLEAN.toString())){
                return DataTypes.BOOLEAN;
        } else if (line.equals(DataTypes.CHAR.toString())){
                return DataTypes.CHAR;
            } else if (line.equals(DataTypes.DOUBLE.toString())){
                return DataTypes.DOUBLE;
            } else if (line.equals(DataTypes.INTEGER.toString())){
                return DataTypes.INTEGER;
            } else if(line.equals(DataTypes.STRING.toString())){
                return DataTypes.STRING;
            } else {
                throw new UnsupportedVariableException();
            }
    }

    /**
     * This method gives us the DataTypes from enum which is matched to a string type
     * @param parameter - String of a data type
     * @return - The suitable data type from enum
     * @throws UnsupportedVariableException
     */
    public static DataTypes getTypeFromParam(String parameter) throws UnsupportedVariableException {
        if(parameter.matches(RegexPatterns.LEGAL_INT)){
            return DataTypes.INTEGER;
        } else if (parameter.matches(RegexPatterns.LEGAL_CHAR)){
            return DataTypes.CHAR;
        } else if (parameter.matches(RegexPatterns.LEGAL_DOUBLE)){
            return DataTypes.DOUBLE;
        } else if (parameter.matches(RegexPatterns.LEGAL_BOOLEAN)){
            return DataTypes.BOOLEAN;
        } else if(parameter.matches(RegexPatterns.LEGAL_STRING)){
            return DataTypes.STRING;
        } else {
            throw new UnsupportedVariableException();
        }
    }

}

