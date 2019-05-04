package test;

import noyau.*;
import noyau.ArithmeticException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class testDivision {

	private Case a1;
	private Case a3;
	private Case b2;
	private Case b4;

	private Division div;
	private Division divPar0;

	@Before
	public void before() throws CycleException {

		a1 = new Case("A1" , 100.0);
		a3 = new Case("A3" , 0.5);
		b2 = new Case("B2",12.0);
		Case b3 = new Case("B3", 0);

		divPar0 = new Division(a3 , b3) ;
		b4 = new Case("B4", divPar0) ;

	}

	@Test
	public void testEval() throws noyau. CaseErreurException, ArithmeticException {

		div = new Division(a1, a3);
		assertEquals(200.0, div.eval(), 0.0);
	}

	@Test(expected = ArithmeticException.class)
	public void testArithmeticException() throws  CaseErreurException, ArithmeticException {

		divPar0.eval();
	}

	@Test(expected = CaseErreurException.class)
	public void testCaseErreurException() throws  CaseErreurException, ArithmeticException {

		div = new Division(a1,b4);
		div.eval();
	}

	@Test
	public void testContenue()  {

		div = new Division(a1, a3);
		assertEquals("(A1 / A3)", div.toStringSimple());
	}


	@Test
	public void testContenueDev() throws CycleException {

		div = new Division(a1, a3);
		assertEquals("(A1 / A3)", div.toStringDevSimple());

		a1.setFormule(new Addition(b2,b2));
		a3.setFormule(new Addition(b2,b2));
		div = new Division(a1, a3);
		assertEquals("((B2 + B2) / (B2 + B2))", div.toStringDevSimple());
	}

}
