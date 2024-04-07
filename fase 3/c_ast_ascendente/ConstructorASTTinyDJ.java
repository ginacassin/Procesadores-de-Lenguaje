package c_ast_ascendente;

import java_cup.runtime.Scanner;
import java_cup.runtime.Symbol;

public class ConstructorASTTinyDJ extends ConstructorASTTiny {
    public void debug_message(String msg) {}
    public void debug_shift(Symbol token) {
        System.out.println(((UnidadLexica)token).lexema());
    }
    public ConstructorASTTinyDJ(Scanner ast) {
        super(ast);
    }
}
