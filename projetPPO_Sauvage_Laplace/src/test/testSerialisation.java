package test;

import noyau.*;
import org.junit.Before;
import org.junit.Test;
import java.io.IOException;

import static org.junit.Assert.assertEquals;

public class testSerialisation {

    private Grille fr;

    @Before
    public void before() {
        fr = new Grille(100, 100, true);
    }

    @Test
    public void testSauvegarderCharger()
            throws IOException, ClassNotFoundException, CaseErreurException {
        fr.modifCreaCase("A1", 10);
        fr.modifCreaCase("A2", 10);
        fr.modifCreaCase("A3", new Multiplication(fr.getCases().get("A1"), fr.getCases().get("A2")));

        fr.sauvegarderGrille("../../grille.dat");

        fr.modifCreaCase("A3", 1);

        Grille tmp = Grille.chargerGrille("../../grille.dat");

        assertEquals(100, tmp.getCases().get("A3").valeur(), 0);

    }
}
