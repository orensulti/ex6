package oop.ex6.parser;
import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SjavaException;
import oop.ex6.scopes.Scope;
import oop.ex6.scopes.VarAlreadyExistInScope;
import oop.ex6.variables.*;
import java.util.regex.Matcher;

/**
 * This is a statement class of type variable declaration or assignment, implements Statements.
 * It implements the method handleStatement() which in this case take care of statements in the file
 * of variable declaration or variable assignments.
 */
public class VariableDeclarationOrAssignmentStatement implements Statement {

    // constants:
    // constants regarding the regex groups
    final int PREDECLARATION = 1, VAR_TYPE = 2, NAME_AND_VALUE = 3;
    final int ASSIGNMENT_VAR_NAME = 1, VAR_VALUE = 2, DECLARATION_VAR_NAME = 3;

    // data members:
    Scope scope;
    String line;

    /**
     * Constructor for VariableDeclarationOrAssignmentStatement to initialize the scope and the line.
     * @param scope - Scope object
     * @param line - String of the line in the file
     */
    public VariableDeclarationOrAssignmentStatement(Scope scope, String line) {
        this.scope = scope;
        this.line = line;
    }

    /**
     * overrides the handleStatement() method.
     * take care of the variable line, exception will be thrown if something is not good with the line.
     * In this method I check for assignment case, for example : a= 5; and for declaration case, for
     * example: final int a=5,b=5;
     * @throws SjavaException if did not work.
     */
    @Override
    public void handleStatement() throws SjavaException {

        Matcher assignmentMatcher;
        String variableName;
        String variableValue;

        assignmentMatcher = RegexPatterns.LEGAL_ASSIGNMENT_PATTERN.matcher(line);

        // if the variable line is an assignment line, handle the variable assignment line scenario:
        // we should only update the variable value by calling assignVarValue() method of scope.

        if (assignmentMatcher.matches()){
            variableName = assignmentMatcher.group(ASSIGNMENT_VAR_NAME);
            variableValue = assignmentMatcher.group(VAR_VALUE);
            scope.assignVarValue(variableName, variableValue);
        }

        // handle the variable declaration scenario:
        else{
            // identify VARIABLE_DECELERATION_PATTERN by the regex

            Matcher variableMatcher = RegexPatterns.VARIABLE_DECELERATION_PATTERN.matcher(line);
            variableMatcher.matches();

            String variablesType = variableMatcher.group(VAR_TYPE);
            String predeclaration = variableMatcher.group(PREDECLARATION);

            // extract the names and values to variables array
            String[] variables = variableMatcher.group(NAME_AND_VALUE).split(",");

            Matcher variableDeclarationMatcher;
            // for each of name and value combination
            for (String variable : variables) {
                variableDeclarationMatcher = RegexPatterns.VARIABLE_DECLARATION_WITHOUT_VALUE_PATTERN.
                        matcher(variablesType + " " + variable + ";");

                if (variableDeclarationMatcher.matches()){
                    // declaration without a value (name only)
                    variableValue = null;
                    variableName = variableDeclarationMatcher.group(DECLARATION_VAR_NAME);
                }
                else
                {
                    assignmentMatcher = RegexPatterns.LEGAL_ASSIGNMENT_PATTERN.matcher(variable + ";");
                    if (assignmentMatcher.matches()){
                        // declaration with value
                        variableValue = assignmentMatcher.group(VAR_VALUE);

                        variableName = assignmentMatcher.group(ASSIGNMENT_VAR_NAME);
                    }
                    else{
                        throw new VariableDeclarationException();
                    }
                }

                Variable varObj;

                // this variable name is already exist in this scope.
                if (scope.findVariableInCurrentScope(variableName) != null){
                    throw new VarAlreadyExistInScope();
                }

                Variable oldVariable = scope.findVariableInHisParentsScope(variableValue);
                // the variable is found in a parent scope:
                if (oldVariable != null) {
                    varObj = VariableFactory.createVariable(
                            VariableTypes.getTypeFromLine(variablesType), variableName);

                    oldVariable = Variable.deepCopyVariable(oldVariable);
                    varObj.setVariableOtherVariable(oldVariable);
                }
                else{

                    // create the variable via factory
                    if (variableValue == null){

                        varObj = VariableFactory.createVariable
                                (VariableTypes.getTypeFromLine(variablesType), variableName);

                    }
                    else {
                        varObj = VariableFactory.createVariable
                                (VariableTypes.getTypeFromLine(variablesType), variableName, variableValue);
                    }
                }
                // handle final variable
                if (predeclaration != null) {
                    FinalVariableDecorator FinalVariable = new
                            FinalVariableDecorator(varObj);
                    scope.addNewVariable(FinalVariable);
                } else {
                    scope.addNewVariable(varObj);
                }

            }
        }
    }
}