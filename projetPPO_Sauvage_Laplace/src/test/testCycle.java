package test;


import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import noyau.Addition;
import noyau.Case;
import noyau.CycleException;
import noyau.Division;
import noyau.Moyenne;
import noyau.Somme;
import noyau.Soustraction;

public class testCycle {

    private Case a1;
    private Case b2;
    private Case c6;

    @Before
    public void before() throws CycleException {
        a1 = new Case("A1", 0);
        Case a2 = new Case("A2", 0);
        Case a3 = new Case("A3", 0);
        Case a4 = new Case("A4", 0);
        Case a6 = new Case("A6", 0);
        b2 = new Case("B2", 0);
        Case b4 = new Case("B4", 0);
        Case b6 = new Case("B6", 0);
        Case c2 = new Case("C2", 0);
        Case c4 = new Case("C4", 0);
        c6 = new Case("C6", 0);
        Case d6 = new Case("D6", 0);
        Case e4 = new Case("E4", 0);
        List<Case> listeCaseb6 = new ArrayList<>();
        List<Case> listeCasec6 = new ArrayList<>();
        List<Case> listeCased6 = new ArrayList<>();

        a1.fixerValeur(100.0);
        a2.fixerValeur(50.0);
        a3.fixerValeur(0.5);
        a4.fixerValeur(0.0);
        b2.fixerValeur(12.0);
        c2.fixerValeur(30.0);
        b4.setFormule(new Addition(a1, a2));

        // Création de la liste de case pour l'évaluation de la somme en B6
        listeCaseb6.add(a2);
        listeCaseb6.add(b2);
        listeCaseb6.add(a3);

        b6.setFormule(new Somme(listeCaseb6));

        // Création de la liste de case pour l'évaluation de la moyenne en
        // c6
        listeCasec6.add(b6);
        listeCasec6.add(b4);
        listeCasec6.add(a4);

        c6.setFormule(new Moyenne(listeCasec6));

        c4.setFormule(new Division(a1, a3));

        a6.setFormule(new Soustraction(c6, c2));

        // Création de la liste de case pour l'évaluation de la moyenne en
        // d6
        listeCased6.add(b4);
        listeCased6.add(e4);

        d6.setFormule(new Somme(listeCased6));
    }

    @Test(expected = CycleException.class)
    public void creationCycleDirect()
            throws CycleException {
        a1.setFormule(new Addition(a1, b2));
    }

    @Test(expected = CycleException.class)
    public void creationCycleIndirect()
            throws CycleException {
        b2.setFormule(new Addition(a1, c6));
    }
}

