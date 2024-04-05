package asint;


public class SintaxisAbstractaTiny {

    // clase que permite consultar las coordenadas del nodo en el texto
    // fuente para comunicar errores o consultar identificadores
    public static abstract class Nodo  {
        public Nodo() {
            fila=col=-1;
        }
        private int fila;
        private int col;
        public Nodo ponFila(int fila) {
            this.fila = fila;
            return this;
        }
        public Nodo ponCol(int col) {
            this.col = col;
            return this;
        }
        public int leeFila() {
            return fila;
        }
        public int leeCol() {
            return col;
        }
    }


    public static abstract class Exp extends Nodo {
        public Exp() {
            super();
        }
    }

    private static abstract class ExpBin extends Exp {
        protected Exp opnd0;
        protected Exp opnd1;
        public ExpBin(Exp opnd0, Exp opnd1) {
            super();
            this.opnd0 = opnd0;
            this.opnd1 = opnd1;
        }
    }

    private static abstract class ExpUn extends Exp {
        protected Exp opnd0;
        public ExpUn(Exp opnd0) {
            super();
            this.opnd0 = opnd0;
        }
    }

    public static class Suma extends ExpBin {
        public Suma(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "suma("+opnd0+","+opnd1+")";
        }
    }
    public static class Resta extends ExpBin {
        public Resta(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "resta("+opnd0+","+opnd1+")";
        }
    }
    public static class Mul extends ExpBin {
        public Mul(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mul("+opnd0+","+opnd1+")";
        }
    }
    public static class Div extends ExpBin {
        public Div(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "div("+opnd0+","+opnd1+")";
        }
    }
    public static class Asignacion extends ExpBin {
        public Asignacion(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "asig("+opnd0+","+opnd1+")";
        }
    }
    public static class Menor extends ExpBin {
        public Menor(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "menor("+opnd0+","+opnd1+")";
        }
    }
    public static class Mayor extends ExpBin {
        public Mayor(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mayor("+opnd0+","+opnd1+")";
        }
    }
    public static class MenorIgual extends ExpBin {
        public MenorIgual(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "menor_igual("+opnd0+","+opnd1+")";
        }
    }
    public static class MayorIgual extends ExpBin {
        public MayorIgual(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mayor_igual("+opnd0+","+opnd1+")";
        }
    }
    public static class Igual extends ExpBin {
        public Igual(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "igual("+opnd0+","+opnd1+")";
        }
    }
    public static class NoIgual extends ExpBin {
        public NoIgual(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "no_igual("+opnd0+","+opnd1+")";
        }
    }
    public static class And extends ExpBin {
        public And(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "and("+opnd0+","+opnd1+")";
        }
    }
    public static class Or extends ExpBin {
        public Or(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "or("+opnd0+","+opnd1+")";
        }
    }
    public static class Mod extends ExpBin {
        public Mod(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mod("+opnd0+","+opnd1+")";
        }
    }


    public static class Negativo extends ExpUn {
        public Negativo(Exp opnd0) {
            super(opnd0);
        }
        public String toString() {
            return "negativo("+opnd0+")";
        }
    }
    public static class Not extends ExpUn {
        public Not(Exp opnd0) {
            super(opnd0);
        }
        public String toString() {
            return "not("+opnd0+")";
        }
    }
    public static class Indireccion extends ExpUn {
        public Indireccion(Exp opnd0) {
            super(opnd0);
        }
        public String toString() {
            return "indireccion("+opnd0+")";
        }
    }

    public static class Index extends Exp {
        protected Exp opnd;
        protected Exp index;
        public Index(Exp opnd, Exp index) {
            super();
            this.index = index;
            this.opnd = opnd;
        }
        public String toString() {
            return "index("+opnd+","+index+")";
        }
    }

    public static class Acceso extends Exp {
        protected Exp opnd;
        protected String iden;
        public Acceso(Exp opnd, String iden) {
            super();
            this.opnd = opnd;
            this.iden = iden;
        }
        public String toString() {
            return "index("+opnd+","+iden+")";
        }
    }


    public static class Lit_ent extends Exp {
        private String num;
        public Lit_ent(String num) {
            super();
            this.num = num;
        }
        public String toString() {
            return "lit_ent("+num+"["+leeFila()+","+leeCol()+"])";
        }
    }
    public static class Lit_real extends Exp {
        private String num;
        public Lit_real(String num) {
            super();
            this.num = num;
        }
        public String toString() {
            return "lit_real("+num+"["+leeFila()+","+leeCol()+"])";
        }
    }
    public static class True extends Exp {
        public True() {
            super();
        }
        public String toString() {
            return "true(["+leeFila()+","+leeCol()+"])";
        }
    }
    public static class False extends Exp {
        public False() {
            super();
        }
        public String toString() {
            return "false(["+leeFila()+","+leeCol()+"])";
        }
    }
    public static class Lit_cadena extends Exp {
        private String cadena;
        public Lit_cadena(String cadena) {
            super();
            this.cadena = cadena;
        }
        public String toString() {
            return "lit_cadena("+cadena+"["+leeFila()+","+leeCol()+"])";
        }
    }
    public static class Iden extends Exp {
        private String id;
        public Iden(String id) {
            super();
            this.id = id;
        }
        public String toString() {
            return "iden("+id+"["+leeFila()+","+leeCol()+"])";
        }
    }
    public static class Null extends Exp {
        public Null() {
            super();
        }
        public String toString() {
            return "null(["+leeFila()+","+leeCol()+"])";
        }
    }



    public static abstract class ParamsR extends Nodo {
        public ParamsR() {
            super();
        }
    }
    public static class Si_ParamsR extends ParamsR {
        private ParamsRL paramsrl;
        public Si_ParamsR(ParamsRL paramsrl) {
            super();
            this.paramsrl = paramsrl;
        }
        public String toString() {
            return "si_paramsR("+paramsrl+")";
        }
    }
    public static class No_ParamsR extends ParamsR {
        public No_ParamsR() {
            super();
        }
        public String toString() {
            return "no_paramsR()";
        }
    }

    public static abstract class ParamsRL extends Nodo {
        public ParamsRL() {
            super();
        }
    }
    public static class Muchos_ParamsR extends ParamsRL {
        private ParamsRL paramrl;
        private Exp exp;
        public Muchos_ParamsR(ParamsRL paramrl, Exp exp) {
            super();
            this.paramrl = paramrl;
            this.exp = exp;
        }
        public String toString() {
            return "muchos_paramsR("+paramrl+","+exp+")";
        }
    }
    public static class Un_ParamsR extends ParamsRL {
        private Exp exp;
        public Un_ParamsR(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "un_paramsR("+exp+")";
        }
    }

    public static abstract class Insts extends Nodo {
        public Insts() {
            super();
        }
    }
    public static class Si_Instr extends Insts {
        private InstsAux instsAux;
        public Si_Instr(InstsAux instsAux) {
            super();
            this.instsAux = instsAux;
        }
        public String toString() {
            return "si_instr("+instsAux+")";
        }
    }
    public static class No_Instr extends Insts {
        public No_Instr() {
            super();
        }
        public String toString() {
            return "no_instr()";
        }
    }

    public static abstract class InstsAux extends Nodo {
        public InstsAux() {
            super();
        }
    }
    public static class Muchas_Instr extends InstsAux {
        private InstsAux instsAux;
        private Inst inst;
        public Muchas_Instr(InstsAux instsAux, Inst inst) {
            super();
            this.instsAux = instsAux;
            this.inst = inst;
        }
        public String toString() {
            return "Muchas_Instr("+instsAux+","+inst+")";
        }
    }
    public static class Una_Instr extends InstsAux {
        private Inst inst;
        public Una_Instr(Inst inst) {
            super();
            this.inst = inst;
        }
        public String toString() {
            return "una_instr("+inst+")";
        }
    }

    public static abstract class Inst extends Nodo {
        public Inst() {
            super();
        }
    }
    public static class Instr_Expr extends Inst {
        private Exp exp;
        public Instr_Expr(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "instr_expr("+exp+")";
        }
    }
    public static class Instr_If extends Inst {
        private Exp exp;
        private Bloq bloq;
        public Instr_If(Exp exp, Bloq bloq) {
            super();
            this.exp = exp;
            this.bloq = bloq;
        }
        public String toString() {
            return "instr_if("+exp+","+bloq+")";
        }
    }
    public static class Instr_If_Else extends Inst {
        private Exp exp;
        private Bloq bloq1;
        private Bloq bloq2;

        public Instr_If_Else(Exp exp, Bloq bloq1, Bloq bloq2) {
            super();
            this.exp = exp;
            this.bloq1 = bloq1;
            this.bloq2 = bloq2;
        }
        public String toString() {
            return "instr_if_else("+exp+","+bloq1+","+bloq2+")";
        }
    }
    public static class Instr_While extends Inst {
        private Exp exp;
        private Bloq bloq;
        public Instr_While(Exp exp, Bloq bloq) {
            super();
            this.exp = exp;
            this.bloq = bloq;
        }
        public String toString() {
            return "instr_while("+exp+","+bloq+")";
        }
    }
    public static class Instr_Read extends Inst {
        private Exp exp;
        public Instr_Read(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "instr_read("+exp+")";
        }
    }
    public static class Instr_Write extends Inst {
        private Exp exp;
        public Instr_Write(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "instr_write("+exp+")";
        }
    }
    public static class Instr_Nl extends Inst {
        public Instr_Nl() {
            super();
        }
        public String toString() {
            return "instr_nl()";
        }
    }
    public static class Instr_New extends Inst {
        private Exp exp;
        public Instr_New(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "instr_new("+exp+")";
        }
    }
    public static class Instr_Del extends Inst {
        private Exp exp;
        public Instr_Del(Exp exp) {
            super();
            this.exp = exp;
        }
        public String toString() {
            return "instr_del("+exp+")";
        }
    }
    public static class Instr_Call extends Inst {
        private String iden;
        private ParamsR paramsR;
        public Instr_Call(String iden, ParamsR paramsR) {
            super();
            this.iden = iden;
            this.paramsR = paramsR;
        }
        public String toString() {
            return "instr_call("+iden+","+paramsR+")";
        }
    }
    public static class Instr_Bloque extends Inst {
        private Bloq bloq;
        public Instr_Bloque(Bloq bloq) {
            super();
            this.bloq = bloq;
        }
        public String toString() {
            return "instr_bloque("+bloq+")";
        }
    }

    public static class Campo extends Nodo {
        private T tipo;
        private String iden;
        public Campo(T tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        public String toString() {
            return "campo("+tipo+","+iden+")";
        }
    }

    public static abstract class LCampos extends Nodo {
        public LCampos() {
            super();
        }
    }
    public static class Muchos_Campos extends LCampos {
        private LCampos lCampos;
        private Campo campo;
        public Muchos_Campos(LCampos lCampos, Campo campo) {
            super();
            this.campo = campo;
            this.lCampos = lCampos;
        }
        public String toString() {
            return "muchos_campos("+lCampos+","+campo+")";
        }
    }
    public static class Un_Campo extends LCampos {
        private Campo campo;
        public Un_Campo(Campo campo) {
            super();
            this.campo = campo;
        }
        public String toString() {
            return "un_campo("+campo+")";
        }
    }


    public static abstract class T extends Nodo {
        public T() {
            super();
        }
    }
    public static class TipoArray extends T {
        private T tipo;
        private String litEnt;
        public TipoArray(T tipo, String litEnt) {
            super();
            this.tipo = tipo;
            this.litEnt = litEnt;
        }
        public String toString() {
            return "tipo_array("+tipo+","+litEnt+")";
        }
    }
    public static class TipoPunt extends T {
        private T tipo;
        public TipoPunt(T tipo) {
            super();
            this.tipo = tipo;
        }
        public String toString() {
            return "tipo_punt("+tipo+")";
        }
    }
    public static class TipoStruct extends T {
        private LCampos lCampos;
        public TipoStruct(LCampos lCampos) {
            super();
            this.lCampos = lCampos;
        }
        public String toString() {
            return "tipo_struct("+lCampos+")";
        }
    }
    public static class TipoInt extends T {
        public TipoInt() {
            super();
        }
    }
    public static class TipoReal extends T {
        public TipoReal() {
            super();
        }
    }
    public static class TipoBool extends T {
        public TipoBool() {
            super();
        }
    }
    public static class TipoString extends T {
        public TipoString() {
            super();
        }
    }
    public static class Identificador extends T {
        private String iden;
        public Identificador(String iden) {
            super();
            this.iden = iden;
        }
        public String toString() {
            return "muchos_campos("+iden+")";
        }
    }


    public static abstract class Param extends Nodo {
        public Param() {
            super();
        }
    }
    public static class ParamRef extends Param {
        private T tipo;
        private String iden;
        public ParamRef(T tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        public String toString() {
            return "param_ref("+tipo+","+iden+")";
        }
    }
    public static class ParamNoRef extends Param {
        private T tipo;
        private String iden;
        public ParamNoRef(T tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        public String toString() {
            return "param("+tipo+","+iden+")";
        }
    }

    public static abstract class ParamsF extends Nodo {
        public ParamsF() {
            super();
        }
    }
    public static class SiParamF extends ParamsF {
        private ParamsFL paramsFL;
        public SiParamF(ParamsFL paramsFL) {
            super();
            this.paramsFL = paramsFL;
        }
        public String toString() {
            return "si_paramF("+paramsFL+")";
        }
    }
    public static class NoParamF extends ParamsF {
        public NoParamF() {
            super();
        }
        public String toString() {
            return "no_paramF()";
        }
    }

    public static abstract class ParamsFL extends Nodo {
        public ParamsFL() {
            super();
        }
    }
    public static class MuchosParamsF extends ParamsFL {
        private ParamsFL paramsFL;
        private Param param;
        public MuchosParamsF(ParamsFL paramsFL, Param param) {
            super();
            this.paramsFL = paramsFL;
            this.param = param;
        }
        public String toString() {
            return "muchos_paramsF("+paramsFL+","+param+")";
        }
    }
    public static class UnParamF extends ParamsFL {
        private Param param;
        public UnParamF(Param param) {
            super();
            this.param = param;
        }
        public String toString() {
            return "un_paramF("+param+")";
        }
    }

    public static abstract class Dec extends Nodo {
        public Dec() {
            super();
        }
    }
    public static class DecVar extends Dec {
        private T tipo;
        private String iden;
        public DecVar(T tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        public String toString() {
            return "dec_var("+tipo+","+iden+")";
        }
    }
    public static class DecTipo extends Dec {
        private T tipo;
        private String iden;
        public DecTipo(T tipo, String iden) {
            super();
            this.tipo = tipo;
            this.iden = iden;
        }
        public String toString() {
            return "dec_tipo("+tipo+","+iden+")";
        }
    }
    public static class DecProc extends Dec {
        private String iden;
        private ParamsF paramsF;
        private Bloq bloq;
        public DecProc(String iden, ParamsF paramsF, Bloq bloq) {
            super();
            this.paramsF = paramsF;
            this.iden = iden;
            this.bloq = bloq;
        }
        public String toString() {
            return "dec_proc("+iden+","+paramsF+","+bloq+")";
        }
    }

    public static abstract class Decs extends Nodo {
        public Decs() {
            super();
        }
    }
    public static class SiDecs extends Decs {
        private DecsAux decsAux;
        public SiDecs(DecsAux decsAux) {
            super();
            this.decsAux = decsAux;
        }
        public String toString() {
            return "si_decs("+decsAux+")";
        }
    }
    public static class NoDecs extends Decs {
        public NoDecs() {
            super();
        }
    }

    public static abstract class DecsAux extends Nodo {
        public DecsAux() {
            super();
        }
    }
    public static class MuchasDecs extends DecsAux {
        private DecsAux decsAux;
        private Dec dec;
        public MuchasDecs(DecsAux decsAux, Dec dec) {
            super();
            this.decsAux = decsAux;
            this.dec = dec;
        }
        public String toString() {
            return "muchas_decs("+decsAux+","+dec+")";
        }
    }
    public static class UnaDec extends DecsAux {
        private Dec dec;
        public UnaDec(Dec dec) {
            super();
            this.dec = dec;
        }
        public String toString() {
            return "una_dec("+dec+")";
        }
    }

    public static class Bloq extends Nodo {
        private Insts insts;
        private Decs decs;
        public Bloq(Decs decs, Insts insts) {
            super();
            this.insts = insts;
            this.decs = decs;
        }
        public String toString() {
            return "bloq("+decs+","+insts+")";
        }
    }

    public static class Prog extends Nodo {
        private Bloq bloq;
        public Prog(Bloq bloq) {
            super();
            this.bloq = bloq;
        }
        public String toString() {
            return "prog("+bloq+")";
        }
    }


    // Constructoras
    public Prog prog(Bloq bloq) {
        return new Prog(bloq);
    }
    public Bloq bloq(Decs decs, Insts insts) {
        return new Bloq(decs, insts);
    }
    public Decs si_decs(DecsAux decsAux) {
        return new SiDecs(decsAux);
    }
    public Decs no_decs() {
        return new NoDecs();
    }
    public DecsAux muchas_decs(DecsAux decsAux, Dec dec) {
        return new MuchasDecs(decsAux, dec);
    }
    public DecsAux una_dec(Dec dec) {
        return new UnaDec(dec);
    }
    public Dec dec_var(T tipo, String iden) {
        return new DecVar(tipo, iden);
    }
    public Dec dec_tipo(T tipo, String iden) {
        return new DecTipo(tipo, iden);
    }
    public Dec dec_proc(String iden, ParamsF paramsF, Bloq bloq) {
        return new DecProc(iden, paramsF, bloq);
    }
    public ParamsF si_paramF(ParamsFL paramsFL) {
        return new SiParamF(paramsFL);
    }
    public ParamsF no_paramF() {
        return new NoParamF();
    }
    public ParamsFL muchos_paramsF(ParamsFL paramsFL, Param param) {
        return new MuchosParamsF(paramsFL, param);
    }
    public ParamsFL un_paramF(Param param) {
        return new UnParamF(param);
    }
    public Param param_ref(T tipo, String iden) {
        return new ParamRef(tipo, iden);
    }
    public Param param(T tipo, String iden) {
        return new ParamNoRef(tipo, iden);
    }
    public T tipo_array(T tipo, String iden) {
        return new TipoArray(tipo, iden);
    }
    public T tipo_punt(T tipo) {
        return new TipoPunt(tipo);
    }
    public T tipo_struct(LCampos lCampos) {
        return new TipoStruct(lCampos);
    }
    public T ent() {
        return new TipoInt();
    }
    public T real() {
        return new TipoReal();
    }
    public T bool() {
        return new TipoBool();
    }
    public T tipo_string() {
        return new TipoString();
    }
    public T tipo_iden(String iden) {
        return new Identificador(iden);
    }
    public LCampos muchos_campos(LCampos lCampos, Campo campo) {
        return new Muchos_Campos(lCampos, campo);
    }
    public LCampos un_campo(Campo campo) {
        return new Un_Campo(campo);
    }
    public Campo campo(T tipo, String iden) {
        return new Campo(tipo, iden);
    }
    public Insts si_instr(InstsAux instsAux) {
        return new Si_Instr(instsAux);
    }
    public Insts no_instr() {
        return new No_Instr();
    }
    public InstsAux muchas_instr(InstsAux instsAux, Inst inst) {
        return new Muchas_Instr(instsAux, inst);
    }
    public InstsAux una_instr(Inst inst) {
        return new Una_Instr(inst);
    }
    public Inst instr_expr(Exp exp) {
        return new Instr_Expr(exp);
    }
    public Inst instr_if(Exp exp, Bloq bloq) {
        return new Instr_If(exp, bloq);
    }
    public Inst instr_if_else(Exp exp, Bloq bloq1, Bloq bloq2) {
        return new Instr_If_Else(exp, bloq1, bloq2);
    }
    public Inst instr_while(Exp exp, Bloq bloq) {
        return new Instr_While(exp, bloq);
    }
    public Inst instr_read(Exp exp) {
        return new Instr_Read(exp);
    }
    public Inst instr_write(Exp exp) {
        return new Instr_Write(exp);
    }
    public Inst instr_nl() {
        return new Instr_Nl();
    }
    public Inst instr_new(Exp exp) {
        return new Instr_New(exp);
    }
    public Inst instr_del(Exp exp) {
        return new Instr_Del(exp);
    }
    public Inst instr_call(String iden, ParamsR paramsR) {
        return new Instr_Call(iden, paramsR);
    }
    public Inst instr_bloque(Bloq bloq) {
        return new Instr_Bloque(bloq);
    }
    public ParamsR si_paramsR(ParamsRL paramsRL) {
        return new Si_ParamsR(paramsRL);
    }
    public ParamsR no_paramsR() {
        return new No_ParamsR();
    }
    public ParamsRL muchos_paramsR(ParamsRL paramsRL, Exp exp) {
        return new Muchos_ParamsR(paramsRL, exp);
    }
    public ParamsRL un_paramsR(Exp exp) {
        return new Un_ParamsR(exp);
    }
    public Exp asig(Exp exp1, Exp exp2) {
        return new Asignacion(exp1, exp2);
    }
    public Exp menor(Exp exp1, Exp exp2) {
        return new Menor(exp1, exp2);
    }
    public Exp mayor(Exp exp1, Exp exp2) {
        return new Mayor(exp1, exp2);
    }
    public Exp menor_igual(Exp exp1, Exp exp2) {
        return new MenorIgual(exp1, exp2);
    }
    public Exp mayor_igual(Exp exp1, Exp exp2) {
        return new MayorIgual(exp1, exp2);
    }
    public Exp igual(Exp exp1, Exp exp2) {
        return new Igual(exp1, exp2);
    }
    public Exp no_igual(Exp exp1, Exp exp2) {
        return new NoIgual(exp1, exp2);
    }
    public Exp suma(Exp exp1, Exp exp2) {
        return new Suma(exp1, exp2);
    }
    public Exp resta(Exp exp1, Exp exp2) {
        return new Resta(exp1, exp2);
    }
    public Exp and(Exp exp1, Exp exp2) {
        return new And(exp1, exp2);
    }
    public Exp or(Exp exp1, Exp exp2) {
        return new Or(exp1, exp2);
    }
    public Exp mult(Exp exp1, Exp exp2) {
        return new Mul(exp1, exp2);
    }
    public Exp div(Exp exp1, Exp exp2) {
        return new Div(exp1, exp2);
    }
    public Exp mod(Exp exp1, Exp exp2) {
        return new Mod(exp1, exp2);
    }
    public Exp negativo(Exp exp) {
        return new Negativo(exp);
    }
    public Exp not(Exp exp) {
        return new Not(exp);
    }
    public Exp index(Exp exp1, Exp exp2) {
        return new Index(exp1, exp2);
    }
    public Exp acceso(Exp exp, String iden) {
        return new Acceso(exp, iden);
    }
    public Exp indireccion(Exp exp) {
        return new Indireccion(exp);
    }
    public Exp lit_ent(String iden) {
        return new Lit_ent(iden);
    }
    public Exp lit_real(String iden) {
        return new Lit_real(iden);
    }
    public Exp btrue() {
        return new True();
    }
    public Exp bfalse() {
        return new False();
    }
    public Exp lit_cadena(String iden) {
        return new Lit_cadena(iden);
    }
    public Exp iden(String iden) {
        return new Iden(iden);
    }
    public Exp vnull() {
        return new Null();
    }
}
