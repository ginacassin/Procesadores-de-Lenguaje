package asint;

import asint.SintaxisAbstractaTiny.Exp;
import asint.SintaxisAbstractaTiny.ParamsR;
import asint.SintaxisAbstractaTiny.ParamsRL;
import asint.SintaxisAbstractaTiny.Insts;
import asint.SintaxisAbstractaTiny.InstsAux;
import asint.SintaxisAbstractaTiny.Inst;
import asint.SintaxisAbstractaTiny.Campo;
import asint.SintaxisAbstractaTiny.LCampos;
import asint.SintaxisAbstractaTiny.T;
import asint.SintaxisAbstractaTiny.ParamsF;
import asint.SintaxisAbstractaTiny.Param;
import asint.SintaxisAbstractaTiny.ParamsFL;
import asint.SintaxisAbstractaTiny.Dec;
import asint.SintaxisAbstractaTiny.Decs;
import asint.SintaxisAbstractaTiny.DecsAux;
import asint.SintaxisAbstractaTiny.Bloq;
import asint.SintaxisAbstractaTiny.Prog;

public interface Procesamiento {
    void procesa(Exp exp);
    void procesa(ParamsR paramsR);
    void procesa(ParamsRL paramsRL);
    void procesa(Insts insts);
    void procesa(InstsAux instsAux);
    void procesa(Inst inst);
    void procesa(Campo campo);
    void procesa(LCampos lCampos);
    void procesa(T t);
    void procesa(ParamsF paramsF);
    void procesa(Param param);
    void procesa(ParamsFL paramsFL);
    void procesa(Dec dec);
    void procesa(Decs decs);
    void procesa(DecsAux decsAux);
    void procesa(Bloq bloq);
    void procesa(Prog prog);
}