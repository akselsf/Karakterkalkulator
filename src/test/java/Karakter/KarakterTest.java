package Karakter;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Karakter.app.Grade;
import Karakter.app.GradeManager;
import Karakter.ui.GradeContainerCreator;

import javafx.scene.layout.Pane;

public class KarakterTest {
    private GradeManager gm;

    @Test
    public void testConstructor() {
        gm = new GradeManager();

        assertEquals(0, gm.getGrades().size(), "Antall karakterer er ikke 0");
    }

    @Test
    public void testContainerCreator() {
        gm = new GradeManager();
        Pane pane = new GradeContainerCreator(gm, null).getPane();
        assertEquals(0, pane.getChildren().toArray().length, "Ble ikke laget riktig antall pane's for karakterene");

        gm.addGrade("FFF", "F");
        gm.addGrade("DDD", "D");
        gm.addGrade("CCC", "C");
        gm.addGrade("AAA", "A");
        pane = new GradeContainerCreator(gm, null).getPane();

        assertEquals(4, pane.getChildren().toArray().length, "Ble ikke laget riktig antall pane's for karakterene");

    }

    @Test
    void testIterator() {
        gm = new GradeManager();
        gm.addGrade("FFF", "F");
        gm.addGrade("DDD", "D");
        gm.addGrade("CCC", "C");
        gm.addGrade("AAA", "A");
        gm.addGrade("AAA2", "A");

        int count = 0;
        for (Grade g : gm) {
            if (g.getTallKarakter() <= 5 && g.getTallKarakter() >= 0) {
                count++;
            }
        }
        assertEquals(5, count, "Iterator gikk ikke gjennom alle karakterer");

        gm.deleteGrade("FFF");
        gm.deleteGrade("AAA");

        count = 0;
        for (Grade g : gm) {
            if (g.getTallKarakter() <= 5 && g.getTallKarakter() >= 0) {
                count++;
            }
        }
        assertEquals(3, count, "Iterator fungerte ikke etter karakterer var slettet");

    }

    @Test
    void testCalculate() {
        gm = new GradeManager();
        gm.addGrade("FFF", "F");
        gm.addGrade("DDD", "D");
        gm.addGrade("CCC", "C");
        gm.addGrade("AAA", "A");
        gm.addGrade("AAA2", "A");

        assertEquals("3.0", gm.getResults().get(1), "Gjennomsnittet ble ikke riktig");

        // Median ved oddetall
        assertEquals("3.0", gm.getResults().get(0), "Medianen ble ikke riktig");
        // Median ved partall
        gm.addGrade("BBB2", "B");
        assertEquals("3.5", gm.getResults().get(0), "Medianen ble ikke riktig");

    }

    @Test
    void testSortering() {

        gm = new GradeManager();
        gm.addGrade("DDD", "D");
        gm.addGrade("FFF", "F");
        gm.addGrade("CCC", "C");
        gm.addGrade("AAA", "A");

        // sorter stigende
        gm.reorderGrades("g-");
        String[] grades = { "AAA", "CCC", "DDD", "FFF" };
        for (int i = 0; i < grades.length; i++) {
            assertEquals(grades[i], gm.getGrades().get(i).getEmnekode(), "Sorteringen ble ikke riktig");
        }
        // sorter alfabetisk
        gm.addGrade("AABABAB", "F");
        gm.reorderGrades("a+");
        String[] grades2 = { "AAA", "AABABAB", "CCC", "DDD", "FFF" };
        for (int i = 0; i < grades2.length; i++) {
            assertEquals(grades2[i], gm.getGrades().get(i).getEmnekode(), "Sorteringen ble ikke riktig");
        }

    }

    @Test
    void testLagring() {
        gm = new GradeManager();
        gm.addGrade("DDD", "D");
        gm.addGrade("FFF", "F");
        gm.addGrade("CCC", "C");
        gm.addGrade("AAA", "A");
        gm.reorderGrades("a+");
        System.out.println(gm.getGrades());
        gm.saveToFile();

        gm = new GradeManager();
        gm.getFromFile();
        System.out.println(gm.getGrades());
        String[] grades3 = { "AAA", "CCC", "DDD", "FFF" };

        for (int i = 0; i < grades3.length; i++) {
            assertEquals(grades3[i], gm.getGrades().get(i).getEmnekode(), "Lagringen ble ikke riktig");
        }

    }

    @Test
    void testValidGrade() {
        gm = new GradeManager();
        gm.addGrade("EOEOEOEO", "F");

        // Ugyldig karakter (skal ikke legges til)
        gm.addGrade("EOEOEOEO", "G");

        assertEquals(1, gm.getGrades().size(), "Karakter med 'G' som karakter ble lagt til (feil)");
        // Ugyldig emnekode (skal ikke legges til)
        gm.addGrade("5", "G");
        assertEquals(1, gm.getGrades().size(), "Emne med 'G' som navn ble lagt til (må ha minst 3 bokstaver)");

        // Minstekrav for emnekode-lenge (skal legges til)
        gm.addGrade("ABC", "F");
        assertEquals(2, gm.getGrades().size(),
                "Emne med 'ABC' (tre bokstaver) som navn ble ikke lagt til (skal kunne ha 3 bokstaver)");

        // Makslangde for emnekode (skal legges til)
        gm.addGrade("ABC4567891", "F");
        assertEquals(3, gm.getGrades().size(),
                "Emne med 'ABC' (tre bokstaver) som navn ble ikke lagt til (skal kunne ha 3 bokstaver)");

    }

}
