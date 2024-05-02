package ast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ScopeManager {
    private List<Scope> ambitos;
    private int ambito_actual;

    public ScopeManager(){
        this.ambitos = new ArrayList<Scope>();
        this.ambito_actual = -1;
    }

    private class Scope{
        boolean es_clase;
        Map<String, List<ASTNode>> scope;

        protected Scope(boolean es_clase) {
            this.es_clase = es_clase;
            this.scope = new HashMap<String, List<ASTNode>>();
        }

        protected boolean esClase() {
            return es_clase;
        }

        protected Map<String, List<ASTNode>> getScopeMap() {
            return scope;
        }
    }

    public void abreBloque(boolean es_clase){
        ambitos.add(new Scope(es_clase));
        ++ambito_actual;
    }

    public void cierraBloque(){
        ambitos.remove(ambito_actual);
        --ambito_actual;
    }

    //Para cuando encuentro una declaracion
    public void insertaId(String id, ASTNode dec){
        if(contieneIdEnAmbitoActual(id))
            ambitos.get(ambito_actual).getScopeMap().get(id).add(dec);
        else{
            List<ASTNode> lista = new ArrayList<ASTNode>();
            lista.add(dec);
            ambitos.get(ambito_actual).getScopeMap().put(id, lista);
        }
    }

    public boolean contieneIdEnAmbitoActual(String id){
        return ambitos.get(ambito_actual).getScopeMap().containsKey(id);
    }

    // Verdadero si el ambito actual es clase o struct
    public boolean contenidaEnClase(){
        return (ambito_actual >= 1 && ambitos.get(1).esClase());
    }

    //Para cuando encuentro un uso de id
    public List<ASTNode> buscaId(String id){
        List<ASTNode> candidatos = new ArrayList<ASTNode>();
        int i = ambito_actual;
        boolean found = false;
        while(!found && i >= 0){
            if(ambitos.get(i).getScopeMap().containsKey(id)){
                candidatos.addAll(ambitos.get(i).getScopeMap().get(id));
                found = true;
            }
            else
                i--;
        }
        return candidatos;
    }

    public List<ASTNode> buscaIdEnClase(String id){
        List<ASTNode> candidatos = new ArrayList<ASTNode>();
        if(ambitos.get(1).getScopeMap().containsKey(id)){
            candidatos.addAll(ambitos.get(1).getScopeMap().get(id));
        }
        return candidatos;
    }

}
