package oop.ex6.parser;

import oop.ex6.main.RegexPatterns;
import oop.ex6.scopes.GlobalScope;
import oop.ex6.scopes.MethodScope;
import oop.ex6.scopes.Scope;

/**
 * StatementFactory class with one static method createSuitableStatement which get the scope and the line
 * and choose the correct statement to handle.
 */
public class StatementFactory {


    /**
     * Return the correct Statement according to the line and the scope.
     * @param scope - Scope object
     * @param line - String of the line
     * @return - Statement
     * @throws ParserException if a bad parsing.
     */
    public static Statement createSuitableStatement(Scope scope, String line) throws ParserException {

        Statement statement = null;
        // we want from the factory to create the suitable statement under the assumption we are in
        // a global scope.

        if (scope instanceof GlobalScope) {
            // global scope line can only contains a variable line(declaration or assignment)
            // or method declaration line. (of course also a comment or empty line but we treated it before
            // in parser)
            if (RegexPatterns.VARIABLE_DECELERATION_PATTERN.matcher(line).matches() ||
                    RegexPatterns.VARIABLE_ASSIGNMENT_PATTERN.matcher(line).matches()) {
                statement = new VariableDeclarationOrAssignmentStatement(scope, line);
            } else if (RegexPatterns.METHOD_DECELERATION_PATTERN.matcher(line).matches()) {
                statement = new MethodDeclarationStatement(scope, line);
            } else {
                throw new IllegalStatementException();
            }
        }
        else if (scope instanceof MethodScope) {
            // method scope line can only contains the following:
            // 1. method invocation to other method
            // 2. variable line
            // of course the line may be comment line empty line, return line or end block line,
            // all of these cases I already treated in the parser.
            if (RegexPatterns.METHOD_INVOCATION.matcher(line).matches()) {
                statement =  new MethodInvocationStatement(scope, line);
            }
            else if (RegexPatterns.VARIABLE_DECELERATION_PATTERN.matcher(line).matches() ||
                    RegexPatterns.VARIABLE_ASSIGNMENT_PATTERN.matcher(line).matches()) {
                statement = new VariableDeclarationOrAssignmentStatement(scope, line);
            }
            else if (RegexPatterns.IF_OR_WHILE_BLOCK_PATTERN.matcher(line).matches()){
                statement = new ConditionStatement(scope, line);
            }
            else {
                throw new IllegalStatementException();
            }

        }
        return statement;
    }

}
