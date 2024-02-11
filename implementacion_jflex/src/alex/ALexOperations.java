package alex;

public class ALexOperations {
    public static class ECaracterInesperado extends RuntimeException {
        public ECaracterInesperado(String msg) {
            super(msg);
        }
    }

    private AnalizadorLexicoTiny alex;

    public ALexOperations(AnalizadorLexicoTiny alex) {
        this.alex = alex;
    }

    public void error() {
        throw new ECaracterInesperado("***" + alex.fila() + " Caracter inexperado: " + alex.lexema());
    }

    public UnidadLexica unidadSuma() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.SUMA);
    }

    public UnidadLexica unidadResta() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.RESTA);
    }

    public UnidadLexica unidadMul() {

        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MUL);
    }

    public UnidadLexica unidadDiv() {

        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DIV);
    }

    public UnidadLexica unidadParentesisAbrir() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PABRIR);
    }

    public UnidadLexica unidadParentesisCerrar() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PCERRAR);
    }

    public UnidadLexica unidadAbrirBloque() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.BABRIR);
    }

    public UnidadLexica unidadCerrarBloque() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.BCERRAR);
    }

    public UnidadLexica unidadTamanoAbrir() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TABRIR);
    }

    public UnidadLexica unidadTamanoCerrar() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TCERRAR);
    }

    public UnidadLexica unidadFinDeclaraciones() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.FINDEC);
    }

    public UnidadLexica unidadAsignacion() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ASIG);
    }

    public UnidadLexica unidadMenor() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MENOR);
    }

    public UnidadLexica unidadMayor() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MAYOR);
    }

    public UnidadLexica unidadMenorIgual() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MENORIGUAL);
    }

    public UnidadLexica unidadMayorIgual() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.MAYORIGUAL);
    }

    public UnidadLexica unidadIgual() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.IGUAL);
    }

    public UnidadLexica unidadNoIgual() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NOIGUAL);
    }

    public UnidadLexica unidadAnd() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.AND);
    }

    public UnidadLexica unidadOr() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.OR);
    }

    public UnidadLexica unidadNot() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NOT);
    }

    public UnidadLexica unidadTrue() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TRUE);
    }

    public UnidadLexica unidadFalse() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.FALSE);
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
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.BOOL);
    }

    public UnidadLexica unidadTipoString() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.STRING);
    }

    public UnidadLexica unidadNull() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NULL);
    }

    public UnidadLexica unidadProc() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PROC);
    }

    public UnidadLexica unidadIf() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.IF);
    }

    public UnidadLexica unidadElse() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.ELSE);
    }

    public UnidadLexica unidadWhile() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.WHILE);
    }

    public UnidadLexica unidadStruct() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.STRUCT);
    }

    public UnidadLexica unidadNew() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NEW);
    }

    public UnidadLexica unidadDelete() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.DELETE);
    }

    public UnidadLexica unidadRead() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.READ);
    }

    public UnidadLexica unidadWrite() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.WRITE);
    }

    public UnidadLexica unidadNl() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.NL);
    }

    public UnidadLexica unidadType() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.TYPE);
    }

    public UnidadLexica unidadCall() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.CALL);
    }

    public UnidadLexica unidadEval() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.EVAL);
    }

    public UnidadLexica unidadPunto() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PUNTO);
    }

    public UnidadLexica unidadComa() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.COMA);
    }

    public UnidadLexica unidadPuntoYComa() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.PUNTOCOMA);
    }

    public UnidadLexica unidadIdentificador() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.ID, alex.lexema());
    }

    public UnidadLexica unidadLiteralEntero() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LITENT, alex.lexema());
    }

    public UnidadLexica unidadLiteralReal() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LITREAL, alex.lexema());
    }

    public UnidadLexica unidadLiteralCadena() {
        return new UnidadLexicaMultivaluada(alex.fila(), alex.columna(), ClaseLexica.LITCADENA, alex.lexema());
    }

    public UnidadLexica unidadEof() {
        return new UnidadLexicaUnivaluada(alex.fila(), alex.columna(), ClaseLexica.EOF);
    }
}
