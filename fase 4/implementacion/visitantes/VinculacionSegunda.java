package visitantes;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

import java.util.ArrayList;
import java.util.List;

public class VinculacionSegunda extends ProcesamientoDef {
    private TablaSimbolos ts;
    private List<String> errors;
    public VinculacionSegunda(TablaSimbolos ts, List<String> errors) {
        this.ts = ts;
        this.errors = errors;
    }

    @Override
    public void procesa(MuchasDecs muchasDecs) {
        muchasDecs.getDecsAux().procesa(this);
        muchasDecs.getDec().procesa(this);
    }

    @Override
    public void procesa(UnaDec unaDec) {
        unaDec.getDec().procesa(this);
    }

    @Override
    public void procesa(DecVar decVar) {
        decVar.getTipo().procesa(this);
    }

    @Override
    public void procesa(DecTipo decTipo) {
        decTipo.getTipo().procesa(this);
    }

    @Override
    public void procesa(DecProc decProc) {} // noop

    @Override
    public void procesa(SiParamF siParamF) {
        siParamF.getParamsFL().procesa(this);
    }

    @Override
    public void procesa(NoParamF noParamF) {} // noop

    @Override
    public void procesa(MuchosParamsF muchosParamsF) {
        muchosParamsF.getParamsFL().procesa(this);
        muchosParamsF.getParam().procesa(this);
    }

    @Override
    public void procesa(UnParamF unParamF) {
        unParamF.getParam().procesa(this);
    }

    @Override
    public void procesa(ParamRef paramRef) {
        paramRef.getTipo().procesa(this);
    }

    @Override
    public void procesa(ParamNoRef param) {
        param.getTipo().procesa(this);
    }

    @Override
    public void procesa(TipoArray tipoArray) {
        tipoArray.getTipo().procesa(this);
    }

    @Override
    public void procesa(TipoPunt tipoPunt) {
        if (tipoPunt.getTipo() instanceof Identificador) {
            Nodo vinculo = ts.vinculoDe(tipoPunt.getTipo().getIden());
            if (!(vinculo instanceof DecTipo)) {
                errors.add("ERROR_VINCULACION. Vinculo debe ser declaración de tipo. " + tipoPunt.getFilaColInfo());
            }
            tipoPunt.getTipo().setVinculo(vinculo);
        } else {
            tipoPunt.getTipo().procesa(this);
        }
    }

    @Override
    public void procesa(TipoStruct tipoStruct) {
        tipoStruct.getlCampos().procesa(this);
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
    public void procesa(Identificador id) {} // noop
}
