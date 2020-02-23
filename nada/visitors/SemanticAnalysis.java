package nada.visitors;
import nada.node.*;
import nada.analysis.*; // DepthFirstAdapter
import nada.*; // SymbolTable, SymbolEntry
import java.util.*; // LinkedList


public class SemanticAnalysis extends DepthFirstAdapter{

    private SymbolTable table;
    private boolean roleEnabled;
    private boolean printInfo;

    /**
     * 
     * @param roleEnabled whether role analysis is enabled
     * @param printInfo whether the info in a symbol table is printed out when the table is exited
     */
    public SemanticAnalysis(boolean roleEnabled, boolean printInfo){
        this.roleEnabled = roleEnabled;
        this.printInfo = printInfo;
        this.table = new SymbolTable(roleEnabled);
        // load in predefined identifiers
        // in nada, we only have one predefined identifier, INTEGER
        if(roleEnabled == true){
            this.table.enterSymbol(new SymbolEntry("INTEGER", SymbolEntry.TYPE));
        }
        else{
            this.table.enterSymbol(new SymbolEntry("INTEGER", SymbolEntry.NONE));
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
            if(! entry.roleBelongsTo(role)){
                // role inconsistency error
                System.out.println("Role error: [" + identifier.getLine() + "," +
                    identifier.getPos() + "] " + name + " should be of role " + 
                    SymbolEntry.roleToString(role) + ", not " + SymbolEntry.roleToString(entry.getRole()) + ".");
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
                identifier.getPos() + "] " + name + " is already declared.");
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
    


    // subprogram_spec = proc ident formal_part?;
    @Override
    public void inASubprogramSpec(ASubprogramSpec node) {
        TIdent identifier = node.getIdent();
        this.enterIdentifier(identifier, SymbolEntry.PROC);
        this.table.enterScope();
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


    // param_spec = ident_list colon out? ident;
    /**
     * examine whether this parameter specfication conforms to the semantic analysis rule
     * and if there are more than one identifiers labeled out, abort the program
     * if there is only one identifier labeled out, return true
     * else return false
     * @param node AParamSpec node
     */
    public boolean examineParamSpec(AParamSpec node) {
        TIdent identifier = node.getIdent();
        this.findIdentifier(identifier, SymbolEntry.TYPE);
        AIdentList identList = (AIdentList) node.getIdentList();
        LinkedList<TIdent> identifiers = this.getIdentifiers(identList);
        this.enterIdentifiers(identifiers, SymbolEntry.PARAM);
        // up to now, if everything goes fine, then check 'out' issue.
        if(node.getOut() == null){
            // no out, return false
            return false;
        }
        else{
            TOut out = node.getOut();
            // check the number of identifiers
            if(identifiers.size() != 1){
                System.out.println("Mode error: [" + out.getLine() + "," +
                    out.getPos() + "] More than one identifiers have out mode.");
                System.exit(1);
            }
            // out exists; return true
            return true;
        }
    }

    public void outStart(Start node) {
        this.table.exitScope(printInfo);
    }


    // formal_part = l_paren param_spec another_param_spec* r_paren;
    @Override
    public void caseAFormalPart(AFormalPart node) {
        AParamSpec paramSpec = (AParamSpec) node.getParamSpec();
        boolean hasOut = this.examineParamSpec(paramSpec);
        List<PAnotherParamSpec> anotherParamSpecs = node.getAnotherParamSpec();
        for(PAnotherParamSpec pAnotherParamSpec: anotherParamSpecs){
            paramSpec = (AParamSpec) ((AAnotherParamSpec)pAnotherParamSpec).getParamSpec();
            boolean anotherOut = this.examineParamSpec(paramSpec);
            if(hasOut == true && anotherOut == true){
                System.out.println("Mode error: [" + node.getLParen().getLine() +
                    "] More than one identifiers have out mode.");
                System.exit(1);
            }
            else{
                if(anotherOut == true){
                    hasOut = true;
                }
            }
        }
    }


    // assign_stmt = ident gets simple_expr semi;
    @Override
    public void outAAssignStmt(AAssignStmt node) {
        TIdent identifier = node.getIdent();
        this.findIdentifier(identifier, SymbolEntry.LHS);
    }

    @Override
    public void outAProcCallStmt(AProcCallStmt node) {
        TIdent identifier = node.getIdent();
        this.findIdentifier(identifier, SymbolEntry.PROC);
    }

    @Override
    public void inANamePrimary(ANamePrimary node) {
        TIdent identifier = node.getIdent();
        this.findIdentifier(identifier, SymbolEntry.RHS);
    }
}