package visitantes;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class AsignacionEspacioSegunda extends ProcesamientoDef{
    @Override
    public void procesa(SiDecs siDecs){
        siDecs.getDecsAux().procesa(this);
    }

    @Override
    public void procesa(NoDecs noDecs) {
        // noop
    }

    @Override
    public void procesa(MuchasDecs muchasDecs){
        muchasDecs.getDecsAux().procesa(this);
        muchasDecs.getDec().procesa(this);
    }

    @Override
    public void procesa(UnaDec unaDec){
        unaDec.getDec().procesa(this);
    }

    @Override
    public void procesa(DecVar decVar){
        decVar.getTipo().procesa(this);
    }

    @Override
    public void procesa(DecTipo decTipo){
        decTipo.getTipo().procesa(this);
    }

    @Override
    public void procesa(DecProc decProc){
        // noop
    }

    @Override
    public void procesa(SiParamF siParamF){
        siParamF.getParamsFL().procesa(this);
    }

    @Override
    public void procesa(NoParamF noParamF){
        // noop
    }

    @Override
    public void procesa(MuchosParamsF muchosParamsF){
        muchosParamsF.getParamsFL().procesa(this);
        muchosParamsF.getParam().procesa(this);
    }

    @Override
    public void procesa(UnParamF unParamF){
        unParamF.getParam().procesa(this);
    }

    @Override
    public void procesa(ParamRef paramRef){
        paramRef.getTipo().procesa(this);
    }

    @Override
    public void procesa(ParamNoRef paramNoRef){
        paramNoRef.getTipo().procesa(this);
    }

    @Override
    public void procesa(TipoArray tipoArray){
        tipoArray.getTipo().procesa(this);
    }

    @Override
    public void procesa(TipoPunt tipoPunt){
        if (tipoPunt.getTipo() instanceof Identificador) {
            DecTipo vinculo = (DecTipo) tipoPunt.getTipo().getVinculo();
            tipoPunt.setTam(vinculo.getTipo().getTam());
        } else {
            tipoPunt.getTipo().procesa(this);
        }
    }

    @Override
    public void procesa(TipoStruct tipoStruct){
        tipoStruct.getlCampos().procesa(this);
    }

    @Override
    public void procesa(Muchos_Campos muchosCampos){
        muchosCampos.getlCampos().procesa(this);
        muchosCampos.getCampo().procesa(this);
    }

    @Override
    public void procesa(Un_Campo unCampo){
        unCampo.getCampo().procesa(this);
    }

    @Override
    public void procesa(Campo campo){
        campo.getTipo().procesa(this);
    }

    @Override
    public void procesa(TipoInt tipoInt) {} // noop

    @Override
    public void procesa(TipoReal tipoReal) {} // noop

    @Override
    public void procesa(TipoBool tipoBool) {} // noop

    @Override
    public void procesa(TipoString tipoString) {} // noop

    @Override
    public void procesa(Identificador identificador) {} // noop
}
