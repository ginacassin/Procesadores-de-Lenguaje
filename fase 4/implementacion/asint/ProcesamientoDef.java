package implementacion.asint;

public class ProcesamientoDef implements Procesamiento {
    public void procesa(SintaxisAbstractaTiny.Suma exp) {}
    public void procesa(SintaxisAbstractaTiny.Resta exp) {}
    public void procesa(SintaxisAbstractaTiny.Mul exp) {}
    public void procesa(SintaxisAbstractaTiny.Div exp) {}
    public void procesa(SintaxisAbstractaTiny.Asignacion exp) {}
    public void procesa(SintaxisAbstractaTiny.Menor exp) {}
    public void procesa(SintaxisAbstractaTiny.Mayor exp) {}
    public void procesa(SintaxisAbstractaTiny.MenorIgual exp) {}
    public void procesa(SintaxisAbstractaTiny.MayorIgual exp) {}
    public void procesa(SintaxisAbstractaTiny.Igual exp) {}
    public void procesa(SintaxisAbstractaTiny.NoIgual exp){}
    public void procesa(SintaxisAbstractaTiny.And exp) {}
    public void procesa(SintaxisAbstractaTiny.Or exp) {}
    public void procesa(SintaxisAbstractaTiny.Mod exp) {}
    public void procesa(SintaxisAbstractaTiny.Negativo exp) {}
    public void procesa(SintaxisAbstractaTiny.Not exp) {}
    public void procesa(SintaxisAbstractaTiny.Indireccion exp) {}
    public void procesa(SintaxisAbstractaTiny.Index exp) {}
    public void procesa(SintaxisAbstractaTiny.Acceso exp){}
    public void procesa(SintaxisAbstractaTiny.Lit_ent exp) {}
    public void procesa(SintaxisAbstractaTiny.Lit_real exp) {}
    public void procesa(SintaxisAbstractaTiny.True exp) {}
    public void procesa(SintaxisAbstractaTiny.False exp) {}
    public void procesa(SintaxisAbstractaTiny.Lit_cadena exp) {}
    public void procesa(SintaxisAbstractaTiny.Iden exp) {}
    public void procesa(SintaxisAbstractaTiny.Null exp) {}
    public void procesa(SintaxisAbstractaTiny.Si_ParamsR exp) {}
    public void procesa(SintaxisAbstractaTiny.No_ParamsR exp) {}
    public void procesa(SintaxisAbstractaTiny.Muchos_ParamsR exp) {}
    public void procesa(SintaxisAbstractaTiny.Un_ParamsR exp) {}
    public void procesa(SintaxisAbstractaTiny.Si_Instr exp) {}
    public void procesa(SintaxisAbstractaTiny.No_Instr exp) {}
    public void procesa(SintaxisAbstractaTiny.Muchas_Instr exp) {}
    public void procesa(SintaxisAbstractaTiny.Una_Instr exp) {}
    public void procesa(SintaxisAbstractaTiny.Instr_Expr exp) {}
    public void procesa(SintaxisAbstractaTiny.Instr_If exp) {}
    public void procesa(SintaxisAbstractaTiny.Instr_If_Else exp) {}
    public void procesa(SintaxisAbstractaTiny.Instr_While exp){}
    public void procesa(SintaxisAbstractaTiny.Instr_Read exp) {}
    public void procesa(SintaxisAbstractaTiny.Instr_Write exp){}
    public void procesa(SintaxisAbstractaTiny.Instr_Nl exp) {}
    public void procesa(SintaxisAbstractaTiny.Instr_New exp) {}
    public void procesa(SintaxisAbstractaTiny.Instr_Del exp) {}
    public void procesa(SintaxisAbstractaTiny.Instr_Call exp) {}
    public void procesa(SintaxisAbstractaTiny.Instr_Bloque exp) {}
    public void procesa(SintaxisAbstractaTiny.Campo exp) {}
    public void procesa(SintaxisAbstractaTiny.Muchos_Campos exp) {}
    public void procesa(SintaxisAbstractaTiny.Un_Campo exp) {}
    public void procesa(SintaxisAbstractaTiny.TipoArray exp) {}
    public void procesa(SintaxisAbstractaTiny.TipoPunt exp) {}
    public void procesa(SintaxisAbstractaTiny.TipoStruct exp) {}
    public void procesa(SintaxisAbstractaTiny.TipoInt exp) {}
    public void procesa(SintaxisAbstractaTiny.TipoReal exp){}
    public void procesa(SintaxisAbstractaTiny.TipoBool exp) {}
    public void procesa(SintaxisAbstractaTiny.TipoString exp) {}
    public void procesa(SintaxisAbstractaTiny.Identificador exp) {}
    public void procesa(SintaxisAbstractaTiny.ParamRef exp) {}
    public void procesa(SintaxisAbstractaTiny.ParamNoRef exp){}
    public void procesa(SintaxisAbstractaTiny.SiParamF exp) {}
    public void procesa(SintaxisAbstractaTiny.NoParamF exp){}
    public void procesa(SintaxisAbstractaTiny.MuchosParamsF exp){}
    public void procesa(SintaxisAbstractaTiny.UnParamF exp){}
    public void procesa(SintaxisAbstractaTiny.DecVar exp){}
    public void procesa(SintaxisAbstractaTiny.DecTipo exp) {}
    public void procesa(SintaxisAbstractaTiny.DecProc exp){}
    public void procesa(SintaxisAbstractaTiny.SiDecs exp){}
    public void procesa(SintaxisAbstractaTiny.NoDecs exp){}
    public void procesa(SintaxisAbstractaTiny.MuchasDecs exp){}
    public void procesa(SintaxisAbstractaTiny.UnaDec exp){}

    @Override
    public void procesa(SintaxisAbstractaTiny.Bloq exp) {}

    @Override
    public void procesa(SintaxisAbstractaTiny.Prog exp) {}

    @Override
    public void procesa(SintaxisAbstractaTiny.Op op) {

    }
}




