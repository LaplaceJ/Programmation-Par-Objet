package noyau;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TablooProto extends JPanel {

    private final static String nf =  "./grilleSujet.dat" ;

    private static final long serialVersionUID = 1L;

    private MyTableModel tableModel ;
    // Fourni: ne rien changer.
    private TablooProto(String nomFichier) {
        super(new GridLayout(1, 0));

        // modele de donnees
        // cf. plus loin la inner classe MyTableModel a modifier...
        tableModel = new MyTableModel(nomFichier);

        // la JTable et ses parametres
        JTable table = new JTable(tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(1000, 500));
        table.setGridColor(Color.BLACK);
        table.setShowGrid(true);

        // on ajoute un scroll
        JScrollPane scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        // parametrage de la 1ere ligne = noms des colonnes
        ((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);

        // parametrage de la 1ere colonne consacree a la numerotation des lignes
        TableColumn tm = table.getColumnModel().getColumn(0);
        tm.setPreferredWidth(tm.getPreferredWidth() * 2 / 3);
        tm.setCellRenderer(new PremiereColonneSpecificRenderer(Color.LIGHT_GRAY));

    }

    // Inner class pour changer l'aspect de la premiere colonne consacree a la numerotation des lignes
    // Fourni: ne rien changer.
    class PremiereColonneSpecificRenderer extends DefaultTableCellRenderer {

        private static final long serialVersionUID = 1L;
        Color couleur;

        public PremiereColonneSpecificRenderer(Color couleur) {
            super();
            this.couleur = couleur;
            this.setHorizontalAlignment(JLabel.CENTER);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setBackground(couleur);
            return cell;
        }
    }

    private MyTableModel getTableModel() {
        return tableModel;
    }


    /**
     * Exécution de l'interface graphique a partir d'un terminal.
     * @param args : nom du fichier de départ de la grille. Si il n'y a aucun paramètre alors nous utilisons le fichier nf
     */
    public static void main(String[] args) {


        TablooProto tableur ;

        if (args.length == 0) tableur = new TablooProto(nf);
        else tableur = new TablooProto(args[0]) ;



        // Creation de l'application et lancement
        // Fourni: ne rien changer.
        JFrame frame = new JFrame("TABLO");
        JPanel panel1= new JPanel();

////////////////////////////////////

        JMenuBar menu_bar1 = new JMenuBar();
        /* différents menus */
        JMenu menu1 = new JMenu("Langue");
        JMenu menu2 = new JMenu("Affichage");
        /* differents choix de chaque menu */
        JMenuItem anglais = new JMenuItem("Anglais");
        JMenuItem francais = new JMenuItem("Français");


        JMenuItem valeur = new JMenuItem("Valeur");
        JMenuItem contenu = new JMenuItem("Contenu");
        JMenuItem contenuS = new JMenuItem("Contenu Simple");
        JMenuItem contenuD = new JMenuItem("Contenu Dévelopé");
        JMenuItem contenuDS = new JMenuItem("Contenu Dévelopé Simple");



        frame.getContentPane().add(panel1,"South");
        /* Ajouter les choix au menu  */
        menu1.add(anglais);
        menu1.add(francais);
        menu2.add(valeur);
        menu2.add(contenu);
        menu2.add(contenuS);
        menu2.add(contenuD);
        menu2.add(contenuDS);
        /* Ajouter les menu sur la bar de menu */
        menu_bar1.add(menu1);
        menu_bar1.add(menu2);
        /* Ajouter la bar du menu à la frame */
        frame.setJMenuBar(menu_bar1);



        /* clic sur le choix Démarrer du menu fichier */
        francais.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Grille.langue = "Francais" ;
                tableur.getTableModel().refreshTable();
            }
        });

        /* clic sur le choix Fin du menu fichier */
        anglais.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                Grille.langue = "Anglais" ;
                tableur.getTableModel().refreshTable();
            }
        });

        /* clic sur le choix valeur du menu Affichage */
        valeur.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                tableur.getTableModel().setEtat(MyTableModel.EtatAffichage.VALEUR)   ;
                tableur.getTableModel().refreshTable();
            }
        });

        /* clic sur le choix contenu du menu Affichage */
        contenu.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                tableur.getTableModel().setEtat(MyTableModel.EtatAffichage.CONTENU)   ;
                tableur.getTableModel().refreshTable();
            }
        });

        /* clic sur le choix contenu simple du menu Affichage */
        contenuS.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                tableur.getTableModel().setEtat(MyTableModel.EtatAffichage.CONTENU_SIMPLE)   ;
                tableur.getTableModel().refreshTable();
            }
        });

        /* clic sur le choix contenuD simple du menu Affichage */
        contenuD.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                tableur.getTableModel().setEtat(MyTableModel.EtatAffichage.CONTENU_DEV)   ;
                tableur.getTableModel().refreshTable();
            }
        });

        /* clic sur le choix contenuD simple du menu Affichage */
        contenuDS.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                tableur.getTableModel().setEtat(MyTableModel.EtatAffichage.CONTENU_DEV_SIMPLE)   ;
                tableur.getTableModel().refreshTable();
            }
        });


        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tableur.setOpaque(true);
        frame.setContentPane(tableur);
        frame.pack();
        frame.setVisible(true);

    }
}
