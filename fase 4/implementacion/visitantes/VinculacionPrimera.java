package visitantes;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

import java.util.ArrayList;
import java.util.List;

public class VinculacionPrimera extends ProcesamientoDef {
    /*
        Procesa es sintácticamente equivalente a vincula en la memoria
     */
    private TablaSimbolos ts;
    private VinculacionSegunda vs;
    private List<String> errors;

    public VinculacionPrimera() {
        errors = new ArrayList<>();
    }

    @Override
    public void procesa(Prog prog) {
        ts = new TablaSimbolos(); // creaTS()
        vs = new VinculacionSegunda(ts, errors);
        prog.getBloq().procesa(this); // vincula(Bloq)
    }

    @Override
    public void procesa(Bloq bloq) {
        ts.abreAmbito();
        bloq.getDecs().procesa(this);
        bloq.getInsts().procesa(this);
        ts.cierraAmbito();
    }

    @Override
    public void procesa(SiDecs siDecs) {
        siDecs.getDecsAux().procesa(this); // vincula1(DecsAux)
        siDecs.getDecsAux().procesa(vs); // vincula2(DecsAux)
    }

    @Override
    public void procesa(NoDecs noDecs) {} // noop

    // Primera pasada
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

    public void procesa(DecTipo dec) {
        dec.getTipo().procesa(this);
        ts.inserta(dec.getIden(), dec);
    }

    public void procesa(DecProc dec) {
        ts.inserta(dec.getIden(), dec);
        ts.abreAmbito();
        dec.getParamsF().procesa(this);
        dec.getBloq().procesa(this);
        dec.getParamsF().procesa(vs);
        dec.getBloq().procesa(vs);
        ts.cierraAmbito();
    }

    public void procesa(SiParamF siParamF) {
        siParamF.getParamsFL().procesa(this);
    }

    public void procesa(NoParamF noParamF) {} // noop

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
        if (Integer.parseInt(tipoArray.getLitEnt()) < 1) { // Pre-tipado
            // El tamaño de los arrays es siempre un entero no negativo
            errors.add("ERROR_VINCULACION. El tamaño del array debe ser un positivo no nulo. " + tipoArray.getFilaColInfo());
        }
    }

    @Override
    public void procesa(TipoPunt tipoPunt) {
        if (!(tipoPunt.getTipo() instanceof Identificador)) {
            tipoPunt.getTipo().procesa(this);
        }
    }

    @Override
    public void procesa(TipoStruct tipoStruct) {
        ts.abreAmbito(); // Pre-tipado: las definiciones de tipo registro no tienen campos duplicados
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
    public void procesa(Campo campo) {
        campo.getTipo().procesa(this);
    }

    @Override
    public void procesa(TipoInt tipoInt) {} // noop

    @Override
    public void procesa(TipoReal tipoReal) {} // noop

    @Override
    public void procesa(TipoBool tipoBool) {} // noop

    @Override
    public void procesa(TipoString exp) {} // noop

    @Override
    public void procesa(Identificador id) {
        // Pre-tipado:
        // Los vínculos de los nombres de tipo utilizados en las declaraciones de tipo
        // deben ser declaraciones type
        Nodo vinculo = ts.vinculoDe(id.getIden());
        if(!(vinculo instanceof DecTipo)) {
            errors.add("ERROR_VINCULACION. " + id.getFilaColInfo());
        }
        id.setVinculo(vinculo);
    }

    // Única pasada
    public void procesa(Si_Instr siInstr) {
        siInstr.getInstsAux().procesa(this);
    }

    public void procesa(No_Instr noInstr) {} // noop

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

    public void procesa(Instr_Nl inst) {} // noop

    public void procesa(Instr_New inst) {
        inst.getExp().procesa(this);
    }

    public void procesa(Instr_Del inst) {
        inst.getExp().procesa(this);
    }

    public void procesa(Instr_Call inst) {
        inst.getParamsR().procesa(this);
        Nodo vinculo = ts.vinculoDe(inst.getIden());
        if (vinculo == null) {
            errors.add("ERROR_VINCULACION. Vínculo no encontrado. " + inst.getFilaColInfo());
        }
        inst.setVinculo(vinculo);
    }

    public void procesa(Instr_Bloque inst) {
        inst.getBloq().procesa(this);
    }

    public void procesa(Si_ParamsR siParamsR) {
        siParamsR.getParamsrl().procesa(this);
    }

    @Override
    public void procesa(No_ParamsR noParamsR) {} // noop

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

    public void procesa(Lit_ent exp) {} // noop
    public void procesa(Lit_real exp) {} // noop
    public void procesa(True exp) {} // noop
    public void procesa(False exp) {} // noop
    public void procesa(Lit_cadena exp) {} // noop
    public void procesa(Iden id) {
        Nodo vinculo = ts.vinculoDe(id.getId());
        if (vinculo == null) {
            errors.add("ERROR_VINCULACION. " + id.getFilaColInfo());
        }
        id.setVinculo(vinculo);
    }

    @Override
    public void procesa(Null exp) {} // noop
}
