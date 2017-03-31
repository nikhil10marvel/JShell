package jsh;

import asg.cliche.CLIException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.io.LineNumberReader;
import java.io.Serializable;
import java.net.InetAddress;
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
                } else if(command.startsWith("urlreader") || command.startsWith("urlr")){
                    String[] toki = command.split(" ");
                    System.out.println(url_readUTF(new URL(toki[1]), program));
                    //url_readUTF(new URL(toki[1]), program);   //#TEST
                } else if(command.startsWith("def")){
                    String[] toki = command.split(" ");
                    String var_name = toki[2];
                    String var_Type = toki[1];
                    switch (var_Type) {
                        case "flt":
                            program.vars.floats.add(new FloatVar(var_name, Float.parseFloat(toki[3])));
                            //System.out.println("jsh.Method.execute() added " + Float.parseFloat(toki[3]));    // #Debug
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
                    if(command.contains("[") && command.contains("]")){
                        String test_Cmd = command;
                        String subString = test_Cmd.substring(test_Cmd.indexOf("[")+1, test_Cmd.indexOf("]"));
                        System.out.println(subString);
                        if(subString.startsWith("v:")){
                            String[] toki= subString.split(":");
                            String type = toki[1];
                            String name = toki[2];
                            switch(type){
                                
                                case "flt":
                                    command = test_Cmd.replace(subString, String.valueOf(program.vars.FindFloat(name)));
                                    break;
                                    
                                case "int":
                                    command = test_Cmd.replace(subString, String.valueOf(program.vars.FindINT(name)));
                                    break;
                                    
                                case "str":
                                    command = test_Cmd.replace(subString, program.vars.FindString(name));
                                    break;
                                
                                default:
                                    break;
                            }
                        }
                    }
                    jshell.JShell.shell.processLine(command);
                }
            } catch (CLIException ex) {
                Logger.getLogger(Method.class.getName()).log(Level.SEVERE, null, ex);
            } catch (NumberFormatException ex){
                program.throw_err("Ivalid Value Assigned!");
            } catch (IOException ex) {
                Logger.getLogger(Method.class.getName()).log(Level.SEVERE, null, ex);
                if(ex instanceof UnknownHostException){
                    program.throw_err("Unknown or Unable to find host");
                } else {
                    ex.printStackTrace();
                }
            }
        }
    }
    
    public String url_readUTF(URL url, Program program) throws UnknownHostException, IOException{
        // URL Reachability check
        InetAddress inetAddress = InetAddress.getByName(url.getHost());
        StringBuilder stringBuilder = null;
        if(inetAddress.isReachable(4000)){
          // When we can reach the url
          stringBuilder = new StringBuilder();
          try(BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))){
              String line;
              while((line = br.readLine()) != null){
                  stringBuilder.append(line + "\n");
              }
              
         } catch (Exception ex) {
              ex.printStackTrace();
              program.throw_err("Unexpected Exection!");
          }
        } else {
            // We don't want to read from what we cannot reach, so throw error
            program.throw_err("Unable to reach provided URL[" + url.toString() + "]");
        } 
        return stringBuilder.toString();
    }
    
}
