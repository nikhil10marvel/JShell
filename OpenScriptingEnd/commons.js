var RawTCPServer = type("ose.raw.RawTCPServer");
var RawTCPClient = type("ose.raw.RawTCPClient");
var File = type("java.io.File");
var Scanner = type("java.util.Scanner");
var Logger = type("java.util.logging.Logger");
var LoggingLevel = type("java.util.logging.Level");

function type(program){
    return Java.type(program);
}
function extend(type, abstracts){
    return Java.extend(type, abstracts);
}

function getSuper(extender_instance){
    return Java.super(extender_instance);
}

function alert(msg){
    var JOptionPane = type("javax.swing.JOptionPane");
    JOptionPane.showMessageDialog(null, msg);
}

function input(prompt){
    var key = new Scanner(java.lang.System.in);
    java.lang.System.out.print(prompt);
    return key.nextLine();
}

function getlogger(name){
    return Logger.getLogger(name);
}
