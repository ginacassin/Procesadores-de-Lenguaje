package alex;

public enum ClaseLexica {
    IDEN, ENT, REAL,
    FIN_DECLARACION("&&"),
    EOF("<EOF>"),
    PYCOMA(";"),
    EVAL("@"),
    NOIGUAL("!="),
    MAYOR(">"),
    MAYOR_IGUAL(">="),
    MENOR("<"),
    MENOR_IGUAL("<="),
    IGUAL("="),
    COMP_IGUALDAD("=="),
    MUL("*"),
    DIV("/"),
    LLAVE_AP("{"),
    LLAVE_CIERRE("}"),
    PAREN_AP("("),
    PAREN_CIERRE(")"),
    MENOS("-"),
    MAS("+"),
    // Identificadores:
    LIT_AND("<and>"),
    LIT_OR("<or>"),
    LIT_NOT("<not>"),
    LIT_TRUE("<true>"),
    LIT_FALSE("<false>"),
    LIT_TENT("<int>"),
    LIT_TREAL("<real>"),
    LIT_TBOOL("<bool>");
    
    private String image;

    private ClaseLexica() {
        image = this.toString();
    }
    private ClaseLexica(String image) {
        this.image = image;  
    }
    
    public String getImage() {
        return image;
    }
}
