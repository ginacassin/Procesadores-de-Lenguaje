package alex;

public class ALexOperations {
    private AnalizadorLexicoTiny alex;
    public ALexOperations(AnalizadorLexicoTiny alex) {
        this.alex = alex;
    }

    public UnidadLexica unidadEof() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(),ClaseLexica.EOF);
    }
    public void error() {
        System.err.println("***"+alex.fila()+" Caracter inexperado: "+alex.lexema());
    }


    public UnidadLexica unidadNoIgual() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.NOIGUAL, alex.lexema());
    }

    public UnidadLexica unidadPAp() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PABRIR);
    }

    public UnidadLexica unidadPCierre() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PCERRAR);
    }

    public UnidadLexica unidadMul() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MUL);
    }

    public UnidadLexica unidadSuma() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.SUMA);
    }

    public UnidadLexica unidadResta() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.RESTA);
    }

    public UnidadLexica unidadDiv() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIV);
    }

    public UnidadLexica unidadMenor() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MENOR);
    }

    public UnidadLexica unidadAsignacion() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ASIG);
    }

    public UnidadLexica unidadMayor() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MAYOR);
    }

    public UnidadLexica unidadTamanoAbrir() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TABRIR);
    }

    public UnidadLexica unidadTamanoCerrar() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TCERRAR);
    }

    public UnidadLexica unidadAbrirBloque() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.BABRIR);
    }

    public UnidadLexica unidadCerrarBloque() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.BCERRAR);
    }

    public UnidadLexica unidadFinDeclaraciones() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.FINDEC, alex.lexema());
    }

    public UnidadLexica unidadMenorIgual() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.MENORIGUAL, alex.lexema());
    }

    public UnidadLexica unidadIgual() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.IGUAL, alex.lexema());
    }

    public UnidadLexica unidadMayorIgual() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.MAYORIGUAL, alex.lexema());
    }

    public UnidadLexica unidadAnd() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.AND, alex.lexema());
    }

    public UnidadLexica unidadOr() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.OR, alex.lexema());
    }

    public UnidadLexica unidadNot() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.NOT, alex.lexema());
    }

    public UnidadLexica unidadTrue() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.TRUE, alex.lexema());
    }

    public UnidadLexica unidadFalse() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.FALSE, alex.lexema());
    }

    public UnidadLexica unidadModulo() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MOD);
    }

    public UnidadLexica unidadPuntero() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PUNTERO);
    }

    public UnidadLexica unidadBitwiseAnd() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.BITAND);
    }

    public UnidadLexica unidadTipoEntero() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.ENTERO, alex.lexema());
    }

    public UnidadLexica unidadTipoReal() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.REAL, alex.lexema());
    }

    public UnidadLexica unidadTipoBooleano() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.BOOL, alex.lexema());
    }

    public UnidadLexica unidadTipoString() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.STRING, alex.lexema());
    }

    public UnidadLexica unidadNull() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.NULL, alex.lexema());
    }

    public UnidadLexica unidadProcedimiento() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.PROC, alex.lexema());
    }

    public UnidadLexica unidadIf() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.IF, alex.lexema());
    }

    public UnidadLexica unidadtElse() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.ELSE, alex.lexema());
    }

    public UnidadLexica unidadWhile() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.WHILE, alex.lexema());
    }

    public UnidadLexica unidadEstructura() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.STRUCT, alex.lexema());
    }

    public UnidadLexica unidadNew() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.NEW, alex.lexema());
    }

    public UnidadLexica unidadDelete() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.DELETE, alex.lexema());
    }

    public UnidadLexica unidadRead() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.READ, alex.lexema());
    }

    public UnidadLexica unidadWrite() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.WRITE, alex.lexema());
    }

    public UnidadLexica unidadNl() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.NL, alex.lexema());
    }

    public UnidadLexica unidadType() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.CREARTIPO, alex.lexema());
    }

    public UnidadLexica unidadCall() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.CALL, alex.lexema());
    }

    public UnidadLexica unidadEval() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.EVAL, alex.lexema());
    }

    public UnidadLexica unidadPunto() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PUNTO);
    }

    public UnidadLexica unidadComa() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.COMA);
    }

    public UnidadLexica unidadId() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.IDEN, alex.lexema());
    }

    public UnidadLexica unidadEnt() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LITENT, alex.lexema());
    }

    public UnidadLexica unidadReal() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LITREAL, alex.lexema());
    }

}
