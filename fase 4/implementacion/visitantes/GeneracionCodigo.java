package visitantes;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny;
import asint.SintaxisAbstractaTiny.*;
import maquinaP.MaquinaP;

import java.util.Stack;
public class GeneracionCodigo extends ProcesamientoDef {
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
        while(!sub_pendientes.empty()){
            sub = sub_pendientes.pop();
            maquinaP.desapilad(sub.getNivel());
            sub.getBloq().procesa(this);
            maquinaP.desactiva(sub.getNivel(), sub.getTam());
            maquinaP.ir_ind();
        }
    }

    @Override
    public void procesa(Bloq bloq){
        bloq.getDecs().recolecta_subs(this);
        bloq.getInsts().procesa(this);
        maquinaP.stop();
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
        maquinaP.desapila(); //TODO crear desapila
    }

    @Override
    public void procesa(Instr_If instrIf){
        instrIf.getExp().procesa(this);
        procesa_acc_val(instrIf.getExp()); //TODO check que la llamada es asi
        maquinaP.ir_f(instrIf.getSig());
        instrIf.getBloq().procesa(this);
    }

    @Override
    public void procesa(Instr_If_Else instrIfElse){
        instrIfElse.getExp().procesa(this);
        gen_acc_val(instrIfElse.getExp());
        maquinaP.ir_f(instrIfElse.getElse());
        instrIfElse.getBloq1().procesa(this);
        maquinaP.ir_a(instrIfElse.getSig());
        instrIfElse.getBloq2().procesa(this);
    }

    @Override
    public void procesa(Instr_While instrWhile){
        instrWhile.getExp().procesa(this);
        gen_acc_val(instrWhile.getExp()); //TODO check
        maquinaP.ir_f(instrWhile.getSig());
        instrWhile.getBloq().procesa(this);
        maquinaP.ir_a(instrWhile.getPrim());
    }

    @Override
    public void procesa(Instr_Read instrRead){
        instrRead.getExp().procesa(this);
        gen_acc_val(instrRead.getExp()); //TODO
        maquinaP.read();//TODO check
    }

    @Override
    public void procesa(Instr_Write instrWrite){
        instrWrite.getExp().procesa(this);
        gen_acc_val(instrWrite.getExp()); //TODO
        maquinaP.write();
    }

    @Override
    public void procesa(Instr_Nl instrNl){
        maquinaP.nl();
    }

    @Override
    public void procesa(Instr_New instrNew){
        instrNew.getExp().procesa(this);
        maquinaP.alloc(instrNew.getExp().getTipo().getTam());
        maquinaP.desapila_ind();
    }

    @Override
    public void procesa(Instr_Del instrDel){
        instrDel.getExp().procesa(this);
        maquinaP.apila_ind();
        maquinaP.dealloc(instrDel.getExp().getTipo().getTam());
    }

    @Override
    public void procesa(Instr_Call instrCall){
        maquinaP.activa(instrCall.getVinculo().getNivel(), instrCall.getVinculo().getTam(), instrCall.getSig());
        gen_paso_param(instrCall.getVinculo(), instrCall.getParamsR());
        maquinaP.ir_a(instrCall.getVinculo().getPrim());
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
        //TODO mirar donde esta lo de ref
    }

    void gen_asig(Exp exp){
        //TODO mirar la funcion es_designador()
    }

    void gen_asig_prom(Exp exp){
        //TODO mirar lo de arriba
    }

    @Override
    public void procesa(Menor menor){
        gen_cod_opnds(menor.getOpnd0(), menor.getOpnd1());
        maquinaP.menor();//TODO incluir
    }

    @Override
    public void procesa(Mayor mayor){
        gen_cod_opnds(mayor.getOpnd0(), mayor.getOpnd1());
        maquinaP.mayor();//TODO incluir
    }

    @Override
    public void procesa(MenorIgual menorIgual){
        gen_cod_opnds(menorIgual.getOpnd0(), menorIgual.getOpnd1());
        maquinaP.menorIgual();//TODO incluir
    }

    @Override
    public void procesa(MayorIgual mayorIgual){
        gen_cod_opnds(mayorIgual.getOpnd0(), mayorIgual.getOpnd1());
        maquinaP.mayorIgual();//TODO incluir
    }

    @Override
    public void procesa(Igual igual){
        gen_cod_opnds(igual.getOpnd0(), igual.getOpnd1());
        maquinaP.igual();//TODO incluir
    }

    @Override
    public void procesa(NoIgual noIgual){
        gen_cod_opnds(noIgual.getOpnd0(), noIgual.getOpnd1());
        maquinaP.noIgual();//TODO incluir
    }

    @Override
    public void procesa(Suma suma){
        gen_cod_opnds(suma.getOpnd0(), suma.getOpnd1());
        maquinaP.suma();
    }

    @Override
    public void procesa(Resta resta){
        gen_cod_opnds(resta.getOpnd0(), resta.getOpnd1());
        maquinaP.resta();//TODO incluir
    }

    @Override
    public void procesa(And and){
        gen_cod_opnds(and.getOpnd0(), and.getOpnd1());
        maquinaP.and();//TODO incluir
    }

    @Override
    public void procesa(Or or){
        gen_cod_opnds(or.getOpnd0(), or.getOpnd1());
        maquinaP.or();//TODO incluir
    }

    @Override
    public void procesa(Mul mul){
        gen_cod_opnds(mul.getOpnd0(), mul.getOpnd1());
        maquinaP.mul();
    }

    @Override
    public void procesa(Div div){
        gen_cod_opnds(div.getOpnd0(), div.getOpnd1());
        maquinaP.div();//TODO incluir
    }

    @Override
    public void procesa(Mod mod){
        gen_cod_opnds(mod.getOpnd0(), mod.getOpnd1());
        maquinaP.mod();//TODO incluir
    }

    @Override
    public void procesa(Negativo negativo){
        negativo.getOpnd0().procesa(this);
        gen_acc_val(negativo.getOpnd0()); //TODO
        maquinaP.negativo();// TODO incluir
    }

    @Override
    public void procesa(Not not){
        not.getOpnd0().procesa(this);
        gen_acc_val(not.getOpnd0()); //TODO
        maquinaP.not();// TODO incluir
    }

    @Override
    public void procesa(Index index){
        index.getOpnd().procesa(this);
        index.getIndex().procesa(this);
        gen_acc_val(index.getIndex()); //TODO
        maquinaP.apila_int(index.getTipo().getTam());
        maquinaP.mul();
        maquinaP.suma();
    }

    @Override
    public void procesa(Acceso acceso){
        acceso.getOpnd().procesa(this);
        // TODO hacer las funciones
    }

    @Override
    public void procesa(Indireccion indireccion){
        indireccion.getIndex().procesa(this);
        maquinaP.apila_ind();
    }

    @Override
    public void procesa(Lit_ent litEnt){
        maquinaP.apila_int(litEnt.getNum());
    }

    @Override
    public void procesa(Lit_real litReal){
        maquinaP.apila_real(litReal.getNum());
    }

    @Override
    public void procesa(SintaxisAbstractaTiny.True strue){
        maquinaP.apila_bool(true);
    }

    @Override
    public void procesa(SintaxisAbstractaTiny.False sfalse){
        maquinaP.apila_bool(false);
    }

    @Override
    public void procesa(Lit_cadena cadena){
        maquinaP.apila_string(cadena);
    }

    @Override
    public void procesa(Iden iden){
        gen_acc_id(iden.getVinculo()); //TODO
    }

    @Override
    public void procesa(Null snull){
        maquinaP.null();
    }

    public void gen_cod_opnds(Exp opnd1, Exp opnd2){
        // TODO mirar
    }

    void gen_acc_val(Exp exp){
        // TODO mirar
    }

    void gen_acc_id(DecVar decVar){
        if (decVar.getNivel() == 0)
            maquinaP.apila_ind();
        else
            gen_acc_var(decVar);//TODO
    }

    void gen_acc_id(ParamRef paramRef){
        gen_acc_var(paramRef);//TODO
        maquinaP.apila_ind();
    }

    void gen_acc_id(Param param){
        gen_acc_var(param);//TODO
    }

    void gen_acc_var(Param param){
        maquinaP.apilad(param.getNivel());
        maquinaP.apila_int(param.getDir());
        maquinaP.suma();
    }

    @Override
    public void recolecta_subs(SiDecs siDecs){
        siDecs.getDecsAux().recolecta_subs(this);
    }

    @Override
    public void recolecta_subs(NoDecs noDecs){}

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
    public void recolecta_subs(DecVar decVar){}

    @Override
    public void recolecta_subs(DecTipo decTipo){}

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
        muchosParamsR.getExp().procesa(this);
        // TODO terminar
    }

    void gen_paso_param_aux(UnParamF unParamF, Un_ParamsR unParamsR){
        maquinaP.dup();
        maquinaP.apila_int(unParamF.getParam().getDir());
        maquinaP.suma();
        unParamsR.getExp().procesa(this);
        // TODO terminar
    }

}
