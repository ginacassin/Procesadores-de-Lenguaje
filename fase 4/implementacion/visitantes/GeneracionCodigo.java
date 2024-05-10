package visitantes;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny;
import asint.SintaxisAbstractaTiny.*;
import maquinaP.MaquinaP;
import java.util.Scanner;

import java.util.Stack;

public class GeneracionCodigo extends ProcesamientoDef {
    /*
        Procesa es sintácticamente equivalente a gen-cod en la memoria
     */
    private Stack<DecProc> sub_pendientes;
    private MaquinaP maquinaP;
    public GeneracionCodigo(MaquinaP maquinaP){
        this.maquinaP = maquinaP;
        sub_pendientes = new Stack<DecProc>();
    }

    @Override
    public void procesa(Prog prog){
        DecProc sub;
        prog.getBloq().procesa(this);
        maquinaP.emit(maquinaP.stop());
        while(!sub_pendientes.empty()){
            sub = sub_pendientes.pop();
            maquinaP.emit(maquinaP.desapilad(sub.getNivel()));
            sub.getBloq().procesa(this);
            maquinaP.emit(maquinaP.desactiva(sub.getNivel(), sub.getTam()));
            maquinaP.emit(maquinaP.ir_ind());
        }
    }

    @Override
    public void procesa(Bloq bloq){
        bloq.getDecs().recolecta_subs(this);
        bloq.getInsts().procesa(this);
    }

    @Override
    public void procesa(Si_Instr siInstr){
        siInstr.getInstsAux().procesa(this);
    }

    @Override
    public void procesa(No_Instr noInstr){
        //noop
    }

    @Override
    public void procesa(Muchas_Instr muchasInstr){
        muchasInstr.getInstsAux().procesa(this);
        muchasInstr.getInst().procesa(this);
    }

    @Override
    public void procesa(Una_Instr unaInstr){
        unaInstr.getInst().procesa(this);
    }

    @Override
    public void procesa(Instr_Expr instrExpr){
        instrExpr.getExp().procesa(this);
        //maquinaP.emit(maquinaP.desapila()); // descartar resultado
    }

    @Override
    public void procesa(Instr_If instrIf){
        instrIf.getExp().procesa(this);
        gen_acc_val(instrIf.getExp());
        maquinaP.emit(maquinaP.ir_f(instrIf.getSig()));
        instrIf.getBloq().procesa(this);
    }

    @Override
    public void procesa(Instr_If_Else instrIfElse){
        instrIfElse.getExp().procesa(this);
        gen_acc_val(instrIfElse.getExp());
        maquinaP.emit(maquinaP.ir_f(instrIfElse.getElse()));
        instrIfElse.getBloq1().procesa(this);
        maquinaP.emit(maquinaP.ir_a(instrIfElse.getSig()));
        instrIfElse.getBloq2().procesa(this);
    }

    @Override
    public void procesa(Instr_While instrWhile){
        instrWhile.getExp().procesa(this);
        gen_acc_val(instrWhile.getExp());
        maquinaP.emit(maquinaP.ir_f(instrWhile.getSig()));
        instrWhile.getBloq().procesa(this);
        maquinaP.emit(maquinaP.ir_a(instrWhile.getPrim()));
    }

    @Override
    public void procesa(Instr_Read instrRead){
        instrRead.getExp().procesa(this);
        //gen_acc_val(instrRead.getExp());
        maquinaP.emit(maquinaP.read());
        maquinaP.emit(maquinaP.desapila_ind());
    }

    @Override
    public void procesa(Instr_Write instrWrite){
        instrWrite.getExp().procesa(this);
        gen_acc_val(instrWrite.getExp());
//        if (es_designador(instrWrite.getExp())){
//            maquinaP.emit(maquinaP.apila_ind());
//        }
        maquinaP.emit(maquinaP.write());
    }

    @Override
    public void procesa(Instr_Nl instrNl){
        maquinaP.emit(maquinaP.nl());
    }

    @Override
    public void procesa(Instr_New instrNew){
        instrNew.getExp().procesa(this);
        if (ref(instrNew.getExp().getTipado()) instanceof TipoPunt) {
            maquinaP.emit(maquinaP.alloc(instrNew.getExp().getTipado().getTam()));
        }
        maquinaP.emit(maquinaP.desapila_ind());
    }

    @Override
    public void procesa(Instr_Del instrDel){
        instrDel.getExp().procesa(this);
        maquinaP.emit(maquinaP.apila_ind());
        if (ref(instrDel.getExp().getTipado()) instanceof TipoPunt) {
            maquinaP.emit(maquinaP.dealloc(instrDel.getExp().getTipado().getTam()));
        }
    }

    @Override
    public void procesa(Instr_Call instrCall){
        maquinaP.emit(maquinaP.activa(instrCall.getVinculo().getNivel(), instrCall.getVinculo().getTam(), instrCall.getSig()));
        gen_paso_param((DecProc) instrCall.getVinculo(), instrCall.getParamsR());
        maquinaP.emit(maquinaP.ir_a(instrCall.getVinculo().getPrim()));
    }

    @Override
    public void procesa(Instr_Bloque instrBloque){
        instrBloque.getBloq().procesa(this);
    }

    @Override
    public void procesa(Si_ParamsR siParamsR){
        siParamsR.getParamsrl().procesa(this);
    }

    @Override
    public void procesa(No_ParamsR noParamsR){
        //noop
    }

    @Override
    public void procesa(Muchos_ParamsR muchosParamsR){
        muchosParamsR.getParamrl().procesa(this);
        muchosParamsR.getExp().procesa(this);
    }

    @Override
    public void procesa(Un_ParamsR unParamsR){
        unParamsR.getExp().procesa(this);
    }

    @Override
    public void procesa(Asignacion asignacion){
        asignacion.getOpnd0().procesa(this);
        asignacion.getOpnd1().procesa(this);
        if (ref(asignacion.getOpnd0().getTipado()) instanceof TipoReal &&
            ref(asignacion.getOpnd1().getTipado()) instanceof TipoInt){
            gen_asig_prom(asignacion.getOpnd1());
        } else {
            gen_asig(asignacion.getOpnd1());
        }
    }

    void gen_asig(Exp exp){
        if (es_designador(exp)) {
            maquinaP.emit(maquinaP.copia(exp.getTipado().getTam()));
        }
        else {
            maquinaP.emit(maquinaP.desapila_ind());
        }
    }

    void gen_asig_prom(Exp exp){
        if (es_designador(exp)) {
            maquinaP.emit(maquinaP.apila_ind());
            maquinaP.emit(maquinaP.int2real());
            maquinaP.emit(maquinaP.desapila_ind());
        }
        else {
            maquinaP.emit(maquinaP.int2real());
            maquinaP.emit(maquinaP.desapila_ind());
        }
    }

    @Override
    public void procesa(Menor menor){
        gen_cod_opnds(menor.getOpnd0(), menor.getOpnd1());
        maquinaP.emit(maquinaP.menor());
    }

    @Override
    public void procesa(Mayor mayor){
        gen_cod_opnds(mayor.getOpnd0(), mayor.getOpnd1());
        maquinaP.emit(maquinaP.mayor());
    }

    @Override
    public void procesa(MenorIgual menorIgual){
        gen_cod_opnds(menorIgual.getOpnd0(), menorIgual.getOpnd1());
        maquinaP.emit(maquinaP.menorIgual());
    }

    @Override
    public void procesa(MayorIgual mayorIgual){
        gen_cod_opnds(mayorIgual.getOpnd0(), mayorIgual.getOpnd1());
        maquinaP.emit(maquinaP.mayorIgual());
    }

    @Override
    public void procesa(Igual igual){
        gen_cod_opnds(igual.getOpnd0(), igual.getOpnd1());
        maquinaP.emit(maquinaP.igual());
    }

    @Override
    public void procesa(NoIgual noIgual){
        gen_cod_opnds(noIgual.getOpnd0(), noIgual.getOpnd1());
        maquinaP.emit(maquinaP.noIgual());
    }

    @Override
    public void procesa(Suma suma){
        gen_cod_opnds(suma.getOpnd0(), suma.getOpnd1());
        maquinaP.emit(maquinaP.suma());
    }

    @Override
    public void procesa(Resta resta){
        gen_cod_opnds(resta.getOpnd0(), resta.getOpnd1());
        maquinaP.emit(maquinaP.resta());
    }

    @Override
    public void procesa(And and){
        gen_cod_opnds(and.getOpnd0(), and.getOpnd1());
        maquinaP.emit(maquinaP.and());
    }

    @Override
    public void procesa(Or or){
        gen_cod_opnds(or.getOpnd0(), or.getOpnd1());
        maquinaP.emit(maquinaP.or());
    }

    @Override
    public void procesa(Mul mul){
        gen_cod_opnds(mul.getOpnd0(), mul.getOpnd1());
        maquinaP.emit(maquinaP.mul());
    }

    @Override
    public void procesa(Div div){
        gen_cod_opnds(div.getOpnd0(), div.getOpnd1());
        maquinaP.emit(maquinaP.div());
    }

    @Override
    public void procesa(Mod mod){
        gen_cod_opnds(mod.getOpnd0(), mod.getOpnd1());
        maquinaP.emit(maquinaP.mod());
    }

    @Override
    public void procesa(Negativo negativo){
        negativo.getOpnd0().procesa(this);
        gen_acc_val(negativo.getOpnd0());
        maquinaP.emit(maquinaP.negativo());
    }

    @Override
    public void procesa(Not not){
        not.getOpnd0().procesa(this);
        gen_acc_val(not.getOpnd0());
        maquinaP.emit(maquinaP.not());
    }

    @Override
    public void procesa(Index index){
        index.getOpnd().procesa(this);
        index.getIndex().procesa(this);
        gen_acc_val(index.getIndex());
        if (ref(index.getOpnd().getTipado()) instanceof TipoArray) {
            maquinaP.emit(maquinaP.apila_int(index.getOpnd().getTipado().getTam()));
        }
        maquinaP.emit(maquinaP.mul());
        maquinaP.emit(maquinaP.suma());
    }

    @Override
    public void procesa(Acceso acceso){
        acceso.getOpnd().procesa(this);
        if (ref(acceso.getOpnd().getTipado()) instanceof TipoStruct) {
            TipoStruct registro = (TipoStruct) ref(acceso.getOpnd().getTipado());
            maquinaP.emit(maquinaP.apila_int(desplazamiento(registro.getlCampos(), acceso.getIden())));
        }
        maquinaP.emit(maquinaP.suma());
    }

    private int desplazamiento(LCampos lCampos, String id){
        if (lCampos instanceof Muchos_Campos) {
            if (lCampos.getCampo().getIden().equals(id)){
                return lCampos.getCampo().getDesp();
            }
            else{
                desplazamiento(lCampos.getlCampos(), id);
            }
        }
        else if (lCampos instanceof Un_Campo){
            if (lCampos.getCampo().getIden().equals(id)){
                return lCampos.getCampo().getDesp();
            }
        }

        return 0;
    }

    @Override
    public void procesa(Indireccion indireccion){
        indireccion.getOpnd0().procesa(this);
        maquinaP.emit(maquinaP.apila_ind());
    }

    @Override
    public void procesa(Lit_ent litEnt){
        maquinaP.emit(maquinaP.apila_int(Integer.parseInt(litEnt.getNum())));
    }

    @Override
    public void procesa(Lit_real litReal){
        maquinaP.emit(maquinaP.apila_real(Float.parseFloat(litReal.getNum())));
    }

    @Override
    public void procesa(SintaxisAbstractaTiny.True strue){
        maquinaP.emit(maquinaP.apila_bool(true));
    }

    @Override
    public void procesa(SintaxisAbstractaTiny.False sfalse){
        maquinaP.emit(maquinaP.apila_bool(false));
    }

    @Override
    public void procesa(Lit_cadena cadena){
        maquinaP.emit(maquinaP.apila_string(cadena.getCadena()));
    }

    @Override
    public void procesa(Iden iden){
        if (iden.getVinculo() instanceof  DecVar)
            gen_acc_id((DecVar) iden.getVinculo());
        else if (iden.getVinculo() instanceof ParamNoRef)
            gen_acc_id((ParamNoRef) iden.getVinculo());
        else if (iden.getVinculo() instanceof ParamRef)
            gen_acc_id((ParamRef) iden.getVinculo());
    }

    @Override
    public void procesa(Null snull){
        maquinaP.emit(maquinaP.apila_null());
    }

    public void gen_cod_opnds(Exp opnd1, Exp opnd2){
        if (ref(opnd1.getTipado()) instanceof TipoInt &&
            ref(opnd2.getTipado()) instanceof TipoReal){
            opnd1.procesa(this);
            gen_acc_val(opnd1);
            maquinaP.emit(maquinaP.int2real());
            opnd2.procesa(this);
            gen_acc_val(opnd2);
        }
        else if (ref(opnd1.getTipado()) instanceof TipoReal &&
                   ref(opnd2.getTipado()) instanceof TipoInt){
            opnd1.procesa(this);
            gen_acc_val(opnd1);
            opnd2.procesa(this);
            gen_acc_val(opnd2);
            maquinaP.emit(maquinaP.int2real());
        }
        else {
            opnd1.procesa(this);
            gen_acc_val(opnd1);
            opnd2.procesa(this);
            gen_acc_val(opnd2);
        }
    }

    void gen_acc_val(Exp exp){
        if (es_designador(exp)) {
            maquinaP.emit(maquinaP.apila_ind());
        }
    }

    void gen_acc_id(DecVar decVar){
        if (decVar.getNivel() == 0)
            maquinaP.emit(maquinaP.apila_int(decVar.getDir()));
        else
            procesa_acc_var(decVar);
    }

    void gen_acc_id(ParamRef paramRef){
        procesa_acc_var(paramRef);
        maquinaP.emit(maquinaP.apila_ind());
    }

    void gen_acc_id(Param param){
        this.procesa_acc_var(param);
    }

    @Override
    public void procesa_acc_var(Nodo nodo){
        maquinaP.emit(maquinaP.apilad(nodo.getNivel()));
        maquinaP.emit(maquinaP.apila_int(nodo.getDir()));
        maquinaP.emit(maquinaP.suma());
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

    void gen_paso_param(DecProc decProc, ParamsR paramsR){
        gen_paso_param_aux(decProc.getParamsF(), paramsR);
    }

    void gen_paso_param_aux(ParamsF paramF, ParamsR paramsR){
        if (paramF instanceof SiParamF &&
                paramsR instanceof Si_ParamsR){
            gen_paso_param_aux(paramF.getParamsFL(), paramsR.getParamsrl());
        }
        else if (paramF instanceof NoParamF &&
                paramsR instanceof No_ParamsR){
            // noop
        }
    }

    void gen_paso_param_aux(ParamsFL paramsFL, ParamsRL paramsRL){
        if (paramsFL instanceof MuchosParamsF &&
                paramsRL instanceof Muchos_ParamsR){
            gen_paso_param_aux(paramsFL.getParamsFL(), paramsRL.getParamrl());
            maquinaP.emit(maquinaP.dup());
            maquinaP.emit(maquinaP.apila_int(paramsFL.getParam().getDir()));
            maquinaP.emit(maquinaP.suma());
            paramsRL.getExp().procesa(this);

            if (paramsFL.getParam() instanceof ParamRef ||
                !(es_designador(paramsRL.getExp()))){
                maquinaP.emit(maquinaP.desapila_ind());
            }
            else {
                maquinaP.emit(maquinaP.copia(paramsFL.getParam().getTipo().getTam()));
            }
        }
        else if (paramsFL instanceof UnParamF &&
                paramsRL instanceof Un_ParamsR){
            maquinaP.emit(maquinaP.dup());
            maquinaP.emit(maquinaP.apila_int(paramsFL.getParam().getDir()));
            maquinaP.emit(maquinaP.suma());
            paramsRL.getExp().procesa(this);

            if (paramsFL.getParam() instanceof ParamRef ||
                    !(es_designador(paramsRL.getExp()))){
                maquinaP.emit(maquinaP.desapila_ind());
            }
            else {
                maquinaP.emit(maquinaP.copia(paramsFL.getParam().getTipo().getTam()));
            }
        }
    }
}
