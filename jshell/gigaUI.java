
package jshell;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class gigaUI extends javax.swing.JFrame {

    /**
     * Creates new form gigaUI
     */
    
    String FileName;
    static String font = "Monospaced";
    static int fontSize = 16;
    static boolean Bold = false,Italic = false,Regular = false;
    
    public gigaUI() {
        try {
            makeconfigFile();
            setProperties("config\\gigaUI-conf.jconf");
        } catch (IOException ex) {
            Logger.getLogger(gigaUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        initComponents();
        setTitle("GIGA UI");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("TextEdit.jpg")));
        setLocationRelativeTo(null);
        WorkArea.setFont(new Font(font, Font.ITALIC, fontSize));
        /*if(Italic){WorkArea.setFont(new Font(font, Font.ITALIC, fontSize));}
        if(Bold){WorkArea.setFont(new Font(font, Font.BOLD, fontSize));}
        if(Regular){WorkArea.setFont(new Font(font, Font.PLAIN, fontSize));}*/
    }

    private String read(String file){

        String line = null;
        StringBuilder sb = new StringBuilder();
        
        try {
            FileReader fileReader = new FileReader(file);
            this.FileName = file;

            // Always wrap FileReader in BufferedReader.
            BufferedReader bufferedReader = 
                new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }   

            // Always close files.
            bufferedReader.close();         
        }
        catch(FileNotFoundException ex) {
            System.out.println(
                "Unable to open file '" + 
                file + "'");                
        }
        catch(IOException ex) {                 
             ex.printStackTrace();
        }
        return sb.toString();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {

        //Variables init
        popupMenu1 = new java.awt.PopupMenu();
        WorkArea = new java.awt.TextArea();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        OpenItem = new javax.swing.JMenuItem();
        SaveItem = new javax.swing.JMenuItem();
        NewFileItem = new javax.swing.JMenuItem();
        fontMenu = new javax.swing.JMenu();
        FontSize = new javax.swing.JMenuItem();
        TextFont = new javax.swing.JMenuItem();

        popupMenu1.setLabel("popupMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        //Menus
        jMenu1.setText("File");
        fontMenu.setText("Font");

        OpenItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        OpenItem.setText("Open..");
        OpenItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                OpenItemActionPerformed(evt);
            }
        });
        jMenu1.add(OpenItem);

        SaveItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        SaveItem.setText("Save");
        SaveItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveItemActionPerformed(evt);
            }
        });
        jMenu1.add(SaveItem);

        NewFileItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N, java.awt.event.InputEvent.CTRL_MASK));
        NewFileItem.setText("New..");
        NewFileItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewFileItemActionPerformed(evt);
            }
        });
        jMenu1.add(NewFileItem);
        
        FontSize.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        FontSize.setText("FontSize..");
        FontSize.addActionListener(e ->{
            fontSize = Integer.parseInt(JOptionPane.showInputDialog(rootPane, "FontSize:"));
            WorkArea.setFont(new Font(font, Font.PLAIN, fontSize));
        });
        fontMenu.add(FontSize);

        //Adding menus to menubar
        jMenuBar1.add(jMenu1);
        jMenuBar1.add(fontMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WorkArea, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(WorkArea, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
        );

        pack();
    }

    private void OpenItemActionPerformed(java.awt.event.ActionEvent evt) {
        JFileChooser chooser = new JFileChooser();
        int returnval = chooser.showOpenDialog(null);
        if(returnval == JFileChooser.APPROVE_OPTION){
           String cont = read(chooser.getSelectedFile().toString());
           WorkArea.setText(cont.toString());
        }
    }

    private void SaveItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveItemActionPerformed
        // The name of the file to open.
        String fileName = FileName;

        try {
            FileWriter fileWriter = new FileWriter(fileName);

            // Always wrap FileWriter in BufferedWriter.
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            bufferedWriter.write(WorkArea.getText());

            // Always close files.
            bufferedWriter.close();
        }
        catch(IOException ex) {
            System.out.println(
                "Error writing to file '"
                + fileName + "'");
        }
    }//GEN-LAST:event_SaveItemActionPerformed

    private void NewFileItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewFileItemActionPerformed
       String filedir = JOptionPane.showInputDialog(rootPane, "File Directory", "New...", 3);
       String filename = JOptionPane.showInputDialog(rootPane, "File Name", "New...", 3);
       String fileext = JOptionPane.showInputDialog(rootPane, "File Extension", "New...", 3);
       
       try{
           File file  = new File(filedir + "\\" + filename + "." + fileext);
           file.createNewFile();
           FileName = file.toString();
       }catch(Exception e){
           String Error = "Error Occured: " + e;
           JOptionPane.showMessageDialog(rootPane, Error, "Error Occured", 2);
       } 
    }//GEN-LAST:event_NewFileItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Nimbus Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(gigaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gigaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gigaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gigaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gigaUI().setVisible(true);
            }
        });
    }
    
    public static void Run() {
        //<editor-fold defaultstate="collapsed" desc=" Nimbus Look and Feel ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(gigaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(gigaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(gigaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(gigaUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new gigaUI().setVisible(true);
            }
        });
    }

    // Variables declaration 
    private javax.swing.JMenuItem NewFileItem;
    private javax.swing.JMenuItem OpenItem;
    private javax.swing.JMenuItem SaveItem;
    private java.awt.TextArea WorkArea;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu fontMenu;
    private javax.swing.JMenuItem FontSize;
    private javax.swing.JMenuItem TextFont;
    private javax.swing.JMenuBar jMenuBar1;
    private java.awt.PopupMenu popupMenu1;
    // End of variables declaration
    
    public void exit(){
        try {
            Properties prop = new Properties();
            prop.setProperty("defontsize", String.valueOf(fontSize));
            FileWriter writer  = new FileWriter(new File("config\\gigaUI-conf.jconf"));
            prop.store(writer, null);
            writer.close();
            System.out.println(fontSize);
        } catch (IOException ex) {
            Logger.getLogger(gigaUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        dispose();
    }
    
    private void makeconfigFile() throws IOException{
        File file = new File("config\\gigaUI-conf.jconf");
        if(file.exists()){/*throw new FileAlreadyExistsException("config\\gigaUI-conf.jconf");*/}
        else{file.createNewFile();}
        FileWriter writer = new FileWriter(file);
        Properties prop = new Properties();
        prop.setProperty("defontsize", String.valueOf(fontSize));
        prop.setProperty("defont", font);
        prop.setProperty("bold?", String.valueOf(Bold));
        prop.store(writer, "File:gigaUI-conf.jconf\n gigaUI configurations");
        writer.close();
    }
    
    private void setProperties(String filename){
        FileReader reader = null;
        try {
            File file = new File("config\\gigaUI-conf.jconf");
            reader = new FileReader(file);
            Properties prop = new Properties();
            prop.load(reader);
            font = prop.getProperty("defont");
            fontSize = Integer.valueOf(prop.getProperty("defontsize"));
            Bold = Boolean.valueOf(prop.getProperty("bold?"));
            reader.close();
            System.out.println(fontSize);
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(gigaUI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(gigaUI.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                reader.close();
            } catch (IOException ex) {
                Logger.getLogger(gigaUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
       
    }
}
