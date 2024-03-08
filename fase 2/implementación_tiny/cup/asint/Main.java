package cup.asint;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import cup.alex.AnalizadorLexicoTiny;

public class Main {
    public static void main(String[] args) throws Exception {
        Reader input = new InputStreamReader(new FileInputStream(args[0]));
        AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
        cup.asint.AnalizadorSintacticoTiny asintactico = new cup.asint.AnalizadorSintacticoTiny(alex);
        //asint.setScanner(alex);
        asintactico.parse();
    }
}