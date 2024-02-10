
import java.io.FileInputStream;
import java.io.Reader;
import java.io.InputStreamReader;
import java.io.IOException;

public class AnalizadorLexicoTiny {
    private Reader input;
    private StringBuffer lex;
    private int sigCar;
    private int filaInicio;
    private int columnaInicio;
    private int filaActual;
    private int columnaActual;
    private static String NL = System.getProperty("line.separator");
   
    private static enum Estado { // Listados en sentido anti-horario a partir de arriba al medio de INICIO
        INICIO, REC_FIN_DEC1, REC_FIN_DEC2, REC_COM1, REC_COM2, REC_EOF,
        REC_SEP, REC_EVAL, REC_EXCL, REC_NO_IGUAL, REC_MAYOR, REC_MAYOR_IGUAL,
        REC_MENOR, REC_MENOR_IGUAL, REC_IGUAL, REC_COMP_IGUAL, REC_MUL,
        REC_DIV, REC_LAP, REC_LCIE, REC_PAP, REC_PCIE, REC_ID,
        REC_0, REC_MENOS, REC_ENT, REC_MAS, REC_IDEC1, REC_IDEC2, REC_0DEC,
        REC_EEXP, REC_EXP_MAS, REC_EXP_MENOS, REC_EXP, REC_EXP0

        // REC_POR, REC_DIV, REC_PAP, REC_PCIERR, REC_COMA, REC_IGUAL,
        // REC_MAS, REC_MENOS, REC_ID, REC_ENT, REC_0, REC_IDEC, REC_DEC, REC_COM, REC_EOF
    }

    private Estado estado;

    public AnalizadorLexicoTiny(Reader input) throws IOException {
        this.input = input;
        lex = new StringBuffer();
        sigCar = input.read();
        filaActual=1;
        columnaActual=1;
    }

    public UnidadLexica sigToken() throws IOException {
        estado = Estado.INICIO;
        filaInicio = filaActual;
        columnaInicio = columnaActual;
        lex.delete(0,lex.length());
        while(true) {
            switch(estado) { // Los estados finales no devuelven error. Los no-finales, sí pueden devolver error.
                case INICIO:
                    if (hayAmpersand()) transita(Estado.REC_FIN_DEC1);
                    else if (hayAlmohadilla()) transita(Estado.REC_COM1);
                    else if (hayPYComa()) transita(Estado.REC_SEP);
                    else if (hayArroba()) transita(Estado.REC_EVAL);
                    else if (hayEOF()) transita(Estado.REC_EOF);
                    else if (hayExclamacion()) transita(Estado.REC_EXCL);
                    else if (hayMayor()) transita(Estado.REC_MAYOR);
                    else if (hayMenor()) transita(Estado.REC_MENOR);
                    else if (hayIgual()) transita(Estado.REC_IGUAL);
                    else if (hayMul()) transita(Estado.REC_MUL);
                    else if (hayDiv()) transita(Estado.REC_DIV);
                    else if (hayLAp()) transita(Estado.REC_LAP);
                    else if (hayLCierre()) transita(Estado.REC_LCIE);
                    else if (hayPAp()) transita(Estado.REC_PAP);
                    else if (hayPCierre()) transita(Estado.REC_PCIE);
                    else if (hayLetraOGuionBajo())  transita(Estado.REC_ID);
                    else if (hayCero()) transita(Estado.REC_0);
                    else if (hayResta()) transita(Estado.REC_MENOS);
                    else if (hayDigitoPos()) transita(Estado.REC_ENT);
                    else if (haySuma()) transita(Estado.REC_MAS);
                    else if (haySep() || hayNL()) transitaIgnorando(Estado.INICIO);
                    else error();
                    break;
                case REC_FIN_DEC1:
                    if (hayAmpersand()) transita(Estado.REC_FIN_DEC2);
                    else error();
                    break;
                case REC_FIN_DEC2: return unidadFinDeclaraciones();
                case REC_COM1:
                    if (hayAlmohadilla()) transita(Estado.REC_COM2);
                    else error();
                    break;
                case REC_COM2:
                    if (hayEOF()) transita(Estado.REC_EOF);
                    if (hayN()) transitaIgnorando(Estado.INICIO);
                    else transitaIgnorando(Estado.REC_COM2);
                    break;
                case REC_EOF: return unidadEof();
                case REC_SEP: return unidadPYComa();
                case REC_EVAL: return unidadEval();
                case REC_EXCL:
                    if (hayIgual()) transita(Estado.REC_NO_IGUAL);
                    else error();
                    break;
                case REC_NO_IGUAL: return unidadNoIgual();
                case REC_MAYOR:
                    if (hayIgual()) transita(Estado.REC_MAYOR_IGUAL);
                    else return unidadMayor();
                    break;
                case REC_MAYOR_IGUAL: return unidadMayorIgual();
                case REC_MENOR:
                    if (hayIgual()) transita(Estado.REC_MENOR_IGUAL);
                    else return unidadMenor();
                    break;
                case REC_MENOR_IGUAL: return unidadMenorIgual();
                case REC_IGUAL:
                    if (hayIgual()) transita(Estado.REC_COMP_IGUAL);
                    else return unidadIgual();
                    break;
                case REC_COMP_IGUAL: return unidadCompIgualdad();
                case REC_MUL: return unidadMul();
                case REC_DIV: return unidadDiv();
                case REC_LAP: return unidadLAp();
                case REC_LCIE: return unidadLCierre();
                case REC_PAP: return unidadPAp();
                case REC_PCIE: return unidadPCierre();
                case REC_ID:
                    if (hayLetraOGuionBajo() || hayDigito()) transita(Estado.REC_ID);
                    else return unidadId();
                    break;
                case REC_0:
                    if (hayPunto()) transita(Estado.REC_IDEC1);
                    else if (hayExponente()) transita(Estado.REC_EEXP);
                    else return unidadEnt();
                    break;
                case REC_MENOS:
                    if (hayDigitoPos()) transita(Estado.REC_ENT);
                    else if(hayCero()) transita(Estado.REC_0);
                    else return unidadMenos();
                    break;
                case REC_MAS:
                    if (hayDigitoPos()) transita(Estado.REC_ENT);
                    else if (hayCero()) transita(Estado.REC_0);
                    else return unidadMas();
                    break;
                case REC_ENT:
                    if (hayDigito()) transita(Estado.REC_ENT);
                    else if (hayPunto()) transita(Estado.REC_IDEC1);
                    else if (hayExponente()) transita(Estado.REC_EEXP);
                    else return unidadEnt();
                    break;
                case REC_IDEC1:
                    if (hayDigito()) transita(Estado.REC_IDEC2);
                    else error();
                    break;
                case REC_IDEC2:
                    if (hayDigitoPos()) transita(Estado.REC_IDEC2);
                    else if (hayCero()) transita(Estado.REC_0DEC);
                    else if (hayExponente()) transita(Estado.REC_EEXP);
                    else return unidadReal();
                    break;
                case REC_0DEC:
                    if (hayCero()) transita(Estado.REC_0DEC);
                    if (hayDigitoPos()) transita(Estado.REC_IDEC2);
                    else error();
                    break;
                case REC_EEXP:
                    if (hayResta()) transita(Estado.REC_EXP_MENOS);
                    else if (haySuma()) transita(Estado.REC_EXP_MAS);
                    else if (hayCero()) transita(Estado.REC_EXP0);
                    else if (hayDigitoPos()) transita(Estado.REC_EXP);
                    else error();
                    break;
                case REC_EXP_MAS:
                    if (hayCero()) transita(Estado.REC_EXP0);
                    else if (hayDigitoPos()) transita(Estado.REC_EXP);
                    else error();
                    break;
                case REC_EXP_MENOS:
                    if (hayCero()) transita(Estado.REC_EXP0);
                    else if (hayDigitoPos()) transita(Estado.REC_EXP);
                    else error();
                    break;
                case REC_EXP:
                    if (hayDigito()) transita(Estado.REC_EXP);
                    else return unidadReal();
                    break;
                case REC_EXP0: return unidadReal();
            }
        }
   }
    private void transita(Estado sig) throws IOException {
        lex.append((char)sigCar);
        sigCar();
        estado = sig;
    }
    private void transitaIgnorando(Estado sig) throws IOException {
        sigCar();
        filaInicio = filaActual;
        columnaInicio = columnaActual;
        estado = sig;
    }
    private void sigCar() throws IOException {
        sigCar = input.read();
        if (sigCar == NL.charAt(0)) saltaFinDeLinea();
        if (sigCar == '\n') {
            filaActual++;
            columnaActual=0;
        }
        else {
            columnaActual++;  
        }
    }
    private void saltaFinDeLinea() throws IOException {
        for (int i=1; i < NL.length(); i++) {
            sigCar = input.read();
            if (sigCar != NL.charAt(i)) error();
        }
        sigCar = '\n';
    }
   
    private boolean hayLetraOGuionBajo() {return sigCar >= 'a' && sigCar <= 'z'
                                                || sigCar >= 'A' && sigCar <= 'z'
                                                || sigCar == '_';}
    private boolean hayDigitoPos() {return sigCar >= '1' && sigCar <= '9';}
    private boolean hayCero() {return sigCar == '0';}
    private boolean hayDigito() {return hayDigitoPos() || hayCero();}
    private boolean hayAmpersand() {return sigCar == '&';}
    private boolean hayArroba() {return sigCar == '@';}
    private boolean hayExclamacion() {return sigCar == '!';}
    private boolean hayExponente() {return sigCar == 'e' || sigCar == 'E';}
    private boolean hayMayor() {return sigCar == '>';}
    private boolean hayMenor() {return sigCar == '<';}
    private boolean haySuma() {return sigCar == '+';}
    private boolean hayResta() {return sigCar == '-';}
    private boolean hayMul() {return sigCar == '*';}
    private boolean hayDiv() {return sigCar == '/';}
    private boolean hayPAp() {return sigCar == '(';}
    private boolean hayPCierre() {return sigCar == ')';}
    private boolean hayLAp() {return sigCar == '{';}
    private boolean hayLCierre() {return sigCar == '}';}
    private boolean hayIgual() {return sigCar == '=';}
    private boolean hayPYComa() {return sigCar == ';';}
    private boolean hayPunto() {return sigCar == '.';}
    private boolean hayAlmohadilla() {return sigCar == '#';}
    private boolean haySep() {return sigCar == ' ' || sigCar == '\t' || sigCar=='\n';}
    private boolean hayNL() {return sigCar == '\r' || sigCar == '\b' || sigCar == '\n';}
    private boolean hayN() {return sigCar == '\n';}
    private boolean hayEOF() {return sigCar == -1;}
    private UnidadLexica unidadId() {
        switch(lex.toString().toLowerCase()) {
            case "and":
                return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_AND);
            case "or":
                return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_OR);
            case "not":
                return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_NOT);
            case "true":
                return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_TRUE);
            case "false":
                return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_FALSE);
            case "int":
                return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_TENT);
            case "real":
                return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_TREAL);
            case "bool":
                return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LIT_TBOOL);
            default:
                return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.IDEN,lex.toString());
        }
   }  
    private UnidadLexica unidadEnt() {
        return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.ENT,lex.toString());
    }
    private UnidadLexica unidadReal() {
        return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.REAL,lex.toString());
    }
    private UnidadLexica unidadMas() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAS);
    }
    private UnidadLexica unidadMenos() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOS);
    }
    private UnidadLexica unidadMul() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MUL);
    }
    private UnidadLexica unidadDiv() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.DIV);
    }
    private UnidadLexica unidadLAp() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LLAVE_AP);
    }
    private UnidadLexica unidadLCierre() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.LLAVE_CIERRE);
    }
    private UnidadLexica unidadPAp() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PAREN_AP);
    }
    private UnidadLexica unidadPCierre() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PAREN_CIERRE);
    }
    private UnidadLexica unidadIgual() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.IGUAL);
    }
    private UnidadLexica unidadPYComa() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.PYCOMA);
    }
    private UnidadLexica unidadEof() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EOF);
    }
    private UnidadLexica unidadFinDeclaraciones() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.FIN_DECLARACION);
    }
    private UnidadLexica unidadEval() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.EVAL);
    }
    private UnidadLexica unidadNoIgual() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.NOIGUAL);
    }
    private UnidadLexica unidadMayor() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAYOR);
    }
    private UnidadLexica unidadMayorIgual() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MAYOR_IGUAL);
    }
    private UnidadLexica unidadMenor() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOR);
    }
    private UnidadLexica unidadMenorIgual() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.MENOR_IGUAL);
    }
    private UnidadLexica unidadCompIgualdad() {
        return new UnidadLexicaUnivaluada(filaInicio,columnaInicio,ClaseLexica.COMP_IGUALDAD);
    }
    private void error() {
        System.err.println("("+filaActual+','+columnaActual+"):Caracter inesperado");
        System.exit(1);
    }

    public static void main(String arg[]) throws IOException {
        Reader input = new InputStreamReader(new FileInputStream("input.txt"));
        AnalizadorLexicoTiny al = new AnalizadorLexicoTiny(input);
        UnidadLexica unidad;
        do {
            unidad = al.sigToken();
            System.out.println(unidad);
        }
        while (unidad.clase() != ClaseLexica.EOF);
    } 
}