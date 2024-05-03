package visitantes;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class VinculacionPrimera extends ProcesamientoDef {

    private TablaSimbolos ts;

    public class TamanioArrayInvalido extends RuntimeException {

        TamanioArrayInvalido(String msg) {
            super(msg);
        }
    }

    @Override
    public void procesa(Prog prog) {
        ts = new TablaSimbolos();
        prog.getBloq().procesa(this);
    }
    @Override
    public void procesa(Bloq bloq) {
        ts.abreAmbito();
        bloq.getDecs().procesa(this);
        bloq.getInsts().procesa(this);
    }
    @Override
    public void procesa(SiDecs siDecs) {
        siDecs.getDecsAux().procesa(this);
        siDecs.getDecsAux().procesa(new VinculacionSegunda(ts));
    }
    @Override
    public void procesa(NoDecs noDecs) {}

    public void procesa(MuchasDecs decsAux) {
        decsAux.getDecsAux().procesa(this);
        decsAux.getDec().procesa(this);
    }

    public void procesa(UnaDec dec) {
        dec.getDec().procesa(this);
    }

    public void procesa(DecVar dec) {
        dec.getTipo().procesa(this);
        ts.inserta(dec.getIden(), dec);
    }

    public void procesa(DecProc dec) {
        ts.inserta(dec.getIden(), dec);
        ts.abreAmbito();
        dec.getParamsF().procesa(this);
        dec.getBloq().procesa(this);
        VinculacionSegunda vs = new VinculacionSegunda(ts);
        dec.getParamsF().procesa(vs);
        dec.getBloq().procesa(vs);
        ts.cierraAmbito();
    }

    public void procesa(SiParamF siParamF) {
        siParamF.getParamsFL().procesa(this);
    }

    public void procesa(NoParamF noParamF) {}

    public void procesa(MuchosParamsF muchosParamsF) {
        muchosParamsF.getParamsFL().procesa(this);
        muchosParamsF.getParam().procesa(this);
    }

    public void procesa(UnParamF unParamF) {
        unParamF.getParam().procesa(this);
    }

    public void procesa(ParamRef paramRef) {
        paramRef.getTipo().procesa(this);
        ts.inserta(paramRef.getIden(), paramRef);
    }

    public void procesa(ParamNoRef param) {
        param.getTipo().procesa(this);
        ts.inserta(param.getIden(), param);
    }

    public void procesa(TipoArray tipoArray) {
        tipoArray.getTipo().procesa(this);
        if (Integer.parseInt(tipoArray.getLitEnt()) < 1) {
            throw new TamanioArrayInvalido("El tamaño del array debe ser positivo no nulo");
        }
    }

    @Override
    public void procesa(TipoPunt tipoPunt) {
        if (tipoPunt.getTipo() instanceof Identificador) {
            tipoPunt.getTipo().procesa(this);
        }
    }

    @Override
    public void procesa(TipoStruct tipoStruct) {
        ts.abreAmbito();
        tipoStruct.getlCampos().procesa(this);
        ts.cierraAmbito();
    }

    @Override
    public void procesa(Muchos_Campos muchosCampos) {
        muchosCampos.getlCampos().procesa(this);
        muchosCampos.getCampo().procesa(this);
    }

    @Override
    public void procesa(Un_Campo unCampo) {
        unCampo.getCampo().procesa(this);
    }

    @Override
    public void procesa(TipoInt tipoInt) {}

    @Override
    public void procesa(TipoReal tipoReal) {}

    @Override
    public void procesa(TipoBool tipoBool) {}

    @Override
    public void procesa(TipoString exp) {}

    @Override
    public void procesa(Identificador id) {
        Nodo vinculo = ts.vinculoDe(id.getIden());
        if(!(vinculo instanceof DecTipo)) {
            throw new RuntimeException("Error de vinculación");
        }
        id.setVinculo(vinculo);
    }

    public void procesa(Si_Instr siInstr) {
        siInstr.getInstsAux().procesa(this);
    }

    public void procesa(No_Instr noInstr) {}

    public void procesa(Muchas_Instr muchasInstr) {
        muchasInstr.getInstsAux().procesa(this);
        muchasInstr.getInst().procesa(this);
    }

    public void procesa(Una_Instr unaInstr) {
        unaInstr.getInst().procesa(this);
    }

    public void procesa(Instr_Expr instrExpr) {
        instrExpr.getExp().procesa(this);
    }

    public void procesa(Instr_If instrIf) {
        instrIf.getExp().procesa(this);
        instrIf.getBloq().procesa(this);
    }

    public void procesa(Instr_If_Else instrIfElse) {
        instrIfElse.getExp().procesa(this);
        instrIfElse.getBloq1().procesa(this);
        instrIfElse.getBloq2().procesa(this);
    }

    public void procesa(Instr_While instrWhile) {
        instrWhile.getExp().procesa(this);
        instrWhile.getBloq().procesa(this);
    }

    public void procesa(Instr_Read inst) {
        inst.getExp().procesa(this);
    }

    public void procesa(Instr_Write inst) {
        inst.getExp().procesa(this);
    }

    public void procesa(Instr_Nl inst) {}

    public void procesa(Instr_New inst) {
        inst.getExp().procesa(this);
    }

    public void procesa(Instr_Del inst) {
        inst.getExp().procesa(this);
    }

    public void procesa(Instr_Call inst) {
        inst.getParamsR().procesa(this);
        Nodo vinculo = ts.vinculoDe(inst.getIden());
        inst.setVinculo(vinculo);
        if (vinculo == null) {
            throw new RuntimeException("Vinculo no encontrado");
        }
    }

    public void procesa(Instr_Bloque inst) {
        inst.getBloq().procesa(this);
    }

    public void procesa(Si_ParamsR siParamsR) {
        siParamsR.getParamsrl().procesa(this);
    }

    @Override
    public void procesa(No_ParamsR noParamsR) {}

    @Override
    public void procesa(Muchos_ParamsR muchosParamsR) {
        muchosParamsR.getParamrl().procesa(this);
        muchosParamsR.getExp().procesa(this);
    }

    @Override
    public void procesa(Un_ParamsR unParamsR) {
        unParamsR.getExp().procesa(this);
    }

    @Override
    public void procesa(Asignacion asig) {
        asig.getOpnd0().procesa(this);
        asig.getOpnd1().procesa(this);
    }

    public void procesa(Menor exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Mayor exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(MayorIgual exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(MenorIgual exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Igual exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(NoIgual exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Suma exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Resta exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(And exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Or exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Mul exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Div exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Mod exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Negativo exp) {
        exp.getOpnd().procesa(this);
    }

    public void procesa(Not exp) {
        exp.getOpnd().procesa(this);
    }

    public void procesa(Index exp) {
        exp.getOpnd0().procesa(this);
        exp.getOpnd1().procesa(this);
    }

    public void procesa(Acceso exp) {
        exp.getOpnd().procesa(this);
    }

    public void procesa(Indireccion exp) {
        exp.getOpnd().procesa(this);
    }

    public void procesa(Lit_ent exp) {}
    public void procesa(Lit_real exp) {}
    public void procesa(True exp) {}
    public void procesa(False exp) {}
    public void procesa(Lit_cadena exp) {}
    public void procesa(Iden exp) {
        Nodo vinculo = ts.vinculoDe(exp.getId());
        exp.setVinculo(vinculo);
        if (vinculo == null) {
            throw new RuntimeException("Vinculo no encontrado");
        }
    }

    @Override
    public void procesa(Null exp) {}
}
