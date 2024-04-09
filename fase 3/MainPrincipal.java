import asint.SintaxisAbstractaTiny.*;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ConstructorASTsTiny;
import c_ast_descendente.ConstructorASTsTinyDJ;
import evaluador.recursivo.EvaluadorRecursivo;
import evaluador.visitante.Impresion;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainPrincipal {
    public static void main(String[] args) throws Exception {
        FileInputStream fis = new FileInputStream(args[0]);
        char c = (char) fis.read();
        if (c == 'a') {
            Reader input = new InputStreamReader(fis);
            AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
            c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTinyDJ(alex);

            System.out.println("CONSTRUCCION AST ASCENDENTE");
            Prog prog = (Prog)asint.debug_parse().value;

            System.out.println("IMPRESION RECURSIVA");
            new EvaluadorRecursivo().muestraPrograma(prog);

            System.out.println("IMPRESION INTERPRETE");
            prog.imprime();

            System.out.println("IMPRESION VISITANTE");
            prog.procesa(new Impresion());
        }
        else {
            ConstructorASTsTiny asint = new ConstructorASTsTinyDJ(new InputStreamReader(fis));
            asint.disable_tracing();
            System.out.println("CONSTRUCCION AST DESCENDENTE");
            Prog prog = asint.analiza();

            System.out.println("IMPRESION RECURSIVA");
            new EvaluadorRecursivo().muestraPrograma(prog);

            System.out.println("IMPRESION INTERPRETE");
            prog.imprime();

            System.out.println("IMPRESION VISITANTE");
            prog.procesa(new Impresion());
        }
    }
}
