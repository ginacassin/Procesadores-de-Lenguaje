package visitantes;

import asint.SintaxisAbstractaTiny;
import asint.SintaxisAbstractaTiny.*;
import maquinaP.MaquinaP;

import java.util.Stack;

public class Etiquetado extends RecolectaDef{
    private Stack<DecProc> sub_pendientes;
    private MaquinaP maquinaP;
    private int etq;
    public Etiquetado(MaquinaP maquinaP){
        this.maquinaP = maquinaP;
        this.etq = 0;
        sub_pendientes = new Stack<DecProc>();
    }

    void etiquetado(Prog prog){
        DecProc sub;
        prog.setPrim(etq);
        etiquetado(prog.getBloq());
        while(!sub_pendientes.empty()){
            sub = sub_pendientes.pop();
            sub.setPrim(etq);
            etq++;
            etiquetado(sub.getBloq());
            etq++;
            sub.setSig(etq);
        }
        prog-setSig(etq);
    }

    void etiquetado(Bloq bloq){
        bloq.setPrim(etq);
        recolecta_subs(bloq.getDecs());
        etiquetado(bloq.getInsts());
        etq++;
        bloq.setSig(etq);
    }
    void etiquetado(Si_Instr siInstr){
        etiquetado(siInstr.getInstsAux());
    }

    void etiquetado(No_Instr noInstr){
        //noop
    }

    void etiquetado(Muchas_Instr muchasInstr){
        muchasInstr.setPrim(etq);
        etiquetado(muchasInstr.getInstsAux()):
        etiquetado(muchasInstr.getInst());
        muchasInstr.setSig(etq);
    }

    void etiquetado(Una_Instr unaInstr){
        unaInstr.setPrim(etq);
        etiquetado(unaInstr.getInst()):
        unaInstr.setSig(etq);
    }

    void etiquetado(Instr_Expr instrExpr){
        instrExpr.setPrim(etq);
        etiquetado(instrExpr.getExp());
        etq++;
        instrExpr.setSig(etq);
    }

    void etiquetado(Instr_If instrIf){
        instrIf.setPrim(etq);
        etiquetado(instrIf.getExp());
        etiquetado_acc_val(instrIf.getExp());
        etq++;
        etiquetado(instrIf.getBloq());
        instrIf.setSig(etq);
    }

    void etiquetado(Instr_If_Else instrIfElse){
        instrIfElse.setPrim(etq);
        etiquetado(instrIfElse.getExp());
        etiquetado_acc_val(instrIfElse.getExp());
        etq++;
        etiquetado(instrIfElse.getBloq1());
        instrIfElse.setElse(etq);
        etq++;
        etiquetado(instrIfElse.getBloq2());
        instrIfElse.setSig(etq);
    }

    void etiquetado(Instr_While instrWhile){
        instrWhile.setPrim(etq);
        etiquetado(instrWhile.getExp());
        etiquetado_acc_val(instrWhile.getExp());
        etq++;
        etiquetado(instrWhile.getBloq());
        etq++;
        instrWhile.setSig(etq);
    }

    void etiquetado(Instr_Read instrRead){
        instrRead.setPrim(etq);
        etiquetado(instrRead.getExp());
        etiquetado_acc_val(instrRead.getExp());
        etq++;
        instrRead.setSig(etq);
    }

    void etiquetado(Instr_Write instrWrite){
        instrWrite.setPrim(etq);
        etiquetado(instrWrite.getExp());
        etiquetado_acc_val(instrWrite.getExp());
        etq++;
        instrWrite.setSig(etq);
    }

    void etiquetado(Instr_Nl instrNl){
        instrNl.setPrim(etq);
        etq++;
        instrNl.setSig(etq);
    }

    void etiquetado(Instr_New instrNew){
        instrNew.setPrim(etq);
        etiquetado(instrNew.getExp());
        etq += 2;
        instrNew.setSig(etq);
    }

    void etiquetado(Instr_Del instrDel){
        instrDel.setPrim(etq);
        etiquetado(instrDel.getExp());
        etq += 2;
        instrDel.setSig(etq);
    }

    void etiquetado(Instr_Call instrCall){
        instrCall.setPrim(etq);
        etq++;
        gen_paso_param(instrCall.getVinculo(), instrCall.getParamsR());
        etq++;
        instrCall.setSig(etq);
    }

    void etiquetado(Instr_Bloque instrBloque){
        instrBloque.setPrim(etq);
        etiquetado(instrBloque.getBloq());
        instrBloque.setSig(etq);
    }

    void etiquetado(Si_ParamsR siParamsR){
        siParamsR.setPrim(etq);
        etiquetado(siParamsR.getParamsrl());
        siParamsR.setSig(etq);
    }

    void etiquetado(No_ParamsR noParamsR){
        noParamsR.setPrim(etq);
        noParamsR.setSig(etq);
    }

    void etiquetado(Muchos_ParamsR muchosParamsR){
        muchosParamsR.setPrim(etq);
        etiquetado(muchosParamsR.getParamrl());
        etiquetado(muchosParamsR.getExp());
        muchosParamsR.setSig(etq);
    }

    void etiquetado(Un_ParamsR unParamsR){
        unParamsR.setPrim(etq);
        etiquetado(unParamsR.getExp());
        unParamsR.setSig(etq);
    }

    void etiquetado(Asignacion asignacion){
        asignacion.setPrim(etq);
        etiquetado(asignacion.getOpnd0());
        etiquetado(asignacion.getOpnd1());
        //TODO mirar donde esta lo de ref
        asignacion.setSig(etq);
    }

    void etiquetado(Menor menor){
        menor.setPrim(etq);
        etiquetado_opnds(menor.getOpnd0(), menor.getOpnd1());
        etq++;
        menor.setSig(etq);
    }

    void etiquetado(Mayor mayor){
        mayor.setPrim(etq);
        etiquetado_opnds(mayor.getOpnd0(), mayor.getOpnd1());
        etq++;
        mayor.setSig(etq);
    }

    void etiquetado(MenorIgual menorIgual){
        menorIgual.setPrim(etq);
        etiquetado_opnds(menorIgual.getOpnd0(), menorIgual.getOpnd1());
        etq++;
        menorIgual.setSig(etq);
    }

    void etiquetado(MayorIgual mayorIgual){
        mayorIgual.setPrim(etq);
        etiquetado_opnds(mayorIgual.getOpnd0(), mayorIgual.getOpnd1());
        etq++;
        mayorIgual.setSig(etq);
    }

    void etiquetado(Igual igual){
        igual.setPrim(etq);
        etiquetado_opnds(igual.getOpnd0(), igual.getOpnd1());
        etq+;
        igual.setSig(etq);
    }

    void etiquetado(NoIgual noIgual){
        noIgual.setPrim(etq);
        etiquetado_opnds(noIgual.getOpnd0(), noIgual.getOpnd1());
        etq++;
        noIgual.setSig(etq);
    }

    void etiquetado(Suma suma){
        suma.setPrim(etq);
        etiquetado_opnds(suma.getOpnd0(), suma.getOpnd1());
        etq+;
        suma.setSig(etq);
    }

    void etiquetado(Resta resta){
        resta.setPrim(etq);
        etiquetado_opnds(resta.getOpnd0(), resta.getOpnd1());
        etq++;
        resta.setSig(etq);
    }

    void etiquetado(And and){
        and.setPrim(etq);
        etiquetado_opnds(and.getOpnd0(), and.getOpnd1());
        etq++;
        and.setSig(etq);
    }

    void etiquetado(Or or){
        or.setPrim(etq);
        etiquetado_opnds(or.getOpnd0(), or.getOpnd1());
        etq++;
        or.setSig(etq);
    }

    void etiquetado(Mul mul){
        mul.setPrim(etq);
        etiquetado_opnds(mul.getOpnd0(), mul.getOpnd1());
        etq++;
        mul.setSig(etq);
    }

    void etiquetado(Div div){
        div.setPrim(etq);
        etiquetado_opnds(div.getOpnd0(), div.getOpnd1());
        etq++;
        div.setSig(etq);
    }

    void etiquetado(Mod mod){
        mod.setPrim(etq);
        etiquetado_opnds(mod.getOpnd0(), mod.getOpnd1());
        etq++;
        mod.setSig(etq);
    }

    void etiquetado(Negativo negativo){
        negativo.setPrim(etq);
        etiquetado(negativo.getOpnd0());
        etiquetado_acc_val(negativo.getOpnd0());
        etq++;
        negativo.setSig(etq);
    }

    void etiquetado(Not not){
        not.setPrim(etq);
        etiquetado(not.getOpnd0());
        etiquetado_acc_val(not.getOpnd0());
        etq++;
        not.setSig(etq);
    }

    void etiquetado(Index index){
        index.setPrim(etq);
        etiquetado(index.getOpnd());
        etiquetado(index.getIndex());
        etiquetado_acc_val(index.getIndex());
        etq += 3;
        index.setSig(etq);
    }

    void etiquetado(Acceso acceso){
        acceso.setPrim(etq);
        etiquetado(acceso.getOpnd());
        etq += 2;
        acceso.setSig(etq);
    }

    void etiquetado(Indireccion indireccion){
        indireccion.setPrim(etq);
        etiquetado(indireccion.getIndex());
        etq++;
        indireccion.setSig(etq);
    }

    void etiquetado(Lit_ent litEnt){
        litEnt.setPrim(etq);
        etq++;
        litEnt.setSig(etq);
    }

    void etiquetado(Lit_real litReal){
        litReal.setPrim(etq);
        etq++;
        litReal.setSig(etq);
    }

    void etiquetado(SintaxisAbstractaTiny.True strue){
        strue.setPrim(etq);
        etq++;
        strue.setSig(etq);
    }

    void etiquetado(SintaxisAbstractaTiny.False sfalse){
        sfalse.setPrim(etq);
        etq++;
        sfalse.setSig(etq);
    }

    void etiquetado(Lit_cadena cadena){
        cadena.setPrim(etq);
        etq++;
        cadena.setSig(etq);
    }

    void etiquetado(Iden iden){
        cadena.setPrim(etq);
        etiquetado_acc_id(iden.getVinculo());
        cadena.setSig(etq);
    }

    void etiquetado(Null snull){
        cadena.setPrim(etq);
        etq++;
        cadena.setSig(etq);
    }

    void etiquetado_opnds(Exp opnd1, Exp opnd2){
        etiquetado(opnd1);
        etiquetado_acc_val(opnd1);
        etiquetado(opnd2);
        etiquetado_acc_val(opnd2);
    }

    void etiquetado_acc_val(Exp exp){
        // TODO mirar
    }

    void etiquetado_acc_id(DecVar decVar){
        if (decVar.getNivel() == 0)
            etq++;
        else
            etiquetado_acc_var(decVar);
    }

    void etiquetado_acc_id(ParamRef paramRef){
        etiquetado_acc_var(paramRef);
        etq++;
    }

    void etiquetado_acc_id(Param param){
        etiquetado_acc_var(param);
    }

    void etiquetado_acc_var(Param param){
        etq++;
    }

    @Override
    public void recolecta_subs(SiDecs siDecs){
        recolecta_subs(siDecs.getDecsAux());
    }

    //@Override
    //public void recolecta_subs(NoDecs noDecs){}

    @Override
    public void recolecta_subs(MuchasDecs muchasDecs){
        recolecta_subs(muchasDecs.getDecsAux());
        recolecta_subs(muchasDecs.getDec());
    }

    @Override
    public void recolecta_subs(UnaDec unaDec){
        recolecta_subs(unaDec.getDec());
    }

    //@Override
    //public void recolecta_subs(DecVar decVar){}

    //@Override
    //public void recolecta_subs(DecTipo decTipo){}

    @Override
    public void recolecta_subs(DecProc decProc){
        sub_pendientes.push(decProc);
    }

    void etiquetado_paso_param(DecProc decProc, ParamsR paramsR){
        etiquetado_paso_param_aux(decProc.getParamsF(), paramsR);
    }

    void etiquetado_paso_param_aux(SiParamF paramF, Si_ParamsR siParamsR){
        etiquetado_paso_param_aux(paramF.getParamsFL(), siParamsR.getParamsrl());
    }

    void etiquetado_paso_param_aux(NoParamF paramF, No_ParamsR paramsR){
        // noop
    }

    void etiquetado_paso_param_aux(MuchosParamsF muchosParamsF, Muchos_ParamsR muchosParamsR){
        etiquetado_paso_param_aux(muchosParamsF.getParamsFL(), muchosParamsR.getParamrl());
        etq += 3;
        etiquetado(muchosParamsR.getExp());
        etq++;
    }

    void etiquetado_paso_param_aux(UnParamF unParamF, Un_ParamsR unParamsR){
        etq += 3;
        etiquetado(unParamsR.getExp());
        etq++;
    }
}
