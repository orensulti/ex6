package oop.ex6.parser;

import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SjavaException;
import oop.ex6.methods.Method;
import oop.ex6.scopes.GlobalScope;
import oop.ex6.scopes.Scope;
import oop.ex6.variables.Variable;

import java.util.ArrayList;
import java.util.regex.Matcher;

/**
 * This is a statement class of type method declaration statement, implements Statements.
 * It implements the method handleStatement() which in this case takes care of statements in the file
 * of method declaration (take place only in global scope)
 */
public class MethodDeclarationStatement implements Statement{


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
    public MethodDeclarationStatement(Scope scope, String line){
        this.scope = scope;
        this.line = line;
    }


    @Override
    public void handleStatement() throws SjavaException{

        Matcher methodMatcher = RegexPatterns.METHOD_DECELERATION_PATTERN.matcher(line);

        methodMatcher.matches();


        String methodName = methodMatcher.group(METHOD_NAME);
        String arguments = methodMatcher.group(METHOD_ARGUMENTS);


        // create the variables from the arguments, if one of the argument is illegal
        // exception will be thrown
        ArrayList<Variable> variablesList = Method.createVariablesFromArguments(arguments);


        Method method = new Method(methodName, variablesList);


        // add this method to the scope, if there is already a method with this name in the scope
        // addMethod will throw ScopeException
        GlobalScope.getInstance().addMethod(method);



    }
}
