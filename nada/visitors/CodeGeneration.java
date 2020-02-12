package nada.visitors;
import java.io.*;
import java.util.*;
import java.nio.file.*; // Path
import nada.node.*;
import nada.analysis.*;


public class CodeGeneration extends DepthFirstAdapter{
    // only responsible for converting procedure to method code
    private class ProcedureWriter extends DepthFirstAdapter{
        private FileWriter writer;
        int indentLevel;
        ProcedureWriter(FileWriter writer, int indentLevel){
            this.writer = writer;
            this.indentLevel = indentLevel;
        }
    }


    private FileWriter writer;
    // convert all the procedures into private methods
    private LinkedList<ASubprogramBody> procedures = new LinkedList<ASubprogramBody>();
    private String className;
    private ProcedureWriter procedureWriter;
    int indentLevel = 0;

    public CodeGeneration(String AdaFileName){
        String JavaFileName = this.JavaFileName(AdaFileName);
        try {
            this.writer = new FileWriter(JavaFileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        this.procedureWriter = new ProcedureWriter(this.writer, this.indentLevel);
    }
    
    public static String getProcedureName(ASubprogramBody procedure){
        ASubprogramSpec spec = (ASubprogramSpec) procedure.getSubprogramSpec();
        return spec.getIdent().getText();
    }

    // return the corresponding Java file name
    // this method has a side effect: change this.className
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

    @Override
    public void inASubprogramBody(ASubprogramBody node) {
        this.procedures.add(node);
    }

    @Override
    public void outStart(Start node) {
        // begin our code generation
        try{
            this.writer.write("public class " + this.className +"{");
            this.indentLevel++;

            this.newLine(true);

            this.setUpMain();

            // generate code for each procedure
            Iterator<ASubprogramBody> iterator = this.procedures.iterator();
            while(iterator.hasNext()){
                this.newLine(true);
    
                iterator.next().apply(this.procedureWriter);
            }
            this.newLine(false);
            this.writer.write('}');
            this.writer.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void setUpMain() throws IOException{
        this.writer.write("public static void main(final String[] args) {");
        this.indentLevel++;
        this.newLine(true);

        ASubprogramBody outMostProc = this.procedures.getFirst();
        String procedureName = getProcedureName(outMostProc);
        this.writer.write(procedureName + "();");
        this.indentLevel--;
        this.newLine(true);

        this.writer.write('}');
        this.newLine(true);
    }


    private void newLine(boolean indentation) throws IOException {
        this.writer.write('\n');
        if(indentation){
            for(int i = 0; i < this.indentLevel; i++){
                this.writer.write('\t');
            }
        }
    }
}