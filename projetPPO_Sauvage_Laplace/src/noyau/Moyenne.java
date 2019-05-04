package noyau;

import java.io.Serializable;
import java.util.List;

public class Moyenne extends Fonctions implements Serializable {

    /**
     * @param cases_ope : liste de cases utilisées pour calculer la moyenne
     */
    public Moyenne(List<Case> cases_ope) {
        this.cases_ope = cases_ope;
    }


    /**
     *
     * @return moyenne des valeurs des cases de la liste case_ope
     *
     * @throws CaseErreurException si l'une des cases n'a pas de valeur, alors
     *    nous propageons une CaseErreurException. Ainsi la case qui va contenir cette formule
     *    sera elle aussi considéré comme une case sans valeur
     *
     * @throws ListeVideException si la liste cases_ope est vide, alors nous propageons cette exception
     */
    public double eval() throws ListeVideException, CaseErreurException {
        double res = 0;

        if (cases_ope.size() == 0)
            throw new ListeVideException();

        for (Case c : cases_ope) {
            try {
                res += c.valeur();
            } catch (CaseErreurException e) {
                throw new CaseErreurException();
            }
        }
        return res / cases_ope.size();
    }

    /**
     *  Renvoie le contenu simple d'une formule
     *
     * @return string au format : MOYENNE\MEAN(" nom des cases de la liste case_ope ")
     */
    public String toStringSimple() {



        String str = "";

        // Le nom de la fonction change en fonction de la langue de la Grille
        if (Grille.langue.equals("Francais")) {
            str += "MOYENNE(";
        } else {
            str += "MEAN(";
        }

        if (cases_ope.size() == 0)
            return str + ")" ;

        for (int i = 0; i < cases_ope.size() - 1; i++) {
            str += cases_ope.get(i).getNom() + ", ";
        }
        str += cases_ope.get(cases_ope.size() - 1).getNom();

        return str + ")";
    }

    /**
     *  Renvoie le contenu d'une formule
     *
     * @return string au format : MOYENNE\MEAN(" nom des cases de la liste case_ope ") = valeur \ ???
     */
    public String toString() {

        String str = toStringSimple();

        try {
            str += " = " + eval();
        } catch (CaseErreurException | ListeVideException e) {
            str += " = ????";
        }

        return str;
    }

    /**
     *  Renvoie le contenu dévelopé simple d'une formule
     *
     * @return string au format : MOYENNE\MEAN(" contenu développé  des cases de la liste case_ope ")
     */
    public String toStringDevSimple() {

        String str;
        // TODO gestion des langues et des listes vides
        str = "";
        if (Grille.langue.equals("Francais")) {
            str += "MOYENNE(";
        } else {
            str += "MEAN(";
        }

        if (cases_ope.size() == 0)
            return str + ")" ;

        for (int i = 0; i < cases_ope.size() - 1; i++) {

            if (cases_ope.get(i).getFormule() != null) {
                // Si la case utilise une formule, nous opérons la récursion

                str += cases_ope.get(i).getFormule().toStringDevSimple() + ", ";
            } else {
                // Sinon nous afficherons l'identifiant de la case
                str += cases_ope.get(i).getNom() + ", ";
            }
        }

        // Permets de ne pas finir avec une virgule

        if (cases_ope.get(cases_ope.size() - 1).getFormule() != null) {
            str += cases_ope.get(cases_ope.size() - 1).getFormule().toStringDevSimple();
        } else {
            str += cases_ope.get(cases_ope.size() - 1).getNom();
        }

        return str + ")";

    }

    /**
     *  Renvoie le contenu dévelopé d'une formule
     *
     * @return string au format : MOYENNE\MEAN(" contenu développé  des cases de la liste case_ope ") = valeur \ ???
     */
    public  String toStringDev() {
        String str = toStringDevSimple();

        try {
            str += " = " + eval();
        } catch (CaseErreurException | ListeVideException e) {
            str += " = ????";
        }

        return  str ;
    }
}
