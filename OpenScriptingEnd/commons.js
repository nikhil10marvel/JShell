var RawTCPServer = type("ose.raw.RawTCPServer");
var RawTCPClient = type("ose.raw.RawTCPClient");
var AbstractRawTCPServer = Java.extend(RawTCPServer);
var AbstractRawTCPClient = Java.extend(RawTCPClient);
var File = type("java.io.File");
var Scanner = type("java.util.Scanner");
var Logger = type("java.util.logging.Logger");
var LoggingLevel = type("java.util.logging.Level");
var LOG_Info = LoggingLevel.INFO;
var LOG_Severe = LoggingLevel.SEVERE;
var LOG_Warning = LoggingLevel.WARNING;
var sys = new Object();
sys.out = java.lang.System.out;
sys.err = java.lang.System.err;
sys.in = java.lang.System.in;
var rootlogger = new Object();
rootlogger.info = function (msg) {
    getlogger("root").info(msg);
}
rootlogger.severe = function (msg){
    getlogger("root").severe(msg);
}
rootlogger.warn = function (msg){
    getlogger("root").warning(msg);
}

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

function log(level, msg){
    getlogger("root").log(level, msg);
}
