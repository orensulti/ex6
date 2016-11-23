package oop.ex6.main;
import java.util.regex.Pattern;
/**
 * This class has contains all of the regular expressions we will use in the program.
 */
public class RegexPatterns {
    public final static String

            // Basic regex
            TRUE_OR_FALSE = "true|false",
            VARIABLE_LEGAL_NAME = "_\\w+|[a-zA-Z]\\w*",
            LEGAL_INT = "\\-?\\d+",
            LEGAL_DOUBLE = "\\-?\\d+(?:\\.\\d+)?",
            LEGAL_BOOLEAN = LEGAL_INT + "|" + LEGAL_DOUBLE + "|" + TRUE_OR_FALSE,
            LEGAL_CHAR = "\\s*'.?'\\s*",
            LEGAL_STRING = "\".*\"",
            METHOD_LEGAL_NAME ="[a-zA-Z]\\w*",
            VAR_TYPE_OPTIONS = "int|String|boolean|char|double",
            PREDECLARATION = "final",
            NOT_BOOLEAN = "^((?!true|false).)*$",

            // Statement regex
            LEGAL_VALUE = "(" + LEGAL_DOUBLE + ")|(" + LEGAL_STRING + ")|(" + LEGAL_CHAR + ")|(" +
                    LEGAL_BOOLEAN + ")|("
                    + VARIABLE_LEGAL_NAME + ")",
            EMPTY_LINE = "\\s*",
            COMMENT_REGEX = "\\//.*",
            LEGAL_VARIABLE_ASSIGNMENT = "\\s*(" + VARIABLE_LEGAL_NAME + ")\\s*=\\s*" + "(" + LEGAL_VALUE +
                    ")\\s*;\\s*",
            VARIABLE_DECLARATION_WITHOUT_VALUE = "\\s*(" + PREDECLARATION + "\\s+)?(" + VAR_TYPE_OPTIONS
                    + ")\\s+" + "(" +
                    VARIABLE_LEGAL_NAME + ")" + "\\s*;\\s*",
            LEGAL_VARIABLE_DECELERATION = "\\s*(" + PREDECLARATION + "\\s+)?(" + VAR_TYPE_OPTIONS +
                    ")\\s+((?:\\w+)*\\s*(?:[^=]\\s*[^,]+)?(?:,\\s*\\w+\\s*(?:=\\s*[^,]+)?)*)\\s*;\\s*",
            LEGAL_METHOD_DECLARATION = "^\\s*void\\s+(" + METHOD_LEGAL_NAME + ")\\s*\\((.*)\\)\\s*\\{\\s*$",
            LEGAL_METHOD_CALL = "\\s*([a-zA-Z]\\w*)\\s*\\((.*)\\)\\s*;\\s*",
            METHOD_RETURN = "^\\s*return\\s*;\\s*",
            OPEN_BRACKET = "[^\\{\\}]*\\{\\s*",
            CLOSE_BRACKET = "^\\s*\\}\\s*$",
            OPERATOR = "(\\s*\\|\\|\\s*|\\s*&&\\s*)",
            IF_OR_WHILE = "^\\s*(while|if)\\s*",
            IF_OR_WHILE_BLOCK = IF_OR_WHILE + "\\(" + EMPTY_LINE + "((\\w|" + LEGAL_DOUBLE + ")+" +
                    EMPTY_LINE
            + "(?:(?:" + OPERATOR + EMPTY_LINE  +"(\\w|" +LEGAL_DOUBLE + ")+" + EMPTY_LINE + ")" +
            EMPTY_LINE + ")*)\\)" + EMPTY_LINE + "\\{\\s*$";

    // regex:
    public static final Pattern
            // Name patters:
            VARIABLE_LEGAL_NAME_PATTERN = Pattern.compile(VARIABLE_LEGAL_NAME),
            METHOD_LEGAL_NAME_PATTERN = Pattern.compile(METHOD_LEGAL_NAME),

            // Parser command patterns:
            VARIABLE_DECELERATION_PATTERN = Pattern.compile(LEGAL_VARIABLE_DECELERATION),
            VARIABLE_ASSIGNMENT_PATTERN =   Pattern.compile(LEGAL_VARIABLE_ASSIGNMENT),
            METHOD_DECELERATION_PATTERN = Pattern.compile(LEGAL_METHOD_DECLARATION),
            COMMENT_REGEX_PATTERN = Pattern.compile(COMMENT_REGEX),
            METHOD_INVOCATION = Pattern.compile(LEGAL_METHOD_CALL),
            RETURN_PATTERN = Pattern.compile(METHOD_RETURN),
            EMPTY_LINE_REGEX = Pattern.compile(EMPTY_LINE),
            OPEN_BRACKET_PATTERN = Pattern.compile(OPEN_BRACKET),
            CLOSE_BRACKET_PATTERN = Pattern.compile(CLOSE_BRACKET),
            VARIABLE_DECLARATION_WITHOUT_VALUE_PATTERN = Pattern.compile(VARIABLE_DECLARATION_WITHOUT_VALUE),
            LEGAL_ASSIGNMENT_PATTERN = Pattern.compile(LEGAL_VARIABLE_ASSIGNMENT),
            IF_OR_WHILE_BLOCK_PATTERN = Pattern.compile(IF_OR_WHILE_BLOCK),


    // variable types regex:
    LEGAL_INT_PATTERN = Pattern.compile(LEGAL_INT),
    LEGAL_DOUBLE_PATTERN = Pattern.compile(LEGAL_DOUBLE),
    LEGAL_BOOLEAN_PATTERN = Pattern.compile(LEGAL_BOOLEAN),
    LEGAL_CHAR_PATTERN = Pattern.compile(LEGAL_CHAR),
    LEGAL_STRING_PATTERN = Pattern.compile(LEGAL_STRING);






}
