package noyau;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Case implements Serializable {

    // Nom de la case
    private String nom;

    // Valeur contenue dans la case
    private double valeur;

    // Formule contenue dans la case
    private Formule formule;

    // Prédécesseur de la case
    private List<Case> predecesseur;

    // Successeur de la case
    private List<Case> successeur;

    // Indique si une case est utilisable ou pas
    private boolean isCaseErreur;

    /**
     * @param nom : nom de la case
     * @param d   : valeur à donner à la case
     * @Constructeur avec un double, permet de créer une case avec comme un double donné
     */
    public Case(String nom, double d) {

        this.nom = nom;
        this.predecesseur = new ArrayList<>();
        this.successeur = new ArrayList<>();
        this.isCaseErreur = false;
        this.formule = null;

        fixerValeur(d);

    }

    /**
     * @param nom : nom de la case
     * @param f   : formule à donner à la case
     * @throws CycleException : dans le cas ou f créée un cycle
     * @Constructeur avec un double, permet de créer une case avec comme une formule donné
     */
    public Case(String nom, Formule f)
            throws CycleException {

        this.nom = nom;
        this.predecesseur = new ArrayList<>();
        this.successeur = new ArrayList<>();
        this.isCaseErreur = false;

        setFormule(f);

    }

    /**
     * @return valeur de la case
     * @throws CaseErreurException : si la valeur de la case ne peut pas être évaluée, nous propageons cette exception
     * @fonction : Renvoie la valeur de la case
     */
    public double valeur() throws CaseErreurException {

        if (isCaseErreur)
            throw new CaseErreurException();
        return valeur;

    }

    /**
     * change la valeur de la case par un double donné
     *
     * @param d : valeur a fixer
     */
    public void fixerValeur(double d) {

        isCaseErreur = false;
        valeur = d;
        formule = null;
        delPredecesseur();
        predecesseur.clear();
        actualiserValeur();

    }

    /**
     * Supprime l'objet courant de la liste des successeurs de ces prédécesseurs.
     */
    private void delPredecesseur() {

        if (formule != null) {
            List<Case> cs = formule.getCasesFormule();
            int i;
            // Boucle 1 : dans nos successeurs
            for (Case c : cs) {
                i = 0;

                //  Boucle 1 : dans la liste des prédécesseurs de nos successeurs
                for (Case cc : c.getSuccesseur()) {
                    if (cc == this) {

                        //suppression
                        c.getSuccesseur().remove(i);
                        break;
                    }
                    i++;
                }

            }
        }

    }

    /**
     * @param f : formule à attribuer
     * @throws CycleException : propagation si rechercheCycle détecte un cycle
     * @fonction : Permet d'attribuer une formule a une case.
     */
    public void setFormule(Formule f)
            throws CycleException {

        // S'il y a un cycle, alors il y propagation d'un CycleException
        rechercheCycle(f);

        // Suppression l'objet courant de la liste des successeurs de nos prédécesseurs.
        delPredecesseur();


        // Nouvelle formule
        formule = f;
        isCaseErreur = false;

        // Nous vidons la liste des prédécesseurs
        predecesseur.clear();

        // ajout des successeurs et prédécesseurs dans les listes correspondantes
        addPreSucc();

        // Actualisation des valeurs
        actualiserValeur();


    }

    /**
     * @fonction : initialise la liste des predecesseurs et ajoute
     * l'objet courrant dans la liste des successeurs du prédécesseur
     */
    private void addPreSucc() {

        // Nous avons deja changer la formule

        List<Case> cs = formule.getCasesFormule();

        for (Case c : cs) {
            // s'ajoute dans la liste des successeurs du prédécesseur
            c.getSuccesseur().add(this);

            // ajout c dans la liste des prédécesseurs
            predecesseur.add(c);
        }
    }

    /**
     * @param f : nouvelle formule à tester
     * @throws CycleException : si la formule créée un cycle, nous propageons un CycleException
     * @fonction : détecte si la formule f créée un cycle
     */
    private void rechercheCycle(Formule f) throws CycleException {

        List<Case> cs;

        cs = f.getCasesFormule();

        for (Case c : cs) {
            // cycle direct :
            if (c == this) throw new CycleException();

            // cycleIndirect
            c.rechercheCycleIndirect(this);

        }
    }

    /**
     * @param racine : case que nous utilisons pour détecter un cycle
     * @throws CycleException :  si la case est retrouvée dans la liste des predecesseurs, nous propageons un CycleException
     * @fonction : détecte les cycles indirects. Si dans une des récursions racine est un
     * des prédécésseurs de l'objet courant. Alors ceci implique que nous bouclons et que donc
     * qu'il y a un cycle indirect.
     */
    private void rechercheCycleIndirect(Case racine) throws CycleException {

        for (Case c : predecesseur) {

            if (predecesseur.contains(racine))
                throw new CycleException();

            c.rechercheCycleIndirect(racine);

        }

    }

    /**
     * @fonction : permet de recalculer les cases en lors d'un changement de valeur de la case courante
     */
    private void actualiserValeur() {

        // Condition pour savoir si je doit m'actualiser maintenant
        // par rapport a tmpActu


        if (formule != null) {
            try {
                // actualisations de la valeur
                valeur = formule.eval();
                isCaseErreur = false;
            }
            // Si il y a une erreur nous désactivons la case
            catch (ArithmeticException | CaseErreurException | ListeVideException e) {
                isCaseErreur = true;
            }
        }

        // actualisations des successeurs
        for (Case c : successeur) c.actualiserValeur();


    }

    /**
     *
     * @return chaîne de caractères correspondant au contenu
     */
    String contenu() {
        if (formule == null) {
            return Double.toString(valeur);
        } else {
            return formule.toString();
        }
    }

    String contenuS() {
        if (formule == null) {
            return Double.toString(valeur);
        } else {
            return formule.toStringSimple();
        }
    }

    /**
     *
     * @return chaîne de caractères correspondant au contenu développe
     */
    String contenu_developpe() {
        if (formule == null) {
            return Double.toString(valeur);
        } else {
            return formule.toStringDev();
        }

    }

    /**
     * @return chaîne de caractères correspondant au contenu développe simple
     */
     String contenuDeveloppeSimple() {
        if (formule == null) {
            return Double.toString(valeur);
        } else {
            return formule.toStringDevSimple();
        }

    }

    Formule getFormule() {
        return formule;
    }

    private List<Case> getSuccesseur() {
        return successeur;
    }

    String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return getNom();
    }

}
