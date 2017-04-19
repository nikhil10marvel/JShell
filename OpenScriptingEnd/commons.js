function type(pack){
    return Java.type(pack);
}
function extend(type, abstracts){
    return Java.extend(type, abstracts);
}
function getSuper(Extend){
    return Java.super(Extend);
}

var RawTCPServer = type("ose.raw.RawTCPServer");