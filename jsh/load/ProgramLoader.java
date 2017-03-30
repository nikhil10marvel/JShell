package jsh.load;

import com.esotericsoftware.yamlbeans.YamlConfig;
import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.math.MathContext;
import java.net.URI;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import jsh.Method;
import jsh.Program;

/**
 *
 * @author Nikhil
 */
public class ProgramLoader {
    
    public void loadProgramURL(String urlz){
        try {
            URL url = new URL(urlz);
            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
            parseProgram(new YamlReader(br));
        } catch (IOException ex) {
            Logger.getLogger(ProgramLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void loadProgram(File file){
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            YamlReader yamlReader = new YamlReader(bufferedReader);
            parseProgram(yamlReader);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProgramLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void loadProgram(String path){
        loadProgram(new File(path));
    }
    
    private void parseProgram(YamlReader yamlReader){
        try {
            Program program = yamlReader.read(Program.class);
//            System.out.println("jsh.load.ProgramLoader.parseProgram()" + program.toString());     //#Debug
            yamlReader.close();
            runProgram(program);
        } catch (YamlException ex) {
            Logger.getLogger(ProgramLoader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProgramLoader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void serializeProgram(Program program, String file) throws IOException{
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(file)));
        oos.writeObject(program);
        oos.close();
    }
    
    public Program DeserializeProgram(String file) throws IOException, ClassNotFoundException{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(file)));
        Program program = (Program) ois.readObject();
        ois.close();
        return program;
    }
    
    protected void runProgram(Program program){
        program.runMain();
    }
    
//    Test, create  sample program #Debug
    public static void main(String[] args) {
//        try {
//            Program sampleProgram = new Program();
//            sampleProgram.program = "sample";
//            sampleProgram.methods = new Method[1];
//            Method mainMethod = new Method();
//            mainMethod.method = "main";
//            mainMethod.commands = new String[1];
//            mainMethod.commands[0] = "msgbox \"Hi Hello Namasakr\"";
//            sampleProgram.methods[0] = mainMethod;
//            System.out.println("jsh.load.ProgramLoader.main() " + sampleProgram.toString());
//            YamlWriter yamlWriter = new YamlWriter(new FileWriter(new File("sample.jsh")));
//            yamlWriter.write(sampleProgram);
//            yamlWriter.close();
//        } catch (IOException ex) {
//            Logger.getLogger(ProgramLoader.class.getName()).log(Level.SEVERE, null, ex);
//        }
        new ProgramLoader().loadProgram("sample.jsh");
    }
    
}
