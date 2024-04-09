import java.io.InputStreamReader;
import java.io.Reader;

import asint.SintaxisAbstractaTiny.*;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ConstructorASTsTiny;
import c_ast_descendente.ConstructorASTsTinyDJ;
import evaluador.recursivo.ImpresorRecursivo;
import evaluador.visitante.Impresion;

public class DomJudge {
    public static void main(String[] args) throws Exception {
        Reader input = new InputStreamReader(System.in);
        char c = (char) input.read();
        if (c == 'a') {
            AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
            c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTinyDJ(alex);

            System.out.println("CONSTRUCCION AST ASCENDENTE");
            Prog prog = (Prog)asint.debug_parse().value;

            System.out.println("IMPRESION RECURSIVA");
            new ImpresorRecursivo().muestraPrograma(prog);

            System.out.println("IMPRESION INTERPRETE");
            prog.imprime();

            System.out.println("IMPRESION VISITANTE");
            prog.procesa(new Impresion());
        }
        else {
            ConstructorASTsTiny asint = new ConstructorASTsTinyDJ(input);
            asint.disable_tracing();
            System.out.println("CONSTRUCCION AST DESCENDENTE");
            Prog prog = asint.analiza();

            System.out.println("IMPRESION RECURSIVA");
            new ImpresorRecursivo().muestraPrograma(prog);

            System.out.println("IMPRESION INTERPRETE");
            prog.imprime();

            System.out.println("IMPRESION VISITANTE");
            prog.procesa(new Impresion());
        }
    }
}
