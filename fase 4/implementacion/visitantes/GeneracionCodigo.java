package visitantes;

import asint.SintaxisAbstractaTiny;
import asint.SintaxisAbstractaTiny.*;
import maquinaP.MaquinaP;

import java.util.Stack;
public class GeneracionCodigo extends RecolectaDef{
    private Stack<DecProc> sub_pendientes;
    private MaquinaP maquinaP;
    public GeneracionCodigo(MaquinaP maquinaP){
        this.maquinaP = maquinaP;
        sub_pendientes = new Stack<DecProc>();
    }

    void gen_cod(Prog prog){
        DecProc sub;
        gen_cod(prog.getBloq());
        while(!sub_pendientes.empty()){
            sub = sub_pendientes.pop();
            maquinaP.desapilad(sub.getNivel());
            gen_cod(sub.getBloq());
            maquinaP.desactiva(sub.getNivel(), sub.getTam());
            maquinaP.ir_ind();
        }
    }

    void gen_cod(Bloq bloq){
        recolecta_subs(bloq.getDecs());
        gen_cod(bloq.getInsts());
        maquinaP.stop();
    }
    void gen_cod(Si_Instr siInstr){
        gen_cod(siInstr.getInstsAux());
    }

    void gen_cod(No_Instr noInstr){
        //noop
    }

    void gen_cod(Muchas_Instr muchasInstr){
        gen_cod(muchasInstr.getInstsAux()):
        gen_cod(muchasInstr.getInst());
    }

    void gen_cod(Una_Instr unaInstr){
        gen_cod(unaInstr.getInst()):
    }

    void gen_cod(Instr_Expr instrExpr){
        gen_cod(instrExpr.getExp());
        maquinaP.desapila(); //TODO crear desapila
    }

    void gen_cod(Instr_If instrIf){
        gen_cod(instrIf.getExp());
        gen_acc_val(instrIf.getExp());
        maquinaP.ir_f(instrIf.getSig());
        gen_cod(instrIf.getBloq());
    }

    void gen_cod(Instr_If_Else instrIfElse){
        gen_cod(instrIfElse.getExp());
        gen_acc_val(instrIfElse.getExp());
        maquinaP.ir_f(instrIfElse.getElse());
        gen_cod(instrIfElse.getBloq1());
        maquinaP.ir_a(instrIfElse.getSig());
        gen_cod(instrIfElse.getBloq2());
    }

    void gen_cod(Instr_While instrWhile){
        gen_cod(instrWhile.getExp());
        gen_acc_val(instrWhile.getExp());
        maquinaP.ir_f(instrWhile.getSig());
        gen_cod(instrWhile.getBloq());
        maquinaP.ir_a(instrWhile.getPrim());
    }

    void gen_cod(Instr_Read instrRead){
        gen_cod(instrRead.getExp());
        gen_acc_val(instrRead.getExp());
        maquinaP.read();//TODO check
    }

    void gen_cod(Instr_Write instrWrite){
        gen_cod(instrWrite.getExp());
        gen_acc_val(instrWrite.getExp());
        maquinaP.write();
    }

    void gen_cod(Instr_Nl instrNl){
        maquinaP.nl();
    }

    void gen_cod(Instr_New instrNew){
        gen_cod(instrNew.getExp());
        maquinaP.alloc(instrNew.getExp().getTipo().getTam());
        maquinaP.desapila_ind();
    }

    void gen_cod(Instr_Del instrDel){
        gen_cod(instrDel.getExp());
        maquinaP.apila_ind();
        maquinaP.dealloc(instrDel.getExp().getTipo().getTam());
    }

    void gen_cod(Instr_Call instrCall){
        maquinaP.activa(instrCall.getVinculo().getNivel(), instrCall.getVinculo().getTam(), instrCall.getSig());
        gen_paso_param(instrCall.getVinculo(), instrCall.getParamsR());
        maquinaP.ir_a(instrCall.getVinculo().getPrim());
    }

    void gen_cod(Instr_Bloque instrBloque){
        gen_cod(instrBloque.getBloq());
    }

    void gen_cod(Si_ParamsR siParamsR){
        gen_cod(siParamsR.getParamsrl());
    }

    void gen_cod(No_ParamsR noParamsR){
        //noop
    }

    void gen_cod(Muchos_ParamsR muchosParamsR){
        gen_cod(muchosParamsR.getParamrl());
        gen_cod(muchosParamsR.getExp());
    }

    void gen_cod(Un_ParamsR unParamsR){
        gen_cod(unParamsR.getExp());
    }

    void gen_cod(Asignacion asignacion){
        gen_cod(asignacion.getOpnd0());
        gen_cod(asignacion.getOpnd1());
        //TODO mirar donde esta lo de ref
    }

    void gen_asig(Exp exp){
        //TODO mirar la funcion es_designador()
    }

    void gen_asig_prom(Exp exp){
        //TODO mirar lo de arriba
    }

    void gen_cod(Menor menor){
        gen_cod_opnds(menor.getOpnd0(), menor.getOpnd1());
        maquinaP.menor();//TODO incluir
    }

    void gen_cod(Mayor mayor){
        gen_cod_opnds(mayor.getOpnd0(), mayor.getOpnd1());
        maquinaP.mayor();//TODO incluir
    }

    void gen_cod(MenorIgual menorIgual){
        gen_cod_opnds(menorIgual.getOpnd0(), menorIgual.getOpnd1());
        maquinaP.menorIgual();//TODO incluir
    }

    void gen_cod(MayorIgual mayorIgual){
        gen_cod_opnds(mayorIgual.getOpnd0(), mayorIgual.getOpnd1());
        maquinaP.mayorIgual();//TODO incluir
    }

    void gen_cod(Igual igual){
        gen_cod_opnds(igual.getOpnd0(), igual.getOpnd1());
        maquinaP.igual();//TODO incluir
    }

    void gen_cod(NoIgual noIgual){
        gen_cod_opnds(noIgual.getOpnd0(), noIgual.getOpnd1());
        maquinaP.noIgual();//TODO incluir
    }

    void gen_cod(Suma suma){
        gen_cod_opnds(suma.getOpnd0(), suma.getOpnd1());
        maquinaP.suma();
    }

    void gen_cod(Resta resta){
        gen_cod_opnds(resta.getOpnd0(), resta.getOpnd1());
        maquinaP.resta();//TODO incluir
    }

    void gen_cod(And and){
        gen_cod_opnds(and.getOpnd0(), and.getOpnd1());
        maquinaP.and();//TODO incluir
    }

    void gen_cod(Or or){
        gen_cod_opnds(or.getOpnd0(), or.getOpnd1());
        maquinaP.or();//TODO incluir
    }

    void gen_cod(Mul mul){
        gen_cod_opnds(mul.getOpnd0(), mul.getOpnd1());
        maquinaP.mul();
    }

    void gen_cod(Div div){
        gen_cod_opnds(div.getOpnd0(), div.getOpnd1());
        maquinaP.div();//TODO incluir
    }

    void gen_cod(Mod mod){
        gen_cod_opnds(mod.getOpnd0(), mod.getOpnd1());
        maquinaP.mod();//TODO incluir
    }

    void gen_cod(Negativo negativo){
        gen_cod(negativo.getOpnd0());
        gen_acc_val(negativo.getOpnd0());
        maquinaP.negativo();// TODO incluir
    }

    void gen_cod(Not not){
        gen_cod(not.getOpnd0());
        gen_acc_val(not.getOpnd0());
        maquinaP.not();// TODO incluir
    }

    void gen_cod(Index index){
        gen_cod(index.getOpnd());
        gen_cod(index.getIndex());
        gen_acc_val(index.getIndex());
        maquinaP.apila_int(index.getTipo().getTam());
        maquinaP.mul();
        maquinaP.suma();
    }

    void gen_cod(Acceso acceso){
        gen_cod(acceso.getOpnd());
        // TODO hacer las funciones
    }

    void gen_cod(Indireccion indireccion){
        gen_cod(indireccion.getIndex());
        maquinaP.apila_ind();
    }

    void gen_cod(Lit_ent litEnt){
        maquinaP.apila_int(litEnt.getNum());
    }

    void gen_cod(Lit_real litReal){
        maquinaP.apila_real(litReal.getNum());
    }

    void gen_cod(SintaxisAbstractaTiny.True strue){
        maquinaP.apila_bool(true);
    }

    void gen_cod(SintaxisAbstractaTiny.False sfalse){
        maquinaP.apila_bool(false);
    }

    void gen_cod(Lit_cadena cadena){
        maquinaP.apila_string(cadena);
    }

    void gen_cod(Iden iden){
        gen_acc_id(iden.getVinculo());
    }

    void gen_cod(Null snull){
        maquinaP.null();
    }

    void gen_cod_opnds(Exp opnd1, Exp opnd2){
        // TODO mirar
    }

    void gen_acc_val(Exp exp){
        // TODO mirar
    }

    void gen_acc_id(DecVar decVar){
        if (decVar.getNivel() == 0)
            maquinaP.apila_ind();
        else
            gen_acc_var(decVar);
    }

    void gen_acc_id(ParamRef paramRef){
        gen_acc_var(paramRef);
        maquinaP.apila_ind();
    }

    void gen_acc_id(Param param){
        gen_acc_var(param);
    }

    void gen_acc_var(Param param){
        maquinaP.apilad(param.getNivel());
        maquinaP.apila_int(param.getDir());
        maquinaP.suma();
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

    void gen_paso_param(DecProc decProc, ParamsR paramsR){
        gen_paso_param_aux(decProc.getParamsF(), paramsR);
    }

    void gen_paso_param_aux(SiParamF paramF, Si_ParamsR siParamsR){
        gen_paso_param_aux(paramF.getParamsFL(), siParamsR.getParamsrl());
    }

    void gen_paso_param_aux(NoParamF paramF, No_ParamsR paramsR){
        // noop
    }

    void gen_paso_param_aux(MuchosParamsF muchosParamsF, Muchos_ParamsR muchosParamsR){
        gen_paso_param_aux(muchosParamsF.getParamsFL(), muchosParamsR.getParamrl());
        maquinaP.dup();
        maquinaP.apila_int(muchosParamsF.getParam());
        maquinaP.suma();
        gen_cod(muchosParamsR.getExp());
        // TODO terminar
    }

    void gen_paso_param_aux(UnParamF unParamF, Un_ParamsR unParamsR){
        maquinaP.dup();
        maquinaP.apila_int(unParamF.getParam().getDir());
        maquinaP.suma();
        gen_cod(unParamsR.getExp());
        // TODO terminar
    }

}
