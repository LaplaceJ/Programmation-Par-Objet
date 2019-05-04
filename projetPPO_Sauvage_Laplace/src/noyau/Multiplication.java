package noyau;

import java.io.Serializable;


public class Multiplication extends OperationBinaire implements Serializable {

	/**
	 *
	 * @return case gauche * case droite
	 *
	 * @throws CaseErreurException si l'une des cases n'a pas de valeur, alors
	 *    nous propageons une CaseErreurException. Ainsi la case qui va contenir cette formule
	 *    sera elle aussi considéré comme une case sans valeur
	 *
	 */
    public double eval() throws CaseErreurException {
    	try {
			return gauche.valeur() * droite.valeur() ;
		} catch (CaseErreurException e) {
			throw new CaseErreurException() ; 
			
		} 
    }


	/**
	 * Renvoie le contenu simple d'une formule
	 *
	 * @return string au format : ("nom case gauche" * "nom case droite")
	 */
	public String toStringSimple() {return "(" + gauche.getNom() + " * " + droite.getNom() + ")";    }

	/**
	 *  Renvoie le contenu d'une formule
	 *
	 * @return string au format : ("nom case gauche" * "nom case droite") = valeur \ ???
	 */
    public String toString() {


		String str = "(" + gauche.getNom() +  " * " + droite.getNom() + ")" ;

		try {
			str += " = " + eval();
		} catch (CaseErreurException  e) {
			str += " = ????";
		}

		return str;
    }

	/**
	 *
	 * @param gauche : élément gauche de l'opération
	 * @param droite : élément droit de l'opération
	 */
    public Multiplication(Case gauche, Case droite) {
    	this.gauche = gauche ; 
    	this.droite = droite ; 
    }



	/**
	 *  Renvoie le contenu développé d'une formule
	 *
	 * @return string au format : (" contenu développé simple de la case gauche" * "contenu développé simple de la  case droite") = valeur \ ???
	 */
    public String toStringDevSimple() {
    	
    	String str = "(" ;
    	
    	if(gauche.getFormule() == null){
    		str += gauche.getNom() ; 
    	}else{
    		str +=  gauche.getFormule().toStringDevSimple()  ;
    	}
    	
    	str += " * " ; 
    	
    	if(droite.getFormule() == null){
    		str += droite.getNom() ; 
    	}else{
    		str +=  droite.getFormule().toStringDevSimple();
    	}

		return str + ")" ;
    }

	/**
	 *  Renvoie le contenu développé simple d'une formule
	 *
	 * @return string au format : (" contenu développé simple de la case gauche" * "contenu développé simple de la  case droite")
	 */
	public  String toStringDev() {
		String str = toStringDevSimple();

		try {
			str += " = " + eval();
		} catch (CaseErreurException e) {
			str += " = ????";
		}
		return str ;

	}
}
