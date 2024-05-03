package implementacion.visitantes;

import implementacion.asint.ProcesamientoDef;
import implementacion.asint.SintaxisAbstractaTiny.*;

public class VinculacionSegunda extends ProcesamientoDef {
    private TablaSimbolos ts;
    public VinculacionSegunda(TablaSimbolos ts) {
        this.ts = ts;
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
    public void procesa(DecProc decProc) {}

    @Override
    public void procesa(SiParamF siParamF) {
        siParamF.getParamsFL().procesa(this);
    }

    @Override
    public void procesa(NoParamF noParamF) {}

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
    public void procesa(ParamNoRef param) {
        param.getTipo().procesa(this);
    }

    @Override
    public void procesa(ParamRef siParamF) {
        siParamF.getTipo().procesa(this);
    }

    @Override
    public void procesa(TipoArray tipoArray) {
        tipoArray.getTipo().procesa(this);
    }

    @Override
    public void procesa(TipoPunt tipoPunt) {
        if (tipoPunt.getTipo() instanceof Identificador) {
            Nodo nodo = ts.vinculoDe(tipoPunt.getTipo().getIden());
            tipoPunt.getTipo().setVinculo(nodo);
            if (!(nodo instanceof DecTipo)) {
                throw new RuntimeException("Vinculo debe ser declaración de tipo");
            }
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
    public void procesa(TipoInt tipoInt) {}

    @Override
    public void procesa(TipoReal tipoReal) {}

    @Override
    public void procesa(TipoBool tipoBool) {}

    @Override
    public void procesa(TipoString exp) {}

    @Override
    public void procesa(Identificador id) {}
}
