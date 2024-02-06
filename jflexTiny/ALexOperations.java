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
    }

    public UnidadLexica unidadPAp() {
    }

    public UnidadLexica unidadPCierre() {
    }

    public UnidadLexica unidadMul() {
    }

    public UnidadLexica unidadSuma() {
    }

    public UnidadLexica unidadResta() {
    }

    public UnidadLexica unidadDiv() {
    }

    public UnidadLexica unidadMenor() {
    }

    public UnidadLexica unidadAsignacion() {
    }

    public UnidadLexica unidadMayor() {
    }

    public UnidadLexica unidadTamanoAbrir() {
    }

    public UnidadLexica unidadTamanoCerrar() {
    }

    public UnidadLexica unidadAbrirBloque() {
    }

    public UnidadLexica unidadCerrarBloque() {
    }

    public UnidadLexica unidadFinDeclaraciones() {
    }

    public UnidadLexica unidadMenorIgual() {
    }

    public UnidadLexica unidadIgual() {
    }

    public UnidadLexica unidadMayorIgual() {
    }
}
