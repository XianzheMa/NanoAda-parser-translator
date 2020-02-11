package nada;
import java.util.*;

public class SymbolTable {
    private Stack<Map<String, SymbolEntry>> stack;
    // since Stack is already equipped with method size(),
    // no need to define a separate variable level
    // simple_mode is used to just report name
    // complete_mode is used to report both name and role
    boolean roleEnabled;

    // constructor, pushes the first layer of map and loads predefined identifiers
    public SymbolTable(boolean roleEnabled){
        this.roleEnabled = roleEnabled;
        this.stack = new Stack<Map<String, SymbolEntry>>();
        HashMap<String, SymbolEntry> map = new HashMap<String, SymbolEntry>();
        this.stack.push(map);
    }


    // enter a new scope
    public void enterScope(){
        this.stack.push(new HashMap<String, SymbolEntry>());
    }

    // exit an existing scope
    // if exit successfully, return true
    // else, there is no map to exit, return false
    // printInfo controls whether it should print the info cantained in the map being popped
    public boolean exitScope(boolean printInfo){
        if(this.stack.empty()){
            // empty table, return false
            return false;
        }
        Map<String, SymbolEntry> map = this.stack.pop();
        if(printInfo){
            System.out.println("\nLevel " + this.stack.size() + ":");
            System.out.println("---------");
            for(SymbolEntry s : map.values()){
                System.out.println(s.toString(this.roleEnabled));
            }
        }
        return true;
    }

    // returns:
    // true iff successfully entered a SymbolEntry
    // false iff this identifier already exists in the topmost map
    public boolean enterSymbol(final SymbolEntry s){
        Map<String, SymbolEntry> map = this.stack.peek();
        if(map.containsKey(s.getName())){
            return false;
        }

        // insert it into the map
        map.put(s.getName(), s);
        return true;
    }

    // returns:
    // the SymbolEntry it can first get by searching the table one layer after another
    // if the SymbolEntry cannot be found, null is returned
    public SymbolEntry findSymbol(String name){
        for(int i = this.stack.size() - 1; i >= 0; i--){
            Map<String, SymbolEntry> map = this.stack.get(i);
            SymbolEntry s = map.get(name);
            if(s != null){
                return s;
            }
        }

        // it does not find the symbol
        // in this case, null is returned
        return null;
    }

    // return the level of the topmost map
    public int getLevel(){
        return stack.size() - 1;
    }
}