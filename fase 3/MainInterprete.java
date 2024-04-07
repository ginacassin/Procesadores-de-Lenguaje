import asint.SintaxisAbstractaTiny.*;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_ascendente.GestionErroresTiny;
import c_ast_descendente.ConstructorASTsTiny;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

public class MainInterprete {
    public static void main(String[] args) throws Exception {
        if (args[0].equals("asc")) {
            Reader input = new InputStreamReader(new FileInputStream(args[1]));
            AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
            c_ast_ascendente.ConstructorASTTiny asint = new c_ast_ascendente.ConstructorASTTinyDJ(alex);

            // asint.debug_parse();

            Prog prog = (Prog)asint.parse().value;
            prog.imprime();
        }
        else {
            c_ast_descendente.ConstructorASTsTiny asint = new ConstructorASTsTiny(new FileInputStream(args[1]));
            asint.disable_tracing();
            asint.programa();

            asint.analiza().imprime();
        }
    }
}
