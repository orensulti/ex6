package oop.ex6.parser;

import oop.ex6.main.SjavaException;
/**
 * Interface for Statement, requires the subclasses to implement handleStatement() method.
 */
public interface Statement {
    void handleStatement() throws SjavaException;

}
