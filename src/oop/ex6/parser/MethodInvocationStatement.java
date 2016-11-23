package oop.ex6.parser;

import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SjavaException;
import oop.ex6.methods.BadArgsException;
import oop.ex6.methods.Method;
import oop.ex6.scopes.CallMethodDoesNotExistException;
import oop.ex6.scopes.GlobalScope;
import oop.ex6.scopes.Scope;
import oop.ex6.variables.Variable;
import oop.ex6.variables.VariableFactory;
import oop.ex6.variables.VariableTypes;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * This is a MethodInvocationStatement class which implements Statement.
 * This class is responsible to handle MethodInvocationStatement.
 */
public class MethodInvocationStatement implements Statement {

    // constants:
    final static int METHOD_NAME = 1;
    final static int METHOD_ARGUMENTS = 2;

    // data members:
    Scope scope;
    String line;

    /**
     * Constructor for MethodDeclarationStatement which get Scope object and string of the line.
     * and initialize the data members.
     * @param scope - Scope object
     * @param line - String of the line
     */
    public MethodInvocationStatement(Scope scope, String line){
        this.scope = scope;
        this.line = line;
    }


    @Override
    /**
     * Overrides handleStatement. this method verifies that a method call is valid.
     * Checks it given parameters validation, and checks that function was declared before/after.
     */
    public void handleStatement() throws SjavaException {
        Matcher methodInvocationMatcher;
        String methodName;
        String methodParam;
        String[] paramsStrList;
        ArrayList<Variable> paramsVarList = new ArrayList<>();

        methodInvocationMatcher = RegexPatterns.METHOD_INVOCATION.matcher(this.line);
        methodInvocationMatcher.matches();

        // Gather data from line groups && check if methodExistInScope:
        // Capture data from group1 (name) and create a new Method from scope.
        methodName = methodInvocationMatcher.group(METHOD_NAME);
        Method method = GlobalScope.getInstance().findMethod(methodName);

        // Method exists in globalScope (had been declared):
        // Create it variables and validate that method call is correct. i.e ( foo(a,b,c) params are valid ).
        if (method != null){
            String callVarName;
            // Save methodCall arguments line(i.e : " a,b,c ")
            methodParam = methodInvocationMatcher.group(METHOD_ARGUMENTS);

            // Check if method call is an empty call, i.e ( foo() )
            // If such, validate it args with the current declared method.
            // If no exception found, return and do not carry on.
            if (methodParam.isEmpty()){
                method.validateArgs(null);
                return;
            }

            // Method call is not null, parameters exist:
            // Create new variables from them and validate with current method declaration.
            methodParam = methodParam.replaceAll(" ","");
            paramsStrList = methodParam.split(",");


            for (int i = 0; i< paramsStrList.length; i++) {
                // Two cases: 1. method calls with parameter, i.e : foo(a,b);
                // 2. Method calls with values, i.e : foo(5,"string");
                if ((paramsStrList[i].matches(RegexPatterns.VARIABLE_LEGAL_NAME)) &&
                        paramsStrList[i].matches(RegexPatterns.NOT_BOOLEAN)){
                    // Case 1:
                    // Check if Variable exists in scope, else: throw exception
                    Variable newVar = scope.findVariableInAllScopes(paramsStrList[i]);
                    if (newVar == null){
                        // Parameter called by method is not found in scope
                        // Means no parameter exists: throw exception.
                        throw new BadArgsException();
                    }
                    paramsVarList.add(newVar);
                } else {
                    // Case 2:
                    callVarName = method.getVarNameInIndex(i);
                    // If there exist more args in call than function sign
                    // throw Exception
                    if (callVarName == null){
                        throw new BadArgsException();
                    }

                    // Create a new variable (one that function calls) with it dataType, name, value.
                    Variable newVar = VariableFactory.createVariable(VariableTypes.getTypeFromParam
                            (paramsStrList[i]), callVarName, paramsStrList[i]);
                    paramsVarList.add(newVar);
                }
            }
        method.validateArgs(paramsVarList);

        } // method != null case

        // Method is null -> has not been declared in globalScope.
        // Throw's exception.
        else {
            throw new CallMethodDoesNotExistException();
        }

    }

}
