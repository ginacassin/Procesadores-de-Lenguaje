/* AnalizadorSintacticoTiny.java */
/* Generated By:JavaCC: Do not edit this line. AnalizadorSintacticoTiny.java */
package asint;

public class AnalizadorSintacticoTiny implements AnalizadorSintacticoTinyConstants {
    protected void newToken(Token t) {}

//revisar que esto lo acepta

// ======== REGLAS SINTACTICAS ========
  final public 
  void programa() throws ParseException {
    trace_call("programa");
    try {

      bloque();
    } finally {
      trace_return("programa");
    }
}

//incluir eof ?
  final public   void bloque() throws ParseException {
    trace_call("bloque");
    try {

      declaraciones();
      instrucciones();
    } finally {
      trace_return("bloque");
    }
}

  final public void declaraciones() throws ParseException {
    trace_call("declaraciones");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INT:
      case real:
      case bool:
      case string:
      case proc:
      case struct:
      case type:
      case iden:
      case 42:{
        declaracionesAux();
        jj_consume_token(34);
        break;
        }
      default:
        jj_la1[0] = jj_gen;

      }
    } finally {
      trace_return("declaraciones");
    }
}

  final public void declaracionesAux() throws ParseException {
    trace_call("declaracionesAux");
    try {

      declaracion();
      recDeclaracion();
    } finally {
      trace_return("declaracionesAux");
    }
}

  final public void recDeclaracion() throws ParseException {
    trace_call("recDeclaracion");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 35:{
        jj_consume_token(35);
        declaracion();
        recDeclaracion();
        break;
        }
      default:
        jj_la1[1] = jj_gen;

      }
    } finally {
      trace_return("recDeclaracion");
    }
}

  final public void declaracion() throws ParseException {
    trace_call("declaracion");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INT:
      case real:
      case bool:
      case string:
      case struct:
      case iden:
      case 42:{
        declaracionVar();
        break;
        }
      case type:{
        declaracionTipo();
        break;
        }
      case proc:{
        declaracionProc();
        break;
        }
      default:
        jj_la1[2] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("declaracion");
    }
}

  final public void declaracionVar() throws ParseException {
    trace_call("declaracionVar");
    try {

      tipo0();
      jj_consume_token(iden);
    } finally {
      trace_return("declaracionVar");
    }
}

  final public void declaracionTipo() throws ParseException {
    trace_call("declaracionTipo");
    try {

      jj_consume_token(type);
      tipo0();
      jj_consume_token(iden);
    } finally {
      trace_return("declaracionTipo");
    }
}

  final public void declaracionProc() throws ParseException {
    trace_call("declaracionProc");
    try {

      jj_consume_token(proc);
      jj_consume_token(iden);
      paramsFormales();
      bloque();
    } finally {
      trace_return("declaracionProc");
    }
}

  final public void paramsFormales() throws ParseException {
    trace_call("paramsFormales");
    try {

      jj_consume_token(36);
      paramsFormalesAux();
      jj_consume_token(37);
    } finally {
      trace_return("paramsFormales");
    }
}

  final public void paramsFormalesAux() throws ParseException {
    trace_call("paramsFormalesAux");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case INT:
      case real:
      case bool:
      case string:
      case struct:
      case iden:
      case 42:{
        paramsFormalesLista();
        break;
        }
      default:
        jj_la1[3] = jj_gen;

      }
    } finally {
      trace_return("paramsFormalesAux");
    }
}

  final public void paramsFormalesLista() throws ParseException {
    trace_call("paramsFormalesLista");
    try {

      param();
      recParamFormal();
    } finally {
      trace_return("paramsFormalesLista");
    }
}

  final public void recParamFormal() throws ParseException {
    trace_call("recParamFormal");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 38:{
        jj_consume_token(38);
        param();
        recParamFormal();
        break;
        }
      default:
        jj_la1[4] = jj_gen;

      }
    } finally {
      trace_return("recParamFormal");
    }
}

  final public void param() throws ParseException {
    trace_call("param");
    try {

      tipo0();
      referencia();
      jj_consume_token(iden);
    } finally {
      trace_return("param");
    }
}

  final public void referencia() throws ParseException {
    trace_call("referencia");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 39:{
        jj_consume_token(39);
        break;
        }
      default:
        jj_la1[5] = jj_gen;

      }
    } finally {
      trace_return("referencia");
    }
}

  final public void tipo0() throws ParseException {
    trace_call("tipo0");
    try {

      tipo1();
      recArray();
    } finally {
      trace_return("tipo0");
    }
}

  final public void recArray() throws ParseException {
    trace_call("recArray");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 40:{
        jj_consume_token(40);
        jj_consume_token(literalEntero);
        jj_consume_token(41);
        recArray();
        break;
        }
      default:
        jj_la1[6] = jj_gen;

      }
    } finally {
      trace_return("recArray");
    }
}

  final public void tipo1() throws ParseException {
    trace_call("tipo1");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 42:{
        jj_consume_token(42);
        tipo1();
        break;
        }
      case INT:
      case real:
      case bool:
      case string:
      case struct:
      case iden:{
        tipo2();
        break;
        }
      default:
        jj_la1[7] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("tipo1");
    }
}

  final public void tipo2() throws ParseException {
    trace_call("tipo2");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case struct:{
        jj_consume_token(struct);
        jj_consume_token(43);
        listaCampos();
        jj_consume_token(44);
        break;
        }
      case INT:{
        jj_consume_token(INT);
        break;
        }
      case real:{
        jj_consume_token(real);
        break;
        }
      case bool:{
        jj_consume_token(bool);
        break;
        }
      case string:{
        jj_consume_token(string);
        break;
        }
      case iden:{
        jj_consume_token(iden);
        break;
        }
      default:
        jj_la1[8] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("tipo2");
    }
}

  final public void listaCampos() throws ParseException {
    trace_call("listaCampos");
    try {

      campo();
      recCampo();
    } finally {
      trace_return("listaCampos");
    }
}

  final public void recCampo() throws ParseException {
    trace_call("recCampo");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 38:{
        jj_consume_token(38);
        campo();
        recCampo();
        break;
        }
      default:
        jj_la1[9] = jj_gen;

      }
    } finally {
      trace_return("recCampo");
    }
}

  final public void campo() throws ParseException {
    trace_call("campo");
    try {

      tipo0();
      jj_consume_token(iden);
    } finally {
      trace_return("campo");
    }
}

  final public void instrucciones() throws ParseException {
    trace_call("instrucciones");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case IF:
      case WHILE:
      case NEW:
      case delete:
      case read:
      case write:
      case nl:
      case call:
      case 45:{
        instruccionesAux();
        break;
        }
      default:
        jj_la1[10] = jj_gen;

      }
    } finally {
      trace_return("instrucciones");
    }
}

  final public void instruccionesAux() throws ParseException {
    trace_call("instruccionesAux");
    try {

      instruccion();
      recInstruccion();
    } finally {
      trace_return("instruccionesAux");
    }
}

  final public void recInstruccion() throws ParseException {
    trace_call("recInstruccion");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 35:{
        jj_consume_token(35);
        instruccion();
        recInstruccion();
        break;
        }
      default:
        jj_la1[11] = jj_gen;

      }
    } finally {
      trace_return("recInstruccion");
    }
}

  final public void instruccion() throws ParseException {
    trace_call("instruccion");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 45:{
        jj_consume_token(45);
        expr();
        break;
        }
      case IF:{
        jj_consume_token(IF);
        expr();
        bloque();
        factorIf();
        break;
        }
      case WHILE:{
        jj_consume_token(WHILE);
        expr();
        bloque();
        break;
        }
      case read:{
        jj_consume_token(read);
        expr();
        break;
        }
      case write:{
        jj_consume_token(write);
        expr();
        break;
        }
      case nl:{
        jj_consume_token(nl);
        break;
        }
      case NEW:{
        jj_consume_token(NEW);
        expr();
        break;
        }
      case delete:{
        jj_consume_token(delete);
        expr();
        break;
        }
      case call:{
        jj_consume_token(call);
        jj_consume_token(iden);
        paramsReales();
        break;
        }
      default:
        jj_la1[12] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("instruccion");
    }
}

  final public void factorIf() throws ParseException {
    trace_call("factorIf");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case ELSE:{
        jj_consume_token(ELSE);
        bloque();
        break;
        }
      default:
        jj_la1[13] = jj_gen;

      }
    } finally {
      trace_return("factorIf");
    }
}

  final public void paramsReales() throws ParseException {
    trace_call("paramsReales");
    try {

      jj_consume_token(36);
      paramsRealesAux();
      jj_consume_token(37);
    } finally {
      trace_return("paramsReales");
    }
}

  final public void paramsRealesAux() throws ParseException {
    trace_call("paramsRealesAux");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case not:
      case TRUE:
      case FALSE:
      case NULL:
      case iden:
      case literalEntero:
      case literalReal:
      case literalCadena:
      case 36:
      case 47:{
        paramsRealesLista();
        break;
        }
      default:
        jj_la1[14] = jj_gen;

      }
    } finally {
      trace_return("paramsRealesAux");
    }
}

  final public void paramsRealesLista() throws ParseException {
    trace_call("paramsRealesLista");
    try {

      expr();
      recParamReal();
    } finally {
      trace_return("paramsRealesLista");
    }
}

  final public void recParamReal() throws ParseException {
    trace_call("recParamReal");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case not:
      case TRUE:
      case FALSE:
      case NULL:
      case iden:
      case literalEntero:
      case literalReal:
      case literalCadena:
      case 36:
      case 47:{
        expr();
        recParamReal();
        break;
        }
      default:
        jj_la1[15] = jj_gen;

      }
    } finally {
      trace_return("recParamReal");
    }
}

  final public void expr() throws ParseException {
    trace_call("expr");
    try {

      e0();
    } finally {
      trace_return("expr");
    }
}

  final public void e0() throws ParseException {
    trace_call("e0");
    try {

      e1();
      factorE1();
    } finally {
      trace_return("e0");
    }
}

  final public void factorE1() throws ParseException {
    trace_call("factorE1");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case not:
      case TRUE:
      case FALSE:
      case NULL:
      case iden:
      case literalEntero:
      case literalReal:
      case literalCadena:
      case 36:
      case 47:{
        e0();
        factorE1();
        break;
        }
      default:
        jj_la1[16] = jj_gen;

      }
    } finally {
      trace_return("factorE1");
    }
}

  final public void e1() throws ParseException {
    trace_call("e1");
    try {

      e2();
      recOp1();
    } finally {
      trace_return("e1");
    }
}

  final public void recOp1() throws ParseException {
    trace_call("recOp1");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:{
        op1();
        e2();
        recOp1();
        break;
        }
      default:
        jj_la1[17] = jj_gen;

      }
    } finally {
      trace_return("recOp1");
    }
}

  final public void e2() throws ParseException {
    trace_call("e2");
    try {

      e3();
      factorE3();
      recSuma();
    } finally {
      trace_return("e2");
    }
}

  final public void recSuma() throws ParseException {
    trace_call("recSuma");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 46:{
        jj_consume_token(46);
        e3();
        recSuma();
        break;
        }
      default:
        jj_la1[18] = jj_gen;

      }
    } finally {
      trace_return("recSuma");
    }
}

  final public void factorE3() throws ParseException {
    trace_call("factorE3");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 47:{
        jj_consume_token(47);
        e3();
        break;
        }
      default:
        jj_la1[19] = jj_gen;

      }
    } finally {
      trace_return("factorE3");
    }
}

  final public void e3() throws ParseException {
    trace_call("e3");
    try {

      e4();
      factorE4();
    } finally {
      trace_return("e3");
    }
}

  final public void factorE4() throws ParseException {
    trace_call("factorE4");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case and:{
        jj_consume_token(and);
        e3();
        break;
        }
      case or:{
        jj_consume_token(or);
        e4();
        break;
        }
      default:
        jj_la1[20] = jj_gen;

      }
    } finally {
      trace_return("factorE4");
    }
}

  final public void e4() throws ParseException {
    trace_call("e4");
    try {

      e5();
      recOp4();
    } finally {
      trace_return("e4");
    }
}

  final public void recOp4() throws ParseException {
    trace_call("recOp4");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 54:
      case 55:
      case 56:{
        op4();
        e5();
        recOp4();
        break;
        }
      default:
        jj_la1[21] = jj_gen;

      }
    } finally {
      trace_return("recOp4");
    }
}

  final public void e5() throws ParseException {
    trace_call("e5");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case not:
      case 47:{
        op5();
        e5();
        break;
        }
      case TRUE:
      case FALSE:
      case NULL:
      case iden:
      case literalEntero:
      case literalReal:
      case literalCadena:
      case 36:{
        e6();
        break;
        }
      default:
        jj_la1[22] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("e5");
    }
}

  final public void e6() throws ParseException {
    trace_call("e6");
    try {

      e6Aux();
      recOp6();
    } finally {
      trace_return("e6");
    }
}

  final public void recOp6() throws ParseException {
    trace_call("recOp6");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 40:
      case 42:
      case 57:{
        op6();
        recOp6();
        break;
        }
      default:
        jj_la1[23] = jj_gen;

      }
    } finally {
      trace_return("recOp6");
    }
}

  final public void e6Aux() throws ParseException {
    trace_call("e6Aux");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 36:{
        jj_consume_token(36);
        e0();
        jj_consume_token(37);
        break;
        }
      case literalEntero:{
        jj_consume_token(literalEntero);
        break;
        }
      case literalReal:{
        jj_consume_token(literalReal);
        break;
        }
      case TRUE:{
        jj_consume_token(TRUE);
        break;
        }
      case FALSE:{
        jj_consume_token(FALSE);
        break;
        }
      case literalCadena:{
        jj_consume_token(literalCadena);
        break;
        }
      case iden:{
        jj_consume_token(iden);
        break;
        }
      case NULL:{
        jj_consume_token(NULL);
        break;
        }
      default:
        jj_la1[24] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("e6Aux");
    }
}

  final public void op1() throws ParseException {
    trace_call("op1");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 48:{
        jj_consume_token(48);
        break;
        }
      case 49:{
        jj_consume_token(49);
        break;
        }
      case 50:{
        jj_consume_token(50);
        break;
        }
      case 51:{
        jj_consume_token(51);
        break;
        }
      case 52:{
        jj_consume_token(52);
        break;
        }
      case 53:{
        jj_consume_token(53);
        break;
        }
      default:
        jj_la1[25] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op1");
    }
}

  final public void op4() throws ParseException {
    trace_call("op4");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 54:{
        jj_consume_token(54);
        break;
        }
      case 55:{
        jj_consume_token(55);
        break;
        }
      case 56:{
        jj_consume_token(56);
        break;
        }
      default:
        jj_la1[26] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op4");
    }
}

  final public void op5() throws ParseException {
    trace_call("op5");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 47:{
        jj_consume_token(47);
        break;
        }
      case not:{
        jj_consume_token(not);
        break;
        }
      default:
        jj_la1[27] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op5");
    }
}

  final public void op6() throws ParseException {
    trace_call("op6");
    try {

      switch ((jj_ntk==-1)?jj_ntk_f():jj_ntk) {
      case 40:{
        jj_consume_token(40);
        expr();
        jj_consume_token(41);
        break;
        }
      case 57:{
        jj_consume_token(57);
        jj_consume_token(iden);
        break;
        }
      case 42:{
        jj_consume_token(42);
        break;
        }
      default:
        jj_la1[28] = jj_gen;
        jj_consume_token(-1);
        throw new ParseException();
      }
    } finally {
      trace_return("op6");
    }
}

  /** Generated Token Manager. */
  public AnalizadorSintacticoTinyTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[29];
  static private int[] jj_la1_0;
  static private int[] jj_la1_1;
  static {
	   jj_la1_init_0();
	   jj_la1_init_1();
	}
	private static void jj_la1_init_0() {
	   jj_la1_0 = new int[] {0x5045e000,0x0,0x5045e000,0x4041e000,0x0,0x0,0x0,0x4041e000,0x4041e000,0x0,0x2fa80000,0x0,0x2fa80000,0x100000,0xc0021c00,0xc0021c00,0xc0021c00,0x0,0x0,0x0,0x300,0x0,0xc0021c00,0x0,0xc0021800,0x0,0x0,0x400,0x0,};
	}
	private static void jj_la1_init_1() {
	   jj_la1_1 = new int[] {0x400,0x8,0x400,0x400,0x40,0x80,0x100,0x400,0x0,0x40,0x2000,0x8,0x2000,0x0,0x8013,0x8013,0x8013,0x3f0000,0x4000,0x8000,0x0,0x1c00000,0x8013,0x2000500,0x13,0x3f0000,0x1c00000,0x8000,0x2000500,};
	}

  {
      enable_tracing();
  }
  /** Constructor with InputStream. */
  public AnalizadorSintacticoTiny(java.io.InputStream stream) {
	  this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public AnalizadorSintacticoTiny(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source = new AnalizadorSintacticoTinyTokenManager(this, jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
	  ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
	 try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
	 token_source.ReInit(this,jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public AnalizadorSintacticoTiny(java.io.Reader stream) {
	 jj_input_stream = new SimpleCharStream(stream, 1, 1);
	 token_source = new AnalizadorSintacticoTinyTokenManager(this, jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
	if (jj_input_stream == null) {
	   jj_input_stream = new SimpleCharStream(stream, 1, 1);
	} else {
	   jj_input_stream.ReInit(stream, 1, 1);
	}
	if (token_source == null) {
 token_source = new AnalizadorSintacticoTinyTokenManager(this, jj_input_stream);
	}

	 token_source.ReInit(this,jj_input_stream);
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public AnalizadorSintacticoTiny(AnalizadorSintacticoTinyTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(AnalizadorSintacticoTinyTokenManager tm) {
	 token_source = tm;
	 token = new Token();
	 jj_ntk = -1;
	 jj_gen = 0;
	 for (int i = 0; i < 29; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
	 Token oldToken;
	 if ((oldToken = token).next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 if (token.kind == kind) {
	   jj_gen++;
	   trace_token(token, "");
	   return token;
	 }
	 token = oldToken;
	 jj_kind = kind;
	 throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
	 if (token.next != null) token = token.next;
	 else token = token.next = token_source.getNextToken();
	 jj_ntk = -1;
	 jj_gen++;
	   trace_token(token, " (in getNextToken)");
	 return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
	 Token t = token;
	 for (int i = 0; i < index; i++) {
	   if (t.next != null) t = t.next;
	   else t = t.next = token_source.getNextToken();
	 }
	 return t;
  }

  private int jj_ntk_f() {
	 if ((jj_nt=token.next) == null)
	   return (jj_ntk = (token.next=token_source.getNextToken()).kind);
	 else
	   return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
	 jj_expentries.clear();
	 boolean[] la1tokens = new boolean[58];
	 if (jj_kind >= 0) {
	   la1tokens[jj_kind] = true;
	   jj_kind = -1;
	 }
	 for (int i = 0; i < 29; i++) {
	   if (jj_la1[i] == jj_gen) {
		 for (int j = 0; j < 32; j++) {
		   if ((jj_la1_0[i] & (1<<j)) != 0) {
			 la1tokens[j] = true;
		   }
		   if ((jj_la1_1[i] & (1<<j)) != 0) {
			 la1tokens[32+j] = true;
		   }
		 }
	   }
	 }
	 for (int i = 0; i < 58; i++) {
	   if (la1tokens[i]) {
		 jj_expentry = new int[1];
		 jj_expentry[0] = i;
		 jj_expentries.add(jj_expentry);
	   }
	 }
	 int[][] exptokseq = new int[jj_expentries.size()][];
	 for (int i = 0; i < jj_expentries.size(); i++) {
	   exptokseq[i] = jj_expentries.get(i);
	 }
	 return new ParseException(token, exptokseq, tokenImage);
  }

  private boolean trace_enabled;

/** Trace enabled. */
  final public boolean trace_enabled() {
	 return trace_enabled;
  }

  private int trace_indent = 0;
/** Enable tracing. */
  final public void enable_tracing() {
	 trace_enabled = true;
  }

/** Disable tracing. */
  final public void disable_tracing() {
	 trace_enabled = false;
  }

  protected void trace_call(String s) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Call:	" + s);
	 }
	 trace_indent = trace_indent + 2;
  }

  protected void trace_return(String s) {
	 trace_indent = trace_indent - 2;
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.println("Return: " + s);
	 }
  }

  protected void trace_token(Token t, String where) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Consumed token: <" + tokenImage[t.kind]);
	   if (t.kind != 0 && !tokenImage[t.kind].equals("\"" + t.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t.image) + "\"");
	   }
	   System.out.println(" at line " + t.beginLine + " column " + t.beginColumn + ">" + where);
	 }
  }

  protected void trace_scan(Token t1, int t2) {
	 if (trace_enabled) {
	   for (int i = 0; i < trace_indent; i++) { System.out.print(" "); }
	   System.out.print("Visited token: <" + tokenImage[t1.kind]);
	   if (t1.kind != 0 && !tokenImage[t1.kind].equals("\"" + t1.image + "\"")) {
		 System.out.print(": \"" + TokenMgrError.addEscapes(t1.image) + "\"");
	   }
	   System.out.println(" at line " + t1.beginLine + " column " + t1.beginColumn + ">; Expected token: <" + tokenImage[t2] + ">");
	 }
  }

}
