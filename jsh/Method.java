package jsh;

import asg.cliche.CLIException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Nikhil
 */
public class Method implements Serializable{
    
    //String returns;
    public String[] commands;
    public String method;
    
    public void execute(Program program){
        for (String command : commands) {
            try {
                if(command.startsWith("call")){
                    String[] toki = command.split(" ");
                    Method find = program.getMethod(toki[1]);
                    if(find != null) find.execute(program);
                    else program.throw_err("Unable to Find Method " + toki[1]);
                } else {
                    jshell.JShell.shell.processLine(command);
                }
            } catch (CLIException ex) {
                Logger.getLogger(Method.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
