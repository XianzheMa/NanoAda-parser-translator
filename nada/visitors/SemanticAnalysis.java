package nada.visitors;
import nada.node.*;
import nada.analysis.*; // DepthFirstAdapter
import nada.*; // SymbolTable, SymbolEntry
import java.util.*; // LinkedList

// TODO: ask why the case function checks if every part exists, even if it must exist.

public class SemanticAnalysis extends DepthFirstAdapter{
    private SymbolTable table;
    private boolean roleEnabled;
    private boolean printInfo;
    public SemanticAnalysis(boolean roleEnabled, boolean printInfo){
        this.roleEnabled = roleEnabled;
        this.printInfo = printInfo;
        this.table = new SymbolTable(roleEnabled);
        // load in predefined identifiers
        if(roleEnabled == true){
            this.table.enterSymbol(new SymbolEntry("BOOLEAN", SymbolEntry.TYPE));
            this.table.enterSymbol(new SymbolEntry("CHAR", SymbolEntry.TYPE));
            this.table.enterSymbol(new SymbolEntry("INTEGER", SymbolEntry.TYPE));
            this.table.enterSymbol(new SymbolEntry("TRUE", SymbolEntry.CONST));
            this.table.enterSymbol(new SymbolEntry("FALSE", SymbolEntry.CONST));
        }
        else{
            this.table.enterSymbol(new SymbolEntry("BOOLEAN", SymbolEntry.NONE));
            this.table.enterSymbol(new SymbolEntry("CHAR", SymbolEntry.NONE));
            this.table.enterSymbol(new SymbolEntry("INTEGER", SymbolEntry.NONE));
            this.table.enterSymbol(new SymbolEntry("TRUE", SymbolEntry.NONE));
            this.table.enterSymbol(new SymbolEntry("FALSE", SymbolEntry.NONE));
        }
    }

    // given an identifier node, check if it exists in the symbole table
    // If roleEnabled is set, the role would also be matched with the role recorded in the table
    // If an error happenes, it would print out info and exit the program
    public void findIdentifier(TIdent identifier, int role){
        String name = identifier.getText();
        SymbolEntry entry = this.table.findSymbol(name);
        if(entry == null){
            // not found
            System.out.println("Undeclaration error: [" + identifier.getLine() + "," +
                identifier.getPos() + "] Unknown identifier " + name + ".");
            System.exit(1);
        }

        if(this.roleEnabled){
            if(role != entry.getRole()){
                // role inconsistency error
                System.out.println("Type error: [" + identifier.getLine() + "," +
                    identifier.getPos() + "] " + name + "should be of type " + 
                    SymbolEntry.roleToString(role) + ".");
                System.exit(1);
            }
        }
    }

    // enter this identifier in the symbol table
    // if the identifier already exists
    // the program aborts and print an error
    public void enterIdentifier(TIdent identifier, int role){
        String name = identifier.getText();
        if(this.roleEnabled == false){
            role = SymbolEntry.NONE;
        }
        boolean result = this.table.enterSymbol(new SymbolEntry(name, role));
        if(result == false){
            System.out.println("Redeclaration error: [" + identifier.getLine() + "," +
                identifier.getPos() + "] " + name + "is already declared");
                System.exit(1);
        }
    }

    // similar usage as the previous one
    // enter a group of identifiers of the same role in the symbol table
    public void enterIdentifiers(LinkedList<TIdent> list, int role){
        Iterator<TIdent> iterator = list.iterator();
        while(iterator.hasNext()){
            this.enterIdentifier(iterator.next(), role);
        }
    }


    // identifier list only appears in declaration part
    // ident_list = ident another_ident*
    public LinkedList<TIdent> getIdentifiers(final AIdentList identList){
        // TODO: not so sure of the implementation. Test later
        LinkedList<TIdent> list = new LinkedList<TIdent>();
        // add the first identifier
        list.add(identList.getIdent());
        LinkedList<PAnotherIdent> anotherList = identList.getAnotherIdent();
        Iterator<PAnotherIdent> iterator = anotherList.iterator();
        // add identifiers in the identifier list
        while(iterator.hasNext()){
            AAnotherIdent anotherIdent = (AAnotherIdent) iterator.next();
            list.add(anotherIdent.getIdent());
        }
        return list;
    }

    // For scope analysis, we only need to enter a new scope at subprogram body.
    // We only need to exit a scope at the end of the whole process as well as subprogramBody.

    // subprogram_body = subprogram_spec is decl_part begin stmt_seq end ident? semi;
    @Override
    public void inASubprogramBody(ASubprogramBody node) {
        this.table.enterScope();
    }


    // subprogram_spec = proc ident formal_part?;
    @Override
    public void inASubprogramSpec(ASubprogramSpec node) {
        TIdent identifier = node.getIdent();
        this.enterIdentifier(identifier, SymbolEntry.PROC);
    }


    // subprogram_body = subprogram_spec is decl_part begin stmt_seq end ident? semi;
    @Override
    public void outASubprogramBody(ASubprogramBody node){
        this.table.exitScope(this.printInfo);
        TIdent identifier = node.getIdent();
        if(identifier != null){
            this.findIdentifier(identifier, SymbolEntry.PROC);
        }
    }

    // object_decl = ident_list colon ident semi;
    @Override
    public void outAObjectDecl(AObjectDecl node) {
        // TODO: ask the correct checking order
        TIdent identifier = node.getIdent();
        this.findIdentifier(identifier, SymbolEntry.TYPE);
        AIdentList identList = (AIdentList) node.getIdentList();
        this.enterIdentifiers(this.getIdentifiers(identList), SymbolEntry.VAR);
    }

    // number_decl = ident_list colon const gets simple_expr semi;
    @Override
    public void outANumberDecl(ANumberDecl node) {
        AIdentList identList = (AIdentList) node.getIdentList();
        this.enterIdentifiers(this.getIdentifiers(identList), SymbolEntry.CONST);
    }

    // enum_typedef = l_paren ident_list r_paren;
    @Override
    public void outAEnumTypedef(AEnumTypedef node) {
        // TODO: what is enumTypedef?
        AIdentList identList = (AIdentList) node.getIdentList();
        this.checkIdentifiers(this.getIdentifiers(identList), SymbolEntry.CONST);
    }

    
    public void outStart(Start node) {
        this.table.exitScope(printInfo);
    }
}