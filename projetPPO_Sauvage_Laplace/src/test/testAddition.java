package test;

import noyau.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testAddition {

    private Case a1;
    private Case a2;
    private Case b2;
    private Case b4;

    private Addition add;

    @Before
    public void before() throws CycleException {

        a1 = new Case("A1", 100);
        a2 = new Case("A2", 50);
        b2 = new Case("B2", 12);
        Case b3 = new Case("B3", 0);


        b4 = new Case("B4", new Division(a2, b3));
    }

    @Test
    public void testEval() throws noyau.CaseErreurException {

        add = new Addition(a1, a2);
        assertEquals(150, add.eval(), 0.0);
    }

    @Test(expected = CaseErreurException.class)
    public void testCaseErreurException() throws CaseErreurException {

        add = new Addition(a1, b4);
        add.eval();
    }

    @Test
    public void testContenue() {

        add = new Addition(a1, a2);

        assertEquals("(A1 + A2)", add.toStringSimple());
    }


    @Test
    public void testContenueDev() throws CycleException {

        add = new Addition(a1, a2);
        assertEquals("(A1 + A2)", add.toStringDevSimple());

        a1.setFormule(new Addition(b2, b2));
        a2.setFormule(new Addition(b2, b2));
        add = new Addition(a1, a2);
        assertEquals("((B2 + B2) + (B2 + B2))", add.toStringDevSimple());
    }

}

