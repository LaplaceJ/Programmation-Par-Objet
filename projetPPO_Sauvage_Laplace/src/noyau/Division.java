package noyau;

import java.io.Serializable;

public class Division extends OperationBinaire implements Serializable {

    /**
     *
     * @param gauche : élément gauche de l'opération
     * @param droite : élément droit de l'opération
     */
    public Division(Case gauche, Case droite) {
        this.gauche = gauche;
        this.droite = droite;
    }

    /**
     *
     * @return case gauche / case droite
     *
     * @throws CaseErreurException si l'une des cases n'a pas de valeur, alors
     *    nous propageons une CaseErreurException. Ainsi la case qui va contenir cette formule
     *    sera elle aussi considéré comme une case sans valeur
     *
     * @throws ArithmeticException si l'élément droit est 0 alors nous propageons cette exception
     */

    public double eval() throws ArithmeticException, CaseErreurException {
        double d, g;

        try {
            d = droite.valeur();
            g = gauche.valeur();
        } catch (CaseErreurException e) {
            throw new CaseErreurException();
        }

        if (d == 0.0) throw new ArithmeticException();

        return g / d;
    }

    /**
     *  Renvoie le contenu d'une formule
     *
     * @return string au format : ("nom case gauche" / "nom case droite") = valeur \ ???
     */
    public String toString() {


        String str = "(" + gauche.getNom() + " / " + droite.getNom() + ")";

        try {
            str += " = " + eval();
        } catch (CaseErreurException | ArithmeticException e) {
            str += " = ????";
        }

        return str;
    }

    /**
     * Renvoie le contenu simple d'une formule
     *
     * @return string au format : ("nom case gauche" / "nom case droite")
     */
    public String toStringSimple() {
        return "(" + gauche.getNom() + " / " + droite.getNom() + ")";
    }


    /**
     *  Renvoie le contenu développé simple d'une formule
     *
     * @return string au format : (" contenu développé simple de la case gauche" / "contenu développé simple de la  case droite")
     */
    public String toStringDevSimple() {
        String str = "(";
        if (gauche.getFormule() != null) {
            // Si la case utilise une formule, nous opérons la récursion
            str += gauche.getFormule().toStringDevSimple();

        } else {
            str += gauche.getNom();
        }
        str += " / ";
        if (droite.getFormule() != null) {
            // Si la case utilise une formule, nous opérons la récursion
            str += droite.getFormule().toStringDevSimple();

        } else {
            str += droite.getNom();
        }
        str += ")";
        return str;
    }

    /**
     *  Renvoie le contenu développé d'une formule
     *
     * @return string au format : (" contenu développé simple de la case gauche" / "contenu développé simple de la  case droite") = valeur \ ???
     */
    public String toStringDev() {
        String str = toStringDevSimple();

        try {
            str += " = " + eval();
        } catch (CaseErreurException | ArithmeticException e) {
            str += " = ????";
        }

        return str;
    }
}
