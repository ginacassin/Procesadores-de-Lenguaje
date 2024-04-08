package evaluador.visitante;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class Impresion extends ProcesamientoDef{
    private void imprimeOpnd(Exp opnd, int np) {
        if(opnd.prioridad() < np) {System.out.println("(");};
        opnd.procesa(this);
        if(opnd.prioridad() < np) {System.out.println(")");};
    }
    private void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1) {
        imprimeOpnd(opnd0,np0);
        System.out.println(" "+op+" ");
        imprimeOpnd(opnd1,np1);
    }
    private void imprimeExpUni(Exp opnd, String op, int np) {
        System.out.println(op+" ");
        imprimeOpnd(opnd,np);
    }
    public void procesa(Suma exp) {
        imprimeExpBin(exp.getOpnd0(), "+", exp.getOpnd1(), 2, 3);
    }
    public void procesa(Resta exp) {
        imprimeExpBin(exp.getOpnd0(), "-", exp.getOpnd1(), 3, 3);
    }
    public void procesa(Mul exp) {
        imprimeExpBin(exp.getOpnd0(), "*", exp.getOpnd1(), 4, 5);
    }
    public void procesa(Div exp) {
        imprimeExpBin(exp.getOpnd0(), "/", exp.getOpnd1(), 4, 5);
    }
    public void procesa(Asignacion exp) {
        imprimeExpBin(exp.getOpnd0(), "=", exp.getOpnd1(), 1, 0);
    }
    public void procesa(Menor exp) {
        imprimeExpBin(exp.getOpnd0(), "<", exp.getOpnd1(), 1, 2);
    }
    public void procesa(Mayor exp) {
        imprimeExpBin(exp.getOpnd0(), ">", exp.getOpnd1(), 1, 2);
    }
    public void procesa(MenorIgual exp) {
        imprimeExpBin(exp.getOpnd0(), "<=", exp.getOpnd1(), 1, 2);
    }
    public void procesa(MayorIgual exp) {
        imprimeExpBin(exp.getOpnd0(), ">=", exp.getOpnd1(), 1, 2);
    }
    public void procesa(Igual exp) {
        imprimeExpBin(exp.getOpnd0(), "==", exp.getOpnd1(), 1, 2);
    }
    public void procesa(NoIgual exp) {
        imprimeExpBin(exp.getOpnd0(), "!=", exp.getOpnd1(), 1, 2);
    }
    public void procesa(And exp) {
        imprimeExpBin(exp.getOpnd0(), "and", exp.getOpnd1(), 4, 3);
    }
    public void procesa(Or exp) {
        imprimeExpBin(exp.getOpnd0(), "and", exp.getOpnd1(), 4, 3);
    }
    public void procesa(Mod exp) {
        imprimeExpBin(exp.getOpnd0(), "and", exp.getOpnd1(), 4, 5);
    }
    public void procesa(Negativo exp) {
        imprimeExpUni(exp.getOpnd0(), "-", 5);
    }
    public void procesa(Not exp) {
        imprimeExpUni(exp.getOpnd0(), "not", 5);
    }
    public void procesa(Indireccion exp) {
        imprimeExpUni(exp.getOpnd0(), "^", 6);
    }
    public void procesa(Index exp) {
        imprimeOpnd(exp.getOpnd(), exp.prioridad());
        System.out.println("[");
        System.out.println(exp.getIndex());
        System.out.println("]");
    }
    public void procesa(Acceso exp) {
        imprimeOpnd(exp.getOpnd(), exp.prioridad());
        System.out.println("." + exp.getIden());
    }
    public void procesa(Lit_ent exp) {
        System.out.println(exp.getNum());
    }
    public void procesa(Lit_real exp) {
        System.out.println(exp.getNum());
    }
    public void procesa(True exp) {
        System.out.println("true");
    }
    public void procesa(False exp) {
        System.out.println("false");
    }
    public void procesa(Lit_cadena exp) {
        System.out.println(exp.getCadena());
    }
    public void procesa(Iden exp) {
        System.out.println(exp.getId());
    }
    public void procesa(Null exp) {
        System.out.println("null");
    }
    public void procesa(Si_ParamsR exp) {
        exp.getParamsrl().procesa(this);
    }
    public void procesa(No_ParamsR exp) { }
    public void procesa(Muchos_ParamsR exp) {
        exp.getParamrl().procesa(this);
        System.out.println(",");
        exp.getExp().procesa(this);
    }
    public void procesa(Un_ParamsR exp) {
        exp.getExp().procesa(this);
    }
    public void procesa(Si_Instr exp) {
        exp.getInstsAux().procesa(this);
    }
    public void procesa(No_Instr exp) { }
    public void procesa(Muchas_Instr exp) {
        exp.getInstsAux().procesa(this);
        System.out.println(";");
        exp.getInst().procesa(this);
    }
    public void procesa(Una_Instr exp) {
        exp.getInst().procesa(this);
    }
    public void procesa(Instr_Expr exp) {
        System.out.println("@");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_If exp) {
        System.out.println("if");
        exp.getExp().procesa(this);
        exp.getBloq().procesa(this);
    }
    public void procesa(Instr_If_Else exp) {
        System.out.println("if");
        exp.getExp().procesa(this);
        exp.getBloq1().procesa(this);
        System.out.println("else");
        exp.getBloq2().procesa(this);
    }
    public void procesa(Instr_While exp) {
        System.out.println("while");
        exp.getExp().procesa(this);
        exp.getBloq().procesa(this);
    }
    public void procesa(Instr_Read exp) {
        System.out.println("read");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_Write exp) {
        System.out.println("write");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_Nl exp) {
        System.out.println("nl");
    }
    public void procesa(Instr_New exp) {
        System.out.println("new");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_Del exp) {
        System.out.println("delete");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_Call exp) {
        System.out.println("call " + exp.getIden() + "(");
        exp.getParamsR().procesa(this);
        System.out.println(")");
    }
    public void procesa(Instr_Bloque exp) {
        exp.getBloq().procesa(this);
    }
    public void procesa(Campo exp) {
        exp.getTipo().procesa(this);
        System.out.println(exp.getIden());
    }
    public void procesa(Muchos_Campos exp) {
        exp.getlCampos().procesa(this);
        System.out.println(",");
        exp.getCampo().procesa(this);
    }
    public void procesa(Un_Campo exp) {
        exp.getCampo().procesa(this);
    }
    public void procesa(TipoArray exp) {
        exp.getTipo().procesa(this);
        System.out.println("[" + exp.getLitEnt() + "]");
    }
    public void procesa(TipoPunt exp) {
        System.out.println("^");
        exp.getTipo().procesa(this);
    }
    public void procesa(TipoStruct exp) {
        System.out.println("struct{");
        exp.getlCampos().procesa(this);
        System.out.println("}");
    }
    public void procesa(TipoInt exp) {
        System.out.println("int");
    }
    public void procesa(TipoReal exp) {
        System.out.println("real");
    }
    public void procesa(TipoBool exp) {
        System.out.println("bool");
    }
    public void procesa(TipoString exp) {
        System.out.println("string");
    }
    public void procesa(Identificador exp) {
        System.out.println(exp.getIden());
    }
    public void procesa(ParamRef exp) {
        exp.getTipo().procesa(this);
        System.out.println("&");
        System.out.println(exp.getIden());
    }
    public void procesa(ParamNoRef exp) {
        exp.getTipo().procesa(this);
        System.out.println(exp.getIden());
    }
    public void procesa(SiParamF exp) {
        exp.getParamsFL().procesa(this);
    }
    public void procesa(NoParamF exp) { }
    public void procesa(MuchosParamsF exp) {
        exp.getParamsFL().procesa(this);
        System.out.println(",");
        exp.getParam().procesa(this);
    }
    public void procesa(UnParamF exp) {
        exp.getParam().procesa(this);
    }
    public void procesa(DecVar exp) {
        exp.getTipo().procesa(this);
        System.out.println(exp.getIden());
    }
    public void procesa(DecTipo exp) {
        System.out.println("type ");
        exp.getTipo().procesa(this);
        System.out.println(exp.getIden());
    }
    public void procesa(DecProc exp) {
        System.out.println("proc ");
        System.out.println(exp.getIden());
        System.out.println("(");
        exp.getParamsF().procesa(this);
        System.out.println(")");
        exp.getBloq().procesa(this);
    }
    public void procesa(SiDecs exp) {
        exp.getDecsAux().procesa(this);
        System.out.println("&&");
    }
    public void procesa(NoDecs exp) { }
    public void procesa(MuchasDecs exp) {
        exp.getDecsAux().procesa(this);
        System.out.println(";");
        System.out.println();
        exp.getDec().procesa(this);
    }
    public void procesa(UnaDec exp) {
        exp.getDec().procesa(this);
    }
    public void procesa(Bloq exp) {
        System.out.println("{");
        System.out.println();
        exp.getDecs().procesa(this);
        exp.getInsts().procesa(this);
        System.out.println();
        System.out.println("}");
    }
    public void procesa(Prog exp) {
        exp.getBloq().procesa(this);
    }

}
