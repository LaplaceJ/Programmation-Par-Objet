package noyau;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grille implements Serializable {


    // Map qui va contenir les cases du tableurs
    private Map<String, Case> cases;


    static String langue = "Anglais"; // ou Francais

    // taille en abscisse de la grille
    private int abscisse;

    // taille en ordonne de la grille
    private int ordonne;


    public Grille(int abscisse, int ordonne, boolean fr) {
        this.abscisse = abscisse;
        this.ordonne = ordonne;

        if (fr) langue = "Francais";
        else langue = "Anglais";

        cases = new HashMap<>();

    }

    /**
     * Sauvegarde la grille dans le fichier path
     *
     * @param path : nom du fichier où va être sérialisée la grille
     * @throws IOException
     */
    public void sauvegarderGrille(String path) throws IOException {
        File file = new File(path);
        ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
        out.writeObject(this);
        out.close();
    }

    /**
     * Renvoie une grille chargé à partir du fichier path
     *
     * @param path : nom du fichier ou  charger la grille
     * @return grille contenue dans le fichier path
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static Grille chargerGrille(String path) throws IOException, ClassNotFoundException {
        File file = new File(path);
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
        Grille tmp = (Grille) in.readObject();
        in.close();
        return tmp;
    }

    /**
     * @param nomCase nom de la case à sélectionner
     * @return chaîne de caractères correspondant au contenu
     * @throws CaseNotFoundException : la case donnée n'existe pas
     */
    String affichageContenu(String nomCase) throws CaseNotFoundException {

        if (cases.get(nomCase) == null)
            throw new CaseNotFoundException();
        return cases.get(nomCase).contenu();

    }

    /**
     * @param nomCase nom de la case à sélectionner
     * @return chaîne de caractères correspondant au contenu simple
     * @throws CaseNotFoundException : la case donnée n'existe pas
     */
    String affichageContenuS(String nomCase) throws CaseNotFoundException {

        if (cases.get(nomCase) == null)
            throw new CaseNotFoundException();
        return cases.get(nomCase).contenuS();

    }

    /**
     * @param nomCase nom de la case à sélectionner
     * @return chaîne de caractères correspondant à la valeur de la case
     * @throws CaseNotFoundException : la case donnée n'existe pas
     */
    String affichageValeur(String nomCase) throws CaseNotFoundException {

        if (cases.get(nomCase) == null)
            throw new CaseNotFoundException();
        try {
            return Double.toString(cases.get(nomCase).valeur());
        } catch (CaseErreurException e) {
            return "Erreur case";
        }

    }


    /**
     * @param nomCase nom de la case à sélectionner
     * @return chaîne de caractères correspondant au contenu développe
     * @throws CaseNotFoundException
     */
    String affichageContenuD(String nomCase) throws CaseNotFoundException {

        if (cases.get(nomCase) == null)
            throw new CaseNotFoundException();
        return cases.get(nomCase).contenu_developpe();

    }

    /**
     * @param nomCase nom de la case à sélectionner
     * @return chaîne de caractères correspondant au contenu développe simple
     * @throws CaseNotFoundException
     */
    String affichageContenuDS(String nomCase) throws CaseNotFoundException {

        if (cases.get(nomCase) == null)
            throw new CaseNotFoundException();
        return cases.get(nomCase).contenuDeveloppeSimple();

    }

    /**
     * Cette fonction permet de modifier une case. Si le paramètre nomCase n'existe pas dans la liste "cases"
     * alors nous créons une nouvelle case associée au nom donné en paramètre.
     *
     * @param nomCase : nom de la case à modifier ou à créer
     * @param f : formule que la case va contenir
     */
    public void modifCreaCase(String nomCase, Formule f) {


        if (cases.containsKey(nomCase)) {
            try {
                cases.get(nomCase).setFormule(f);
            } catch (CycleException e) {
                System.err.println("CycleException");
            }
        } else {

            try {
                cases.put(nomCase, new Case(nomCase, f));
            } catch (CycleException e) {
                System.err.println("CycleException");
            }

        }
    }

    /**
     * Cette fonction permet de modifier une case. Si le paramètre nomCase n'existe pas dans la liste "cases"
     * alors nous créons une nouvelle case associée au nom donné en paramètre.
     *
     * @param nomCase : nom de la case à modifier ou à créer
     * @param d : double que la case va contenir
     */
    public void modifCreaCase(String nomCase, double d) {
        if (cases.containsKey(nomCase)) {

            cases.get(nomCase).fixerValeur(d);

        } else {

            cases.put(nomCase, new Case(nomCase, d));

        }
    }


    private Case getCase(String nom) {
        return cases.get(nom);
    }

    int getAbscisse() {
        return abscisse;
    }

    int getOrdonne() {
        return ordonne;
    }

    public Map<String, Case> getCases() {
        return cases;
    }

    public void setCases(Map<String, Case> cases) {
        this.cases = cases;
    }


    /**
     * Permet de générer le fichier grilleSujet.dat
     *
     * @param args ignored
     */
    public static void main(String[] args) {

        Grille fr = new Grille(10, 10, true);
        try {
            fr.modifCreaCase("A1", 100);
            fr.modifCreaCase("A2", 50);
            fr.modifCreaCase("A3", 0.5);
            fr.modifCreaCase("A4", 0);
            fr.modifCreaCase("B2", 12);
            fr.modifCreaCase("C2", 30);
            fr.modifCreaCase("B4", new Addition(fr.getCase("A1"), fr.getCase("A2")));


            List<Case> listeCaseB6 = new ArrayList<>();
            // Création de la liste de case pour l'évaluation de la somme en B6
            listeCaseB6.add(fr.getCase("A2"));
            listeCaseB6.add(fr.getCase("B2"));
            listeCaseB6.add(fr.getCase("A3"));

            fr.modifCreaCase("B6", new Somme(listeCaseB6));

            List<Case> listeCaseC6 = new ArrayList<>();
            // Création de la liste de case pour l'évaluation de la moyenne en
            // c6
            listeCaseC6.add(fr.getCase("B6"));
            listeCaseC6.add(fr.getCase("B4"));
            listeCaseC6.add(fr.getCase("A4"));


            fr.modifCreaCase("C6", new Moyenne(listeCaseC6));
            fr.modifCreaCase("C4", new Division(fr.getCase("A1"), fr.getCase("A3")));
            fr.modifCreaCase("A6", new Soustraction(fr.getCase("C6"), fr.getCase("C2")));
            fr.modifCreaCase("E4", new Division(fr.getCase("A2"), fr.getCase("A4")));

            List<Case> listeCaseD6 = new ArrayList<>();
            // Création de la liste de case pour l'évaluation de la moyenne en
            // d6
            listeCaseD6.add(fr.getCase("B4"));
            listeCaseD6.add(fr.getCase("E4"));

            fr.modifCreaCase("D6", new Somme(listeCaseD6));


            fr.sauvegarderGrille("./grilleSujet.dat");
        } catch (java.lang.ArithmeticException | IOException ignored) {
        }
    }
}
