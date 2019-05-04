package noyau;

import java.io.Serializable;
import java.util.List;

public abstract class Fonctions extends Formule implements Serializable {
    
	// Liste de cases utilisées par la fonction
    protected List<Case> cases_ope ;


    /**
     *
     * @return liste des cases utilisées par la fonctions
     */
    public  List<Case> getCasesFormule(){
    	return cases_ope ; 
    };

}
