package nada.visitors;
import java.io.*;
import java.util.*;
import java.nio.file.*; // Path
import nada.node.*;
import nada.analysis.*;


public class CodeGeneration extends DepthFirstAdapter{

    /**
     * only responsible for converting procedure to method code
     */
    private class ProcedureWriter extends DepthFirstAdapter{
        //private ReturnableChecker returnableChecker;
        public ProcedureWriter(){
            //returnableChecker = new ReturnableChecker();
        }

        /**
         * Will prepend "T" to all names
         * @param identList
         * @return
         */
        private LinkedList<String> getIdentNames(AIdentList identList){
            LinkedList<String> list = new LinkedList<String>();
            // add the first identifier
            list.add("T" + identList.getIdent().getText());
            LinkedList<PAnotherIdent> anotherList = identList.getAnotherIdent();
            Iterator<PAnotherIdent> iterator = anotherList.iterator();
            // add identifiers in the identifier list
            while(iterator.hasNext()){
                AAnotherIdent anotherIdent = (AAnotherIdent) iterator.next();
                list.add("T" + anotherIdent.getIdent().getText());
            }
            return list;
        }

        @Override
        public void caseASubprogramBody(ASubprogramBody node){
            this.write("private ");
            // see if it is returnable
            // node.apply(this.returnableChecker);
            // boolean returnable = this.returnableChecker.isReturnable();
            // if(returnable){
            //     this.write("int ");
            // }
            // else{
            //     this.write("void ");
            // }
            this.write("void ");
            node.getSubprogramSpec().apply(this);
            this.write(" {");
            CodeGeneration.this.beginNewLine(1);
            node.getDeclPart().apply(this);
            CodeGeneration.this.beginNewLine(0);
            node.getStmtSeq().apply(this);
            CodeGeneration.this.beginNewLine(-1);
            this.write("}");
        }

        @Override
        public void caseADeclPart(ADeclPart node) {
            List<PBasicDecl> copy = node.getBasicDecl();
            Iterator<PBasicDecl> iterator = copy.iterator();
            while(iterator.hasNext()){
                iterator.next().apply(this);
                if(iterator.hasNext()){
                    CodeGeneration.this.beginNewLine(0);
                }
            }
        }

        @Override
        public void caseASpbDeclBasicDecl(ASpbDeclBasicDecl node) {
            // do nothing
        }


        @Override
        public void caseAObjectDecl(AObjectDecl node) {
            // Nada only has integer type
            this.write("int ");
            // get names
            List<String> nameList = this.getIdentNames((AIdentList) node.getIdentList());
            Iterator<String> iterator = nameList.iterator();
            while(iterator.hasNext()){
                this.write(iterator.next());
                if(iterator.hasNext()){
                    this.write(", ");
                }
            }
            this.write(";");
        }

        @Override
        public void caseANumberDecl(ANumberDecl node) {
            this.write("int ");
            List<String> nameList = this.getIdentNames((AIdentList) node.getIdentList());
            Iterator<String> iterator = nameList.iterator();
            while(iterator.hasNext()){
                this.write(iterator.next());
                if(iterator.hasNext()){
                    this.write(", ");
                }
            }
            this.write(";");
            CodeGeneration.this.beginNewLine(0);
            // restart
            iterator = nameList.iterator();
            while(iterator.hasNext()){
                this.write(iterator.next() + " = ");
                
            }
            node.getSimpleExpr().apply(this);
            this.write(";");
        }


        @Override
        public void caseASubprogramSpec(ASubprogramSpec node) {
            String methodName = "M" + node.getIdent().getText();
            this.write(methodName);
            this.write("(");
            // since its optional, we extract parentheses out
            if(node.getFormalPart() != null){
                node.getFormalPart().apply(this);
            }
            this.write(")");
        }

        @Override
        public void caseAFormalPart(AFormalPart node) {
            node.getParamSpec().apply(this);
            List<PAnotherParamSpec> copy = node.getAnotherParamSpec();
            Iterator<PAnotherParamSpec> iterator = copy.iterator();
            while(iterator.hasNext()){
                this.write(", ");
                iterator.next().apply(this);
            }
        }

        // ignore out mode for now
        @Override
        public void caseAParamSpec(AParamSpec node) {
            List<String> listNames = this.getIdentNames((AIdentList) node.getIdentList());
            Iterator<String> iterator = listNames.iterator();
            while(iterator.hasNext()){
                this.write("int " + iterator.next());
                if(iterator.hasNext()){
                    this.write(", ");
                }
            }
        }

        @Override
        public void caseAStmtSeq(AStmtSeq node) {
            node.getStatement().apply(this);
            List<PStatement> stmts = node.getStatements();
            Iterator<PStatement> iterator = stmts.iterator();
            while(iterator.hasNext()){
                CodeGeneration.this.beginNewLine(0);
                iterator.next().apply(this);
            }
        }

        @Override
        public void caseANullStmt(ANullStmt node) {
            this.write(";");
        }

        @Override
        public void caseAAssignStmt(AAssignStmt node) {
            this.write("T" + node.getIdent().getText() + " = ");
            node.getSimpleExpr().apply(this);
            this.write(";");
        }

        @Override
        public void caseAWriteWriteStmt(AWriteWriteStmt node) {
            this.write("System.out.print(");
            if(node.getWriteExpr() != null){
                node.getWriteExpr().apply(this);
            }
            else{
                // an empty string
                this.write("\"\"");
            }
            this.write(");");
        }

        @Override
        public void caseAWritelnWriteStmt(AWritelnWriteStmt node) {
            this.write("System.out.println(");
            if(node.getWriteExpr() != null){
                node.getWriteExpr().apply(this);
            }
            else{
                // an empty string
                this.write("\"\"");
            }
            this.write(");");
        }

        @Override
        public void caseAIfStmt(AIfStmt node) {
            this.write("if(");
            node.getRelation().apply(this);
            this.write("){");
            CodeGeneration.this.beginNewLine(1);
            node.getStmtSeq().apply(this);
            CodeGeneration.this.beginNewLine(-1);
            this.write("}");
            CodeGeneration.this.beginNewLine(0);
            if(node.getElseifClause() != null){
                List<PElseifClause> elseifs = node.getElseifClause();
                Iterator<PElseifClause> iterator = elseifs.iterator();
                while(iterator.hasNext()){
                    iterator.next().apply(this);
                    if(iterator.hasNext()){
                        CodeGeneration.this.beginNewLine(0);
                    }
                }
            }
            CodeGeneration.this.beginNewLine(0);
            if(node.getElseClause() != null){
                node.getElseClause().apply(this);
            }
            // no semicolon needed
        }

        @Override
        public void caseAElseifClause(AElseifClause node) {
            this.write("else if(");
            node.getRelation().apply(this);
            this.write("){");
            CodeGeneration.this.beginNewLine(1);
            node.getStmtSeq().apply(this);
            CodeGeneration.this.beginNewLine(-1);
            this.write("}");
        }

        @Override
        public void caseAElseClause(AElseClause node) {
            this.write("else{");
            CodeGeneration.this.beginNewLine(1);
            node.getStmtSeq().apply(this);
            CodeGeneration.this.beginNewLine(-1);
            this.write("}");
        }

        @Override
        public void caseALoopStmt(ALoopStmt node) {
            this.write("while(");
            node.getRelation().apply(this);
            this.write("){");
            CodeGeneration.this.beginNewLine(1);
            node.getStmtSeq().apply(this);
            CodeGeneration.this.beginNewLine(-1);
            this.write("}");
        }

        @Override
        public void caseAProcCallStmt(AProcCallStmt node) {
            this.write("M" + node.getIdent().getText() + "(");
            if(node.getActualParamPart() != null){
                node.getActualParamPart().apply(this);
            }
            this.write(");");
        }

        @Override
        public void caseAActualParamPart(AActualParamPart node) {
            node.getSimpleExpr().apply(this);
            List<PAnotherParam> list = node.getAnotherParam();
            Iterator<PAnotherParam> iterator = list.iterator();
            while(iterator.hasNext()){
                this.write(", ");
                iterator.next().apply(this);
            }
        }

        @Override
        public void caseARelation(ARelation node) {
            node.getSimpleExpr().apply(this);
            this.write(" ");
            if(node.getRelationClause() != null){
                node.getRelationClause().apply(this);
            }
        }

        @Override
        public void caseARelationClause(ARelationClause node) {
            node.getRelOp().apply(this);
            this.write(" ");
            node.getSimpleExpr().apply(this);
        }

        @Override
        public void caseAStringLitWriteExpr(AStringLitWriteExpr node) {
            this.write(node.getStringLit().getText());
            // TODO: check if println(3) works
        }

        @Override
        public void caseANegPrimFactor(ANegPrimFactor node) {
            this.write("(-");
            node.getPrimary().apply(this);
            this.write(")");
        }
        
        @Override
        public void caseANumLitPrimary(ANumLitPrimary node) {
            this.write(node.getNumberLit().getText());
        }

        @Override
        public void caseANamePrimary(ANamePrimary node) {
            this.write("T" + node.getIdent().getText());
        }

        @Override
        public void caseAExprPrimary(AExprPrimary node) {
            this.write("(");
            node.getSimpleExpr().apply(this);
            this.write(")");
        }

        @Override
        public void caseAEqRelOp(AEqRelOp node) {
            this.write(" == ");
        }

        @Override
        public void caseANeqRelOp(ANeqRelOp node) {
            this.write(" != ");
        }

        @Override
        public void caseALtRelOp(ALtRelOp node) {
            this.write(" < ");
        }

        @Override
        public void caseALeRelOp(ALeRelOp node) {
            this.write(" <= ");
        }

        @Override
        public void caseAGtRelOp(AGtRelOp node) {
            this.write(" > ");
        }

        @Override
        public void caseAGeRelOp(AGeRelOp node) {
            this.write(" >= ");
        }

        @Override
        public void caseAPlusAddOp(APlusAddOp node) {
            this.write(" + ");
        }

        @Override
        public void caseAMinusAddOp(AMinusAddOp node) {
            this.write(" - ");
        }

        @Override
        public void caseAMultMulOp(AMultMulOp node) {
            this.write(" * ");
        }

        @Override
        public void caseADivMulOp(ADivMulOp node) {
            this.write(" / ");
        }

        @Override
        public void caseAModMulOp(AModMulOp node) {
            this.write(" % ");
        }
        /**
         * helps make other method not too verbose when writing Java code
         * @param Javacode the code needed to be printed out.
         */
        private void write(String Javacode){
            CodeGeneration.this.write(Javacode);
        }
    }

    private FileWriter writer;
    private ProcedureWriter procedureWriter;
    // convert all the procedures into private methods
    private LinkedList<ASubprogramBody> procedures = new LinkedList<ASubprogramBody>();
    private String className;
    int indentLevel;

    /**
     * constructor, initialize correctly all the fields it has
     * @param AdaFileName the path of the source nada code
     */
    public CodeGeneration(String AdaFileName){
        String JavaFileName = this.JavaFileName(AdaFileName);
        this.indentLevel = 0;
        this.procedureWriter = new ProcedureWriter();
        try {
            this.writer = new FileWriter(JavaFileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
    
    /**
     * get the procedure name, prepend an 'M' to it so as to avoid conflict with Java keyword
     * @param procedure
     * @return a string roughly equivalent to 'M' + procedure.toString()
     */
    public String getProcedureName(ASubprogramBody procedure){
        ASubprogramSpec spec = (ASubprogramSpec) procedure.getSubprogramSpec();
        return 'M' +  spec.getIdent().getText();
    }

    /**
     * return the corresponding Java file name.
     * Whether it is a relative path or a full path depends on the given wholePathName
     * Roughly speaking, it just replaces the file extension and capitalizes the first char.
     * This method has a side effect: change this.className
     * @param wholePathName
     * @return the corresponding Java filename
     */
    // TODO: test with nada code with no extension
    private String JavaFileName(String wholePathName){
        Path path = Paths.get(wholePathName);
        Path parentPath = path.getParent();
        String shortAdaName = path.getFileName().toString();
        int dotIndex = shortAdaName.lastIndexOf('.');
        if(dotIndex == -1){
            // the file does not end with an extension
            dotIndex = shortAdaName.length();
        }
    
        String firstLetter = shortAdaName.substring(0, 1).toUpperCase();
        this.className = firstLetter + shortAdaName.substring(1, dotIndex);
    
        return Paths.get(parentPath.toString(), this.className + ".java").toString();
    }


    /**
     * find a new procedure node, add it to this.procedures
     * @param node the newly found procedure node
     */
    @Override
    public void inASubprogramBody(ASubprogramBody node) {
        this.procedures.add(node);
    }

    /**
     * This is where the CodeGeneration actually starts generating code
     */
    @Override
    public void outStart(Start node) {
        // begin our code generation
        try{
            this.writer.write("public class " + this.className +"{");
            this.beginNewLine(1);

            this.setMain();

            // generate code for each procedure
            Iterator<ASubprogramBody> iterator = this.procedures.iterator();
            while(iterator.hasNext()){
            this.beginNewLine(0);
            // add an empty line to separate it from the previous method
            this.beginNewLine(0);
                iterator.next().apply(this.procedureWriter);
            }
            this.beginNewLine(-1);
            this.writer.write('}');
            this.writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    // write main method
    private void setMain() throws IOException{
        this.writer.write("public static void main(final String[] args) {");
        this.beginNewLine(1);

        ASubprogramBody outMostProc = this.procedures.getFirst();
        String procedureName = this.getProcedureName(outMostProc);
        this.writer.write(procedureName + "();");
        this.beginNewLine(-1);

        this.writer.write('}');
    }


    /**
     * First apply indendation change to this.indentLevel
     * Then begins a new line with the newly changed indentation level
     * @param indentationChange
     */
    private void beginNewLine(int indentationChange) {
        this.indentLevel += indentationChange;
        try {
            this.writer.write('\n');
            for(int i = 0; i < this.indentLevel; i++){
                this.writer.write('\t');
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * a utility method for procedureWriter, helping it to output source code and handle exception.
     * @param Javacode the Java code needed to be printed out
     */
    private void write(String Javacode){
        try {
            this.writer.write(Javacode);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}