package noyau;

import javax.swing.table.AbstractTableModel;
import java.io.IOException;

// Inner class pour etablir la connexion entre la JTable graphique et un modele de donnees.
// Pour nous le modele de donnees sera une grille du noyau de representation et de calcul
// construite et sauvegardee par serialisation comme precedemmment.
// Dans ce prototype exemple, le modele de donnees est une simple matrice de String "en dur".
// Il faudra le remplacer par une connexion a une telle grille.
class MyTableModel extends AbstractTableModel {

    /**
     * Enum permettant de choisir le type d'affichage
     */
    public enum EtatAffichage {
        CONTENU, CONTENU_SIMPLE, CONTENU_DEV_SIMPLE, CONTENU_DEV,
        VALEUR
    }

    // variable permettant de garder en mémoire le type d'affichage choisi par l'utilisateur
    private EtatAffichage etat ;


    private static final long serialVersionUID = 1L;

    private Grille g;

    MyTableModel(String nomFichier) {

        try {
            g = Grille.chargerGrille(nomFichier);
            etat = EtatAffichage.CONTENU ;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Le fichier ne correspond pas : " + e);
        }


    }


    @Override
    // Standard: doit retourner le nbre de colonnes de la JTable
    public int getColumnCount() {

        return g.getOrdonne() + 1;

    }

    @Override
    // Standard: doit retourner le nbre de lignes de la JTable
    public int getRowCount() {

        return g.getAbscisse();

    }

    // Standard: doit renvoyer le nom de la colonne a afficher en tete
    // Fourni: ne rien changer.
    @Override
    public String getColumnName(int col) {
        if (col == 0) {
            return ""; // colonne consacrée aux numeros de ligne
        } else {
            return "" + (char) ((int) ('A') + col - 1);
        }
    }

    private String getRowName(int row) {
        return "" + (row + 1);
    }


    // Utilitaire interne fourni (ne rien changer)
    // Retourne le nom d'une case a partir de ses coordonnees dans la JTable.
    private String getNomCase(int row, int col) {
        return this.getColumnName(col) + String.valueOf(row + 1); // row commence a 0
    }

    @Override
    // Standard: doit renvoyer le contenu a afficher de la case correspondante
    public Object getValueAt(int row, int col) {
        if (col == 0) return getRowName(row);
        try {

            switch (etat) {
                case CONTENU:
                    return g.affichageContenu(getColumnName(col) + Integer.toString(row + 1));
                case CONTENU_DEV:
                    return g.affichageContenuD(getColumnName(col) + Integer.toString(row + 1));
                case CONTENU_DEV_SIMPLE:
                    return g.affichageContenuDS(getColumnName(col) + Integer.toString(row + 1));
                case CONTENU_SIMPLE:
                    return g.affichageContenuS(getColumnName(col) + Integer.toString(row + 1));
                case VALEUR:
                    return g.affichageValeur(getColumnName(col) + Integer.toString(row + 1));
                default:
                    return "";
            }


        } catch (CaseNotFoundException e) {
            return "";
        }


    }


    // Standard.
    // Fourni: ne rien changer.
    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    // Standard: determine si une case est editable ou non.
    // Fourni: ne rien changer.
    // Seules les cases de la 1er colonne ne le sont pas
    // (consacrees a la numerotation des lignes)
    @Override
    public boolean isCellEditable(int row, int col) {
        if (col < 1) {
            return false; // col 0 consacree a la numerotation des lignes (non editable)
        } else {
            return true;
        }
    }


    // Standard: l'utilisateur a entré une valeur dans une case,
    // mettre a jour le modèle de donnees connecte.
    // L'utilisateur a modifie une case.
    // Si c'est une valeur numerique (sinon ne rien faire)
    // - modifier la case correspondante dans la grille si cette case existe
    // - ajouter la case correspondante dans la grille
    @Override
    public void setValueAt(Object value, int row, int col) {
        if ((value instanceof String))
            g.modifCreaCase(getNomCase(row, col), Double.parseDouble((String) value));


        // Ne pas modifier :
        // mise a jour automatique de l'affichage suite a la modification
        fireTableDataChanged();
        //fireTableCellUpdated(row, col);

    }

    void setEtat(EtatAffichage e) {
        etat = e;
    }

    void refreshTable() {
        fireTableDataChanged();
    }
}

