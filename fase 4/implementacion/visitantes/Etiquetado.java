package visitantes;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny;
import asint.SintaxisAbstractaTiny.*;
import maquinaP.MaquinaP;

import java.util.Stack;

public class Etiquetado extends ProcesamientoDef {
    /*
        Procesa es sintácticamente equivalente a etiquetado en la memoria
     */
    private MaquinaP maquinaP;
    private Stack<DecProc> sub_pendientes;
    private int etq;

    public Etiquetado(MaquinaP maquinaP){
        this.maquinaP = maquinaP;
        this.etq = 0;
        this.sub_pendientes = new Stack<DecProc>(); // pila-vacia
    }

    @Override
    public void procesa(Prog prog){
        DecProc sub;
        prog.setPrim(etq);
        prog.getBloq().procesa(this);
        etq++;
        while(!sub_pendientes.empty()){
            sub = sub_pendientes.pop();
            sub.setPrim(etq);
            etq++;
            sub.getBloq().procesa(this);
            etq++;
            sub.setSig(etq);
        }
        prog.setSig(etq);
    }

    @Override
    public void procesa(Bloq bloq){
        bloq.setPrim(etq);
        bloq.getDecs().recolecta_subs(this);
        bloq.getInsts().procesa(this);
        bloq.setSig(etq);
    }

    @Override
    public void procesa(Si_Instr siInstr){
        siInstr.getInstsAux().procesa(this);
    }

    @Override
    public void procesa(No_Instr noInstr){
        // noop
    }

    @Override
    public void procesa(Muchas_Instr muchasInstr){
        muchasInstr.setPrim(etq);
        muchasInstr.getInstsAux().procesa(this);
        muchasInstr.getInst().procesa(this);
        muchasInstr.setSig(etq);
    }

    @Override
    public void procesa(Una_Instr unaInstr){
        unaInstr.setPrim(etq);
        unaInstr.getInst().procesa(this);
        unaInstr.setSig(etq);
    }

    @Override
    public void procesa(Instr_Expr instrExpr){
        instrExpr.setPrim(etq);
        instrExpr.getExp().procesa(this);
//        etq++;
        instrExpr.setSig(etq);
    }

    @Override
    public void procesa(Instr_If instrIf){
        instrIf.setPrim(etq);
        instrIf.getExp().procesa(this);
        procesa_acc_val(instrIf.getExp());
        etq++;
        instrIf.getBloq().procesa(this);
        instrIf.setSig(etq);
    }

    @Override
    public void procesa(Instr_If_Else instrIfElse){
        instrIfElse.setPrim(etq);
        instrIfElse.getExp().procesa(this);
        procesa_acc_val(instrIfElse.getExp());
        etq++;
        instrIfElse.getBloq1().procesa(this);
        etq++;
        instrIfElse.setElse(etq);
        instrIfElse.getBloq2().procesa(this);
        instrIfElse.setSig(etq);
    }

    @Override
    public void procesa(Instr_While instrWhile){
        instrWhile.setPrim(etq);
        instrWhile.getExp().procesa(this);
        procesa_acc_val(instrWhile.getExp());
        etq++;
        instrWhile.getBloq().procesa(this);
        etq++;
        instrWhile.setSig(etq);
    }

    @Override
    public void procesa(Instr_Read instrRead){
        instrRead.setPrim(etq);
        instrRead.getExp().procesa(this);
        etq += 2;
        instrRead.setSig(etq);
    }

    @Override
    public void procesa(Instr_Write instrWrite){
        instrWrite.setPrim(etq);
        instrWrite.getExp().procesa(this);
        etq++;
        instrWrite.setSig(etq);
    }

    @Override
    public void procesa(Instr_Nl instrNl){
        instrNl.setPrim(etq);
        etq++;
        instrNl.setSig(etq);
    }

    @Override
    public void procesa(Instr_New instrNew){
        instrNew.setPrim(etq);
        instrNew.getExp().procesa(this);
        etq += 2;
        instrNew.setSig(etq);
    }

    @Override
    public void procesa(Instr_Del instrDel){
        instrDel.setPrim(etq);
        instrDel.getExp().procesa(this);
        etq += 2;
        instrDel.setSig(etq);
    }

    @Override
    public void procesa(Instr_Call instrCall){
        instrCall.setPrim(etq);
        etq++;
        etiquetado_paso_param((DecProc) instrCall.getVinculo(), instrCall.getParamsR());
        etq++;
        instrCall.setSig(etq);
    }

    @Override
    public void procesa(Instr_Bloque instrBloque){
        instrBloque.setPrim(etq);
        instrBloque.getBloq().procesa(this);
        instrBloque.setSig(etq);
    }

    @Override
    public void procesa(Si_ParamsR siParamsR){
        siParamsR.setPrim(etq);
        siParamsR.getParamsrl().procesa(this);
        siParamsR.setSig(etq);
    }

    @Override
    public void procesa(No_ParamsR noParamsR){
        noParamsR.setPrim(etq);
        noParamsR.setSig(etq);
    }

    @Override
    public void procesa(Muchos_ParamsR muchosParamsR){
        muchosParamsR.setPrim(etq);
        muchosParamsR.getParamrl().procesa(this);
        muchosParamsR.getExp().procesa(this);
        muchosParamsR.setSig(etq);
    }

    @Override
    public void procesa(Un_ParamsR unParamsR){
        unParamsR.setPrim(etq);
        unParamsR.getExp().procesa(this);
        unParamsR.setSig(etq);
    }

    @Override
    public void procesa(Asignacion asignacion){
        asignacion.setPrim(etq);
        asignacion.getOpnd0().procesa(this);
        asignacion.getOpnd1().procesa(this);
        if (ref(asignacion.getOpnd0().getTipado()) != ref(asignacion.getOpnd1().getTipado()) &&
                es_designador(asignacion.getOpnd1()))
        {
            etq += 3;
        }
        else {
            etq++;
        }
        asignacion.setSig(etq);
    }

    @Override
    public void procesa(Menor menor){
        menor.setPrim(etq);
        etiquetado_opnds(menor.getOpnd0(), menor.getOpnd1());
        etq++;
        menor.setSig(etq);
    }

    @Override
    public void procesa(Mayor mayor){
        mayor.setPrim(etq);
        etiquetado_opnds(mayor.getOpnd0(), mayor.getOpnd1());
        etq++;
        mayor.setSig(etq);
    }

    @Override
    public void procesa(MenorIgual menorIgual){
        menorIgual.setPrim(etq);
        etiquetado_opnds(menorIgual.getOpnd0(), menorIgual.getOpnd1());
        etq++;
        menorIgual.setSig(etq);
    }

    @Override
    public void procesa(MayorIgual mayorIgual){
        mayorIgual.setPrim(etq);
        etiquetado_opnds(mayorIgual.getOpnd0(), mayorIgual.getOpnd1());
        etq++;
        mayorIgual.setSig(etq);
    }

    @Override
    public void procesa(Igual igual){
        igual.setPrim(etq);
        etiquetado_opnds(igual.getOpnd0(), igual.getOpnd1());
        etq++;
        igual.setSig(etq);
    }

    @Override
    public void procesa(NoIgual noIgual){
        noIgual.setPrim(etq);
        etiquetado_opnds(noIgual.getOpnd0(), noIgual.getOpnd1());
        etq++;
        noIgual.setSig(etq);
    }

    @Override
    public void procesa(Suma suma){
        suma.setPrim(etq);
        etiquetado_opnds(suma.getOpnd0(), suma.getOpnd1());
        etq++;
        suma.setSig(etq);
    }

    @Override
    public void procesa(Resta resta){
        resta.setPrim(etq);
        etiquetado_opnds(resta.getOpnd0(), resta.getOpnd1());
        etq++;
        resta.setSig(etq);
    }

    @Override
    public void procesa(And and){
        and.setPrim(etq);
        etiquetado_opnds(and.getOpnd0(), and.getOpnd1());
        etq++;
        and.setSig(etq);
    }

    @Override
    public void procesa(Or or){
        or.setPrim(etq);
        etiquetado_opnds(or.getOpnd0(), or.getOpnd1());
        etq++;
        or.setSig(etq);
    }

    @Override
    public void procesa(Mul mul){
        mul.setPrim(etq);
        etiquetado_opnds(mul.getOpnd0(), mul.getOpnd1());
        etq++;
        mul.setSig(etq);
    }

    @Override
    public void procesa(Div div){
        div.setPrim(etq);
        etiquetado_opnds(div.getOpnd0(), div.getOpnd1());
        etq++;
        div.setSig(etq);
    }

    @Override
    public void procesa(Mod mod){
        mod.setPrim(etq);
        etiquetado_opnds(mod.getOpnd0(), mod.getOpnd1());
        etq++;
        mod.setSig(etq);
    }

    @Override
    public void procesa(Negativo negativo){
        negativo.setPrim(etq);
        negativo.getOpnd0().procesa(this);
        procesa_acc_val(negativo.getOpnd0());
        etq++;
        negativo.setSig(etq);
    }

    @Override
    public void procesa(Not not){
        not.setPrim(etq);
        not.getOpnd0().procesa(this);
        procesa_acc_val(not.getOpnd0());
        etq++;
        not.setSig(etq);
    }

    @Override
    public void procesa(Index index){
        index.setPrim(etq);
        index.getOpnd().procesa(this);
        index.getIndex().procesa(this);
        procesa_acc_val(index.getIndex());
        etq += 3;
        index.setSig(etq);
    }

    @Override
    public void procesa(Acceso acceso){
        acceso.setPrim(etq);
        acceso.getOpnd().procesa(this);
        etq += 2;
        acceso.setSig(etq);
    }

    @Override
    public void procesa(Indireccion indireccion){
        indireccion.setPrim(etq);
        indireccion.getOpnd0().procesa(this);
        etq++;
        indireccion.setSig(etq);
    }

    @Override
    public void procesa(Lit_ent litEnt){
        litEnt.setPrim(etq);
        etq++;
        litEnt.setSig(etq);
    }

    @Override
    public void procesa(Lit_real litReal){
        litReal.setPrim(etq);
        etq++;
        litReal.setSig(etq);
    }

    @Override
    public void procesa(SintaxisAbstractaTiny.True strue){
        strue.setPrim(etq);
        etq++;
        strue.setSig(etq);
    }

    @Override
    public void procesa(SintaxisAbstractaTiny.False sfalse){
        sfalse.setPrim(etq);
        etq++;
        sfalse.setSig(etq);
    }

    @Override
    public void procesa(Lit_cadena cadena){
        cadena.setPrim(etq);
        etq++;
        cadena.setSig(etq);
    }

    @Override
    public void procesa(Iden iden){
        iden.setPrim(etq);
        procesa_acc_id((DecVar) iden.getVinculo());
        iden.setSig(etq);
    }

    @Override
    public void procesa(Null snull){
        snull.setPrim(etq);
        etq++;
        snull.setSig(etq);
    }

    void etiquetado_opnds(Exp opnd1, Exp opnd2){
        opnd1.procesa(this);
        procesa_acc_val(opnd1);
        opnd2.procesa(this);
        procesa_acc_val(opnd2);
    }

    @Override
    public void procesa_acc_val(Exp exp){
        if (es_designador(exp)) {
            etq++;
        }
    }

    @Override
    public void procesa_acc_id(DecVar decVar){
        if (decVar.getNivel() == 0)
            etq++;
        else
            decVar.procesa_acc_var(this);
    }

    @Override
    public void procesa_acc_id(ParamNoRef paramNoRef){
        paramNoRef.procesa_acc_var(this);
    }

    @Override
    public void procesa_acc_id(ParamRef paramRef){
        paramRef.procesa_acc_var(this);
        etq++;
    }

    @Override
    public void procesa_acc_var(Nodo nodo){
        etq += 3;
    }

    @Override
    public void recolecta_subs(SiDecs siDecs){
        siDecs.getDecsAux().recolecta_subs(this);
    }

    @Override
    public void recolecta_subs(NoDecs noDecs){
        // noop
    }

    @Override
    public void recolecta_subs(MuchasDecs muchasDecs){
        muchasDecs.getDecsAux().recolecta_subs(this);
        muchasDecs.getDec().recolecta_subs(this);
    }

    @Override
    public void recolecta_subs(UnaDec unaDec){
        unaDec.getDec().recolecta_subs(this);
    }

    @Override
    public void recolecta_subs(DecVar decVar){
        // noop
    }

    @Override
    public void recolecta_subs(DecTipo decTipo){
        // noop
    }

    @Override
    public void recolecta_subs(DecProc decProc){
        sub_pendientes.push(decProc);
    }

    void etiquetado_paso_param(DecProc decProc, ParamsR paramsR){
        etiquetado_paso_param_aux(decProc.getParamsF(), paramsR);
    }

    void etiquetado_paso_param_aux(ParamsF paramF, ParamsR paramsR){
        if (paramF instanceof SiParamF && paramsR instanceof Si_ParamsR) {
            etiquetado_paso_param_aux(paramF.getParamsFL(), paramsR.getParamsrl());
        }
        else if (paramF instanceof NoParamF && paramsR instanceof No_ParamsR) {
            // noop
        }
    }

    void etiquetado_paso_param_aux(ParamsFL paramsFL, ParamsRL paramsRL){
        if (paramsFL instanceof MuchosParamsF && paramsRL instanceof Muchos_ParamsR) {
            etiquetado_paso_param_aux(paramsFL.getParamsFL(), paramsRL.getParamrl());
            etq += 3;
            paramsRL.getExp().procesa(this);
            etq++;
        }
        else if (paramsFL instanceof UnParamF && paramsRL instanceof Un_ParamsR) {
            etq += 3;
            paramsRL.getExp().procesa(this);
            etq++;
        }
    }
}
