package jsh;

import asg.cliche.CLIException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsh.var_types.BoolVar;
import jsh.var_types.FloatVar;
import jsh.var_types.IntVar;
import jsh.var_types.StingVar;

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
                } else if(command.startsWith("def")){
                    String[] toki = command.split(" ");
                    String var_name = toki[2];
                    String var_Type = toki[1];
                    switch (var_Type) {
                        case "flt":
                            program.vars.floats.add(new FloatVar(var_name, Float.parseFloat(toki[3])));
                            break;
                        case "int":
                            program.vars.ints.add(new IntVar(var_name, Integer.parseInt(toki[3])));
                            break;
                        case "str":
                            program.vars.strings.add(new StingVar(var_name, toki[3]));
                            break;
                        case "bool":
                            program.vars.bools.add(new BoolVar(var_name, Boolean.parseBoolean(toki[3])));
                        default:
                            break;
                    }
                }else {
                    if(command.contains("v[") && command.contains("]")){
                        String subString = command.substring(command.indexOf("v["), command.indexOf("]"));
                        String toki[] = subString.split(":");
                        String vartype = toki[0];
                        String varname = toki[1];
                        if(!((vartype == null && varname == null)||(vartype.equals("") && varname.equals(""))||(vartype.equals(" ") && varname.equals(" ")))){
                            switch(vartype){
                                case "flt":
                                    command.replace(subString, String.valueOf(program.vars.FindFloat(varname)));
                                    break;
                                case "int":
                                   command.replace(subString, String.valueOf(program.vars.FindINT(varname)));
                                    break;
                                case "str":
                                    command.replace(subString, program.vars.FindString(varname));
                                    break;
                                case "bool":
                                    command.replace(subString, String.valueOf(program.vars.FindBool(varname)));
                            }
                        }
                    }
                    jshell.JShell.shell.processLine(command);
                }
            } catch (CLIException ex) {
                Logger.getLogger(Method.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex){
                program.throw_err("Ivalid Value Assigned!");
            }
        }
    }
    
}
