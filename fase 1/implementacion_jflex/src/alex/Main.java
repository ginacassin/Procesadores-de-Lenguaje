package alex;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class Main {
    public static void main(String[] args) throws FileNotFoundException, IOException {
        // test argument: implementacion_jflex/src/alex/input.txt
        Reader input = new InputStreamReader(new FileInputStream((args[0])));
        AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
        UnidadLexica unidad = null;
        do {
            try {
                unidad = al.yylex();
                System.out.println(unidad);
            }
            catch (ALexOperations.ECaracterInesperado e) {
                System.out.println(e.getMessage());
                System.exit(1);
            }
        }
        while (unidad == null || unidad.clase() != ClaseLexica.EOF);
    }
}
