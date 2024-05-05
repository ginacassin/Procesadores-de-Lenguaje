import asint.SintaxisAbstractaTiny.*;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ConstructorASTsTiny;
import c_ast_descendente.ConstructorASTsTinyDJ;
import evaluador.recursivo.ImpresorRecursivo;
import evaluador.visitante.Impresion;
import maquinaP.MaquinaP;
import visitantes.*;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainPrincipal {
    public static void main(String[] args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: java Main <archivo> <op>"); //op es desc o asc
            System.exit(1);
        }

        String archivo = args[0];
        String op = args[1]; // Indica el constructor de ASTs

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

        // Proceso de vinculacion
        prog.procesa(new VinculacionPrimera());

        // Proceso de comprobacion de tipos
        prog.procesa(new Tipado());

        // Proceso de asignacion de espacio
        prog.procesa(new AsignacionEspacioPrimera());

        MaquinaP maquinaP = new MaquinaP(1, 1, 1, 1);

        // Proceso etiquetado
        prog.procesa(new Etiquetado(maquinaP));

        // Proceso de generacion de codigo
        prog.procesa(new GeneracionCodigo(maquinaP));

        maquinaP.ejecuta();
    }
}
