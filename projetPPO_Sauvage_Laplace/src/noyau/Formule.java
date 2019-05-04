package noyau;

import java.io.Serializable;
import java.util.List;

// import com.modeliosoft.modelio.javadesigner.annotations.objid;


public abstract class Formule implements Serializable {

    /**
     * @return résultat de la formule
     *
     * @throws ArithmeticException : propagé par une division par 0
     * @throws ListeVideException : propagé par une liste vide dans une fonction
     * @throws CaseErreurException si l'une des cases n'a pas de valeur, alors
     *    nous propageons une CaseErreurException. Ainsi la case qui va contenir cette formule
     *    sera elle aussi considéré comme une case sans valeur
     */
    public abstract double eval() throws ArithmeticException, ListeVideException, CaseErreurException;


    public abstract String toString();

    public abstract String toStringSimple();

    public abstract String toStringDev();

    public abstract String toStringDevSimple();

    public abstract List<Case> getCasesFormule();

}
