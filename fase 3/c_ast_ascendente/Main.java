package c_ast_ascendente;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import asint.SintaxisAbstractaTiny;
import evaluador.visitante.Impresion;
import java_cup.runtime.Symbol;

public class Main {
   public static void main(String[] args) throws Exception {
       Reader input = new InputStreamReader(new FileInputStream(args[0]));
       AnalizadorLexicoTiny alex = new AnalizadorLexicoTiny(input);
       ConstructorASTTiny asint = new ConstructorASTTinyDJ(alex);

       Symbol s = null;
       try {
           s = asint.debug_parse();
       } catch (GestionErroresTiny.ErrorSintactico e) {
           System.out.println((e.getMessage()));
       }

       SintaxisAbstractaTiny.Prog p = (SintaxisAbstractaTiny.Prog) s.value;


   }
}