package asint;

import alex.UnidadLexica;
import alex.AnalizadorLexicoTiny;
import alex.ClaseLexica;
import errors.GestionErroresTiny;
import java.io.IOException;
import java.io.Reader;
import java.util.EnumSet;
import java.util.Set;

public class AnalizadorSintacticoTiny {
    private UnidadLexica anticipo;       // token adelantado
    private AnalizadorLexicoTiny alex;   // analizador léxico 
    private GestionErroresTiny errores;  // gestor de errores
    private Set<ClaseLexica> esperados;  // clases léxicas esperadas
   
    public AnalizadorSintacticoTiny(Reader input) throws IOException {
            // se crea el gestor de errores
        errores = new GestionErroresTiny();
            // se crea el analizador léxico
        alex = new AnalizadorLexicoTiny(input,errores);
        esperados = EnumSet.noneOf(ClaseLexica.class);
            // Se lee el primer token adelantado
        sigToken();                      
    }
    public void analiza() {
        programa();
        empareja(ClaseLexica.EOF);
    }

    // Comienza el codigo nuestro:
    private void programa() {
        bloque();
    }

    private void bloque() {
        empareja(ClaseLexica.LLAVE_AP);
        declaraciones();
        instrucciones();
        empareja(ClaseLexica.LLAVE_CIERRE);
    }
    
    private void declaraciones() {
        switch(anticipo.clase()) {
            case LIT_TENT: case LIT_TREAL: case LIT_TBOOL: 
                declaracionesAux();
                empareja(ClaseLexica.FIN_DECLARACION);
                break;
            default:        
                esperados(ClaseLexica.LIT_TENT, ClaseLexica.LIT_TREAL, ClaseLexica.LIT_TBOOL);
                break;
        } 
    }

    private void declaracionesAux() {
        declaracionVar();
        recDeclaracionesAux();
    }

    private void recDeclaracionesAux() {
        switch(anticipo.clase()) {
            case PYCOMA:
                empareja(ClaseLexica.PYCOMA);
                declaracionVar();
                recDeclaracionesAux();
                break;
            default:        
                esperados(ClaseLexica.PYCOMA);
                break;
        } 
    }

    private void declaracionVar() {
        tipo();
        empareja(ClaseLexica.IDEN);
    }

    private void tipo() {
        switch(anticipo.clase()) {
            case LIT_TENT:
                empareja(ClaseLexica.LIT_TENT);
                break;
            case LIT_TREAL:
                empareja(ClaseLexica.LIT_TREAL);
                break;
            case LIT_TBOOL:
                empareja(ClaseLexica.LIT_TBOOL);
                break;
            default:        
                esperados(ClaseLexica.LIT_TENT, ClaseLexica.LIT_TREAL, ClaseLexica.LIT_TBOOL);
                error();
                break;
        }
    }

    private void instrucciones() {
        switch(anticipo.clase()) {
            case EVAL:
                instruccionesAux();
                break;
            default:        
                esperados(ClaseLexica.EVAL);
                break;
        }
    }

    private void instruccionesAux() {
        instruccion();
        recInstruccionesAux();
    }

    private void recInstruccionesAux() {
        switch(anticipo.clase()) {
            case PYCOMA:
                empareja(ClaseLexica.PYCOMA);
                instruccionesAux();
                break;
            default:        
                esperados(ClaseLexica.EVAL);
                break;
        }
    }

    private void instruccion() {
        empareja(ClaseLexica.EVAL);
        expr();
    }

    private void expr() {
        e0();
    }

    private void e0() {
        e1();
        facE0();
    }

    private void facE0() {
        switch(anticipo.clase()) {
            case IGUAL:
                empareja(ClaseLexica.IGUAL);
                e0();
                break;
            default:        
                esperados(ClaseLexica.IGUAL);
                break;
        }
    }

    private void e1() {
        e2();
        recE1();
    }

    private void recE1() {
        switch(anticipo.clase()) {
            case MENOR: case MAYOR: case MENOR_IGUAL: case MAYOR_IGUAL: case COMP_IGUALDAD: case NOIGUAL:
                op1();
                e2();
                recE1();
                break;
            default:        
                esperados(ClaseLexica.MENOR, ClaseLexica.MAYOR, ClaseLexica.MENOR_IGUAL, ClaseLexica.MAYOR_IGUAL, ClaseLexica.COMP_IGUALDAD, ClaseLexica.NOIGUAL);
                break;
        }
    }

    private void e2() {
        e3();
        facE2();
        recE2();
    }

    private void recE2() {
        switch(anticipo.clase()) {
            case MAS:
                empareja(ClaseLexica.MAS);
                e3();
                recE2();
                break;
            default:        
                esperados(ClaseLexica.MAS);
                break;
        }
    }

    private void facE2() {
        switch(anticipo.clase()) {
            case MENOS:
                empareja(ClaseLexica.MENOS);
                e3();
                recE2();
                break;
            default:        
                esperados(ClaseLexica.MENOS);
                break;
        }
    }

    private void e3() {
        e4();
        facE3();
    }

    private void facE3() {
        switch(anticipo.clase()) {
            case LIT_AND:
                empareja(ClaseLexica.LIT_AND);
                e3();
                break;
            case LIT_OR:
                empareja(ClaseLexica.LIT_OR);
                e4();
                break;
            default:        
                esperados(ClaseLexica.MENOS);
                break;
        }
    }

    private void e4() {
        e5();
        recE4();
    }

    private void recE4() {
        switch(anticipo.clase()) {
            case MUL: case DIV:
                op4();
                e5();
                recE4();
                break;
            default:
                esperados(ClaseLexica.MUL, ClaseLexica.DIV);
                break;
        }
    }

    private void e5() {
        switch(anticipo.clase()) {
            case MENOS: case LIT_NOT:
                op5();
                e5();
                break;
            case PAREN_AP: case ENT: case REAL: case LIT_TRUE: case LIT_FALSE: case IDEN:
                e6();
                break;
            default:        
                esperados(ClaseLexica.MENOS, ClaseLexica.LIT_NOT,
                            ClaseLexica.PAREN_AP, ClaseLexica.ENT, ClaseLexica.REAL, ClaseLexica.LIT_TRUE, ClaseLexica.LIT_FALSE, ClaseLexica.IDEN);
                error();
                break;
        }
    }

    private void e6() {
        switch(anticipo.clase()) {
            case PAREN_AP:
                empareja(ClaseLexica.PAREN_AP);
                e0();
                empareja(ClaseLexica.PAREN_CIERRE);
                break;
            case ENT:
                empareja(ClaseLexica.ENT);
                break;
            case REAL:
                empareja(ClaseLexica.REAL);
                break;
            case LIT_TRUE:
                empareja(ClaseLexica.LIT_TRUE);
                break;
            case LIT_FALSE:
                empareja(ClaseLexica.LIT_FALSE);
                break;
            case IDEN:
                empareja(ClaseLexica.IDEN);
                break;
            default:        
                esperados(ClaseLexica.PAREN_AP, ClaseLexica.ENT, ClaseLexica.REAL, ClaseLexica.LIT_TRUE, ClaseLexica.LIT_FALSE, ClaseLexica.IDEN);
                error();
                break;
        }
    }

    private void op1() {
        switch(anticipo.clase()) {
            case MENOR:
                empareja(ClaseLexica.MENOR);
                break;
            case MAYOR:
                empareja(ClaseLexica.MAYOR);
                break;
            case MENOR_IGUAL:
                empareja(ClaseLexica.MENOR_IGUAL);
                break;
            case MAYOR_IGUAL:
                empareja(ClaseLexica.MAYOR_IGUAL);
                break;
            case COMP_IGUALDAD:
                empareja(ClaseLexica.COMP_IGUALDAD);
                break;
            case NOIGUAL:
                empareja(ClaseLexica.NOIGUAL);
                break;
            default:        
                esperados(ClaseLexica.MENOR, ClaseLexica.MAYOR, ClaseLexica.MENOR_IGUAL, ClaseLexica.MAYOR_IGUAL, ClaseLexica.COMP_IGUALDAD, ClaseLexica.NOIGUAL);
                error();
                break;
        }
    }

    private void op4() {
        switch(anticipo.clase()) {
            case MUL:
                empareja(ClaseLexica.MUL);
                break;
            case DIV:
                empareja(ClaseLexica.DIV);
                break;
            default:
                esperados(ClaseLexica.MUL, ClaseLexica.DIV);
                error();
                break;
        }
    }

    private void op5() {
        switch(anticipo.clase()) {
            case MENOS:
                empareja(ClaseLexica.MENOS);
                break;
            case LIT_NOT:
                empareja(ClaseLexica.LIT_NOT);
                break;
            default:
                esperados(ClaseLexica.MENOS, ClaseLexica.LIT_NOT);
                error();
                break;
        }
    }


    // Resto de funciones
    private void esperados(ClaseLexica ... esperadas) {
        for(ClaseLexica c: esperadas) {
            esperados.add(c);
        }
    }

    private void empareja(ClaseLexica claseEsperada) {
        if (anticipo.clase() == claseEsperada) {
            traza_emparejamiento(anticipo);
            sigToken();
        }    
        else {
            esperados(claseEsperada);
            error();
        }
    }

    private void sigToken() {
        try {
            anticipo = alex.sigToken();
            esperados.clear();
        }
        catch(IOException e) {
            errores.errorFatal(e);
        }
    }

    private void error() {
        errores.errorSintactico(anticipo.fila(), anticipo.columna(), anticipo.clase(), esperados);
    }
    
    protected void traza_emparejamiento(UnidadLexica anticipo) {}  
}
