package implementacion.visitantes;

import implementacion.asint.SintaxisAbstractaTiny.Nodo;

import java.util.HashMap;
import java.util.Map;

public class TablaSimbolos {
    private Ambito currentAmbito;

    public void abreAmbito() {
        currentAmbito = new Ambito(currentAmbito);
    }

    public void cierraAmbito() {
        currentAmbito = currentAmbito.getPadre();
    }

    public void inserta(String id, Nodo dec) {
        currentAmbito.inserta(id, dec);
    }

    public boolean contiene(String id) {
        return currentAmbito.contiene(id);
    }

    public Nodo vinculoDe(String id) {
        return currentAmbito.vinculoDe(id);
    }

    private class Ambito {
        private Ambito padre;
        private Map<String, Nodo> env;

        public Ambito(Ambito padre) {
            this.padre = padre;
            this.env = new HashMap<>();
        }

        public Ambito getPadre() {
            return padre;
        }

        public void inserta(String id, Nodo nodo) {
            if(env.containsKey(id))
                throw new IdDuplicada("Id: " + id + "ya esta en la tabla de simbolos");
            env.put(id, nodo);
        }

        public boolean contiene(String id) {
            return env.containsKey(id);
        }

        public Nodo vinculoDe(String id) {
            return env.get(id);
        }
    }

    public class IdDuplicada extends RuntimeException {
        public IdDuplicada(String msg) {
            super(msg);
        }
    }
}