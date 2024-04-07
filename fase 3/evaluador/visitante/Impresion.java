package evaluador.visitante;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class Impresion extends ProcesamientoDef{
    private void imprimeOpnd(Exp opnd, int np) {
        if(opnd.prioridad() < np) {System.out.print("(");};
        opnd.procesa(this);
        if(opnd.prioridad() < np) {System.out.print(")");};
    }
    private void imprimeExpBin(Exp opnd0, String op, Exp opnd1, int np0, int np1) {
        imprimeOpnd(opnd0,np0);
        System.out.print(" "+op+" ");
        imprimeOpnd(opnd1,np1);
    }
    private void imprimeExpUni(Exp opnd, String op, int np) {
        System.out.print(op+" ");
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
        System.out.print("[");
        System.out.print(exp.getIndex());
        System.out.print("]");
    }
    public void procesa(Acceso exp) {
        imprimeOpnd(exp.getOpnd(), exp.prioridad());
        System.out.print("." + exp.getIden());
    }
    public void procesa(Lit_ent exp) {
        System.out.print(exp.getNum());
    }
    public void procesa(Lit_real exp) {
        System.out.print(exp.getNum());
    }
    public void procesa(True exp) {
        System.out.print("true");
    }
    public void procesa(False exp) {
        System.out.print("false");
    }
    public void procesa(Lit_cadena exp) {
        System.out.print(exp.getCadena());
    }
    public void procesa(Iden exp) {
        System.out.print(exp.getId());
    }
    public void procesa(Null exp) {
        System.out.print("null");
    }
    public void procesa(Si_ParamsR exp) {
        exp.getParamsrl().procesa(this);
    }
    public void procesa(No_ParamsR exp) { }
    public void procesa(Muchos_ParamsR exp) {
        exp.getParamrl().procesa(this);
        System.out.print(",");
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
        System.out.print(";");
        exp.getInst().procesa(this);
    }
    public void procesa(Una_Instr exp) {
        exp.getInst().procesa(this);
    }
    public void procesa(Instr_Expr exp) {
        System.out.print("@");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_If exp) {
        System.out.print("if");
        exp.getExp().procesa(this);
        exp.getBloq().procesa(this);
    }
    public void procesa(Instr_If_Else exp) {
        System.out.print("if");
        exp.getExp().procesa(this);
        exp.getBloq1().procesa(this);
        System.out.print("else");
        exp.getBloq2().procesa(this);
    }
    public void procesa(Instr_While exp) {
        System.out.print("while");
        exp.getExp().procesa(this);
        exp.getBloq().procesa(this);
    }
    public void procesa(Instr_Read exp) {
        System.out.print("read");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_Write exp) {
        System.out.print("write");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_Nl exp) {
        System.out.print("nl");
    }
    public void procesa(Instr_New exp) {
        System.out.print("new");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_Del exp) {
        System.out.print("delete");
        exp.getExp().procesa(this);
    }
    public void procesa(Instr_Call exp) {
        System.out.print("call " + exp.getIden() + "(");
        exp.getParamsR().procesa(this);
        System.out.print(")");
    }
    public void procesa(Instr_Bloque exp) {
        exp.getBloq().procesa(this);
    }
    public void procesa(Campo exp) {
        exp.getTipo().procesa(this);
        System.out.print(exp.getIden());
    }
    public void procesa(Muchos_Campos exp) {
        exp.getlCampos().procesa(this);
        System.out.print(",");
        exp.getCampo().procesa(this);
    }
    public void procesa(Un_Campo exp) {
        exp.getCampo().procesa(this);
    }
    public void procesa(TipoArray exp) {
        exp.getTipo().procesa(this);
        System.out.print("[" + exp.getLitEnt() + "]");
    }
    public void procesa(TipoStruct exp) {
        System.out.print("struct{");
        exp.getlCampos().procesa(this);
        System.out.print("}");
    }
    public void procesa(TipoInt exp) {
        System.out.print("int");
    }
    public void procesa(TipoReal exp) {
        System.out.print("real");
    }
    public void procesa(TipoBool exp) {
        System.out.print("bool");
    }
    public void procesa(TipoString exp) {
        System.out.print("string");
    }
    public void procesa(Identificador exp) {
        System.out.print(exp.getIden());
    }
    public void procesa(ParamRef exp) {
        exp.getTipo().procesa(this);
        System.out.print("&");
        System.out.print(exp.getIden());
    }
    public void procesa(ParamNoRef exp) {
        exp.getTipo().procesa(this);
        System.out.print(exp.getIden());
    }
    public void procesa(SiParamF exp) {
        exp.getParamsFL().procesa(this);
    }
    public void procesa(NoParamF exp) { }
    public void procesa(MuchosParamsF exp) {
        exp.getParamsFL().procesa(this);
        System.out.print(",");
        exp.getParam().procesa(this);
    }
    public void procesa(UnParamF exp) {
        exp.getParam().procesa(this);
    }
    public void procesa(DecVar exp) {
        exp.getTipo().procesa(this);
        System.out.print(exp.getIden());
    }
    public void procesa(DecTipo exp) {
        System.out.print("type ");
        exp.getTipo().procesa(this);
        System.out.print(exp.getIden());
    }
    public void procesa(DecProc exp) {
        System.out.print("proc ");
        System.out.print(exp.getIden());
        System.out.print("(");
        exp.getParamsF().procesa(this);
        System.out.print(")");
        exp.getBloq().procesa(this);
    }
    public void procesa(SiDecs exp) {
        exp.getDecsAux().procesa(this);
        System.out.print("&&");
    }
    public void procesa(NoDecs exp) { }
    public void procesa(MuchasDecs exp) {
        exp.getDecsAux().procesa(this);
        System.out.print(";");
        System.out.println();
        exp.getDec().procesa(this);
    }
    public void procesa(UnaDec exp) {
        exp.getDec().procesa(this);
    }
    public void procesa(Bloq exp) {
        System.out.print("{");
        System.out.println();
        exp.getDecs().procesa(this);
        exp.getInsts().procesa(this);
        System.out.println();
        System.out.print("}");
    }
    public void procesa(Prog exp) {
        exp.getBloq().procesa(this);
    }
}
