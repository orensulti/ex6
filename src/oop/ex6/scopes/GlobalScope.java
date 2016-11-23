package oop.ex6.scopes;
import oop.ex6.methods.Method;

import java.util.HashMap;

/**
 * A single-Tone GlobalScope class, used to define the main scope of the files
 */
public class GlobalScope extends Scope{
    // Single-Tone Global Scope initialization:
    private static GlobalScope instance = new GlobalScope();
    public static GlobalScope getInstance() {
        return instance;
    }
    private GlobalScope() {
        super();
    }

    // Data members:
    // GlobalScope includes Methods HashMap && Variables HashMap.
    private HashMap<String, Method> methods = new HashMap<>();

    // Methods:
    public void addMethod(Method methodToAdd) throws MethodOverLoadingException {
        if (!this.methods.containsKey(methodToAdd.getMethodName())){
            this.methods.put(methodToAdd.getMethodName(), methodToAdd);
        }
        // Overloading case:
        else{
            throw new MethodOverLoadingException();
        }
    }

    public static void reset() {
        instance = new GlobalScope();

    }

    public Method findMethod(String methodName){
        return this.methods.get(methodName);
    }

}
