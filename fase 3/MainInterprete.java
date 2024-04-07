import asint.SintaxisAbstractaTiny.*;
import c_ast_ascendente.AnalizadorLexicoTiny;
import c_ast_descendente.ConstructorASTsTinyDJ;

import java.io.FileInputStream;
import java.io.FileReader;
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
        else if (args[0].equals("desc")){
            Reader input = new InputStreamReader(new FileInputStream(args[1]));
            c_ast_descendente.ConstructorASTsTiny asint = new ConstructorASTsTinyDJ(input);
            //asint.disable_tracing();
            asint.programa();

            asint.analiza().imprime();
        }
    }
}
