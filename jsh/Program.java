package jsh;

import java.io.Serializable;

/**
 *
 * @author Nikhil
 */
public class Program implements Serializable{
    
    public Method[] methods;
    public Var vars = new Var();
    public String program;
    
    public Method getMethod(String name){
        Method return_method = new Method();
        for (Method method : methods) {
            if(method.method.equals(name)){
                return_method = method;
                break;
            }
        }
        if(return_method == null){
            throw_err("Method " + name + " Not Found");
        }
        return return_method;
    }
    
    public String toString(){
        return "" + methods + "" + program + vars + "";
    }
    
    public void runMain(){
        Method mainMethod = getMethod("main");
        mainMethod.execute(this);
    }
    
    public void throw_err(String msg){
        System.err.println("DISASTER@ " + program + "\ncause:" + msg);
        throw new RuntimeException("Program Failure! Check Error Report.");
    }
    
}
