package test;

import noyau.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class testSomme {

    private ArrayList<Case> casesSommeEE;
    private ArrayList<Case> casesSomme;
    private ArrayList<Case> casesSommeVide;

    private Somme somme;

    @Before
    public void before() throws  CycleException {

        casesSommeVide = new ArrayList<>();
        casesSomme = new ArrayList<>();
        casesSommeEE = new ArrayList<>() ;

        ArrayList<Case> moy = new ArrayList<>() ;

        Case a1 = new Case("A1", 100.0);
        Case a2 = new Case("A2", 50.0);
        Case a3 = new Case("A3", new Addition(a2, a2));

        moy.add(a2);
        moy.add(a2);


        Case a4 = new Case("A4", new Moyenne(moy));
        Case b2 = new Case("B2", 12.0);
        Case b4 = new Case("B4", 0.0);
        Case b6 = new Case("B6", new Division(b2, b4));

        casesSomme.add(a1);
        casesSomme.add(a2);
        casesSomme.add(a3);
        casesSomme.add(a4);



        casesSommeEE.add(b6);
        casesSommeEE.add(b2);


    }
    @Test
    public void testEval() throws noyau. CaseErreurException,  ListeVideException {

        somme = new Somme(casesSomme);
        assertEquals(300, somme.eval(),0.0);
    }

    @Test(expected = CaseErreurException.class)
    public void testCaseErreurException() throws  CaseErreurException, ListeVideException {

        somme = new Somme(casesSommeEE);
        somme.eval() ;
    }

    @Test(expected = ListeVideException.class)
    public void testCaseListeVideException() throws ListeVideException, CaseErreurException {

        somme = new Somme(casesSommeVide);
        somme.eval() ;
    }

    @Test
    public void testContenue()  {
        somme = new Somme(casesSomme);
        assertEquals("SOMME(A1, A2, A3, A4)", somme.toStringSimple());
    }


    @Test
    public void testContenueDev()  {
        somme = new Somme(casesSomme);
        assertEquals("SOMME(A1, A2, (A2 + A2), MOYENNE(A2, A2))", somme.toStringDevSimple());

    }

}
