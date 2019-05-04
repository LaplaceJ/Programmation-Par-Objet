package test;

import noyau.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testSoustraction {

    private Case a1;
    private Case a2;
    private Case b2;
    private Case b4;

    private Soustraction sous;

    @Before
    public void before() throws CycleException {

        a1 = new Case ("A1" , 100);
        a2 = new Case( "A2" , 50);
        b2 = new Case("B2",12);
        Case b3 = new Case("B2", 0);
        b4 =  new Case("B2",new Division(b2 , b3));
    }

    @Test
    public void testEval() throws noyau. CaseErreurException {
        sous = new Soustraction(a1, a2);
        assertEquals(50, sous.eval(), 0.0);
    }

    @Test(expected = CaseErreurException.class)
    public void testCaseErreurException() throws CaseErreurException {
        sous = new Soustraction(a1,b4);
        sous.eval() ;
    }

    @Test
    public void testContenue()  {
        sous = new Soustraction(a1, a2);

        assertEquals("(A1 - A2)", sous.toStringSimple());
    }


    @Test
    public void testContenueDev() throws CycleException {

        sous = new Soustraction(a1, a2);
        assertEquals("(A1 - A2)", sous.toStringDevSimple());

        a1.setFormule(new Addition(b2,b2));
        a2.setFormule(new Addition(b2,b2));
        sous = new Soustraction(a1, a2);

        assertEquals("((B2 + B2) - (B2 + B2))", sous.toStringDevSimple());
    }

}
