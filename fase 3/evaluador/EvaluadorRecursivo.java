package evaluador;

import asint.SintaxisAbstractaTiny.*;
import asint.SintaxisAbstractaTiny;

import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.HashMap;

public class EvaluadorRecursivo extends Evaluador {
    private EvaluadorRecursivo entornoPadre;
    private Set<Object> env;

    private class TStruct { // Clase usada para guardar structs en el env
        private Set<String> idens;
        private Map<String, TStruct> masStructs;
        private String name;
        public TStruct() {
            super();
        }
        public Set<String> getIdens() {
            return idens;
        }
        public Map<String, TStruct> getMasStructs() {
            return masStructs;
        }
        public void addIden(String iden) { idens.add(iden);}
        public void addStruct(String iden, TStruct struct) { masStructs.put(iden, struct);}
        public void setName(String name) {
            this.name = name;
        }
    }
    private class TProc { // Clase usada para representar un procedimiento
        private Set<String> idens;
        private String name;
        public TProc() {
            super();
        }
        public Set<String> getIdens() {
            return idens;
        }
        public String getName() {
            return name;
        }
        public void addIden(String iden) {
            this.idens.add(iden);
        }
        public void setName(String name) {
            this.name = name;
        }
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

    public EvaluadorRecursivo(EvaluadorRecursivo padre) {
        this.env = new TreeSet<>();
        this.entornoPadre = padre;
    }

    // funcion que se llama solo externamente
    public void evalua(Prog n) {
        procesaBloque(n.getBloq());
    }

    private void procesaBloque(Bloq bloq) {
        EvaluadorRecursivo hijo = new EvaluadorRecursivo(this); // creamos un nuevo hijo con un nuevo scope
        hijo.consEnv(bloq.getDecs()); // procesa declaraciones
        hijo.procesaInstr(bloq.getInsts()); // procesa instrucciones
    }
    private void consEnv(Decs decs) {
        if(claseDe(decs,SiDecs.class)) {
           consEnv(decs.getDecsAux());
        }
        // else, no hacemos nada con el NoDecs
    }
    private void consEnv(DecsAux decs) {
        if(claseDe(decs,MuchasDecs.class)) {
            consEnv(decs.getDecsAux());
            consEnv(decs.getDec());
        }
        else if (claseDe(decs, UnaDec.class)) {
            consEnv(decs.getDec());
        }
    }
    private void consEnv(Dec dec) {
        if(env.contains(dec.getIden())) {
            throw new ECteDuplicada("Cte duplicada: "+dec.getIden()+
                                     " fila:"+dec.leeFila()+" col:"+dec.leeCol()); 
        }
        if (claseDe(dec, DecVar.class)) {
            env.add(dec.getIden());
        }
        else if (claseDe(dec, DecTipo.class)) {
            if (claseDe(dec.getTipo(), TipoStruct.class)) {
                TStruct struct = procesaDecStruct(dec.getTipo().getlCampos());
                struct.setName(dec.getIden());
                env.add(struct);
                // TODO imprimir?
            }
            // TODO otros tipos ademas de structs... Por ejemplo: type int[5] t5ints;
        }
        else { // DecProc
            TProc proc = procesaDecProc(dec.getParamsF());
            proc.setName(dec.getIden());
            env.add(proc);
            procesaBloque(dec.getBloq());
        }
    }

    /*
     * Procesamiento de Structs
     */
    // Recibe los campos de un struct y devuelve un TStruct
    private TStruct procesaDecStruct(LCampos campos) {
        TStruct output = new TStruct();
        procesaCampos(campos, output);

        return output;
    }

    // Recibe la lista de campos de un struct y procesa uno a uno
    private void procesaCampos(LCampos lcampos, TStruct struct) {
        if(claseDe(lcampos,Muchos_Campos.class)) {
            procesaCampos(lcampos.getlCampos(), struct);
            procesaCampos(lcampos.getCampo(), struct);
        }
        else if (claseDe(lcampos, Un_Campo.class)) {
            procesaCampos(lcampos.getCampo(), struct);
        }
    }

    // Procesa un campo en particular agregandolo al struct pasado por argumento
    private void procesaCampos(Campo campo, TStruct struct) {
        T t = campo.getTipo();
        while (claseDe(campo.getTipo(), TipoArray.class) || claseDe(campo.getTipo(), TipoPunt.class)) {
            t = t.getTipo(); // "Desenvolvemos" al tipo
            // TODO imprimir?
        }

        if (claseDe(t, TipoStruct.class)) {
            struct.addStruct(campo.getIden(), procesaDecStruct(t.getlCampos()));
            // TODO imprimir?
        }
        else { // tipos Iden, real, int, bool, string
            struct.addIden(campo.getIden());
            // TODO imprimir?
        }
    }

    /*
     * Procesamiento de Procedimientos
     */
    private TProc procesaDecProc(ParamsF params) {
        TProc output = new TProc();
        if (claseDe(params, SiParamF.class)) {
            procesaParametros(params.getParamsFL(), output);
        }

        return output;
    }

    private void procesaParametros(ParamsFL paramsfl, TProc proc) {
        if (claseDe(paramsfl,MuchosParamsF.class)) {
            procesaParametros(paramsfl.getParamsFL(), proc);
            procesaParametros(paramsfl.getParam(), proc);
        }
        else if (claseDe(paramsfl, UnParamF.class)) {
            procesaParametros(paramsfl.getParam(), proc);
        }
    }
    
    private void procesaParametros(Param param, TProc proc) {
        if (claseDe(param, ParamNoRef.class)) {
            
        }
        else {

        }
    }

    // private float eval(Exp exp) {
    //     if(claseDe(exp,Lit_ent.class) || claseDe(exp,Lit_real.class)) {
    //         return Float.valueOf(exp.valor()).floatValue();
    //     }
    //     else if(claseDe(exp,Iden.class)) {
    //         if(! env.containsKey(exp.iden())) {
    //             throw new ECteNoDefinida("Cte indefinida: "+exp.iden()+
    //                                     " fila:"+exp.leeFila()+" col:"+exp.leeCol()); 
    //         }
    //         else {
    //             return env.get(exp.iden());
    //         }
    //     }
    //     else {
    //         float v1 = eval(exp.opnd0());
    //         float v2 = eval(exp.opnd1());
    //         if(claseDe(exp,Suma.class)) {
    //             return v1+v2;
    //         }
    //         else if(claseDe(exp,Resta.class)) {
    //             return v1-v2;
    //         }
    //         else if(claseDe(exp,Mul.class)) {
    //             return v1*v2;
    //         }
    //         else {
    //             return v1/v2;
    //         }
    //     }
    // } 
    
    private boolean claseDe(Object o, Class c) {
        return o.getClass() == c;
    }    
}
