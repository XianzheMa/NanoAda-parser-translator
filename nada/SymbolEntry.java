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


    // name is just the identifier name
    private String name;
    // role should be one of the five listed above
    private int role;

    // warning: give an undefined role will trigger undefined behavior
    // id should be case sensitive
    public SymbolEntry(String id, int role){
        this.name = id;
        this.role = role;
    }
 
    public String toString(boolean roleEnabled){
        if (roleEnabled == true){
           return "Name: " + this.name + " " + "Role: " + roleToString();
        }
        else{
           return "Name: " + this.name;
        }
    }

    @Override
    public String toString(){
        this.toString(false);
    }

    public String getName(){
        return this.name;
    }

    public int getRole(){
        return this.role;
    }

    private String roleToString(){
        String s = "";
        switch (role){
            case NONE:  s = "None";      break;
            case CONST: s = "CONSTANT";  break;
            case PARAM: s = "PARAMETER"; break;
            case PROC:  s = "PROCEDURE"; break;
            case TYPE:  s = "TYPE";      break;
            case VAR:   s = "VARIABLE";  break;
            default:    s = "None";
        }
        return s;
    }
}