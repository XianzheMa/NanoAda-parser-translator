package nada;
import nada.parser.*;
import nada.lexer.*;
import nada.visitors.*;
import nada.node.*;

import java.io.*;
import java.nio.file.*;

import static java.lang.System.*;
public class TerminalApp {
    public static void main(final String[] args) {
        if (args.length < 1) {
            out.println("USAGE");
            return;
        }
        System.out.println(Paths.get(args[0]).getParent().toString());
        try {
            final Lexer lexer = new Lexer(new PushbackReader(new BufferedReader(new FileReader(args[0])), 1024));
            final Parser parser = new Parser(lexer);
            final Start ast = parser.parse();
            ast.apply(new SemanticAnalysis(true, true));
            ast.apply(new CodeGeneration(args[0]));
        } catch (final Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        return;
    }
}