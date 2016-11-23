package oop.ex6.methods;
import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SjavaException;
import oop.ex6.scopes.Scope;
import oop.ex6.variables.*;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;

/**
 * A class representing the Method object, main methods of this class are:
 * addVariable and validateArgs which allow the parser to decide rather a method is valid or not.
 */
public class Method extends Scope {


    // DATA MEMBERS:
    private String name;
    private List<Variable> listOfArgs;

    // Constructors:
    // 1. An empty function, includes only a name, without any variables.
    // 2. Function contains variables, decelerations && more.

    /**
     * 1st Constructor - describes an empty function, with only a 'return' line.
     * @param methodName param to be set as Method name.
     * @throws IllegalNameException if bad name
     */
    public Method(String methodName) throws IllegalNameException {
        setName(methodName);
        this.listOfArgs = null;
    }

    /**
     * 2st Constructor - describes a normal function: includes variables.
     * @param methodName to be set as method name.
     * @param varList list in the method.
     * @throws IllegalNameException if name not legal
     */
    public Method(String methodName, ArrayList<Variable> varList) throws IllegalNameException {
        this(methodName);
        if (varList != null) {
            this.listOfArgs = varList;
        }

    }

    /**
     * Returns list of args
     * @return list of arguments.
     */

    public List<Variable> getListOfArgs(){
        return listOfArgs;
    }

    /**
     * This is a static method which creates Variable objects from the arguments of the method declaration.
     * If the variables are legal the method will return list of variables,
     * Otherwise, throw exception
     * @param arguments line given to initialize.
     * @return ArrayList of variables.
     * @throws SjavaException exception
     */
    public static ArrayList<Variable> createVariablesFromArguments(String arguments) throws SjavaException{

        final int VAR_PREDECLARATION = 1;
        final int VAR_TYPE = 2;
        final int VAR_NAME = 3;
        final String EMPTY_STRING = "";

        String[] variables = arguments.split(",");
        ArrayList<Variable> variablesList = new ArrayList<>();
        Matcher matcher;
        String variableType;
        String variableName;
        String predeclaration;
        boolean isFinal;


        // no arguments
        if (variables.length == 1 && variables[0].equals(EMPTY_STRING)){
            return null;
        }

        for (String variable : variables) {
            matcher = RegexPatterns.VARIABLE_DECLARATION_WITHOUT_VALUE_PATTERN.
                    matcher(variable + ";");
            if (!matcher.matches()){
                throw new IllegalNameException();
            }
            predeclaration = matcher.group(VAR_PREDECLARATION);
            variableType = matcher.group(VAR_TYPE);
            variableName = matcher.group(VAR_NAME);

            isFinal = predeclaration != null;

            Variable var = VariableFactory.createVariable(
                    VariableTypes.getTypeFromLine(variableType), variableName);

            var.setVariableIsInitialized(true);

            if (isFinal){
                var.setVariableIsInitialized(false);
                variablesList.add(new FinalVariableDecorator(var));
            }
            else {
                variablesList.add(var);
            }

            // check if there is already an argument with the same name of the last added variable.
            for (int j = 0; j < variablesList.size() - 1; j++) {
                if (variablesList.get(j).getVariableName().equals(variablesList.get(variablesList.size()
                        - 1).getVariableName())) {
                    throw new DuplicationInArgsNamesException();
                }
            }

        }
        return variablesList;

    }

    /**
     * Validates args of a method are called correctly, has few cases:
     * 1. Wrong type, 2. Wrong numOfArgs, 3. unInitialized params.
     * @param paramToCheck list of parameters to check.
     * @throws BadArgsException if bad arg
     */
    public void validateArgs(ArrayList<Variable> paramToCheck) throws BadArgsException{

        // A call to no param's is made and OK, i.e: foo();
        if (paramToCheck == null && this.listOfArgs == null){
            return;
        }
        if (!isArgsValid(paramToCheck)){
            throw new BadArgsException();
        }
    }

    /**
     * Sets name to Method object, used by Constructors.
     * @param methodName name to set.
     * @throws IllegalNameException if bad method name
     */
    public void setName(String methodName) throws IllegalNameException {
        if (isNameValid(methodName)) {
            this.name = methodName;
        } else {
            throw new IllegalNameException();
        }
    }

    /**
     * Checks if a Method call has valid Args, for example: foo(1,2).
     * @param paramToCheck a list of Variables to check.
     * @return true if call fits function definition, else: false.
     */
    private boolean isArgsValid(ArrayList<Variable> paramToCheck){
        // Catch wrongSize && null
        if (paramToCheck == null || this.listOfArgs == null || paramToCheck.size() != this.listOfArgs.size()){
            return false;
        } else {
            // Runs on both lists (has same size),
            // Return false if Type of each does not fit.
            for (int i = 0 ; i<paramToCheck.size(); i++) {
                if (!VariableTypes.isCompatibleWith(this.listOfArgs.get(i).getType(),
                        paramToCheck.get(i).getType())){
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Returns if name valid due to RegexPatterns.
     * @param methodName String to check.
     * @return true / false.
     */
    private boolean isNameValid(String methodName){
        if (methodName == null){
            return false;
        }else {
            Matcher m = RegexPatterns.METHOD_LEGAL_NAME_PATTERN.matcher(methodName);
            return m.matches();
        }
    }

    public String getMethodName() {
        return name;
    }

    public String getVarNameInIndex(int index){
        // to avoid nullPointerException, cases where Calling method has more params then
        // function itself, i.e : func(int a) -> called as func(1,2).

        if (index > this.listOfArgs.size() - 1){
            return null;
        }
        return this.listOfArgs.get(index).getVariableName();
    }



}