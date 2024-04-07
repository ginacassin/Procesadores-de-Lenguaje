package evaluador;

public class EvaluadorRecursivo extends Evaluador {
    private void imprime(String str) {
        System.out.println(str);
    }
    private void imprimeNL() {
        // System.out.println();
    }
    private String getFilaColInfo(Nodo n) {
        return "$f:"+n.leeFila()+",c:"+n.leeCol()+"$";
    }


    // funcion que se llama solo externamente
    public void muestraPrograma(Prog n) {
        muestraBloque(n.getBloq());
    }

    private void muestraBloque(Bloq bloq) {
        imprime("{"); imprimeNL();
        muestraDecs(bloq.getDecs()); // muestra declaraciones
        muestraInstr(bloq.getInsts()); // muestra instrucciones
        imprimeNL(); imprime("}");
    }
    private void muestraDecs(Decs decs) {
        if(claseDe(decs,SiDecs.class)) {
           muestraDecs(decs.getDecsAux());
           imprime("&&");
        }
        // else, no hacemos nada con el NoDecs
    }
    private void muestraDecs(DecsAux decs) {
        if(claseDe(decs,MuchasDecs.class)) {
            muestraDecs(decs.getDecsAux());
            imprime(";"); imprimeNL();
            muestraDecs(decs.getDec());
        }
        else if (claseDe(decs, UnaDec.class)) {
            muestraDecs(decs.getDec());
        }
    }
    private void muestraDecs(Dec dec) {
        if (claseDe(dec, DecVar.class)) {
            muestraT(dec.getTipo());
            imprime(dec.getIden());
        }
        else if (claseDe(dec, DecTipo.class)) {
            imprime("<type>");
            muestraT(dec.getTipo());
            imprime(dec.getIden());
        }
        else { // DecProc
            imprime("<proc>");
            imprime(dec.getIden());
            imprime("(");
            muestraParamsF(dec.getParamsF());
            imprime(")");
            muestraBloque(dec.getBloq());
        }
    }

    /*
     * Procesamiento de Procedimientos
     */
    private void muestraParamsF(ParamsF params) {
        if (claseDe(params, SiParamF.class)) {
            muestraParametros(params.getParamsFL());
        }
    }

    private void muestraParametros(ParamsFL paramsfl) {
        if (claseDe(paramsfl,MuchosParamsF.class)) {
            muestraParametros(paramsfl.getParamsFL());
            imprime(",");
            muestraParametros(paramsfl.getParam());
        }
        else if (claseDe(paramsfl, UnParamF.class)) {
            muestraParametros(paramsfl.getParam());
        }
    }
    
    private void muestraParametros(Param param) {
        if (claseDe(param, ParamRef.class)) {
            muestraT(param.getTipo());
            imprime("&");
            imprime(param.getIden());
        }
        else { // ParamNoRef
            muestraT(param.getTipo());
            imprime(param.getIden());
        }
    }

    // Tipos
    private void muestraT(T tipo) {
        if (claseDe(tipo, TipoArray.class)) {
            muestraT(tipo.getTipo());
            imprime("[");
            imprime(tipo.getLitEnt());
            imprime("]");
        }
        else if (claseDe(tipo, TipoPunt.class)) {
            imprime("^");
            muestraT(tipo.getTipo());
        }
        else if (claseDe(tipo, TipoStruct.class)) {
            imprime("<struct>");
            imprime("{");
            muestraCampos(tipo.getlCampos());
            imprime("}");
        }
        else if (claseDe(tipo, TipoInt.class)) {
            imprime("<int>");
        }
        else if (claseDe(tipo, TipoReal.class)) {
            imprime("<real>");
        }
        else if (claseDe(tipo, TipoBool.class)) {
            imprime("<bool>");
        }
        else if (claseDe(tipo, TipoString.class)) {
            imprime("<string>");
        }
        else if (claseDe(tipo, Identificador.class)) {
            imprime(tipo.getIden());
        }
    }

    /*
     * Procesamiento de Structs
     */
    private void muestraCampos(LCampos lcampos) {
        if(claseDe(lcampos,Muchos_Campos.class)) {
            muestraCampos(lcampos.getlCampos());
            imprime(",");
            muestraCampos(lcampos.getCampo());
        }
        else if (claseDe(lcampos, Un_Campo.class)) {
            muestraCampos(lcampos.getCampo());
        }
    }

    private void muestraCampos(Campo campo) {
        muestraT(campo.getTipo());
        imprime(campo.getIden());
    }

    /*
     * Procesamiento de instrucciones
    */
    private void muestraInstr(Insts insts) {
        if (claseDe(insts, Si_Instr.class)) {
            muestraInstr(insts.getInstsAux());
        }
    }

    private void muestraInstr(InstsAux insts) {
        if(claseDe(insts, Muchas_Instr.class)) {
            muestraInstr(insts.getInstsAux());
            imprime(";");
            muestraInstr(insts.getInst());
        }
        else if (claseDe(insts, Una_Instr.class)) {
            muestraInstr(insts.getInst());
        }
    }

    private void muestraInstr(Inst inst) {
        if (claseDe(inst, Instr_Expr.class)) {
            imprime("@");
            muestraExp(inst.getExp());
        }
        else if (claseDe(inst, Instr_If.class)) {
            imprime("<if>");
            muestraExp(inst.getExp());
            muestraBloque(inst.getBloq());
        }
        else if (claseDe(inst, Instr_If_Else.class)) {
            imprime("<if>");
            muestraExp(inst.getExp());
            muestraBloque(inst.getBloq1());
            imprime("<else>");
            muestraBloque(inst.getBloq2());
        }
        else if (claseDe(inst, Instr_While.class)) {
            imprime("<while>");
            muestraExp(inst.getExp());
            muestraBloque(inst.getBloq());
        }
        else if (claseDe(inst, Instr_Read.class)) {
            imprime("<read>");
            muestraExp(inst.getExp());
        }
        else if (claseDe(inst, Instr_Write.class)) {
            imprime("<write>");
            muestraExp(inst.getExp());
        }
        else if (claseDe(inst, Instr_Nl.class)) {
            imprimeNL();
        }
        else if (claseDe(inst, Instr_New.class)) {
            imprime("<new>");
            muestraExp(inst.getExp());
        }
        else if (claseDe(inst, Instr_Del.class)) {
            imprime("<delete>");
            muestraExp(inst.getExp());
        }
        else if (claseDe(inst, Instr_Call.class)) {
            imprime("<call>");
            imprime(inst.getIden());
            imprime("(");
            muestraParamsR(inst.getParamsR());
            imprime(")");
        }
        else if (claseDe(inst, Instr_Bloque.class)) {
            muestraBloque(inst.getBloq());
        }
    }

    private void muestraParamsR(ParamsR paramsR) {
        if (claseDe(paramsR, Si_ParamsR.class)) {
            muestraParamsR(paramsR.getParamrl());
        }
    }

    private void muestraParamsR(ParamsRL paramsRL) {
        if (claseDe(paramsRL, MuchosParamsF.class)) {
            muestraParamsR(paramsRL.getParamrl());
            imprime(",");
            muestraExp(paramsRL.getExp());
        }
        else if (claseDe(paramsRL, Un_ParamsR.class)) {
            muestraExp(paramsRL.getExp());
        }
    }

    private void muestraExp(Exp exp) {
        if (claseDe(exp, Asignacion.class)) {
            muestraExpBin(exp.getOpnd0(), "=", exp.getOpnd1(), 1, 0);
        }
        else if (claseDe(exp, Menor.class)) {
            muestraExpBin(exp.getOpnd0(), "<", exp.getOpnd1(), 1, 2);
        }
        else if (claseDe(exp, Mayor.class)) {
            muestraExpBin(exp.getOpnd0(), ">", exp.getOpnd1(), 1, 2);
            
        }
        else if (claseDe(exp, MenorIgual.class)) {
            muestraExpBin(exp.getOpnd0(), "<=", exp.getOpnd1(), 1, 2);
        }
        else if (claseDe(exp, MayorIgual.class)) {
            muestraExpBin(exp.getOpnd0(), ">=", exp.getOpnd1(), 1, 2);
            
        }
        else if (claseDe(exp, Igual.class)) {
            muestraExpBin(exp.getOpnd0(), "==", exp.getOpnd1(), 1, 2);
        }
        else if (claseDe(exp, NoIgual.class)) {
            muestraExpBin(exp.getOpnd0(), "!=", exp.getOpnd1(), 1, 2);
        }
        else if (claseDe(exp, Suma.class)) {
            muestraExpBin(exp.getOpnd0(), "+", exp.getOpnd1(), 2, 3);
        }
        else if (claseDe(exp, Resta.class)) {
            muestraExpBin(exp.getOpnd0(), "-", exp.getOpnd1(), 3, 3);
        }
        else if (claseDe(exp, And.class)) {
            muestraExpBin(exp.getOpnd0(), "<and>", exp.getOpnd1(), 4, 3);
        }
        else if (claseDe(exp, Or.class)) {
            muestraExpBin(exp.getOpnd0(), "<or>", exp.getOpnd1(), 4, 4);
        }
        else if (claseDe(exp, Mul.class)) {
            muestraExpBin(exp.getOpnd0(), "*", exp.getOpnd1(), 4, 5);
        }
        else if (claseDe(exp, Div.class)) {
            muestraExpBin(exp.getOpnd0(), "/", exp.getOpnd1(), 4, 5);
        }
        else if (claseDe(exp, Mod.class)) {
            muestraExpBin(exp.getOpnd0(), "%", exp.getOpnd1(), 4, 5);
        }
        else if (claseDe(exp, Negativo.class)) {
            muestraExpUn(exp.getOpnd0(), "-", 5);
        }
        else if (claseDe(exp, Not.class)) {
            muestraExpUn(exp.getOpnd0(), "<not>", 5);
        }
        else if (claseDe(exp, Index.class)) {
            muestraOpnd(exp.getOpnd0(), 6);
            imprime("[");
            muestraExp(exp.getOpnd1());
            imprime("]");
        }
        else if (claseDe(exp, Acceso.class)) {
            muestraOpnd(exp.getOpnd0(), 6);
            imprime(".");
            imprime(exp.getIden());
        }
        else if (claseDe(exp, Indireccion.class)) {
            muestraOpnd(exp.getOpnd0(), 6);
            imprime("^");
        }
        else if (claseDe(exp, Lit_ent.class)) {
            imprime(exp.getNum() + getFilaColInfo(exp));
        }
        else if (claseDe(exp, Lit_real.class)) {
            imprime(exp.getNum() + getFilaColInfo(exp));
        }
        else if (claseDe(exp, True.class)) {
            imprime("<true>" + getFilaColInfo(exp));
        }
        else if (claseDe(exp, False.class)) {
            imprime("<false>" + getFilaColInfo(exp));
        }
        else if (claseDe(exp, Lit_cadena.class)) {
            imprime(exp.getCadena() + getFilaColInfo(exp));
        }
        else if (claseDe(exp, Iden.class)) {
            imprime(exp.getId() + getFilaColInfo(exp));
        }
        else if (claseDe(exp, Null.class)) {
            imprime("<null>" + getFilaColInfo(exp));
        }
    }

    private void muestraExpBin(Exp exp1, String operacion, Exp exp2, int p1, int p2) {
        muestraOpnd(exp1, p1);
        imprime(operacion);
        muestraOpnd(exp2, p2);
    }

    private void muestraExpUn(Exp exp1, String operacion, int p1) {
        imprime(operacion); 
	    muestraOpnd(exp1, p1);
    }
    
    private void muestraOpnd(Exp opnd, int np) {
        if (opnd.prioridad() < np) imprime("(");
        muestraExp(opnd);
        if (opnd.prioridad() < np) imprime(")");
    }

    
    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }    
}
