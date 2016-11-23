package oop.ex6.scopes;

/**
 * This class represents a type of scope inside method which is if or while block.
 */
public class IfOrWhileBlock extends MethodScope {
    public IfOrWhileBlock(Scope scope){
        this.parent = scope;
    }

}
