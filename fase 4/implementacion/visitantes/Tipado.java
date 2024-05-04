package visitantes;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashSet;
import java.util.Set;

import asint.Procesamiento;
import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class Tipado extends ProcesamientoDef {

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

    private static T ambos_ok(T t1, T t2) {
        if (t1 instanceof TipoOK && t2 instanceof TipoOK)
            return new TipoOK();
        return new TipoError();
    }

    private static T ref(T n) {
        if (n instanceof Identificador) {
            return ref(((DecTipo)n.getVinculo()).getTipo());
        }
        return n.getTipado();
    }

    private static boolean es_designador(Exp e) {
        return e instanceof Iden
            || e instanceof Acceso
            || e instanceof Index
            || e instanceof Indireccion;
    }
    
    private static Set<String> theta;

    private static boolean son_unificables(LCampos l1, LCampos l2) {
        if (l1 instanceof Muchos_Campos && l2 instanceof Muchos_Campos) {
            LCampos l1Prime = l1.getlCampos();
            LCampos l2Prime = l2.getlCampos();
            Campo c1 = l1.getCampo();
            Campo c2 = l2.getCampo();
            if (son_unificables(c1.getTipo(), c2.getTipo())) {
                return son_unificables(l1Prime, l2Prime);
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
                                   ((Un_Campo)l2).getCampo().getTipo());
        }
        return false;
    }

    private static boolean son_unificables(T t1, T t2) {
        String equation = t1.toString() + "=" + t1.toString();
        if (!theta.contains(equation)) {
            theta.add(equation);
            return unificables(t1, t2);
        } else {
            return true;
        }
    }

    private static boolean unificables(T t1, T t2) {
        Object t1Prime = ref(t1);
        Object t2Prime = ref(t2);

        // Lógica de unificación
        if (t1Prime.getClass() == t2Prime.getClass()) {
            return true;
        } else if (t1Prime instanceof TipoReal && t2Prime instanceof TipoInt) {
            return true; // Se asigna un int a un real
        } else if (t1Prime instanceof TipoPunt && t2Prime instanceof Null) {
            return true; // Se asigna null a un puntero
        } else if (t1Prime instanceof TipoArray && t2Prime instanceof TipoArray) {
            TipoArray array1 = (TipoArray) t1Prime;
            TipoArray array2 = (TipoArray) t2Prime;
            return Integer.parseInt(array1.getLitEnt()) == Integer.parseInt(array2.getLitEnt())
                && son_unificables(array1.getTipo(), array2.getTipo());
        } else if (t1Prime instanceof TipoPunt && t2Prime instanceof TipoPunt) {
            TipoPunt punt1 = (TipoPunt) t1Prime;
            TipoPunt punt2 = (TipoPunt) t2Prime;
            return son_unificables(punt1.getTipo(), punt2.getTipo());
        } else if (t1Prime instanceof TipoStruct && t2Prime instanceof TipoStruct) {
            TipoStruct struct1 = (TipoStruct) t1Prime;
            TipoStruct struct2 = (TipoStruct) t2Prime;
            return son_unificables(struct1.getlCampos(), struct2.getlCampos());
        } else {
            return false;
        }
    }

    private static boolean compatibles(T t1, T t2) {
        theta = new HashSet<>();
        return unificables(t1, t2);
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
        if (instrExpr.getTipado() instanceof TipoOK)
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
        else
            instrIf.setTipado(new TipoError());
    }

    public void procesa(Instr_If_Else instrIfElse) {
        instrIfElse.getExp().procesa(this);
        instrIfElse.getBloq1().procesa(this);
        instrIfElse.getBloq2().procesa(this);
        if (ref(instrIfElse.getExp().getTipado()) instanceof TipoBool
            && instrIfElse.getBloq1().getTipado() instanceof TipoOK
            && instrIfElse.getBloq2().getTipado() instanceof TipoOK) 
            instrIfElse.setTipado(new TipoOK());
        else
            instrIfElse.setTipado(new TipoError());
    }

    public void procesa(Instr_While instrWhile) {
        instrWhile.getExp().procesa(this);
        instrWhile.getBloq().procesa(this);
        if (ref(instrWhile.getExp().getTipado()) instanceof TipoBool
            && instrWhile.getBloq().getTipado() instanceof TipoOK) 
            instrWhile.setTipado(new TipoOK());
        else
            instrWhile.setTipado(new TipoError());
    }

    public void procesa(Instr_Read inst) {
        inst.getExp().procesa(this);
        T t = ref(inst.getExp().getTipado());
        if ((t instanceof TipoInt
            || t instanceof TipoReal
            || t instanceof TipoString)
            && es_designador(inst.getExp())) {
            inst.setTipado(new TipoOK());
        }
        else
            inst.setTipado(new TipoError());
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
        else
            inst.setTipado(new TipoError());
    }

    public void procesa(Instr_Nl inst) {
        inst.setTipado(new TipoOK());
    }

    public void procesa(Instr_New inst) {
        inst.getExp().procesa(this);
        if (ref(inst.getExp().getTipado()) instanceof TipoPunt)
            inst.setTipado(new TipoOK());
        else
            inst.setTipado(new TipoError());
    }

    public void procesa(Instr_Del inst) {
        inst.getExp().procesa(this);
        if (ref(inst.getExp().getTipado()) instanceof TipoPunt)
            inst.setTipado(new TipoOK());
        else
            inst.setTipado(new TipoError());
    }

    public void procesa(Instr_Call inst) {
        DecProc dec = ((DecProc)inst.getVinculo());
        T t = llamadas_compatibles(dec.getParamsF(), inst.getParamsR());
        inst.setTipado(t);
    }

    private T llamadas_compatibles(ParamsF paramsF, ParamsR paramsR) {
        if (paramsF instanceof SiParamF && paramsR instanceof Si_ParamsR)
            return llamadas_compatibles(paramsF.getParamsFL(), paramsR.getParamsrl());
        else if (paramsF instanceof SiParamF && paramsR instanceof No_ParamsR)
            return new TipoError();
        else if (paramsF instanceof NoParamF && paramsR instanceof Si_ParamsR)
            return new TipoError();
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
    
            exp_paramR.procesa(this);
            Param paramF = paramsFL.getParam();
            if (paramF instanceof ParamNoRef) { // Param normal
                if (es_designador(exp_paramR) 
                    && compatibles(exp_paramR.getTipado(), tipo_paramF)) {
                    return llamadas_compatibles(mas_ParamsFL, mas_ParamsRL);
                }
                return new TipoError();
            }
            else { // Param ref
                if (compatibles(exp_paramR.getTipado(), tipo_paramF)) {
                    return llamadas_compatibles(mas_ParamsFL, mas_ParamsRL);
                }
                return new TipoError();
            }
        }
        else if (paramsFL instanceof UnParamF && paramsRL instanceof Muchos_ParamsR) {
            return new TipoError();
        }
        else if (paramsFL instanceof MuchosParamsF && paramsRL instanceof Un_ParamsR) {
            return new TipoError();
        }
        else if (paramsFL instanceof UnParamF && paramsRL instanceof Un_ParamsR) {
            Param param = paramsFL.getParam();
            Exp exp_paramR = paramsRL.getExp();
            exp_paramR.procesa(this);

            if (param instanceof ParamNoRef) { // Param normal
                if (compatibles(exp_paramR.getTipado(), param.getTipo())) {
                    return new TipoOK();
                }
                else return new TipoError();
            }
            else if (param instanceof ParamRef) {
                if (!es_designador(exp_paramR)) return new TipoError();
                if (compatibles(exp_paramR.getTipado(), param.getTipo())) {
                    return new TipoOK();
                }
                else return new TipoError();
            }
        }
        return null;
    }
}
