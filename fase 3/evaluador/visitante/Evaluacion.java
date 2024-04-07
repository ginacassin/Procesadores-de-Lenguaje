package evaluador.visitante;

import asint.ProcesamientoDef;
import asint.SintaxisAbstractaTiny.*;
import java.util.HashMap;
import java.util.Map;

public class Evaluacion extends ProcesamientoDef{
    private Map<String,Float> env;
    // para almacenar el resultado de la funcion
    // de procesamiento 'eval'
    private float resul;
    public Evaluacion() {
        env = new HashMap<>();
    }
    public class ECteNoDefinida extends RuntimeException {
        public ECteNoDefinida(String msg) {
            super(msg);
        }
    }
    public class ECteDuplicada extends RuntimeException {
        public ECteDuplicada(String msg) {
            super(msg);
        }
    }
    public float leeResul() {return resul;}

    /*public void procesa(Suma exp) {
        exp.getOpnd0().procesa(this);
        float val_opnd0 = resul;
        exp.getOpnd1().procesa(this);
        resul = val_opnd0 + resul;
    }
    public void procesa(Resta exp) {
        exp.getOpnd0().procesa(this);
        float val_opnd0 = resul;
        exp.getOpnd1().procesa(this);
        resul = val_opnd0 - resul;
    }
    public void procesa(Mul exp) {
        exp.getOpnd0().procesa(this);
        float val_opnd0 = resul;
        exp.getOpnd1().procesa(this);
        resul = val_opnd0 * resul;
    }
    public void procesa(Div exp) {
        exp.getOpnd0().procesa(this);
        float val_opnd0 = resul;
        exp.getOpnd1().procesa(this);
        resul = val_opnd0 / resul;
    }*/
    public void procesa(Asignacion exp) {
        
    }
    public void procesa(Menor exp) {

    }
    public void procesa(Mayor exp) {

    }
    public void procesa(MenorIgual exp) {

    }
    public void procesa(MayorIgual exp) {

    }
    public void procesa(Igual exp) {

    }
    public void procesa(NoIgual exp) {

    }
    public void procesa(And exp) {

    }
    public void procesa(Or exp) {

    }
    public void procesa(Mod exp) {

    }
    public void procesa(Negativo exp) {

    }
    public void procesa(Not exp) {

    }
    public void procesa(Indireccion exp) {

    }
    public void procesa(Index exp) {

    }
    public void procesa(Acceso exp) {

    }
    public void procesa(Lit_ent exp) {

    }
    public void procesa(Lit_real exp) {

    }
    public void procesa(True exp) {

    }
    public void procesa(False exp) {

    }
    public void procesa(Lit_cadena exp) {

    }
    public void procesa(Iden exp) {

    }
    public void procesa(Null exp) {

    }
    public void procesa(Si_ParamsR exp) {

    }
    public void procesa(No_ParamsR exp) {

    }
    public void procesa(Muchos_ParamsR exp) {

    }
    public void procesa(Un_ParamsR exp) {

    }
    public void procesa(Si_Instr exp) {

    }
    public void procesa(No_Instr exp) {

    }
    public void procesa(Muchas_Instr exp) {

    }
    public void procesa(Una_Instr exp) {

    }
    public void procesa(Instr_Expr exp) {

    }
    public void procesa(Instr_If exp) {

    }
    public void procesa(Instr_If_Else exp) {

    }
    public void procesa(Instr_While exp) {

    }
    public void procesa(Instr_Read exp) {

    }
    public void procesa(Instr_Write exp) {

    }
    public void procesa(Instr_Nl exp) {

    }
    public void procesa(Instr_New exp) {

    }
    public void procesa(Instr_Del exp) {

    }
    public void procesa(Instr_Call exp) {

    }
    public void procesa(Instr_Bloque exp) {

    }
    public void procesa(Campo exp) {

    }
    public void procesa(Muchos_Campos exp) {

    }
    public void procesa(Un_Campo exp) {

    }
    public void procesa(TipoArray exp) {

    }
    public void procesa(TipoStruct exp) {

    }
    public void procesa(TipoInt exp) {

    }
    public void procesa(TipoReal exp) {

    }
    public void procesa(TipoBool exp) {

    }
    public void procesa(TipoString exp) {

    }
    public void procesa(Identificador exp) {

    }
    public void procesa(ParamRef exp) {

    }
    public void procesa(ParamNoRef exp) {

    }
    public void procesa(SiParamF exp) {

    }
    public void procesa(NoParamF exp) {

    }
    public void procesa(MuchosParamsF exp) {

    }
    public void procesa(UnParamF exp) {

    }
    public void procesa(DecVar exp) {

    }
    public void procesa(DecTipo exp) {

    }
    public void procesa(DecProc exp) {

    }
    public void procesa(SiDecs exp) {

    }
    public void procesa(NoDecs exp) {

    }
    public void procesa(MuchasDecs exp) {

    }
    public void procesa(UnaDec exp) {

    }
    public void procesa(Bloq exp) {

    }
    public void procesa(Prog exp) {

    }
}





