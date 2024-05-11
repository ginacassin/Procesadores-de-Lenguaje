package visitantes;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

import java.util.List;

public class VinculacionSegunda extends ProcesamientoDef {
    private TablaSimbolos ts;
    private boolean outputJuez;
    private boolean hayError = false;
    private ErrorReporter er;

    public VinculacionSegunda(TablaSimbolos ts, boolean outputJuez, ErrorReporter er) {
        this.ts = ts;
        this.outputJuez = outputJuez;
        this.er = er;
    }

    public boolean hayError() {
        return this.hayError;
    }

    private void agregarError(int fila, int col, String razon, String variable) {
        this.hayError = true;
        if (this.outputJuez) {
            er.reportarError(fila, col, ("Errores_vinculacion fila:" + fila + " col:" + col));
        }
        else {
            er.reportarError(fila, col, (fila + "," + col + ":" + razon + ":" + variable));
        }
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
                agregarError(tipoPunt.getTipo().leeFila(), tipoPunt.getTipo().leeCol(), "identificador no declarado", tipoPunt.getTipo().getIden());
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
