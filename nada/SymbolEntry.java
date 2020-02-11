package nada;

public class SymbolEntry extends Object{

    // NONE is used when only doing scope analysis
    // When role analysis is activated, NONE shouldn't be used anywhere
    public static final int NONE = 0;
    public static final int CONST = 1;
    public static final int PARAM = 2;
    public static final int PROC = 3;
    public static final int TYPE = 4;
    public static final int VAR = 5;
    // left hand side: PARAM or VAR
    public static final int LHS = 6;
    // right hand side: CONST, PARAM or VAR
    public static final int RHS = 7;

    // name is just the identifier name
    private final String name;
    // role should be one of the five listed above
    private final int role;

    // warning: give an undefined role will trigger undefined behavior
    // id should be case sensitive
    public SymbolEntry(final String id, final int role) {
        this.name = id;
        this.role = role;
    }

    public String toString(final boolean roleEnabled) {
        if (roleEnabled == true){
           return "Name: " + this.name + " " + "Role: " + roleToString(this.role);
        }
        else{
           return "Name: " + this.name;
        }
    }

    @Override
    public String toString(){
        return this.toString(false);
    }

    public String getName(){
        return this.name;
    }

    public int getRole(){
        return this.role;
    }

    static public String roleToString(int role){
        String s = "";
        switch (role){
            case NONE:  s = "None";      break;
            case CONST: s = "CONSTANT";  break;
            case PARAM: s = "PARAMETER"; break;
            case PROC:  s = "PROCEDURE"; break;
            case TYPE:  s = "TYPE";      break;
            case VAR:   s = "VARIABLE";  break;
            case LHS:   s = "PARAM or VAR"; break;
            case RHS:   s = "CONST, PARAM, or VAR"; break;
            default:    s = "None";
        }
        return s;
    }

}