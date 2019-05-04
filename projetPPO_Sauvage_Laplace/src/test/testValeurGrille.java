package test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import noyau.Addition;
import noyau.Case;
import noyau.CaseErreurException;
import noyau.CycleException;
import noyau.Division;
import noyau.Moyenne;
import noyau.Somme;
import noyau.Soustraction;

public class testValeurGrille {

    private Case a1;
    private Case a2;
    private Case a3;
    private Case a4;
    private Case a6;
    private Case b2;
    private Case b4;
    private Case b6;
    private Case c2;
    private Case c4;
    private Case c6;
    private Case d6;
    private Case e4;
    private List<Case> listeCaseb6;
    private List<Case> listeCasec6;
    private List<Case> listeCased6;

    @Before
    public void before() {
        a1 = new Case("A1", 0);
        a2 = new Case("A2", 0);
        a3 = new Case("A3", 0);
        a4 = new Case("A4", 0);
        a6 = new Case("A6", 0);
        b2 = new Case("B2", 0);
        b4 = new Case("B4", 0);
        b6 = new Case("B6", 0);
        c2 = new Case("C2", 0);
        c4 = new Case("C4", 0);
        c6 = new Case("C6", 0);
        d6 = new Case("D6", 0);
        e4 = new Case("E4", 0);
        listeCaseb6 = new ArrayList<>();
        listeCasec6 = new ArrayList<>();
        listeCased6 = new ArrayList<>();
    }

    @Test
    public void exempleProjet()
            throws CycleException, CaseErreurException {
        try {


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

            assertEquals(100.0, a1.valeur(), 0.0);
            assertEquals(50.0, a2.valeur(), 0.0);
            assertEquals(0.5, a3.valeur(), 0.0);
            assertEquals(0.0, a4.valeur(), 0.0);
            assertEquals(40.833, a6.valeur(), 0.001);
            assertEquals(12.0, b2.valeur(), 0.0);
            assertEquals(150.0, b4.valeur(), 0.0);
            assertEquals(62.5, b6.valeur(), 0.0);
            assertEquals(30.0, c2.valeur(), 0.0);
            assertEquals(200.0, c4.valeur(), 0.0);
            assertEquals(70.833, c6.valeur(), 0.001);
        } catch (ArithmeticException ignored) {
            assert false ;
        }
    }
}
