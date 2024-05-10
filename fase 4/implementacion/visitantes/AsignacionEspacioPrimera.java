package visitantes;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;

public class AsignacionEspacioPrimera extends ProcesamientoDef {
    private int dir;
    private int max_dir;
    private int nivel;
    private AsignacionEspacioSegunda aes;
    public AsignacionEspacioPrimera(){
        this.dir = 0; // contador de direcciones
        this.max_dir = 0; // mantiene la máxima dirección asignada
        this.nivel = 0;
        this.aes = new AsignacionEspacioSegunda();
    }

    private void inc_dir(int inc) { // se incrementa dir y se mantiene max_dir
        this.dir += inc;
        if (this.dir > this.max_dir)
            this.max_dir = this.dir;
    }

    @Override
    public void procesa(Prog prog){
        prog.getBloq().procesa(this);
    }

    @Override
    public void procesa(Bloq bloq){
        int dir_ant = this.dir;
        bloq.getDecs().procesa(this);
        bloq.getDecs().procesa(aes);
        bloq.getInsts().procesa(this);
        this.dir = dir_ant;
    }

    // Primera pasada
    @Override
    public void procesa(SiDecs siDecs){
        siDecs.getDecsAux().procesa(this);
    }

    @Override
    public void procesa(NoDecs noDecs){
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
        decVar.setDir(this.dir);
        decVar.setNivel(this.nivel);
        inc_dir(decVar.getTipo().getTam());
    }

    @Override
    public void procesa(DecTipo decTipo){
        decTipo.getTipo().procesa(this);
    }

    @Override
    public void procesa(DecProc decProc){
        int dir_ant = this.dir;
        int max_dir_ant = this.max_dir;
        this.nivel++;
        decProc.setNivel(this.nivel);
        this.dir = 0;
        this.max_dir = 0;
        decProc.getParamsF().procesa(this);
        decProc.getParamsF().procesa(aes);
        decProc.getBloq().procesa(this);
        decProc.setTam(this.dir);
        this.dir = dir_ant;
        this.max_dir = max_dir_ant;
        this.nivel--;
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
        paramRef.setNivel(this.nivel);
        paramRef.setDir(this.dir);
        inc_dir(paramRef.getTipo().getTam());
    }

    @Override
    public void procesa(ParamNoRef paramNoRef){
        paramNoRef.getTipo().procesa(this);
        paramNoRef.setNivel(this.nivel);
        paramNoRef.setDir(this.dir);
        inc_dir(paramNoRef.getTipo().getTam());
    }

    @Override
    public void procesa(TipoArray tipoArray){
        tipoArray.getTipo().procesa(this);
        tipoArray.setTam(tipoArray.getTipo().getTam() * Integer.parseInt(tipoArray.getLitEnt()));
    }

    @Override
    public void procesa(TipoPunt tipoPunt){
        if (!(tipoPunt.getTipo() instanceof Identificador)) {
            tipoPunt.getTipo().procesa(this);
        }
        tipoPunt.setTam(1);
    }

    @Override
    public void procesa(TipoStruct tipoStruct){
        int dir_ant = this.dir;
        this.dir = 0;
        tipoStruct.getlCampos().procesa(this);
        tipoStruct.setTam(this.dir);
        this.dir = dir_ant;
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
        campo.setDesp(campo.getTipo().getDir());
        campo.getTipo().procesa(this);
        this.dir += campo.getTipo().getTam();
    }

    @Override
    public void procesa(TipoInt tipoInt){
        tipoInt.setTam(1);
    }

    @Override
    public void procesa(TipoReal tipoReal){
        tipoReal.setTam(1);
    }

    @Override
    public void procesa(TipoBool tipoBool){
        tipoBool.setTam(1);
    }

    @Override
    public void procesa(TipoString tipoString){
        tipoString.setTam(1);
    }

    @Override
    public void procesa(Identificador identificador){
        identificador.setTam(1);
    }

    // Única pasada
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
        //noop
    }

    @Override
    public void procesa(Instr_If instrIf){
        instrIf.getBloq().procesa(this);
    }

    @Override
    public void procesa(Instr_If_Else instrIfElse){
        instrIfElse.getBloq1().procesa(this);
        instrIfElse.getBloq2().procesa(this);
    }

    @Override
    public void procesa(Instr_While instrWhile){
        instrWhile.getBloq().procesa(this);
    }

    @Override
    public void procesa(Instr_Call instrCall){
        instrCall.getParamsR().procesa(this);
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
    public void procesa(Muchos_ParamsR muchosParamsR){
        muchosParamsR.getParamrl().procesa(this);
    }

}
