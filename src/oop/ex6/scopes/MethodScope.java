package oop.ex6.scopes;

import oop.ex6.methods.Method;

/**
 * This class represents a scope inside a method.
 */
public class MethodScope extends Scope {
    protected Method method;
    private boolean returned = false;

    // default constructor
    public MethodScope(){
        method = null;
    }

    public MethodScope(Method method, GlobalScope globalScope){
        this();
        this.method = method;
        this.parent = globalScope;
    }




    public void setIsReturned(boolean isReturned){
        returned = isReturned;
    }
    public boolean getReturned(){
        return this.returned;
    }


}
