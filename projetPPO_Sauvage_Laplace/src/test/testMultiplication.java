package test;

import noyau.*;
import noyau.ArithmeticException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testMultiplication {

    private Case a1;
    private Case a2;
    private Case b2;
    private Case b4;

    private Multiplication mult;

    @Before
    public void before() throws CycleException {

        a1 = new Case("A1" , 100.0);
        a2 = new Case("A2" , 50.0);
        b2 = new Case("B2",0);
        b4 = new Case("B4" , new Division(a1, b2));
    }

    @Test
    public void testEval() throws noyau. CaseErreurException {

        mult = new Multiplication(a1, a2);
        assertEquals(5000, mult.eval(), 0.0);
    }

    @Test(expected = CaseErreurException.class)
    public void testCaseErreurException() throws  CaseErreurException{

        mult = new Multiplication(a1,b4);
        mult.eval() ;
    }

    @Test
    public void testContenue()  {

        mult = new Multiplication(a1, a2);
        assertEquals("(A1 * A2)", mult.toStringSimple());
    }


    @Test
    public void testContenueDev() throws CycleException  {

        mult = new Multiplication(a1, a2);
        assertEquals("(A1 * A2)", mult.toStringDevSimple());

        a1.setFormule(new Addition(b2,b2));
        a2.setFormule(new Addition(b2,b2));
        mult = new Multiplication(a1, a2);
        assertEquals("((B2 + B2) * (B2 + B2))", mult.toStringDevSimple());
    }

}
