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
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import jshell.JShell;

public class QInstall {

    private Properties prop;
    private BufferedReader bufferredReader;
    private boolean psswd;
    
    protected List<String> dirs = new ArrayList<String>();
    protected List<String> cmds = new ArrayList<String>();
    private List<File> filelist = new ArrayList<File>();

    public QInstall() {
    }
    
    protected void genQuickInstallFile(String pathname, String inFile) throws FileNotFoundException, IOException{
        new File(pathname).createNewFile();
        prop = new Properties();
        FileReader fr = new FileReader(new File(pathname));
        prop.load(fr);
        FileWriter fw = new FileWriter(new File(pathname));
        FileReader reader = new FileReader(new File(inFile));
        bufferredReader = new BufferedReader(reader);
        String line;
        String[] token;
        while (true) {            
            line = bufferredReader.readLine();
            token = line.split(":");
            if (line.startsWith("[title]:")) {
                prop.setProperty("title", token[1]);
            } else if (line.startsWith("[type]:")) {
                prop.setProperty("type", token[1]);
                if(token[1].equals("enc")){
                    psswd = true;
                }
            } else if (line.startsWith("[div-info]:")){
                prop.setProperty("created by:", token[1]);
                prop.setProperty("description", token[2]);
                prop.setProperty("license", "OPEN PROJECT LICENSE!");
                if(psswd){
                    prop.setProperty("psswd", token[3]);
                    prop.setProperty("notes", token[4] + "\n\n" + "Deployed by JShell QInstall");
                } else {
                    prop.setProperty("notes", token[3] + "\n\n" + "Deployed by JShell QInstall");
                }
            } else if (line.startsWith("[dir]:")){
                dirs.add(token[1]);
            } else if (line.startsWith("N[cmd]:")){
                cmds.add(token[1]);
            } else if (line.contains("end;")){
                break;
            }
        }
        
        genLists();
        
        JShell jsh = new JShell();
        int i;
        for (i = 0; i < cmds.size(); i++) {
            String get = cmds.get(i);
            jsh.writeAppend("script_run.jshell", get);
        }
        prop.setProperty("no of files", Integer.toString(i));
        prop.store(fw, "JShell QInstall Configuration File");
        fw.close();
        fr.close();
        bufferredReader.close();
        reader.close();
    }
    
    protected void makeInstall(String config, String installer) throws FileNotFoundException, IOException{
        prop = new Properties();
        FileReader fr = new FileReader(new File(config));
        prop.load(fr);
        
        int i = Integer.parseInt(prop.getProperty("no of files"));
        for (int x = 0; x < i; x++) {
            String s = prop.getProperty("file" + x);
            filelist.add(new File(s));
        }
        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream(new File(installer));
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("Output to installer : " + installer);

            for(File file : this.filelist){

                    System.out.println("File Added : " + file.getName());
                    ZipEntry ze= new ZipEntry(file.getAbsolutePath());
                    zos.putNextEntry(ze);

                    FileInputStream in =
                           new FileInputStream(file.getAbsoluteFile());

                    int len;
                    while ((len = in.read(buffer)) > 0) {
                            zos.write(buffer, 0, len);
                    }

                    in.close();
            }

            zos.closeEntry();
            //remember close it
            zos.close();

    	System.out.println("Done");
        }catch(IOException ex){
           ex.printStackTrace();
        }
        fr.close();
    }
    
    protected void install(){
        //Getting Vars
        prop = new Properties();
        String title = prop.getProperty("title");
        String type = prop.getProperty("type");
        String password;
        String creator = prop.getProperty("created by:");
        String desc = prop.getProperty("description");
        String notes = prop.getProperty("notes");
    }
    
    public static void main(String[] args) throws IOException {
        QInstall qi = new QInstall();
        qi.genQuickInstallFile(args[1] + "_conf.conf", args[0]);
        qi.makeInstall(args[1] + "_conf.conf", args[1]);
    }
    
    private void genLists(){
        for (int i=0; i<dirs.size(); i++) {
            File ind = new File(dirs.get(i));
            String[] list = ind.list();
            System.out.println(list);
            int x = 0;
            /* (String s: list) {
                File f = new File(s);
                if(f.isFile()){
                    prop.setProperty("file" + x, f.getPath());
                } else if (f.isDirectory()){
                    dirs.add(s);
                }
            }*/
        }
    }
}
