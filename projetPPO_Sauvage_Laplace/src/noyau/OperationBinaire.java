package noyau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public abstract class OperationBinaire extends Formule implements Serializable {

    protected Case droite;

    protected Case gauche ;

    /**
     *
     * @return ArrayList contenant les cases gauche et droite
     */
    public  List<Case> getCasesFormule(){
    	
    	 List<Case>  c = new ArrayList<>() ;
    	 c.add(droite) ; 
    	 c.add(gauche) ; 
    	 
    	return c ; 
    }

}
