package oop.ex6.scopes;

import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableException;
import oop.ex6.methods.Method;

import java.util.HashMap;
import java.util.List;

/**
 * This class is a general Scope class which holds the parent scope and the variables of the scope.
 * has methods to search and to add for variables in the scope.
 */
public class Scope {

    // Each scope holds a data member which points to its parent
    // scope and a list of it's variables. The main methods are addVar(), assignVar().

    protected Scope parent;


    private HashMap<String, Variable> variables;

    /**
     * Constructor for the Single-Tone of GlobalScope.
     */
    public Scope() {
        this.variables = new HashMap<>();
    }
    /**
     * Scope constructor initializes a new Scope.
     * @param parent given Parent pointer.
     */
    public Scope(Scope parent){
        this();
        this.parent = parent;
    }





    /**
     * This is one of the main methods of scope - add new variable.
     * If the variable is already exist in the scope (that's we can check if this variable name exist
     * in the HashMap of variables), throw an exception, otherwise, add this variable to HashMap by using
     * put method.
     * @param variable - Object of a new variable we want to add to this scope
     * @throws ScopeException - throws exception if variable is already exist in the scope.
     */
    public void addNewVariable(Variable variable) throws ScopeException{
        Variable var = findVariableInCurrentScope(variable.getVariableName());
        // this var is found in this scope - already exist, therefore, we will throw an exception
        // because we cannot afford duplications of variables.
        if (var != null){
            throw new VarAlreadyExistInScope();
        }
        // this is a new variable (which does not exist), so we will add it to the HashMap of variables
        else{
            this.variables.put(variable.getVariableName(), variable);
        }
    }

    /**
     * Finds Variable object in all scopes, uses findVarInCurScope and findVarInParentScope methods.
     * @param variableName object to find.
     * @return null if obj is not found, else: it Variable type object.
     */
    public Variable findVariableInAllScopes(String variableName) {
        Variable var = findVariableInCurrentScope(variableName);
        if (var == null){
            // var is not found in CurScope, search upper:
            var = findVariableInHisParentsScope(variableName);
        }
        return var;
    }

    /**
     * This method will assign variable the value.
     * When we update a variable, must be one of the following:
     * 1. The variable is declared in this scope, has a value or has not a value, anyway - just update
     * 2. The variable does not exist in this scope and there is no parent scope - throw exception -
     * VariableDoesNotExistException.
     * 3. The variable does not exist in this scope but exists in one of his parent scope -
     * in this case we want to update the variable value only in the current scope and not in the parent
     * scope where it has found, so this method will use clone method of Variable in order to create identical
     * variable in the current scope, add it and update it, by this way the variable in the parent scope
     * remains unchanged.
     * @param variableName - String of variable name we want to assign him a value
     * @param value - The value to be assigned.
     * @throws VariableException for bad var
     * @throws VariableDoesNotExistException if var does not exist
     */
    public void assignVarValue(String variableName, String value) throws VariableException,
            VariableDoesNotExistException {
        Variable varInCurScope = findVariableInCurrentScope(variableName);
        if (varInCurScope != null){
            // Var exists in cur Scope, set it value
            varInCurScope.setVariableValue(value);
        } else{
            Variable varInParentScope = findVariableInHisParentsScope(variableName);
            if (varInParentScope == null){
                throw new VariableDoesNotExistException();
            }
            // Var exists in a parent scope - deep copy and update.
            Variable newUpdatedCopiedVar = Variable.deepCopyVariable(varInParentScope);
            this.variables.put(variableName, newUpdatedCopiedVar);
            assignVarValue(variableName, value);

        }
    }

    /**
     * Finds Variable in Cur scope
     * @param variableName Variable to check
     * @return null if not found, the var if did find.
     */
    public Variable findVariableInCurrentScope(String variableName){

        Variable var = null;
        if ((this instanceof MethodScope) && !(this instanceof IfOrWhileBlock)){
            var = this.searchForVarInMethodArgs(variableName, (MethodScope) this);
        }
        if (var != null){
            return var;
        }
        return this.variables.get(variableName);
    }

    /**
     * Recursive method which is searching for a variable by his name in his all of his parents scopes.
     * If the variable is found - return the variable, otherwise, return null.
     * @param variableName - String for the name of the variable.
     * @return the variable if found. else: null.
     */
    public Variable findVariableInHisParentsScope(String variableName){
        Variable var = null;
        if ((this instanceof MethodScope) && !(this instanceof IfOrWhileBlock)){
            var = this.searchForVarInMethodArgs(variableName, (MethodScope)this);
        }
        if (var != null){
            return var;
        }
        var = variables.get(variableName);
        // if variable is found in the scope we will return the variable
        if (var != null){
            return var;
        }
        // else we will call recursively this method with parent if we have a parent scope
        // the case we do not have a parent scope will be the stop condition to the recursion
        // and null will be returned - because we did not find the variable in all of his parents scopes.
        else{
            return parent!= null ? parent.findVariableInHisParentsScope(variableName): null;
        }
    }

    public Variable searchForVarInMethodArgs(String variableName, MethodScope methodScope){
        // if parent is GlobalScope so we are in MethodScope
            Method method = methodScope.method;
            List<Variable> listOfArgs = method.getListOfArgs();
            if (listOfArgs != null) for (Variable listOfArg : listOfArgs) {
                if (listOfArg.getVariableName().equals(variableName)) {
                    return listOfArg;
                }
            }

        return null;
    }
    public HashMap<String, Variable> getVariables() {
        return variables;
    }

}
