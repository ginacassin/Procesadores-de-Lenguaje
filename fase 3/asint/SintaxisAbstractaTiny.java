package asint;

public class SintaxisAbstractaTiny {

    private static void imprimeOpnd(Exp opnd, int np) {
        if (opnd.prioridad() < np) {System.out.println("(");};
        opnd.imprime();
        if (opnd.prioridad() < np) {System.out.println(")");};
    }
    private static void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1) {
        imprimeOpnd(opnd0,np0);
        System.out.println(op);
        imprimeOpnd(opnd1,np1);
    }

    private static void imprimeExpUnarioPrefijo(Exp opnd, String op, int np) {
        System.out.println(op);
        imprimeOpnd(opnd,np);
    }

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

        public abstract void procesa(Procesamiento p);


        public abstract void imprime();

        // Función auxiliar para generar vínculo al texto fuente de tipo
        // $f:num fila,c: num col$
        public String getFilaColInfo() {
            return "$f:" + leeFila() + ",c:" + leeCol() + "$";
        }
    }

    public static abstract class Exp extends Nodo {
        public Exp() {
            super();
        }
        public String getIden() {throw new UnsupportedOperationException();}
        public String valor() {throw new UnsupportedOperationException();}
        public Exp getOpnd0() {throw new UnsupportedOperationException();}
        public Exp getOpnd1() {throw new UnsupportedOperationException();}
        public String getNum() {throw new UnsupportedOperationException();}
        public String getCadena() {throw new UnsupportedOperationException();}
        public String getId() {throw new UnsupportedOperationException();}
        public abstract int prioridad();
    }

    private static abstract class ExpBin extends Exp {
        protected Exp opnd0;
        protected Exp opnd1;
        public ExpBin(Exp opnd0, Exp opnd1) {
            super();
            this.opnd0 = opnd0;
            this.opnd1 = opnd1;
        }
        public Exp getOpnd0() {
            return opnd0;
        }
        public Exp getOpnd1() {
            return opnd1;
        }
    }

    private static abstract class ExpUn extends Exp {
        protected Exp opnd0;
        public ExpUn(Exp opnd0) {
            super();
            this.opnd0 = opnd0;
        }
        public Exp getOpnd0() {
            return opnd0;
        }
    }

    public static class Suma extends ExpBin {
        public Suma(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "suma("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"+",opnd1,2,3);
        }
        @Override
        public int prioridad() {
            return 2;

        }
    }
    public static class Resta extends ExpBin {
        public Resta(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "resta("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"-",opnd1,3,3);
        }
        @Override
        public int prioridad() {
            return 2;

        }
    }
    public static class Mul extends ExpBin {
        public Mul(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mul("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"*",opnd1,4,5);
        }
        @Override
        public int prioridad() {
            return 4;

        }
    }
    public static class Div extends ExpBin {
        public Div(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "div("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"/",opnd1,4,5);
        }
        @Override
        public int prioridad() {
            return 4;

        }
    }
    public static class Asignacion extends ExpBin {
        public Asignacion(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "asig("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"=",opnd1,1,0);
        }
        @Override
        public int prioridad() {
            return 0;

        }
    }
    public static class Menor extends ExpBin {
        public Menor(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "menor("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"<",opnd1,1,2);
        }
        @Override
        public int prioridad() {
            return 1;

        }
    }
    public static class Mayor extends ExpBin {
        public Mayor(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mayor("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,">",opnd1,1,2);
        }
        @Override
        public int prioridad() {
            return 1;

        }
    }
    public static class MenorIgual extends ExpBin {
        public MenorIgual(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "menor_igual("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"<=",opnd1,1,2);
        }
        @Override
        public int prioridad() {
            return 1;

        }
    }
    public static class MayorIgual extends ExpBin {
        public MayorIgual(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mayor_igual("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,">=",opnd1,1,2);
        }
        @Override
        public int prioridad() {
            return 1;

        }
    }
    public static class Igual extends ExpBin {
        public Igual(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "igual("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"==",opnd1,1,2);
        }
        @Override
        public int prioridad() {
            return 1;

        }
    }
    public static class NoIgual extends ExpBin {
        public NoIgual(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "no_igual("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"!=",opnd1,1,2);
        }
        @Override
        public int prioridad() {
            return 1;

        }
    }
    public static class And extends ExpBin {
        public And(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "and("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"and",opnd1,4,3);
        }
        @Override
        public int prioridad() {
            return 3;

        }
    }
    public static class Or extends ExpBin {
        public Or(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "or("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"or",opnd1,4,4);
        }
        @Override
        public int prioridad() {
            return 3;

        }
    }
    public static class Mod extends ExpBin {
        public Mod(Exp opnd0, Exp opnd1) {
            super(opnd0,opnd1);
        }
        public String toString() {
            return "mod("+opnd0+","+opnd1+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpBin(opnd0,"%",opnd1,4,5);
        }
        @Override
        public int prioridad() {
            return 4;

        }
    }


    public static class Negativo extends ExpUn {
        public Negativo(Exp opnd0) {
            super(opnd0);
        }
        public String toString() {
            return "negativo("+opnd0+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeExpUnarioPrefijo(opnd0,"-",5);
        }
        @Override
        public int prioridad() {
            return 5;
        }
    }
    public static class Not extends ExpUn {
        public Not(Exp opnd0) {
            super(opnd0);
        }
        public String toString() {
            return "not("+opnd0+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
        @Override
        public void imprime() {
            imprimeExpUnarioPrefijo(opnd0,"not",5);
        }
        @Override
        public int prioridad() {
            return 5;
        }
    }
    public static class Indireccion extends ExpUn {
        public Indireccion(Exp opnd0) {
            super(opnd0);
        }
        public String toString() {
            return "indireccion("+opnd0+")";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeOpnd(opnd0,6);
            System.out.println("^" + getFilaColInfo());
        }
        @Override
        public int prioridad() {
            return 6;

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
        public Exp getIndex() {
            return index;
        }
        public Exp getOpnd() {
            return opnd;
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeOpnd(opnd,6);
            System.out.println("[" + getFilaColInfo());
            index.imprime();
            System.out.println("]");
        }
        @Override
        public int prioridad() {
            return 6;

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
        public String getIden() {
            return iden;
        }
        @Override
        public int prioridad() {
            return 6;
        }
        public Exp getOpnd() {
            return opnd;
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            imprimeOpnd(opnd,6);
            System.out.println(".");
            System.out.println(iden + getFilaColInfo());

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
        public String getNum() {
            return num;
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(num + getFilaColInfo());
        }
        @Override
        public int prioridad() {
            return 7;

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
        public String getNum() {
            return num;
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(num + getFilaColInfo());
        }
        @Override
        public int prioridad() {
            return 7;

        }
    }
    public static class True extends Exp {
        public True() {
            super();
        }
        public String toString() {
            return "true(["+leeFila()+","+leeCol()+"])";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public int prioridad() {
            return 7;
        }
        @Override
        public void imprime() {
            System.out.println("<true>" + getFilaColInfo());

        }
    }
    public static class False extends Exp {
        public False() {
            super();
        }
        public String toString() {
            return "false(["+leeFila()+","+leeCol()+"])";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public int prioridad() {
            return 7;
        }
        @Override
        public void imprime() {
            System.out.println("<false>" + getFilaColInfo());
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
        public String getCadena() {
            return cadena;
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(cadena + getFilaColInfo());
        }
        @Override
        public int prioridad() {
            return 7;

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
        public String getId() {
            return id;
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(id + getFilaColInfo());
        }
        @Override
        public int prioridad() {
            return 7;

        }
    }
    public static class Null extends Exp {
        public Null() {
            super();
        }
        public String toString() {
            return "null(["+leeFila()+","+leeCol()+"])";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public int prioridad() {
            return 7;
        }
        @Override
        public void imprime() {
            System.out.println("<null>" + getFilaColInfo());

        }
    }


    public static abstract class ParamsR extends Nodo {
        public ParamsR() {
            super();
        }
        public ParamsRL getParamrl() { throw new UnsupportedOperationException();}
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
        public ParamsRL getParamsrl() {
            return paramsrl;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            paramsrl.imprime();

        }
    }
    public static class No_ParamsR extends ParamsR {
        public No_ParamsR() {
            super();
        }

        @Override
        public void imprime() { }

        public String toString() {
            return "no_paramsR()";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }

    public static abstract class ParamsRL extends Nodo {
        public ParamsRL() {
            super();
        }
        public Exp getExp() { throw new UnsupportedOperationException();}
        public ParamsRL getParamrl() { throw new UnsupportedOperationException();}
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
        public Exp getExp() {
            return exp;
        }
        public ParamsRL getParamrl() {
            return paramrl;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            paramrl.imprime();
            System.out.println(",");
            exp.imprime();

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
        public Exp getExp() {
            return exp;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            exp.imprime();

        }
    }

    public static abstract class Insts extends Nodo {
        public Insts() {
            super();
        }
        public InstsAux getInstsAux() { throw new UnsupportedOperationException();}
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
        public InstsAux getInstsAux() {
            return instsAux;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            instsAux.imprime();

        }
    }
    public static class No_Instr extends Insts {
        public No_Instr() {
            super();
        }

        @Override
        public void imprime() { }

        public String toString() {
            return "no_instr()";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
        }
    }

    public static abstract class InstsAux extends Nodo {
        public InstsAux() {
            super();
        }
        public Inst getInst() { throw new UnsupportedOperationException();}
        public InstsAux getInstsAux() { throw new UnsupportedOperationException();}
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
        public Inst getInst() {
            return inst;
        }
        public InstsAux getInstsAux() {
            return instsAux;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            instsAux.imprime();
            System.out.println(";");
            inst.imprime();

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
        public Inst getInst() {
            return inst;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            inst.imprime();

        }
    }

    public static abstract class Inst extends Nodo {
        public Inst() {
            super();
        }
        public Exp getExp() { throw new UnsupportedOperationException();}
        public Bloq getBloq() { throw new UnsupportedOperationException();}
        public Bloq getBloq1() { throw new UnsupportedOperationException();}
        public Bloq getBloq2() { throw new UnsupportedOperationException();}
        public String getIden() { throw new UnsupportedOperationException();}
        public ParamsR getParamsR() { throw new UnsupportedOperationException();}
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
        public Exp getExp() {
            return exp;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("@");
            exp.imprime();

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
        public Bloq getBloq() {
            return bloq;
        }
        public Exp getExp() {
            return exp;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<if>");
            exp.imprime();
            bloq.imprime();

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
        public Bloq getBloq1() {
            return bloq1;
        }
        public Bloq getBloq2() {
            return bloq2;
        }
        public Exp getExp() {
            return exp;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<if>");
            exp.imprime();
            bloq1.imprime();
            System.out.println("<else>");
            bloq2.imprime();

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
        public Bloq getBloq() {
            return bloq;
        }
        public Exp getExp() {
            return exp;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<while>");
            exp.imprime();
            bloq.imprime();

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
        public Exp getExp() {
            return exp;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<read>");
            exp.imprime();

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
        public Exp getExp() {
            return exp;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<write>");
            exp.imprime();

        }
    }
    public static class Instr_Nl extends Inst {
        public Instr_Nl() {
            super();
        }

        @Override
        public void imprime() {
            System.out.println("<nl>");
        }

        public String toString() {
            return "instr_nl()";
        }
        public void procesa(Procesamiento p) {
            p.procesa(this);
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
        public Exp getExp() {
            return exp;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }


        @Override
        public void imprime() {
            System.out.println("<new>");
            exp.imprime();

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
        public Exp getExp() {
            return exp;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<delete>");
            exp.imprime();

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
        public String getIden() {
            return iden;
        }
        public ParamsR getParamsR() {
            return paramsR;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<call>");
            System.out.println(iden + getFilaColInfo());
            System.out.println("(");
            paramsR.imprime();
            System.out.println(")");

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
        public Bloq getBloq() {
            return bloq;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            bloq.imprime();
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
        public String getIden() {
            return iden;
        }
        public T getTipo() {
            return tipo;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println(iden + getFilaColInfo());

        }
    }

    public static abstract class LCampos extends Nodo {
        public LCampos() {
            super();
        }
        public Campo getCampo() { throw new UnsupportedOperationException();}
        public LCampos getlCampos() { throw new UnsupportedOperationException();}
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
        public Campo getCampo() {
            return campo;
        }
        public LCampos getlCampos() {
            return lCampos;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            lCampos.imprime();
            System.out.println(",");
            campo.imprime();

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
        public Campo getCampo() {
            return campo;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            campo.imprime();

        }
    }


    public static abstract class T extends Nodo {
        public T() {
            super();
        }
        public LCampos getlCampos() { throw new UnsupportedOperationException();}
        public T getTipo() { throw new UnsupportedOperationException();}
        public String getLitEnt() { throw new UnsupportedOperationException();}
        public String getIden() { throw new UnsupportedOperationException();}
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
        public String getLitEnt() {
            return litEnt;
        }
        public T getTipo() {
            return tipo;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println("[");
            System.out.println(litEnt);
            System.out.println("]" + getFilaColInfo());

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
        public T getTipo() {
            return tipo;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("^");
            tipo.imprime();

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
        public LCampos getlCampos() {
            return lCampos;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<struct>");
            System.out.println("{");
            lCampos.imprime();
            System.out.println("}");

        }
    }
    public static class TipoInt extends T {
        public TipoInt() {
            super();
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<int>");

        }
    }
    public static class TipoReal extends T {
        public TipoReal() {
            super();
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<real>");

        }
    }
    public static class TipoBool extends T {
        public TipoBool() {
            super();
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<bool>");

        }
    }
    public static class TipoString extends T {
        public TipoString() {
            super();
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<string>");

        }
    }
    public static class Identificador extends T {
        private String iden;
        public Identificador(String iden) {
            super();
            this.iden = iden;
        }
        public String toString() {
            return "tipo_iden("+iden+")";
        }
        public String getIden() {
            return iden;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println(iden + getFilaColInfo());

        }
    }


    public static abstract class Param extends Nodo {
        public Param() {
            super();
        }
        public String getIden() { throw new UnsupportedOperationException();}
        public T getTipo() { throw new UnsupportedOperationException();}
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
        public String getIden() {
            return iden;
        }
        public T getTipo() {
            return tipo;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println("&");
            System.out.println(iden + getFilaColInfo());

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
        public String getIden() {
            return iden;
        }
        public T getTipo() {
            return tipo;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println(iden + getFilaColInfo());

        }
    }

    public static abstract class ParamsF extends Nodo {
        public ParamsF() {
            super();
        }
        public ParamsFL getParamsFL() { throw new UnsupportedOperationException();}
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
        public ParamsFL getParamsFL() {
            return paramsFL;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            paramsFL.imprime();

        }
    }
    public static class NoParamF extends ParamsF {
        public NoParamF() {
            super();
        }
        public String toString() {
            return "no_paramF()";
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() { }

    }

    public static abstract class ParamsFL extends Nodo {
        public ParamsFL() {
            super();
        }
        public Param getParam() { throw new UnsupportedOperationException();}
        public ParamsFL getParamsFL() { throw new UnsupportedOperationException();}
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
        public Param getParam() {
            return param;
        }
        public ParamsFL getParamsFL() {
            return paramsFL;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            paramsFL.imprime();
            System.out.println(",");
            param.imprime();

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
        public Param getParam() {
            return param;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            param.imprime();

        }
    }

    public static abstract class Dec extends Nodo {
        public Dec() {
            super();
        }
        public String getIden() { throw new UnsupportedOperationException();}
        public T getTipo() { throw new UnsupportedOperationException();}
        public Bloq getBloq() { throw new UnsupportedOperationException();}
        public ParamsF getParamsF() { throw new UnsupportedOperationException();}
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
        public String getIden() {
            return iden;
        }
        public T getTipo() {
            return tipo;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            tipo.imprime();
            System.out.println(iden + getFilaColInfo());

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
        public String getIden() {
            return iden;
        }
        public T getTipo() {
            return tipo;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<type>");
            tipo.imprime();
            System.out.println(iden+getFilaColInfo());

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
        public Bloq getBloq() {
            return bloq;
        }
        public String getIden() {
            return iden;
        }
        public ParamsF getParamsF() {
            return paramsF;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("<proc>");
            System.out.println(iden + getFilaColInfo());
            System.out.println("(");
            paramsF.imprime();
            System.out.println(")");
            bloq.imprime();
        }
    }

    public static abstract class Decs extends Nodo {
        public Decs() {
            super();
        }
        public DecsAux getDecsAux() { throw new UnsupportedOperationException();}
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
        public DecsAux getDecsAux() {
            return decsAux;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            decsAux.imprime();
            System.out.println("&&");

        }
    }
    public static class NoDecs extends Decs {
        public NoDecs() {
            super();
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }


        @Override
        public void imprime() { }

    }

    public static abstract class DecsAux extends Nodo {
        public DecsAux() {
            super();
        }
        public Dec getDec() { throw new UnsupportedOperationException();}
        public DecsAux getDecsAux() { throw new UnsupportedOperationException();}
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
        public Dec getDec() {
            return dec;
        }
        public DecsAux getDecsAux() {
            return decsAux;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            decsAux.imprime();
            System.out.println(";");
            dec.imprime();

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
        public Dec getDec() {
            return dec;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            dec.imprime();

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
        public Decs getDecs() {
            return decs;
        }
        public Insts getInsts() {
            return insts;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            System.out.println("{");
            decs.imprime();
            insts.imprime();
            System.out.println("}");

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
        public Bloq getBloq() {
            return bloq;
        }

        public void procesa(Procesamiento p) {
            p.procesa(this);
        }

        @Override
        public void imprime() {
            bloq.imprime();
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
