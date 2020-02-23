package nada;
import nada.parser.*;
import nada.lexer.*;
import nada.visitors.*;
import nada.node.*;

import java.io.*;
import java.nio.file.*;

import static java.lang.System.*;
public class Nada {
    public static void main(final String[] args) {
        if (args.length < 1) {
            out.println("USAGE: java nada.Nada <src file>.");
            out.println("<src file> is where you put the path of your nada source file.");
            return;
        }
        try {
            final Lexer lexer = new Lexer(new PushbackReader(new BufferedReader(new FileReader(args[0])), 1024));
            final Parser parser = new Parser(lexer);
            final Start ast = parser.parse();
            ast.apply(new SemanticAnalysis(true, false));
            ast.apply(new CodeGeneration(args[0]));
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return;
    }
}