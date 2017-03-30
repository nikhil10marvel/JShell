package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import jdk.jfr.events.FileWriteEvent;
import jshell.JShell;
import asg.cliche.ConsoleIO;

public class InstallMaker {
    
    private File install_config;
    private File install_zip;
    private File in_config;
    private FileReader fr;
    private BufferedReader br;
    private FileWriter fw;
    private Properties prop;

    protected List<String> cmds = new ArrayList<String>();
    private File ScriptFile;
    private Iterable<String> fileList;
    private String installer_name;

    public InstallMaker(File install_config, File install_zip, File in_config, String installer_name) throws IOException {
        this.install_config = install_config;
        this.install_zip = install_zip;
        this.in_config = in_config;
        this.installer_name = installer_name;
        parse();
        compress();
    }
    
    public InstallMaker(){}
    
    protected void parse() throws FileNotFoundException, IOException{
        fr = new FileReader(in_config);
        br = new BufferedReader(fr);
        prop = new Properties();
        FileReader reader = new FileReader(install_config);
        prop.load(reader);
        fw = new FileWriter(install_config);
        String line;
        String[] toki;
        boolean loop = true;
        JShell jsh = new JShell();
        
        while(loop){
            line = br.readLine();
            toki = line.split(":");
            if (line.startsWith("[title]:")) {
                prop.setProperty("title", toki[1]);
            } else if (line.startsWith("[desc]:")) {
                prop.setProperty("description", toki[1]);
            } else if (line.startsWith("[license]:")) {
                prop.setProperty("license", toki[1]);
            } else if (line.startsWith("[notes]:")) {
                prop.setProperty("notes", toki[1]);
            } else if (line.startsWith("(shell):")) {
                cmds.add(toki[1]);
            } else if (line.startsWith("end;")) {
                loop = false;
            }
        }
        ScriptFile = new File("scr_run.jshell");
        ScriptFile.createNewFile();
        for(int i=0;i<cmds.size();i++) {
            jsh.writeAppend(ScriptFile.getAbsolutePath(), cmds.get(i));
        }
        
        prop.setProperty("script", ScriptFile.getName());
        prop.setProperty("SC", install_zip.getName());
        prop.store(fw, "After Installation Script - Do not Edit");
        
        //closing readers and witers
        reader.close();
        fr.close();
        fw.close();
        br.close();
    }
    
    private void compress(){
        File f = new File(install_zip.getName() + "dir");
        System.out.println(f.mkdir());
        System.out.println(install_zip.renameTo(new File(f.getAbsolutePath() + "/" + install_zip.getName())));
        //System.out.println(in_config.renameTo(new File(f.getAbsolutePath() + "/" + in_config.getName())));
        System.out.println(install_config.renameTo(new File(f.getAbsolutePath() + "/" + installer_name + "_conf.conf")));
        System.out.println(ScriptFile.renameTo(new File(f.getAbsolutePath() + "/" + ScriptFile.getName())));
        Zipper z = new Zipper(f.getAbsolutePath());
        z.makeZip(this.installer_name + ".jsim");
    }
    
    public static void makeInstaller(){
        try {
            File ins_conf = new File("inc.conf");
            ins_conf.createNewFile();
            InstallMaker im = new InstallMaker(ins_conf, new File("File.sc"), new File("inc.ic"), "test_inst");
        } catch (IOException ex) {
            Logger.getLogger(InstallMaker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static void install(String installer, String out_dir) throws IOException{
        Zipper z = new Zipper();
        z.decompresszip(installer + ".jsim", out_dir);
        Properties proper = new Properties();
        FileReader reader = new FileReader(new File(out_dir + "/" + installer + "_conf.conf"));
        proper.load(reader);
        
        String license = proper.getProperty("license");
        String desc = proper.getProperty("description");
        String title = proper.getProperty("title");
        String notes = proper.getProperty("notes");
        String SC = out_dir + "/" + proper.getProperty("SC");
        String script = out_dir + "/" + proper.getProperty("script");
        Scanner key = new Scanner(System.in);
        
        System.out.println(title);
        System.out.println(desc);
        System.out.println(license);
        System.out.println("Do You Accept?[Y/N]");
        String res = key.nextLine();
        if(res.equals("Y") || res.equals("y")){
            z.decompresszip(SC, out_dir);
            
        } else {
            System.out.println("You Do not accept the license, therefore the Installation will not proceed");
        }
        new File(SC).delete();
        reader.close();
    }
    
    protected static void main(String[] args) throws IOException {
        //makeInstaller();
        //install("test_inst", "C:\\Tests-Folder-For-Programs\\TEST_JSIM");
    }
    
}
