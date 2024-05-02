import asint.SintaxisAbstractaTiny.*;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ConstructorASTsTiny;
import c_ast_descendente.ConstructorASTsTinyDJ;
import evaluador.recursivo.ImpresorRecursivo;
import evaluador.visitante.Impresion;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainPrincipal {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: java Main <archivo> <op> <opp>");
            System.exit(1);
        }

        String archivo = args[0];
        String op = args[1]; // Indica el constructor de ASTs
        String opp = args[2]; // Estilo de procesamiento

        FileInputStream fis = new FileInputStream(archivo);
        Prog prog;
        
        if (op.equals("asc")) {
            Reader input = new InputStreamReader(fis);
            AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
            c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTinyDJ(alex);

            System.out.println("CONSTRUCCION AST ASCENDENTE");
            prog = (Prog)asint.debug_parse().value;
        }
        else if (op.equals("desc")) {
            ConstructorASTsTiny asint = new ConstructorASTsTinyDJ(new InputStreamReader(fis));
            asint.disable_tracing();
            System.out.println("CONSTRUCCION AST DESCENDENTE");
            prog = asint.analiza();

        } else {
            System.err.println("Constructor de ASTs no válido");
            return;
        }
        
        // Impresion del procesamiento seleccionado
        if (opp.equals("rec")) {
            System.out.println("IMPRESION RECURSIVA");
            new ImpresorRecursivo().muestraPrograma(prog);
        }
        else if (opp.equals("int")) {
            System.out.println("IMPRESION INTERPRETE");
            prog.imprime();
        }
        else if (opp.equals("vis")) {
            System.out.println("IMPRESION VISITANTE");
            prog.procesa(new Impresion());
        }
    }
}
