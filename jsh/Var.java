package jsh;

import java.util.ArrayList;
import jsh.var_types.BoolVar;
import jsh.var_types.FloatVar;
import jsh.var_types.StingVar;

/**
 *
 * @author Nikhil
 */
public class Var {

    public ArrayList<FloatVar> floats = new ArrayList<FloatVar>();
    public ArrayList<StingVar> strings = new ArrayList<StingVar>();
    public ArrayList<BoolVar> bools = new ArrayList<BoolVar>();
    
    public boolean FindBool(String name){
        boolean boole = false;
        for (BoolVar bool : bools) {
            if(bool.varname.equals(name)){
                 boole = bool.value;
            }
        }
        return boole;
    }
    
    public float FindFloat(String name){
        float afloat = 0f;
        for (FloatVar flt : floats) {
            if(flt.varname.equals(name)){
                 afloat = flt.value;
            }
        }
        return afloat;
    }
    
    public String FindString(String name){
        String str = null;
        for (StingVar sting : strings) {
            if(sting.varname.equals(name)){
                 str = sting.value;
            }
        }
        return str;
    }
    
}
