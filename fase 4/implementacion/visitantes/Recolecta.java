package visitantes;

import asint.SintaxisAbstractaTiny;

public interface Recolecta {
    public void recolecta_subs(SintaxisAbstractaTiny.SiDecs exp) ;
    public void recolecta_subs(SintaxisAbstractaTiny.NoDecs exp) ;
    public void recolecta_subs(SintaxisAbstractaTiny.MuchasDecs exp) ;
    public void recolecta_subs(SintaxisAbstractaTiny.UnaDec exp) ;
    public void recolecta_subs(SintaxisAbstractaTiny.DecVar exp) ;
    public void recolecta_subs(SintaxisAbstractaTiny.DecTipo exp) ;
    public void recolecta_subs(SintaxisAbstractaTiny.DecProc exp) ;
}
