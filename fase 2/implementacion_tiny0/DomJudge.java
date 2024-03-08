import asint.AnalizadorSintacticoEval;
import asint.AnalizadorSintacticoEvalDJ;
import asint.AnalizadorSintacticoTiny;
import asint.AnalizadorSintacticoTinyDJ;
import errors.GestionErroresEval.ErrorLexico;
import errors.GestionErroresEval.ErrorSintactico;
import java.io.InputStreamReader;
public class DomJudge{
    public static void main(String[] args) throws Exception {
        try {  
            AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(new InputStreamReader(System.in));
            asint.analiza();
        }
        catch(ErrorSintactico e) {
            System.out.println("ERROR_SINTACTICO"); 
        }
        catch(ErrorLexico e) {
            System.out.println("ERROR_LEXICO"); 
        }
    }
}