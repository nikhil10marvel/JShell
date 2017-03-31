package jshell;

import util.CoderEdit;
import asg.cliche.ShellFactory;
import asg.cliche.Command;
import asg.cliche.ConsoleIO;
import asg.cliche.Param;
import asg.cliche.Shell;
import java.awt.AWTEventMulticaster;
import java.io.IOException;
import javax.swing.JFrame;
import java.awt.FlowLayout;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.Formatter;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Button;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import java.awt.Label;
import javax.swing.JFileChooser;
import java.awt.Menu;
import java.awt.MenuBar;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.KeyEvent;
import static java.lang.System.gc;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import jsh.load.ProgramLoader;
import util.Browser;
import util.InstallMaker;
import util.Zipper;

public class JShell extends JFrame {

    //Global elements
    JFrame frame;
    Formatter x;
    Scanner fx;
    public String strinput;
    public int intinput;
    public double doubleinput;
    public float floatinput;
    String filecontent;
    FlowLayout FlowLayout = new FlowLayout();
    JTextField txtfield;
    JFileChooser chooser;
    MenuBar menubar;
    Menu menu;

    //Global vars
    protected final int BUILD_ID = 1;
    private boolean ispyint;
    private boolean isPyint;
    private Browser browser;
    public static Shell shell;

    @Command(description = "adds numbers")
    public int add(int a, int b) {
        return a + b;
    }

    @Command()
    public String help() {
        String help = "This is Java Shell - JShell version 2.0 \nfor commands type ?list and for detailed information about a command type ?help <command-name>\n For information on gui elements type help-gui <element name>";
        return help;
    }

    @Command(description = "Help for gui making in JShell")
    public void helpGui(String elemnt) {
        if (elemnt.equals("button") | elemnt.equals("btn")) {
            System.out.println("Element:button\n"
                    + "Syntax:add-button Label <opt>width,<opt>hieght,<opt>x,<opt>y,<opt>command,<opt>commandParam\n"
                    + "Button is gui element which can be which users can interact with.<opt> stands for optional Parameters for adding a button\n"
                    + "The commands that can be given are -p,-r,-lx,-msg");
        } else if (elemnt.equals("txf") | elemnt.equals("TextField")) {
            System.out.println("Element:TextField\n"
                    + "Syntax:add-text-field width,hieght,x,y,<opt>TOOLTIP,<opt>DefaultText\n"
                    + "TextField is a gui element in which users can type text.<opt> stands for optional Parameters for adding a TextField\n"
                    + "Param:\n"
                    + " w - Width of the TextField\n"
                    + " h - Hieght of the TextField\n"
                    + " x & y - the x and y coordinates\n"
                    + " TOOLTIP: Thes text that appears on hover\n"
                    + " DefaultText - The Default Text that appears in the TextField");
        } else if (elemnt.equals("msgbox")) {
            System.out.println("The Msgbox command cretes and alert box that displays your Message\n"
                    + "Param:\n"
                    + " msg: The message to be dislpayed\n"
                    + " <opt>mode: The mode specifies the type of Alert Box\n"
                    + "     mode 1:Creates a simpple AlertBox with Ok button\n"
                    + "     mode 2:Cretaes an Alert Box with a text field in it\n"
                    + "     mode 3:Cretes an Alert Box with Yes or No button");
        } else if (elemnt.equals("xbutton") | elemnt.equals("xbtn")) {
            System.out.println("Element:xbutton\n"
                    + "Syntax:add-xbutton Label width,hieght,x,y,<opt>command,<opt>commandParam\n"
                    + "Button is gui element which can be which users can interact with.<opt> stands for optional Parameters for adding a XBUTTON\n"
                    + "The commands that can be given are -p,-r,-lx,-msg\n"
                    + "Xbutton is a more gui enhanced button that looks better fells better and is better\n");
        } else if (elemnt.equals("label") | elemnt.equals("lbl")) {
            System.out.println("Element:Label\n"
                    + "Syntax:add-label text,x,y,<opt>width,<opt>height\n"
                    + "Label is gui element which displays text.<opt> stands for optional Parameters for adding a labelcw \n"
                    + "Text: the text to be displayed");
        }//else if(elemnt.equals("FileOpenMsgbox"|elemnt.equals("fom"))){}
    }

    @Command(description = "subtracts numbers")
    public int sub(int a, int b) {
        return a - b;
    }

    @Command(description = "divides number")
    public int div(int a, int b) {
        return a / b;
    }

    @Command(description = "Reutrn reaminder of 2 numbers")
    public int modulus(int a, int b) {
        return a % b;
    }

    @Command(description = "Multiplies numbers")
    public int mul(int a, int b) {
        return a * b;
    }

    @Command(description = "calculates simple intrest")
    public double intrest(double a, double b, double c) {
        return a * b * c;
    }

    @Command(description = "creates a gui window.")
    public void createWindow(@Param(name = "title", description = "The title of the window") String title, @Param(name = "width", description = "The width of the window") int w, @Param(name = "height", description = "Height") int h, @Param(name = "visibility", description = "The visibility  of the window.Boolean value,Only true or false accepted") boolean visibility) {
        setTitle(title);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(w, h);
        setLayout(null);
        setVisible(visibility);
        setLocationRelativeTo(null);
    }

    @Command(description = "Adds a button")
    public void addButton(@Param(name = "label", description = "The text to appear on the button") String label) {
        try {
            Button butn = new Button(label);
            butn.setSize(40, 20);
            add(butn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(description = "Adds a button")
    public void addButton(@Param(name = "label", description = "The text to appear on the button") String label, @Param(name = "w", description = "Width of the button") int Width, @Param(name = "h", description = "Height of the button") int Height) {
        try {
            Button butn = new Button(label);
            int WIDTH = Width;
            int HEIGHT = Height;
            butn.setBounds(0, 0, WIDTH, HEIGHT);
            add(butn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(description = "adds a MenuBar")
    public void addMenuBar() {
        menubar = new MenuBar();
        setMenuBar(menubar);
    }

    @Command
    public void addMenu(String menutext) {
        menu = new Menu(menutext);
        menubar.add(menu);
    }

    @Command
    public void addMenuItem(String text) {
        MenuItem item = new MenuItem(text);
        menu.add(item);
    }

    @Command(name = "run-service", description = "Runs a built in service")
    public void rs(String servicename) {
        if (servicename.equals("games")) {
            Game game = new Game();
            game.Run();
        } else if (servicename.equals("gigaUI")) {
            gigaUI ui = new gigaUI();
            ui.Run();
            ui.exit();
        }
    }

    @Command(description = "Adds a button")
    public void addButton(@Param(name = "label", description = "The text to appear on the button") String label, @Param(name = "w", description = "Width of the button") int Width, @Param(name = "h", description = "Height of the button") int Height, int posx, int posy) {
        try {
            Button butn = new Button(label);
            int WIDTH = Width;
            int HEIGHT = Height;
            butn.setBounds(posx, posy, WIDTH, HEIGHT);
            add(butn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(description = "Adds a button with a command that executes when cilcked")
    public void addButton(@Param(name = "label", description = "The text to appear on the button") String label, @Param(name = "w", description = "Width of the button") int Width, @Param(name = "h", description = "Height of the button") int Height, int posx, int posy, String evt, String evtargs) {
        try {
            Button butn = new Button(label);
            int WIDTH = Width;
            int HEIGHT = Height;
            butn.setSize(WIDTH, HEIGHT);
            butn.setLocation(posx, posy);
            butn.addActionListener(e -> {
                switch (evt) {
                    case "-p":
                        System.out.println(evtargs);
                        break;

                    case "-r":
                        reader(evtargs);
                        break;

                    case "-lx":
                        lexer(evtargs);
                        break;

                    case "-msg":
                        msgbox(evtargs);

                    case "-ft":
                        Truncate(evtargs);
                        break;
                }
            });
            add(butn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(name = "add-xbutton", description = "Adds a enhaced button", abbrev = "axb")
    public void addXButton(@Param(name = "label", description = "The text to appear on the button") String label, @Param(name = "w", description = "Width of the button") int Width, @Param(name = "h", description = "Height of the button") int Height, int posx, int posy) {
        try {
            JButton butn = new JButton(label);
            int WIDTH = Width;
            int HEIGHT = Height;
            butn.setBounds(posx, posy, WIDTH, HEIGHT);
            add(butn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(name = "add-xtogglebutton", description = "Adds a enhaced Toggle button which has a on or off state", abbrev = "axtb")
    public void addXToggleButton(@Param(name = "label", description = "The text to appear on the button") String label, @Param(name = "w", description = "Width of the button") int Width, @Param(name = "h", description = "Height of the button") int Height, int posx, int posy) {
        try {
            JToggleButton butn = new JToggleButton(label);
            int WIDTH = Width;
            int HEIGHT = Height;
            butn.setBounds(posx, posy, WIDTH, HEIGHT);
            add(butn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(name = "add-xbutton", description = "adds an Enhaced Button", abbrev = "axb")
    public void addXButton(@Param(name = "label", description = "The text to appear on the button") String label, @Param(name = "w", description = "Width of the button") int Width, @Param(name = "h", description = "Height of the button") int Height, int posx, int posy, String event) {
        try {
            String[] evt = event.split(":");
            JButton butn = new JButton(label);
            int WIDTH = Width;
            int HEIGHT = Height;
            butn.setBounds(posx, posy, WIDTH, HEIGHT);
            butn.addActionListener(e -> {
                switch (evt[0]) {
                    case "-p":
                        System.out.println(evt[1]);
                        break;

                    case "-r":
                        reader(evt[1]);
                        break;
                    case "-lx":
                        lexer(evt[1]);
                        break;

                    case "-msg":
                        msgbox(evt[1]);
                        break;

                    case "-ft":
                        Truncate(evt[1]);
                        break;
                }
            });
            System.out.println(evt[0]);
            add(butn);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(description = "Adds a Text Field")
    public void addTextField(@Param(name = "w", description = "Width of the TextField") int Width, @Param(name = "h", description = "Height of the TextField") int Height, int posx, int posy) {
        try {
            txtfield = new JTextField();
            int WIDTH = Width;
            int HEIGHT = Height;
            txtfield.setSize(WIDTH, HEIGHT);
            txtfield.setLocation(posx, posy);
            add(txtfield);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(description = "Adds a TextFiled")
    public void addTextField(@Param(name = "w", description = "Width of the TextField") int Width, @Param(name = "h", description = "Height of the TextField") int Height, int posx, int posy, String DefaultText, String TOOLTIP) {
        try {
            txtfield = new JTextField();
            int WIDTH = Width;
            int HEIGHT = Height;
            txtfield.setSize(WIDTH, HEIGHT);
            txtfield.setLocation(posx, posy);
            txtfield.setText(DefaultText);
            txtfield.setToolTipText(TOOLTIP);
            add(txtfield);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(description = "Adds a TextField")
    public void addTextField(@Param(name = "w", description = "Width of the TextField") int Width, @Param(name = "h", description = "Height of the TextField") int Height, int posx, int posy, String TOOLTIP) {
        try {
            txtfield = new JTextField();
            int WIDTH = Width;
            int HEIGHT = Height;
            txtfield.setSize(WIDTH, HEIGHT);
            txtfield.setLocation(posx, posy);
            txtfield.setToolTipText(TOOLTIP);
            add(txtfield);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command
    public void FileOpenMsgbox(@Param(name = "ext", description = "the extension for the filter") String ext, @Param(name = "extdesc", description = "the description of your extension") String extdesc) {
        chooser = new JFileChooser(System.getProperty("user.dir"));
        chooser.setFileFilter(new FileNameExtensionFilter(extdesc, ext));
        int returnVal = chooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            System.out.println("You chose to open this file: " + file.getName());
            reader(file.getAbsolutePath());

        }

    }

    @Command
    public void FileOpenMsgbox(@Param(name = "ext", description = "the extension for the filter") String ext, @Param(name = "extdesc", description = "the description of your extension") String extdesc, @Param(name = "dir", description = "The start directory") String dir) {
        chooser = new JFileChooser(dir);
        chooser.setFileFilter(new FileNameExtensionFilter(extdesc, ext));
        int returnVal = chooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            System.out.println("You chose to open this file: " + file.getName());
            reader(file.getAbsolutePath());

        }

    }

    @Command
    public void FileSaveMsgbox(@Param(name = "ext", description = "the extension for the filter") String ext, @Param(name = "extdesc", description = "the description of your extension") String extdesc, @Param(name = "dir", description = "The start directory") String dir, String savetext) {
        chooser = new JFileChooser(dir);
        chooser.setFileFilter(new FileNameExtensionFilter(extdesc, ext));
        int returnVal = chooser.showOpenDialog(frame);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            System.out.println("You chose to open this file: " + file.getName());
            writeAppend(file.getAbsolutePath(), savetext);

        }

    }

    @Command
    public void addLabel(@Param(name = "text", description = "text to be displayed") String text, int posx, int posy, int w, int h) {
        Label label = new Label(text);
        label.setLocation(posx, posy);
        label.setSize(w, h);
        add(label);
    }

    @Command
    public void msgbox(String msg) {
        JOptionPane.showMessageDialog(null, msg);
    }
    
    @Command(name = "jsh_file", abbrev = "jshf", description = "Runs a .jsh program")
    public void runProgram(@Param(name = "file", description = "The .jsh file to execute")String file){
        ProgramLoader programLoader = new ProgramLoader();
        programLoader.loadProgram(file);
    }
    
    @Command(name = "jsh_url", abbrev = "jshu", description = "Runs a .jsh program from url given")
    public void runURL(@Param(name = "url",  description = "The url with the .jsh file to execute") String url){
        ProgramLoader programLoader = new ProgramLoader();
        programLoader.loadProgramURL(url);
    }
    
    @Command
    public void msgbox(String msg, String mode) {
        if (mode.equals("1")) {
            JOptionPane.showMessageDialog(null, msg);
        } else if (mode.equals("2")) {
            JOptionPane.showInputDialog(null, msg);
        } else if (mode.equals("3")) {
            JOptionPane.showConfirmDialog(null, msg);
        }
    }

    @Command
    public void msgbox(String msg, String mode, String title) {
        if (mode.equals("1")) {
            JOptionPane.showMessageDialog(null, msg, title, 1);
        } else if (mode.equals("2")) {
            JOptionPane.showInputDialog(null, msg, title, 2);
        } else if (mode.equals("3")) {
            JOptionPane.showConfirmDialog(null, msg, title, 2);
        }
    }

    @Command(name = "getTxt", description = "gets the text in the text in the textfield", abbrev = "gt")
    public String getText() {
        return txtfield.getText();
    }

    @Command
    public void addFlowLayout() {
        setLayout(FlowLayout);
    }

    @Command(description = "Sets the Size of the Window")
    public void SetWindowSize(int w, int h) {
        try {
            setSize(w, h);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command(description = "Sets the Title of the window")
    public void SetWindowTitle(@Param(name = "title", description = "The title of the window") String title) {
        try {
            setTitle(title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command
    public void ExitWindow() {
        try {
            setVisible(false);
            dispose();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Command
    public void SetWindowVisible(boolean visivility) {
        setVisible(visivility);
    }

    @Command(abbrev = "cwd", description = "Gets the directory where the .jar file is")
    public String currentWorkingDirectory() {
        return System.getProperty("user.dir");
    }

    @Command(description = "Writes to a file if there is no file a new file will be created.")
    public void writeAppend(@Param(name = "file", description = "The file to be writeen") String file, @Param(name = "text", description = "The text to be appended") String text) {
        try {
            String data = text;

            File wrtfl = new File(file);

            //if file doesnt exists, then create it
            if (!wrtfl.exists()) {
                System.out.println("File wasn't found created new file.");
                wrtfl.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(wrtfl.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.write(System.getProperty("line.separator"));
            bufferWritter.close();

            System.out.println("Done");

        } catch (Exception e) {
            System.out.println("Error Occured" + "ERR: " + e);
        }

    }

    public void copywriteer(String file, String text) {
        try {
            String data = text;

            File wrtfl = new File(file);

            //if file doesnt exists, then create it
            if (!wrtfl.exists()) {
                wrtfl.createNewFile();
            }

            //true = append file
            FileWriter fileWritter = new FileWriter(wrtfl.getName(), true);
            BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
            bufferWritter.write(data);
            bufferWritter.write(System.getProperty("line.separator"));
            bufferWritter.close();

        } catch (Exception e) {
            System.out.println("Error Occured" + "ERR: " + e);
        }

    }

    @Command(name = "giga", description = "A simple text editor.")
    public void nano(@Param(name = "file", description = "The file to be opened.If the file does not exsist a new one will be created") String file) {
        Scanner input = new Scanner(System.in);
        try {
            File nf = new File(file);
            if (!nf.exists()) {
                System.out.println("Created New File");
                nf.createNewFile();
            } else {
                reader(file);
            }

            int counter = 0;
            String data;
            while (counter == 0) {
                System.out.print("JShell-giga$ ");
                String cmd = input.nextLine();
                if (cmd.equals("quit-giga")) {
                    counter++;
                } else if (cmd.equals("rdcon")) {
                    reader(file);
                } else {
                    data = cmd;
                    FileWriter wrt = new FileWriter(nf.getName(), true);
                    BufferedWriter bfwrt = new BufferedWriter(wrt);
                    bfwrt.write(data);
                    bfwrt.write(System.getProperty("line.separator"));
                    bfwrt.close();
                }
            }
        } catch (Exception e) {
            System.out.println("ERR: " + e);
        }
    }

    @Command(description = "Outputs text to screen")
    public String print(String txt) throws IOException {
        return txt;

    }

    @Command(abbrev = "lx", description = "Reads the contents of a file and prints every word in a seperate line")
    public void lexer(@Param(name = "file", description = "File to be read.") String file) {
        try {
            fx = new Scanner(new File(file));
        } catch (Exception ex) {
            //Logger.getLogger(JShell.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Error Occured " + "ERR: " + ex);
        }

        while (fx.hasNext()) {
            String a = fx.next();
            System.out.printf("%s \n", a);
        }

        fx.close();
    }

    @Command(description = "Reads simple text from files as it is")
    public void reader(@Param(name = "file", description = "The file to be read from") String file) {
        String fileName = file;

        // This will reference one line at a time
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println(line);
            }

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + fileName + "' " + "ERR: " + ex);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Command(name = "file-truncate", description = "Truncates a fil.Wipes out all data in file", abbrev = "ft")
    public void Truncate(@Param(name = "file", description = "File to be truncated.") String file) {
        try {
            FileChannel outChan = new FileOutputStream(file).getChannel();
            outChan.truncate(0);
            outChan.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Error Occured: File not found");
        } catch (Exception e) {
            System.out.println("ERR: " + e);
        }
    }

    @Command(name = "keyIn", description = "Asks for input", abbrev = "ki")
    public void input(@Param(name = "mode", description = "What kind of input is to be asked.Modes:String -str,Integer -num") String mode, @Param(name = "Prompt", description = "The Prompt for the user") String Prompt) {
        try {
            Scanner key = new Scanner(System.in);
            if (mode.equals("-str")) {
                System.out.print(Prompt);
                String input = key.nextLine();
                this.strinput = input;
                System.out.println(strinput);
            } else if (mode.equals("-num")) {
                System.out.print(Prompt);
                int input = key.nextInt();
                this.intinput = input;
                System.out.println(intinput);
            } else if (mode.equals("-dc")) {
                System.out.print(Prompt);
                double input = key.nextDouble();
                this.doubleinput = input;
                System.out.println(intinput);
            } else if (mode.equals("-flt")) {
                System.out.print(Prompt);
                float input = key.nextFloat();
                this.floatinput = input;
                System.out.println(intinput);
            } else {
                System.out.println("ERR:Uknown mode");
            }
        } catch (Exception e) {
            System.out.println("ERROR OCCURED! ERR:" + e);
        }
    }

    @Command(abbrev = "-/", description = "Single Line Comment")
    public void com(String comment) {

    }

    @Command
    public void getVar(@Param(name = "var", description = "name of the Built-In Variable") String varname) {
        if (varname.equals("strvar")) {
            System.out.println(this.strinput);
        } else if (varname.equals("numvar")) {
            System.out.println(this.intinput);
        } else if (varname.equals("dcvar")) {
            System.out.println(this.doubleinput);
        } else if (varname.equals("fltvar")) {
            System.out.println(this.floatinput);
        } else {
            System.out.println("No built In Variable:" + varname);
        }
    }

    @Command(description = "Copies the file")
    public void Copy(@Param(name = "file", description = "The file to be copied") String file, @Param(name = "copyfile", description = "The name of the copy") String copyfile) {
        String fileName = file;

        // This will reference one line at a time
        String line = null;

        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while ((line = bufferedReader.readLine()) != null) {
                copywriteer(copyfile, line);
            }

            bufferedReader.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        System.out.println("Finished");
    }

    @Command(abbrev = "del", description = "Deletes Files")
    public void delete(@Param(name = "file", description = "The file to be deleted") String file) {
        File delfile = new File(file);
        delfile.delete();
        System.out.println(file + " Deleted");
    }

    @Command
    public void setVarStr(String value) {
        this.strinput = value;
    }

    @Command
    public void setVarNum(int value) {
        this.intinput = value;
    }

    @Command
    public void setVarDc(double value) {
        this.doubleinput = value;
    }

    @Command
    public void setVarFlt(float value) {
        this.floatinput = value;
    }

    @Command(abbrev = "cls")
    public void clear() throws IOException, InterruptedException {
        String os = System.getProperty("os.name");
        if (os.contains("Windows")) {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } else if (os.contains("Unix")) {
            System.out.print("\033[H\033[2J");
        }
    }

    @Command
    public void startGuiExtension(String classname) throws IOException {
        ProcessBuilder pb = new ProcessBuilder("java", "-jar", classname + ".jar");
        pb.directory(new File("ext\\"));
        Process p = pb.start();
    }

    @Command
    public void startExtension_win(String extension, String params) throws IOException {
        String cls = "./ext/" + extension + ".jar ";
        Runtime.getRuntime().exec("cmd /c start java -jar " + cls + "--run " + params);
    }

    @Command
    public void startExtension_lin(String extension, String params) throws IOException {
        String cls = "./ext/" + extension + ".jar";
        Runtime.getRuntime().exec("gnome-terminal -e \"bash -c 'java -jar " + extension + "--run " + params +"'\"");
    }
    
    @Command
    public void netutils(String program, String params) throws IOException{
        if(program.equals("SimpleSocketServer")) {
            String os = System.getProperty("os.name");
            String check = os.toLowerCase();
            System.out.println("OS:" + os);
            System.out.println(check);
            if(check.startsWith("win")){
                Runtime.getRuntime().exec("cmd /c start java -jar ./netutils/SocketExtension.jar --run true");
            }
        }
        if(program.equals("SimpleSocketClient")) {
            String os = System.getProperty("os.name");
            String check = os.toLowerCase();
            System.out.println("OS:" + os);
            if(check.startsWith("win")){
                Runtime.getRuntime().exec("cmd /c start java -jar netutils/SocketExtension.jar --run false " + params);
            }
        }
        if(program.equals("CommandSocketServer")) {
            String os = System.getProperty("os.name");
            String check = os.toLowerCase();
            System.out.println("OS:" + os);
            if(check.startsWith("win")){
                Runtime.getRuntime().exec("cmd /c start cmd-server.bat");
            }
        }
        if(program.equals("CommandSocketClient")) {
           String os = System.getProperty("os.name");
            String check = os.toLowerCase();
            System.out.println("OS:" + os);
            if(check.startsWith("win")){
                Runtime.getRuntime().exec("cmd /c start cmd-client.bat " + params);
            } 
        }
    }

    @Command(name = "start-PyInterp_win", abbrev = "spw", description = "Runs JShell python extensions.")
    public void startPyInterpWin(String file) throws IOException, URISyntaxException {
        if (!new File("./PyInterp/").exists()) {
            new File("./PyInterp/").createNewFile();
            System.out.println("PyInterp not found, downloading minimal PyInterp from \n"
                    + "");
            Scanner key = new Scanner(System.in);
            System.out.println("Confirm?[Y/N]");
            String res = key.nextLine();
            if (res.equals("y")) {
                fetchFile(res, file);
            } else {
                System.out.println("PyInterp not found, downloading directory PyInterp from \n"
                        + "");
                System.out.println("Confirm?[Y/N]");
                res = key.nextLine();
                if (res.equals("y")) {
                    fetchFile(res, file);

                }
            }
        }
        Runtime.getRuntime().exec("cmd /c start PyInterp/Py-int.exe " + file);
    }

    public void extractPyInt() {
        unzip("./JDOWNLOAD/", "./PYINTERP/");
    }

    public void extractExe(File file2) throws FileNotFoundException, IOException {
        URL url = this.getClass().getResource("\\src\\etc\\Py-int.exe");
        FileOutputStream output = new FileOutputStream(file2);
        InputStream input = url.openStream();
        byte[] buffer = new byte[4096];
        int bytesRead = input.read(buffer);
        while (bytesRead != -1) {
            output.write(buffer, 0, bytesRead);
            bytesRead = input.read(buffer);
        }
        output.close();
        input.close();
    }

    public void extractPYINTERP() {
        zip("./PyInterp", this.getClass().getResource("src\\etc\\PYINTERP.zip").toString());
    }

    public void startCoderEdit(String coderpth, String file) throws IOException {
        File files = new File(file);
        if (!files.exists()) {
            files.createNewFile();
        }
        new CoderEdit(coderpth, files.getAbsolutePath());
    }

    @Command
    public void mkdir(@Param(name = "path", description = "The Path of the New Directory") String path) {
        //try {
        //Runtime.getRuntime().exec("cmd /c cd " + parentdir);
        //Runtime.getRuntime().exec("cmd /c mkdir " + dirname);
        //} catch (IOException ex) {
        //Logger.getLogger(JShell.class.getName()).log(Level.SEVERE, null, ex);
        //}
        File index = new File(path);
        index.mkdir();
    }

    @Command
    public void rmdir(@Param(name = "path", description = "The Path of the Directory to be removed") String path) {
        //Runtime.getRuntime().exec("cmd /c cd " + parentname);
        //Runtime.getRuntime().exec("cmd /c rmdir " + dirname);
        File index = new File(path);
        String[] contents = index.list();
        for (String s : contents) {
            File currentFile = new File(index.getPath(), s);
            currentFile.delete();
        }
        index.delete();
    }

    @Command
    public void wait(@Param(name = "millis", description = "The milliseconds to delay") int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {
            Logger.getLogger(JShell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Command(name = "delay", abbrev = "d")
    public void wait2(@Param(name = "seconds", description = "Seconds to delay") int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(JShell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Command
    public void RunApp(String Appname) throws IOException {
        Runtime.getRuntime().exec("java " + Appname + " Run");
    }

    @Command
    public void sendEmail() {
        Scanner key = new Scanner(System.in);
        System.out.print("Mail Host: ");
        String smtp = key.nextLine();

        System.out.println("Port ");
        String port = key.nextLine();

        System.out.println("Username: ");
        String username = key.nextLine();

        System.out.println("Password ");
        String password = key.nextLine();

        System.out.println("To ");
        String to = key.nextLine();

        System.out.println("Subject ");
        String subject = key.nextLine();

        System.out.println("Message ");
        String message = key.nextLine();

        Email email = new Email(username, password, smtp, to, subject, message, port);
        email.sendEmail();
    }

    @Command(name = "zip-compress", abbrev = "zc", description = "compresses a directory's contents")
    public void zip(@Param(name = "directory", description = "directory to be zipped") String dir, @Param(name = "zipFile", description = "the Output Zip File") String fileName) {
        if (dir.equals(".")) {
            util.Zipper zip = new Zipper(currentWorkingDirectory());
            zip.makeZip(fileName + ".sc");
        } else {
            util.Zipper zip = new Zipper(dir);
            zip.makeZip(fileName + ".sc");
        }
    }

    @Command(name = "gen-inst-help")
    public void geninsthelp(String file) {
        writeAppend(file, "<html><head><title>INSTALLER HELP</title></head><body><p>&nbsp;</p>\n"
                + "<p>&nbsp;<img src=\"data:image/jpeg;base64,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\" alt=\"\" width=\"206\" height=\"206\" /></p>\n"
                + "<h1>JShell has Just Got A New Feature! The Installer Feature!</h1>\n"
                + "<p>This Brand New Feature of JShell is really good. This feature enables you to deploy your scripts, extensions or anything! With its installer</p>\n"
                + "<p>But Before you begin making your own Installer you must know the install config script. This script was designed to give users full control over their installer fle. This script enables you to Give your installer a license, description, script to execute after installation,ect.. and soon password protect your installer!</p>\n"
                + "<p>&nbsp;</p>\n"
                + "<p>So lets get started, below is syntax and some commands :-</p>\n"
                + "<p>1) [title] - sets the title of your installer</p>\n"
                + "<p>2) [desc] - the description of your installer</p>\n"
                + "<p>3) [license] - sets the license of your installer</p>\n"
                + "<p>4) [notes] - sets the notes of your Installer. Notes of an installer can be anything which include stuff like cautions, requirements, contact,etc</p>\n"
                + "<p>5) end; - ends the script</p>\n"
                + "<p>&nbsp;</p>\n"
                + "<p>Every command must be seperated by its param by a \":\". Ex:-</p>\n"
                + "<p>[title]:INSTALL PisumExt</p>\n"
                + "<p>[desc]:Passtime games</p>\n"
                + "<p>....</p>\n"
                + "<p>end;</p>\n"
                + "<p>Note: End does not have any parameters. There should not be any blank lines in the script. end command must be alone on a line</p>\n"
                + "<p>Command In JShell:</p>\n"
                + "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; making INSTALL: make-install/mk-inst yourzip.sc yourconfig.ic yourinstallername</p>\n"
                + "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; example: mk-inst HELLOGUI.sc Myconfig.ic HELLOGUIINSTALLER</p>\n"
                + "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; installing from file: install/inst yourinstaller.jsim outputdirectory</p>\n"
                + "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; example:on linux inst HELLOGUIINSTALLER.jsim /home/hellogui</p>\n"
                + "<p>&nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; on window inst HELLOGUIINSTALLER.jsim C:\\MY\\hellogui</p></body></html>");
    }

    @Command(name = "zip-decompress", abbrev = "zdc", description = "decompress a compressed folder")
    public void unzip(@Param(name = "zip", description = "zip to be decompressed") String zipfile, @Param(name = "directory", description = "the OUTPUT Directory") String dir) {
        util.Zipper zip = new Zipper();
        if (dir.equals(".")) {
            zip.decompresszip(zipfile, currentWorkingDirectory());
        } else {
            zip.decompresszip(zipfile, dir);
        }
    }

    @Command(name = "make-install", abbrev = "mk-inst", description = "Makes an Installer, this Installer needs A Zip which will contain its INSTALLATION Files and a Configuration file in .ic format")
    public void makeInstaller(@Param(name = "config_file", description = "A config file that has to be written in the .ic format with proper grammar.run command gen-inst-help for information on the format") String install_config, @Param(name = "files...", description = "These are the files that mut be installed. They must be in a zipped format(.SC/.zip recommended).This compressed folder will extract all its contents automaticly.") String zip, @Param(name = "name", description = "The name of the installer file") String installer_name) throws IOException {
        File f = new File("inc.conf");
        f.createNewFile();
        InstallMaker im = new InstallMaker(f, new File(zip), new File(install_config), installer_name);
    }

    @Command(name = "install", abbrev = "inst", description = "Installs files from .jsim (JShell Installatioin Module) files")
    public void install(@Param(name = "installer", description = "The INstaller file from which you want to install") String installer, @Param(name = "out_dir", description = "The Out Put Directory/ The Directory where you want the files to be installed") String out_dir) throws IOException {
        InstallMaker.install(installer, out_dir);
    }

    @Command(name = "fetch-file", abbrev = "ff", description = "download a file from specified url. The file will be in JDOWNLOAD folder")
    public void fetchFile(@Param(name = "URL", description = "The url of the file") String url, @Param(name = "filename", description = "the name of the downloaded file(the file on computer)") String filename) {
        if (!new File("./JDOWNLOAD").exists()) {
            new File("./JDOWNLOAD").mkdir();
        }
        try {
            URL web = new URL(url);
            ReadableByteChannel rbc = Channels.newChannel(web.openStream());
            File f = new File("./JDOWNLOAD/" + filename);
            FileOutputStream fos = new FileOutputStream(f);
            System.out.println("Downloading File " + filename + " from" + url);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            rbc.close();
            fos.close();
        } catch (MalformedURLException ex) {
            Logger.getLogger(JShell.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(JShell.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Command(name = "web-broweser", abbrev = "www", description = "Opens JShell Web Browser, with url specified")
    public void webBrowser(String start){
        browser = new Browser();
        browser.run(start);
    }

    public static void main(String[] params) throws IOException {
        try {
            Thread.sleep(4750);
        } catch (Exception e) {}
        JOptionPane.showMessageDialog(null, "Welcome");
        System.out.println("Welcome to JShell 2.0");
        shell = ShellFactory.createConsoleShell("JShell", "JShell", new JShell());
        shell.commandLoop();
        gc();
    }

}
