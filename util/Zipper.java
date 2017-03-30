package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author NIKHIL
 * This is an extra creation available 
 * only for the complete version(For Now)
 */
public class Zipper {
    
    private List<String> fileList = new ArrayList<String>();
    private String SOURCE_FOLDER;
    
    private void init(String in){
        this.SOURCE_FOLDER = in;
    }
    
    public Zipper(String folder){
        init(folder);
    }
    
    public Zipper(){}
    
    private void zipIt(String zipFile){
        byte[] buffer = new byte[1024];

        try{

            FileOutputStream fos = new FileOutputStream(zipFile);
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);

            for(String file : this.fileList){

                    System.out.println("File Added : " + file);
                    ZipEntry ze= new ZipEntry(file);
                    zos.putNextEntry(ze);

                    FileInputStream in =
                           new FileInputStream(SOURCE_FOLDER + File.separator + file);

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
    }
    
    private void generateFileList(File node){

	if(node.isFile()){
		fileList.add(generateZipEntry(node.getAbsoluteFile().toString()));
	}

	if(node.isDirectory()){
		String[] subNote = node.list();
		for(String filename : subNote){
			generateFileList(new File(node, filename));
		}
	}

    }

    /**
     * Format the file path for zip
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file){
    	return file.substring(SOURCE_FOLDER.length()+1, file.length());
    }
    
    public void makeZip(String zipfile){
        generateFileList(new File(SOURCE_FOLDER));
        zipIt(zipfile);
    }
    
    private void unzipIt(String zipfile, String out_dir) throws FileNotFoundException, IOException{
        byte[] buffer = new byte[1024];

        try{

           File folder = new File(out_dir);
           if(!folder.exists()){
                   folder.mkdir();
           }

           ZipInputStream zis = new ZipInputStream(new FileInputStream(zipfile));
           ZipEntry ze = zis.getNextEntry();

           while(ze!=null){

              String fileName = ze.getName();
              File newFile = new File(out_dir + File.separator + fileName);

              System.out.println("file unzip : "+ newFile.getAbsoluteFile());
               new File(newFile.getParent()).mkdirs();
               FileOutputStream fos = new FileOutputStream(newFile);

               int len;
               while ((len = zis.read(buffer)) > 0) {
                   fos.write(buffer, 0, len);
               }

               fos.close();
               ze = zis.getNextEntry();
           }

           zis.closeEntry();
           zis.close();

           System.out.println("Done");

       }catch(IOException ex){
          ex.printStackTrace();
       }
    }
    
    public void decompresszip(String zip, String out){
        try {
            unzipIt(zip,out);
        } catch (Exception ex) {
            System.out.println("error occured:" + ex.getMessage());
        }
    }
}
