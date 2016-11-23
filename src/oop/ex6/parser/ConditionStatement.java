package oop.ex6.parser;
import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SjavaException;
import oop.ex6.scopes.Scope;
import oop.ex6.variables.Variable;

import oop.ex6.variables.VariableTypes;
import java.util.regex.Matcher;

/**
 * This class is a ConditionStatement (if or while block with condition inside) which implements Statement.
 * This class is responsible to handle ConditionStatement.
 */
public class  ConditionStatement implements Statement {


    // constants:

    final static int CONDITION_GROUP = 2;
    // data members:
    Scope scope;
    String line;

    /**
     * Constructor for MethodDeclarationStatement which get Scope object and string of the line.
     * and initialize the data members.
     * @param scope - Scope object
     * @param line - String of the line
     */
    public ConditionStatement(Scope scope, String line){
        this.scope = scope;
        this.line = line;
    }
    @Override
    public void handleStatement() throws SjavaException {
        Matcher ifWhileBlockMatcher = RegexPatterns.IF_OR_WHILE_BLOCK_PATTERN.matcher(line);
        ifWhileBlockMatcher.matches();

        // This condition line string is fully checked and does not contain unwanted values.
        // Holds for the condition line i.e if(true) --> holds 'true'.
        String conditionLine = ifWhileBlockMatcher.group(CONDITION_GROUP);
        conditionLine = conditionLine.replaceAll(" ","");

        // Split data on operator and get an array with condition-param to check
        String[] conditionArgs = conditionLine.split(RegexPatterns.OPERATOR);

        // Run on args and validate, we have 2 cases:
        // 1. a boolean case: i.e (true,false,int,double)
        // 2. a parameter case: i.e (a,b,c) -> check if initialized in scope/global_scope.

        for (String param: conditionArgs) {
            if (param.matches(RegexPatterns.LEGAL_BOOLEAN)) {
                // case 1: Value fits a boolean condition, continue
                continue;
            } else {
                if (param.matches(RegexPatterns.VARIABLE_LEGAL_NAME)) {
                    // case 2: a parameter
                    // if Parameter found: continue
                    // as Param was initialized before and fits type of condition check,
                    Variable paramVar = scope.findVariableInAllScopes(param);
                    if (paramVar == null || !paramVar.getIsInitialized()) {
                        // Argument is not initialized or does not exist in any scope.
                        throw new ConditionParamNotFoundException();
                    } else {
                        // Param is found, verify it Type is correct.
                        // If incorrect - throw exception
                        if (!VariableTypes.isCompatibleWith(VariableTypes.DataTypes.BOOLEAN,
                                paramVar.getType())){
                            throw new BadTypeConditionParamException();
                        }
                    }
                }
            }
        }
    }
}
