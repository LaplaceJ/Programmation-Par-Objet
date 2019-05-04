package test;

import noyau.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testMoyenne {



    private ArrayList<Case> casesMoyEE;
    private ArrayList<Case> casesMoy;
    private ArrayList<Case> casesMoyVide;

    private Moyenne moy;

    @Before
    public void before() throws  CycleException {

        casesMoyVide = new ArrayList<>();
        casesMoy = new ArrayList<>();
        casesMoyEE = new ArrayList<>() ;
        ArrayList<Case> casesSomme = new ArrayList<>();


        Case a1 = new Case("A1", 100.0);
        Case a2 = new Case("A2", 50.0);
        Case a3 = new Case("A3", new Addition(a2, a2));

        casesSomme.add(a2);
        casesSomme.add(a2);


        Case a4 = new Case("A4", new Somme(casesSomme));
        Case b2 = new Case("B2", 12.0);
        Case b4 = new Case("B4", 0.0);
        Case b6 = new Case("B6", new Division(b2, b4));

        casesMoy.add(a1);
        casesMoy.add(a2);
        casesMoy.add(a3);
        casesMoy.add(a4);




        casesMoyEE.add(b6);
        casesMoyEE.add(b2);


    }

    @Test
    public void testEval() throws noyau. CaseErreurException,  ListeVideException {

        moy = new Moyenne(casesMoy);
        assertEquals(87.5, moy.eval(),0.001);
    }


    @Test(expected = CaseErreurException.class)
    public void testCaseErreurException() throws ListeVideException, CaseErreurException {
        moy = new Moyenne(casesMoyEE);
        moy.eval() ;
    }

    @Test(expected = ListeVideException.class)
    public void testCaseListeVideException() throws ListeVideException, CaseErreurException {

        moy = new Moyenne(casesMoyVide);
        moy.eval() ;
    }

    @Test
    public void testContenue()  {
        moy = new Moyenne(casesMoy);
        assertEquals("MEAN(A1, A2, A3, A4)", moy.toStringSimple());
    }


    @Test
    public void testContenueDev()  {
        moy = new Moyenne(casesMoy);
        assertEquals("MEAN(A1, A2, (A2 + A2), SUM(A2, A2))", moy.toStringDevSimple());
    }

}