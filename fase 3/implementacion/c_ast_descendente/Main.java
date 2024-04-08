package c_ast_descendente;

import c_ast_descendente.ConstructorASTsTiny;
import java.io.FileReader;
public class Main{
    public static void main(String[] args) throws Exception {
        ConstructorASTsTiny ev = new ConstructorASTsTiny(new FileReader(args[0]));
        System.out.println(ev.analiza());
    }
}