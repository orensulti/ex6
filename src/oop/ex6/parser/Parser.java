package oop.ex6.parser;
import oop.ex6.main.RegexPatterns;
import oop.ex6.main.SjavaException;
import oop.ex6.scopes.GlobalScope;
import oop.ex6.methods.Method;
import oop.ex6.scopes.IfOrWhileBlock;
import oop.ex6.scopes.MethodScope;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;


/**
 * Parser class which is responsible to parse the file and return true or false to the main.
 * true if the file of statements is good, false, otherwise.
 * The parse is done in two main parts:
 * 1. parse the global scope
 * 2. parse inside methods.
 */

public class Parser {

     public static void compile(File sourceFile)
     throws IOException, SjavaException {

         // scan the file first time to parse the global scope.
         Scanner scan = new Scanner(sourceFile);
         Parser.parserGlobalScope(scan);

         scan.reset();

         // scan the file second time to parse the methods scopes.
         scan = new Scanner(sourceFile);
         Parser.parseInsideMethodsScopes(scan);

         scan.reset();
     }


    /**
     * This method should parserGlobalScope, first create GlobalScope (singleton) object, then,
     * read the lines of the global scope, identify the different possibilities for a line for example
     * (variable declaration, set value, empty or comment line , method declaration and more)
     * identification is done by using regex, and then call help methods to treat each type of line -
     * parse the line and create the correct objects according to the line (exception will be bubble up if
     * occurred - for example in variable declaration line because the name of variable is illegal or the
     * value is illegal assignment)
     * @param fileScanner - Object of Scanner to access the file we parse
     * @throws SjavaException if did not parse correctly.
     */

    public static void parserGlobalScope(Scanner fileScanner) throws SjavaException {
        // create global scope object - this is the only time we do this - because global scope has only one
        // therefore we implemented global scope in Singleton design pattern.
        GlobalScope globalScope = GlobalScope.getInstance();

        // counter for brackets and line number
        int bracketsCounter = 0;

        //boolean isLastRowInMethodIsReturn = false;
        String line;

        while (fileScanner.hasNextLine()) {
            // we have new line, get the String of the line from fileScanner.
            line = fileScanner.nextLine();

            // if number of open brackets is lower than the number of closed brackets - throw exception
            if (bracketsCounter < 0) {
                throw new UnbalancedNumberOfBracketsException();
            }
            // if the number of closed brackets and open brackets is equal - it is good - and we know we are
            // in the global scope.

            if (bracketsCounter == 0) {
                // check if the line is empty or a comment line - if so continue to the next line
                if (ignoreLine(line)) {
                    continue;
                }

                // the line is not empty and is not a comment - we should handle this line.
                // this line may be variable line or method declaration line, other options are illegal.
                // let's use our Statements factory for the global scope.
                try {
                    Statement curStatement = StatementFactory.createSuitableStatement(globalScope, line);
                    curStatement.handleStatement();
                } catch (Exception e) {
                    throw new SjavaException(e.getMessage());
                }
            }


            // check if this line has open brackets or close.
            // increase or decrease from bracketsCounter respectively.
            if (RegexPatterns.OPEN_BRACKET_PATTERN.matcher(line).matches()) {
                bracketsCounter++;
            }
            if (RegexPatterns.CLOSE_BRACKET_PATTERN.matcher(line).matches()) {
                bracketsCounter--;

            }
        }
        // we finished reading the file, now lets check if number of brackets is balanced - (=0)
        if (bracketsCounter != 0) {
            throw new UnbalancedNumberOfBracketsException();
        }
        fileScanner.close();
    }


    /**
     * After we parsed the global scope we will parse the inner methods scopes.
     * again read each line, identify the type of the line and treat the line after we know the type of the
     * line for example call method, and after we can assume we are in method scope (and not in global which
     * is different case)
     * Lines which can appear in methods scope and should be treated are:
     * define a block, declaration of variable, assign a value to variable, call method, return and more.
     * @param fileScanner - Object of Scanner to access the file we parse
     * @throws SjavaException if did not parse correctly.
     */
    public static void parseInsideMethodsScopes(Scanner fileScanner) throws SjavaException {

        final int METHOD_NAME = 1;
        String line;
        Matcher methodMatcher;
        GlobalScope globalScope = GlobalScope.getInstance();

        while (fileScanner.hasNext()) {
            line = fileScanner.nextLine();
            methodMatcher = RegexPatterns.METHOD_DECELERATION_PATTERN.matcher(line);

            if (methodMatcher.matches()) {
                String methodName = methodMatcher.group(METHOD_NAME);
                Method methodObj = globalScope.findMethod(methodName);
                MethodScope methodScope = new MethodScope(methodObj, globalScope);
                parseInsideMethod(fileScanner, methodScope);

            }
        }
        fileScanner.close();
    }

    /**
     * Parses inside method.
     * @param fileScanner file to scan on.
     * @param scope to work on.
     * @throws SjavaException if did not parse correctly.
     */
    public static void parseInsideMethod(Scanner fileScanner, MethodScope scope)
            throws SjavaException{
        String line;

        while (fileScanner.hasNext()){

            scope.setIsReturned(false);
            line = fileScanner.nextLine();

            // if it is an empty line or a comment line go to next line
            if (ignoreLine(line)) {
                continue;
            }
            // if we encountered the end block of the method - we finished.
            if (RegexPatterns.CLOSE_BRACKET_PATTERN.matcher(line).matches()){

                return;
            }
            // if we encountered return statement, we will update isLastRowInMethodIsReturn flag
            // and continue to the next line
            if (RegexPatterns.RETURN_PATTERN.matcher(line).matches()) {
                scope.setIsReturned(true);
                continue;
            }


            // other cases are statements we should compile so we will call to the Statements Factory.
            // variable line, call other method(method invocation), if while block

            try {
                Statement curStatement = StatementFactory.createSuitableStatement(scope, line);
                curStatement.handleStatement();
                scope.setIsReturned(false);

                // if we encountered we will open a new scope, and call again to this method with
                // the new ifOrWhile scope object.
                if (curStatement instanceof ConditionStatement){
                    IfOrWhileBlock ifOrWhileScope = new IfOrWhileBlock(scope);
                    parseInsideMethod(fileScanner, ifOrWhileScope);
                }
            } catch (Exception e) {
                throw new SjavaException(e.getMessage());
            }
        }

        // we finished with this method but the flag of isLastRowInMethodIsReturn is false - so we know
        // we had more rows after the last return statement which is not legal.
        if (!scope.getReturned()) {
            throw new StatementAfterReturnException();
        }

    }

    /**
     * Ignore empty line.
     * @param line to ignore.
     * @return true or false.
     */
    public static boolean ignoreLine(String line){
        // this is an empty line or a comment line - we should ignore this line.
        if (RegexPatterns.EMPTY_LINE_REGEX.matcher(line).matches() ||
                RegexPatterns.COMMENT_REGEX_PATTERN.matcher(line).matches()){
            return true;
        }
        return false;
    }

}

