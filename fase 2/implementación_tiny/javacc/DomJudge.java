package javacc;

import javacc.asint.AnalizadorSintacticoTiny;
import javacc.asint.AnalizadorSintacticoTinyDJ;
import javacc.asint.ParseException;
import javacc.asint.TokenMgrError;
import java.io.FileReader;
import java.io.InputStreamReader;
public class DomJudge{
    public static void main(String[] args) throws Exception {
        try{
            AnalizadorSintacticoTiny asint = new AnalizadorSintacticoTinyDJ(new InputStreamReader(System.in));
            asint.programa();
        }
        catch(ParseException e) {
            System.out.println("ERROR_SINTACTICO");
        }
        catch(TokenMgrError e) {
            System.out.println("ERROR_LEXICO");
        }
    }
}