import asint.SintaxisAbstractaTiny.*;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ConstructorASTsTiny;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;

/*
public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 3) {
            System.err.println("Usage: java Main <archivo> <op> <opp>");
            System.exit(1);
        }

        String archivo = args[0];
        String op = args[1]; // Indica el constructor de ASTs
        String opp = args[2]; // Estilo de procesamiento

        Prog prog;

        if (op.equals("a") || op.equals("asc")) { // Ascendente
            System.out.println("CONSTRUCCION AST ASCENDENTE");
            Reader input = new InputStreamReader(new FileInputStream(archivo));
            AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
            c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTinyDJ(alex);
            prog = (Prog)asint.parse().value;

            if (opp.equals("rec")) { // Programación recursiva
                System.out.println("IMPRESION RECURSIVA");
                // TODO: reemplazar por el q corresponde
            } else if (opp.equals("int")) { // Patrón intérprete
                System.out.println("IMPRESION INTERPRETE");
                prog.imprime();
            } else if (opp.equals("vis")) { // Patrón visitante
                System.out.println("IMPRESION VISITANTE");
                Evaluacion evaluacion = new Evaluacion();
                prog.procesa(evaluacion);
                System.out.println(evaluacion.leeResul());
            } else {
                System.err.println("Estilo de procesamiento no válido");
            }
        } else if (op.equals("d") || op.equals("desc")){ // Descendente
            System.out.println("CONSTRUCCION AST DESCENDENTE");
            c_ast_descendente.ConstructorASTsTiny asint = new ConstructorASTsTiny(new FileReader(args[1]));
            asint.disable_tracing();
            prog = asint.analiza();

            // Verificar el estilo de procesamiento
            if (opp.equals("rec")) { // Programación recursiva
                System.out.println("IMPRESION RECURSIVA");
                // TODO: reemplazar por el q corresponde
            } else if (opp.equals("int")) { // Patrón intérprete
                System.out.println("IMPRESION INTERPRETE");
                asint.disable_tracing();
                prog.imprime();
            } else if (opp.equals("vis")) { // Patrón visitante
                System.out.println("IMPRESION VISITANTE");
                Evaluacion evaluacion = new Evaluacion();
                prog.procesa(evaluacion);
                System.out.println(evaluacion.leeResul());
            } else {
                System.err.println("Estilo de procesamiento no válido");
            }
        } else {
            System.err.println("Constructor de ASTs no válido");
        }
    }
}

 */