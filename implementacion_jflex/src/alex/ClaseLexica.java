package alex;

public enum ClaseLexica {
    EOF("EOF"),
    SUMA("+"),
    RESTA("-"),
    MUL("*"),
    DIV("/"),
    PABRIR("("),
    PCERRAR(")"),
    BABRIR("{"),
    BCERRAR("}"),
    TABRIR("["),
    TCERRAR("]"),
    FINDEC("&&"),
    ASIG("="),
    MENOR("<"),
    MAYOR(">"),
    MENORIGUAL("<="),
    MAYORIGUAL(">="),
    IGUAL("=="),
    NOIGUAL("!="),
    AND("<and>"),
    OR("<or>"),
    NOT("<not>"),
    TRUE("<true>"),
    FALSE("<false>"),
    MOD("%"),
    PUNTERO("^"),
    BITAND("&"),
    ENTERO("<int>"),
    REAL("<real>"),
    BOOL("<bool>"),
    STRING("<string>"),
    NULL("<null>"),
    PROC("<proc>"),
    IF("<if>"),
    ELSE("<else>"),
    WHILE("<while>"),
    STRUCT("<struct>"),
    NEW("<new>"),
    DELETE("<delete>"),
    READ("<read>"),
    WRITE("<write>"),
    NL("<nl>"),
    TYPE("<type>"),
    CALL("<call>"),
    EVAL("@"),
    PUNTO("."),
    COMA(","),
    PUNTOCOMA(";"),
    ID,
    LITENT,
    LITREAL,
    LITCADENA;

    private String image;
    public String getImage() {
        return image;
    }
    private ClaseLexica() {
        image = toString();
    }
    private ClaseLexica(String image) {
        this.image = image;
    }
}
