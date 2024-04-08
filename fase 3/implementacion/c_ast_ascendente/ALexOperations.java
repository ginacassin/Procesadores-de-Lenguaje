package c_ast_ascendente;

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
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.SUMA, "+");
    }

    public UnidadLexica unidadResta() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.RESTA, "-");
    }

    public UnidadLexica unidadMul() {

        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MUL, "*");
    }

    public UnidadLexica unidadDiv() {

        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DIV, "/");
    }

    public UnidadLexica unidadParentesisAbrir() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PABRIR, "(");
    }

    public UnidadLexica unidadParentesisCerrar() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PCERRAR, ")");
    }

    public UnidadLexica unidadAbrirBloque() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BABRIR, "{");
    }

    public UnidadLexica unidadCerrarBloque() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BCERRAR, "}");
    }

    public UnidadLexica unidadTamanoAbrir() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TABRIR, "[");
    }

    public UnidadLexica unidadTamanoCerrar() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TCERRAR, "]");
    }

    public UnidadLexica unidadFinDeclaraciones() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FINDEC, "&&");
    }

    public UnidadLexica unidadAsignacion() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ASIG, "=");
    }

    public UnidadLexica unidadMenor() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENOR, "<");
    }

    public UnidadLexica unidadMayor() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYOR, ">");
    }

    public UnidadLexica unidadMenorIgual() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MENORIGUAL, "<=");
    }

    public UnidadLexica unidadMayorIgual() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MAYORIGUAL, ">=");
    }

    public UnidadLexica unidadIgual() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IGUAL, "==");
    }

    public UnidadLexica unidadNoIgual() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NOIGUAL, "!=");
    }

    public UnidadLexica unidadAnd() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.AND, "<and>");
    }

    public UnidadLexica unidadOr() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.OR, "<or>");
    }

    public UnidadLexica unidadNot() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NOT, "<not>");
    }

    public UnidadLexica unidadTrue() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TRUE, "<true>");
    }

    public UnidadLexica unidadFalse() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.FALSE, "<false>");
    }

    public UnidadLexica unidadModulo() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.MOD, "%");
    }

    public UnidadLexica unidadPuntero() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTERO, "^");
    }

    public UnidadLexica unidadBitwiseAnd() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BITAND, "&");
    }

    public UnidadLexica unidadTipoEntero() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ENTERO, "<int>");
    }

    public UnidadLexica unidadTipoReal() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.REAL, "<real>");
    }

    public UnidadLexica unidadTipoBooleano() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.BOOL, "<bool>");
    }

    public UnidadLexica unidadTipoString() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.STRING, "<string>");
    }

    public UnidadLexica unidadNull() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NULL, "<null>");
    }

    public UnidadLexica unidadProc() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PROC, "<proc>");
    }

    public UnidadLexica unidadIf() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IF, "<if>");
    }

    public UnidadLexica unidadElse() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.ELSE, "<else>");
    }

    public UnidadLexica unidadWhile() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WHILE, "<while>");
    }

    public UnidadLexica unidadStruct() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.STRUCT, "<struct>");
    }

    public UnidadLexica unidadNew() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NEW, "<new>");
    }

    public UnidadLexica unidadDelete() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.DELETE, "<delete>");
    }

    public UnidadLexica unidadRead() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.READ, "<read>");
    }

    public UnidadLexica unidadWrite() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.WRITE, "<write>");
    }

    public UnidadLexica unidadNl() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.NL, "<nl>");
    }

    public UnidadLexica unidadType() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.TYPE, "<type>");
    }

    public UnidadLexica unidadCall() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.CALL, "<call>");
    }

    public UnidadLexica unidadEval() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EVAL, "@");
    }

    public UnidadLexica unidadPunto() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTO, ".");
    }

    public UnidadLexica unidadComa() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.COMA, ",");
    }

    public UnidadLexica unidadPuntoYComa() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.PUNTOCOMA, ";");
    }

    public UnidadLexica unidadIdentificador() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.IDEN, alex.lexema());
    }

    public UnidadLexica unidadLiteralEntero() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LITENT, alex.lexema());
    }

    public UnidadLexica unidadLiteralReal() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LITREAL, alex.lexema());
    }

    public UnidadLexica unidadLiteralCadena() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.LITCADENA, alex.lexema());
    }

    public UnidadLexica unidadEof() {
        return new UnidadLexica(alex.fila(), alex.columna(), ClaseLexica.EOF, "<EOF>");
    }
}
