@Override
public boolean bind(ScopeManager sManager) throws BindingException {
    boolean ok = true;
    if(sManager.contieneIdEnAmbitoActual(identificador))
        throw new BindingException("   > Ya existe una declaracion con identificador \'" + identificador + "\' en el mismo ambito", fila);

    tipo.bind(sManager);

    if(expresion != null){
        try{
            ok &= expresion.bind(sManager);
        } catch(BindingException e){
            ok = false;
            GestionErroresTiny.errorBinding(e.getFila());
            System.out.println(e.getMessage());
        }
    }
    //Colocando esto debajo evitamos las definiciones recursivas;
    sManager.insertaId(identificador, this);
    return ok;
}

@Override
public boolean bind(ScopeManager sManager) throws BindingException {
    List<ASTNode> candidatos;
    if(es_this){
        if(!sManager.contenidaEnClase())
            throw new BindingException("   > Solo se permite el uso de \'this->\' dentro de clases", fila);
        candidatos = sManager.buscaIdEnClase(id);
    }
    else
        candidatos = sManager.buscaId(id);

    if(candidatos.isEmpty()){
        throw new BindingException("   > Identificador de variable \'" + id + "\' no declarado", fila);
    }

    super.setCandidatos(candidatos);
    return true;
}
