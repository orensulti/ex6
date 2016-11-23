package oop.ex6.main;
import oop.ex6.parser.Parser;
import oop.ex6.scopes.GlobalScope;

import java.io.File;
import java.io.IOException;

/**
 * This is the main class which calls the Parser two times.
 * First, parse the global scope (which is a single-tone)
 * Second, parse the methods scopes.
 */
public class Sjavac {

    private final static int IO_EXCEPTION = 2, ILLEGAL_CODE = 1, LEGAL_CODE = 0, PATH_INDEX = 0;
    private final static String IO_ERROR_MESSAGE = "IO Exception - error in reading the file";
    private final static String WRONG_FILE_TYPE = "ERROR: only Sjava type is supported!!!";
    private final static String S_JAVA_FILE = ".+\\.sjava";

    public static void main(String[] args) throws SjavaException, IOException {

        File sourceFile = new File(args[PATH_INDEX]);

        // try parse the global scope and methods scope, if the parser will throw an exception,
        // we will print 1 and the exception message.
        // if IO exception will occurred, we will print 2 and the manual IO_ERROR_MESSAGE.
        try {
            if (!sourceFile.getName().matches(S_JAVA_FILE)){ // these is from the school solution
                System.err.println(WRONG_FILE_TYPE);
            }
            GlobalScope.reset();
            Parser.compile(sourceFile);
        } catch (SjavaException e) {
            System.out.println(ILLEGAL_CODE);
            System.err.println(e.getMessage());
            return;
        }
        catch(IOException e)
        {
            System.err.println(IO_ERROR_MESSAGE);
            System.out.println(IO_EXCEPTION);
            return;
        }
        System.out.println(LEGAL_CODE);


    }




}
