package visitantes;

import java.util.HashSet;
import java.util.Set;

import asint.Procesamiento;
import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class Tipado extends ProcesamientoDef {

    private boolean outputJuez;
    private boolean hayError = false;
    private ErrorReporter er = new ErrorReporter();

    public boolean hayErrorTipado() {
        return this.hayError;
    }

    private void agregarError(int fila, int col, String razon) {
        this.hayError = true;
        if (this.outputJuez) {
            er.reportarError(fila, col, ("Errores_tipado fila:" + fila + " col:" + col));
        }
        else {
            er.reportarError(fila, col, (fila + "," + col + ":" + razon));
        }
    }

    public void imprimirErroresTipado() {
        er.imprimirErroresOrdenados();
    }

    public Tipado(boolean outputJuez) {
        this.outputJuez = outputJuez;
    }

    public static class TipoOK extends T {
        @Override
        public void procesa(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa'");
        }
        @Override
        public void procesa_acc_val(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa_acc_val'");
        }
        @Override
        public void procesa_acc_id(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa_acc_id'");
        }
        @Override
        public void procesa_acc_var(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa_acc_var'");
        }
        @Override
        public void recolecta_subs(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'recolecta_subs'");
        }
        @Override
        public void imprime() {
            throw new UnsupportedOperationException("Unimplemented method 'imprime'");
        }
    }
    public static class TipoError extends T {
        @Override
        public void procesa(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa'");
        }
        @Override
        public void procesa_acc_val(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa_acc_val'");
        }
        @Override
        public void procesa_acc_id(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa_acc_id'");
        }
        @Override
        public void procesa_acc_var(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'procesa_acc_var'");
        }
        @Override
        public void recolecta_subs(Procesamiento p) {
            throw new UnsupportedOperationException("Unimplemented method 'recolecta_subs'");
        }
        @Override
        public void imprime() {
            throw new UnsupportedOperationException("Unimplemented method 'imprime'");
        }
    }

    private T ambos_ok(T t1, T t2) {
        if (t1 instanceof TipoOK && t2 instanceof TipoOK)
            return new TipoOK();
        return new TipoError();
    }

    private void aviso_error(Nodo t1, Nodo t2, String mensaje) {
        if (!(t1 instanceof TipoError)) {
            agregarError(t1.leeFila(), t1.leeCol(), mensaje);
        }
        if (!(t2 instanceof TipoError)) {
            agregarError(t2.leeFila(), t2.leeCol(), "algun error");
        }
    }

    private void aviso_error(Nodo t, String mensaje) {
        if (!(t instanceof TipoError)) {
            agregarError(t.leeFila(), t.leeCol(), mensaje);
        }
    }
    public class Pair {
        private final T t1, t2;
        public Pair(T t1, T t2) {
            this.t1 = t1;
            this.t2 = t2;
        }
    }
    private Set<Pair> theta;

    private boolean son_unificables(LCampos l1, LCampos l2, boolean esParamRef) {
        if (l1 instanceof Muchos_Campos && l2 instanceof Muchos_Campos) {
            LCampos l1Prime = l1.getlCampos();
            LCampos l2Prime = l2.getlCampos();
            Campo c1 = l1.getCampo();
            Campo c2 = l2.getCampo();
            if (son_unificables(c1.getTipo(), c2.getTipo(), esParamRef)) {
                return son_unificables(l1Prime, l2Prime, esParamRef);
            }
            else return false;
        }
        else if (l1 instanceof Muchos_Campos && l2 instanceof Un_Campo) {
            return false;
        }
        else if (l1 instanceof Un_Campo && l2 instanceof Muchos_Campos) {
            return false;
        }
        else if (l1 instanceof Un_Campo && l2 instanceof Un_Campo) {
            return son_unificables(((Un_Campo)l1).getCampo().getTipo(), 
                                   ((Un_Campo)l2).getCampo().getTipo(),
                                   esParamRef);
        }
        return false;
    }

    private boolean son_unificables(T t1, T t2, boolean esParamRef) {
        Pair p = new Pair(t1, t2);
        if (!theta.contains(p)) {
            theta.add(p);
            return unificables(t1, t2, esParamRef);
        } else {
            return true;
        }
    }

    private boolean unificables(T t1, T t2, boolean esParamRef) {
        T t1Prime = ref(t1);
        T t2Prime = ref(t2);

        // Lógica de unificación
        if (t1Prime.getClass() == t2Prime.getClass()) {
            return true;
        } else if (t1Prime instanceof TipoReal && t2Prime instanceof TipoInt) {
            return !esParamRef; // Se asigna un int a un real
        } else if (t1Prime instanceof TipoPunt && t2Prime instanceof TipoNull) {
            return true; // Se asigna null a un puntero
        } else if (t1Prime instanceof TipoArray && t2Prime instanceof TipoArray) {
            TipoArray array1 = (TipoArray) t1Prime;
            TipoArray array2 = (TipoArray) t2Prime;
            return Integer.parseInt(array1.getLitEnt()) == Integer.parseInt(array2.getLitEnt())
                && son_unificables(array1.getTipo(), array2.getTipo(), esParamRef);
        } else if (t1Prime instanceof TipoPunt && t2Prime instanceof TipoPunt) {
            TipoPunt punt1 = (TipoPunt) t1Prime;
            TipoPunt punt2 = (TipoPunt) t2Prime;
            return son_unificables(punt1.getTipo(), punt2.getTipo(), esParamRef);
        } else if (t1Prime instanceof TipoStruct && t2Prime instanceof TipoStruct) {
            TipoStruct struct1 = (TipoStruct) t1Prime;
            TipoStruct struct2 = (TipoStruct) t2Prime;
            return son_unificables(struct1.getlCampos(), struct2.getlCampos(), esParamRef);
        } else {
            return false;
        }
    }

    private boolean compatibles(T t1, T t2) {
        return compatibles(t1, t2, false);
    }

    private boolean compatibles(T t1, T t2, boolean esParamRef) {
        // System.out.println("Comparando: " + t1.getClass() +", "+t2.getClass());
        theta = new HashSet<>();
        return unificables(t1, t2, esParamRef);
    }

    @Override
    public void procesa(Prog prog) {
        prog.getBloq().procesa(this);
        T t = prog.getBloq().getTipado();
        prog.setTipado(t);
    }

    @Override
    public void procesa(Bloq bloq) {
        bloq.getDecs().procesa(this);
        bloq.getInsts().procesa(this);
        T t1 = bloq.getDecs().getTipado();
        T t2 = bloq.getInsts().getTipado();
        bloq.setTipado(ambos_ok(t1, t2));
    }

    @Override
    public void procesa(SiDecs siDecs) {
        siDecs.getDecsAux().procesa(this);
        T t = siDecs.getDecsAux().getTipado();
        siDecs.setTipado(t);
    }
    
    @Override
    public void procesa(NoDecs noDecs) {
        noDecs.setTipado(new TipoOK());
    }

    public void procesa(MuchasDecs decsAux) {
        decsAux.getDecsAux().procesa(this);
        decsAux.getDec().procesa(this);
        T t1 = decsAux.getDecsAux().getTipado();
        T t2 = decsAux.getDec().getTipado();
        decsAux.setTipado(ambos_ok(t1, t2));
    }

    public void procesa(UnaDec dec) {
        dec.getDec().procesa(this);
        T t = dec.getDec().getTipado();
        dec.setTipado(t);
    }

    public void procesa(DecVar dec) {
        dec.setTipado(new TipoOK());
    }

    public void procesa(DecTipo dec) {
        dec.setTipado(new TipoOK());
    }

    public void procesa(DecProc dec) {
        dec.getBloq().procesa(this);
        T t = dec.getBloq().getTipado();
        dec.setTipado(t);
    }

    public void procesa(Si_Instr siInstr) {
        siInstr.getInstsAux().procesa(this);
        T t = siInstr.getInstsAux().getTipado();
        siInstr.setTipado(t);
    }

    public void procesa(No_Instr noInstr) {
        noInstr.setTipado(new TipoOK());
    }

    public void procesa(Muchas_Instr muchasInstr) {
        muchasInstr.getInstsAux().procesa(this);
        muchasInstr.getInst().procesa(this);
        T t1 = muchasInstr.getInstsAux().getTipado();
        T t2 = muchasInstr.getInst().getTipado();
        muchasInstr.setTipado(ambos_ok(t1, t2));
    }

    public void procesa(Una_Instr unaInstr) {
        unaInstr.getInst().procesa(this);
        T t = unaInstr.getInst().getTipado();
        unaInstr.setTipado(t);
    }

    public void procesa(Instr_Expr instrExpr) {
        instrExpr.getExp().procesa(this);
        if (instrExpr.getTipado() instanceof TipoError)
            instrExpr.setTipado(new TipoError());
        else
            instrExpr.setTipado(new TipoOK());
    }

    public void procesa(Instr_If instrIf) {
        instrIf.getExp().procesa(this);
        instrIf.getBloq().procesa(this);
        if (ref(instrIf.getExp().getTipado()) instanceof TipoBool
            && instrIf.getBloq().getTipado() instanceof TipoOK) 
            instrIf.setTipado(new TipoOK());
        else {
            aviso_error(instrIf.getExp(), " esperada expresion booleana");
            instrIf.setTipado(new TipoError());
        }
    }

    public void procesa(Instr_If_Else instrIfElse) {
        instrIfElse.getExp().procesa(this);
        instrIfElse.getBloq1().procesa(this);
        instrIfElse.getBloq2().procesa(this);
        if (ref(instrIfElse.getExp().getTipado()) instanceof TipoBool
            && instrIfElse.getBloq1().getTipado() instanceof TipoOK
            && instrIfElse.getBloq2().getTipado() instanceof TipoOK) 
            instrIfElse.setTipado(new TipoOK());
            else {
                aviso_error(instrIfElse.getExp(), " esperada expresion booleana");
                instrIfElse.setTipado(new TipoError());
            }
    }

    public void procesa(Instr_While instrWhile) {
        instrWhile.getExp().procesa(this);
        instrWhile.getBloq().procesa(this);
        if (ref(instrWhile.getExp().getTipado()) instanceof TipoBool
            && instrWhile.getBloq().getTipado() instanceof TipoOK) 
            instrWhile.setTipado(new TipoOK());
            else {
                aviso_error(instrWhile.getExp(), " esperada expresion booleana");
                instrWhile.setTipado(new TipoError());
            }
    }

    public void procesa(Instr_Read inst) {
        inst.getExp().procesa(this);
        T t = ref(inst.getExp().getTipado());
        if (es_designador(inst.getExp())) {
            if (t instanceof TipoInt
                || t instanceof TipoReal
                || t instanceof TipoString) {
                inst.setTipado(new TipoOK());
            }
            else {
                aviso_error(inst.getExp(), "valor no legible");
                inst.setTipado(new TipoError());
            }
        }
        else {
            aviso_error(inst.getExp(), "designador esperado");
            inst.setTipado(new TipoError());
        }
    }

    public void procesa(Instr_Write inst) {
        inst.getExp().procesa(this);
        T t = ref(inst.getExp().getTipado());
        if (t instanceof TipoInt
            || t instanceof TipoReal
            || t instanceof TipoString
            || t instanceof TipoBool) {
            inst.setTipado(new TipoOK());
        }
        else {
            aviso_error(inst.getExp(), "valor no imprimible");
            inst.setTipado(new TipoError());
        }
    }

    public void procesa(Instr_Nl inst) {
        inst.setTipado(new TipoOK());
    }

    public void procesa(Instr_New inst) {
        inst.getExp().procesa(this);
        if (ref(inst.getExp().getTipado()) instanceof TipoPunt)
            inst.setTipado(new TipoOK());
        else {
            aviso_error(inst.getExp(), "esperado tipo puntero");
            inst.setTipado(new TipoError());
        }
    }

    public void procesa(Instr_Del inst) {
        inst.getExp().procesa(this);
        if (ref(inst.getExp().getTipado()) instanceof TipoPunt)
            inst.setTipado(new TipoOK());
        else {
            aviso_error(inst.getExp(), "esperado tipo puntero");
            inst.setTipado(new TipoError());
        }
    }

    public void procesa(Instr_Call inst) {
        inst.getParamsR().procesa(this);
        
        if (!(inst.getVinculo() instanceof DecProc)) {
            aviso_error(inst, "tipos incompatibles en operacion");
            inst.setTipado(new TipoError());
            return;
        }

        DecProc dec = ((DecProc)inst.getVinculo());
        if (num_elems(inst.getParamsR()) == num_elems(dec.getParamsF())) {
            T t = llamadas_compatibles(dec.getParamsF(), inst.getParamsR());
            inst.setTipado(t);
        }
        else {
            aviso_error(inst, "el numero de parametros reales no coincide con el numero de parametros formales");
            inst.setTipado(new TipoError());
        }
    }

    public void procesa(No_ParamsR no_ParamsR) {
        no_ParamsR.setTipado(new TipoOK());
    }

    public void procesa(Si_ParamsR si_ParamsR) {
        si_ParamsR.getParamsrl().procesa(this);
        si_ParamsR.setTipado(si_ParamsR.getParamsrl().getTipado());
    }

    public void procesa(Muchos_ParamsR muchos_ParamsR) {
        muchos_ParamsR.getParamrl().procesa(this);
        muchos_ParamsR.getExp().procesa(this);
        if (muchos_ParamsR.getExp().getTipado() instanceof TipoError){
            muchos_ParamsR.setTipado(new TipoError());
        }
        else 
            muchos_ParamsR.setTipado(muchos_ParamsR.getParamrl().getTipado());
    }

    public void procesa(Un_ParamsR un_ParamsR) {
        un_ParamsR.getExp().procesa(this);
        if (un_ParamsR.getExp().getTipado() instanceof TipoError)
            un_ParamsR.setTipado(new TipoError());
        else 
            un_ParamsR.setTipado(new TipoOK());
    }

    private int num_elems(ParamsR paramsR) {
        if (paramsR instanceof Si_ParamsR)
            return num_elems(paramsR.getParamsrl());
        else // if (paramsR instanceof No_ParamsR)
            return 0;
    }
    
    private int num_elems(ParamsRL paramsRL) {
        if (paramsRL instanceof Muchos_ParamsR) 
            return 1 + num_elems(paramsRL.getParamrl());
        else // if (paramsRL instanceof Un_ParamsR)
            return 1;
    }

    private int num_elems(ParamsF paramsF) {
        if (paramsF instanceof SiParamF)
            return num_elems(paramsF.getParamsFL());
        else // if (paramsF instanceof NoParamF)
            return 0;
    }
    
    private int num_elems(ParamsFL paramsFL) {
        if (paramsFL instanceof MuchosParamsF) 
            return 1 + num_elems(paramsFL.getParamsFL());
        else // if (paramsRL instanceof Un_ParamsR)
            return 1;
    }

    private T llamadas_compatibles(ParamsF paramsF, ParamsR paramsR) {
        if (paramsF instanceof SiParamF && paramsR instanceof Si_ParamsR)
            return llamadas_compatibles(paramsF.getParamsFL(), paramsR.getParamsrl());
        else if (paramsF instanceof NoParamF && paramsR instanceof No_ParamsR)
            return new TipoOK();
        throw new RuntimeException("Error con Params");
    }

    private T llamadas_compatibles(ParamsFL paramsFL, ParamsRL paramsRL) {
        if (paramsFL instanceof MuchosParamsF && paramsRL instanceof Muchos_ParamsR) {
            ParamsFL mas_ParamsFL = paramsFL.getParamsFL();
            T tipo_paramF = paramsFL.getParam().getTipo();
            ParamsRL mas_ParamsRL = paramsRL.getParamrl();
            Exp exp_paramR = paramsRL.getExp();
    
            Param paramF = paramsFL.getParam();
            if (paramF instanceof ParamNoRef) { // Param normal
                if (compatibles(tipo_paramF, exp_paramR.getTipado())) {
                    return llamadas_compatibles(mas_ParamsFL, mas_ParamsRL);
                }
                aviso_error(exp_paramR, "tipo incompatible con tipo de parametro formal");
                return new TipoError();
            }
            else { // Param ref
                if (es_designador(exp_paramR) 
                    && compatibles(tipo_paramF, exp_paramR.getTipado(), true)) {
                    return llamadas_compatibles(mas_ParamsFL, mas_ParamsRL);
                }
                aviso_error(exp_paramR, "tipo incompatible con tipo de parametro formal");
                return new TipoError();
            }
        }
        else if (paramsFL instanceof UnParamF && paramsRL instanceof Un_ParamsR) {
            Param param = paramsFL.getParam();
            Exp exp_paramR = paramsRL.getExp();

            if (param instanceof ParamNoRef) { // Param normal
                if (compatibles(param.getTipo(), exp_paramR.getTipado())) {
                    return new TipoOK();
                }
                aviso_error(exp_paramR, "tipo incompatible con tipo de parametro formal");
                return new TipoError();
            }
            else if (param instanceof ParamRef) {
                if (!es_designador(exp_paramR)) return new TipoError();
                if (compatibles(param.getTipo(), exp_paramR.getTipado())) {
                    return new TipoOK();
                }
                aviso_error(exp_paramR, "tipo incompatible con tipo de parametro formal");
                return new TipoError();
            }
        }
        return null;
    }

    public void procesa(Instr_Bloque inst) {
        inst.getBloq().procesa(this);
        T t = inst.getBloq().getTipado();
        inst.setTipado(t);
    }

    @Override
    public void procesa(Asignacion asig) {
        asig.getOpnd0().procesa(this);
        asig.getOpnd1().procesa(this);

        if (es_designador(asig.getOpnd0())) {
            if (compatibles(asig.getOpnd0().getTipado(), asig.getOpnd1().getTipado())) {
                asig.setTipado(asig.getOpnd0().getTipado());
            }
            else {
                aviso_error(asig, "tipos incompatibles en asignacion");
                asig.setTipado(new TipoError());
            }
        }
        else {
            agregarError(asig.leeFila(), asig.leeCol(), "la parte izquierda debe ser un designador");
            asig.setTipado(new TipoError());
        }
    }

    private void tipado_bin_comp(Exp e1, Exp e2, Exp e) {
        e1.procesa(this);
        e2.procesa(this);
        T t1 = ref(e1.getTipado());
        T t2 = ref(e2.getTipado());
        if (((t1 instanceof TipoInt || t1 instanceof TipoReal) 
                    && (t2 instanceof TipoInt || t2 instanceof TipoReal))
            || (t1 instanceof TipoBool && t2 instanceof TipoBool)
            || (t1 instanceof TipoString && t2 instanceof TipoString)) {
            e.setTipado(new TipoBool());
        }
        else {
            aviso_error(e, "tipos incompatibles en operacion");
            e.setTipado(new TipoError());
        }
    }

    public void procesa(Menor exp) {
        tipado_bin_comp(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    public void procesa(Mayor exp) {
        tipado_bin_comp(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    public void procesa(MenorIgual exp) {
        tipado_bin_comp(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    public void procesa(MayorIgual exp) {
        tipado_bin_comp(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    private void tipado_bin_igualdad(Exp e1, Exp e2, Exp e) {
        e1.procesa(this);
        e2.procesa(this);
        T t1 = ref(e1.getTipado());
        T t2 = ref(e2.getTipado());
        if (((t1 instanceof TipoInt || t1 instanceof TipoReal) 
                    && (t2 instanceof TipoInt || t2 instanceof TipoReal))
            || (t1 instanceof TipoBool && t2 instanceof TipoBool)
            || (t1 instanceof TipoString && t2 instanceof TipoString)
            || ((t1 instanceof TipoPunt || t1 instanceof TipoNull)
                    && (t2 instanceof TipoPunt || t2 instanceof TipoNull))) {
            e.setTipado(new TipoBool());
        }
        else {
            aviso_error(e, "tipos incompatibles en operacion");
            e.setTipado(new TipoError());
        }
    }

    public void procesa(Igual exp) {
        tipado_bin_igualdad(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    public void procesa(NoIgual exp) {
        tipado_bin_igualdad(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    private void tipado_bin_arit(Exp e1, Exp e2, Exp e) {
        e1.procesa(this);
        e2.procesa(this);
        T t1 = ref(e1.getTipado());
        T t2 = ref(e2.getTipado());
        
        if (t1 instanceof TipoInt && t2 instanceof TipoInt) {
            e.setTipado(new TipoInt());
        }
        else if ((t1 instanceof TipoInt || t1 instanceof TipoReal) 
                    && (t2 instanceof TipoInt || t2 instanceof TipoReal)) {
            e.setTipado(new TipoReal());
        }
        else {
            if (!(t1 instanceof TipoError) && !(t2 instanceof TipoError)) aviso_error(e, "tipos incompatibles en operacion");
            e.setTipado(new TipoError());
        }
    }

    public void procesa(Suma exp) {
        tipado_bin_arit(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    public void procesa(Resta exp) {
        tipado_bin_arit(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    public void procesa(Mul exp) {
        tipado_bin_arit(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    public void procesa(Div exp) {
        tipado_bin_arit(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    private void tipado_bin_logi(Exp e1, Exp e2, Exp e) {
        e1.procesa(this);
        e2.procesa(this);
        T t1 = ref(e1.getTipado());
        T t2 = ref(e2.getTipado());
        if (t1 instanceof TipoBool && t2 instanceof TipoBool) {
            e.setTipado(new TipoBool());
        }
        else {
            aviso_error(e, "tipos incompatibles en operacion");
            e.setTipado(new TipoError());
        }
    }

    public void procesa(And exp) {
        tipado_bin_logi(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    public void procesa(Or exp) {
        tipado_bin_logi(exp.getOpnd0(), exp.getOpnd1(), exp);
    }

    public void procesa(Mod exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
        T t1 = ref(exp.getOpnd0().getTipado());
        T t2 = ref(exp.getOpnd1().getTipado());
        if (t1 instanceof TipoInt && t2 instanceof TipoInt) {
            exp.setTipado(new TipoInt());
        }
        else {
            aviso_error(exp, "tipos incompatibles en operacion");
            exp.setTipado(new TipoError());
        }
    }

    public void procesa(Negativo exp) {
        exp.getOpnd0().procesa(this);
        T t = ref(exp.getOpnd0().getTipado());
        if (t instanceof TipoInt || t instanceof TipoReal || t instanceof TipoError) {
            exp.setTipado(t);
        }
        else {
            aviso_error(exp, "tipo incompatible en operacion");
            exp.setTipado(new TipoError());
        }
    }

    public void procesa(Not exp) {
        exp.getOpnd0().procesa(this);
        T t = ref(exp.getOpnd0().getTipado());
        if (t instanceof TipoBool) {
            exp.setTipado(t);
        }
        else {
            aviso_error(exp, "tipo incompatible en operacion");
            exp.setTipado(new TipoError());
        }
    }

    public void procesa(Index exp) {
        exp.getOpnd().procesa(this);
        T t1 = ref(exp.getOpnd().getTipado());
        exp.getIndex().procesa(this);
        T t2 = ref(exp.getIndex().getTipado());
        if (t1 instanceof TipoArray && t2 instanceof TipoInt) {
            exp.setTipado(t1.getTipo());
        }
        else {
            aviso_error(exp, "tipos incompatibles en indexacion");
            exp.setTipado(new TipoError());
        }
    }

    public void procesa(Acceso exp) {
        exp.getOpnd().procesa(this);
        T t = ref(exp.getOpnd().getTipado());
        if (t instanceof TipoStruct) {
            T t2 = esCampoDe(exp.getIden(), t.getlCampos());
            if (t2 instanceof TipoError) {
                aviso_error(exp, "campo inexistente:"+exp.getIden());
            }
            exp.setTipado(t2);
        }
        else {
            aviso_error(exp, "se trata de acceder a un campo de un objeto que no es un registro");
            exp.setTipado(new TipoError());
        }
    }

    private T esCampoDe(String iden, LCampos lcampos) {
        if (lcampos instanceof Muchos_Campos) {
            T t = esCampoDe(iden, lcampos.getCampo());
            if (t instanceof TipoError) {
                return esCampoDe(iden, lcampos.getlCampos());
            }
            else return t;
        }
        else if (lcampos instanceof Un_Campo) {
            return esCampoDe(iden, lcampos.getCampo());
        }
        return null;
    }

    private T esCampoDe(String iden, Campo campo) {
        if (iden.equals(campo.getIden())) {
            return campo.getTipo();
        }
        return new TipoError();
    }

    public void procesa(Indireccion exp) {
        exp.getOpnd0().procesa(this);
        T t = ref(exp.getOpnd0().getTipado());
        if (t instanceof TipoPunt) {
            exp.setTipado(t.getTipo());
        }
        else {
            aviso_error(t, "esperado tipo puntero");
            exp.setTipado(new TipoError());
        }
    }

    public void procesa(Lit_ent exp) {
        exp.setTipado(new TipoInt());
    }

    public void procesa(Lit_real exp) {
        exp.setTipado(new TipoReal());
    }

    public void procesa(True exp) {
        exp.setTipado(new TipoBool());
    }

    public void procesa(False exp) {
        exp.setTipado(new TipoBool());
    }

    public void procesa(Lit_cadena exp) {
        exp.setTipado(new TipoString());
    }

    public void procesa(Iden iden) {
        Nodo vinculo = iden.getVinculo();
        if (vinculo instanceof DecVar) {
            iden.setTipado(((DecVar)vinculo).getTipo());
        }
        else if (vinculo instanceof ParamRef) {
            iden.setTipado(((ParamRef)vinculo).getTipo());
        }
        else if (vinculo instanceof ParamNoRef) {
            iden.setTipado(((ParamNoRef)vinculo).getTipo());
        }
        else {
            aviso_error(iden, iden.getId() + " no es variable ni parametro");
            iden.setTipado(new TipoError());
        }
    }

    public void procesa(Null n) {
        n.setTipado(new TipoNull());
    }
}
