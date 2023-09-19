package Karakter.app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import Karakter.util.AlertCreator;
import Karakter.util.FileManager;

public class GradeManager implements Iterable<Grade> {
    private ArrayList<Grade> grades;
    private FileManager fm;
    private String sortConfig;

    public GradeManager() {
        sortConfig = "a+";
        grades = new ArrayList<Grade>();
        fm = new FileManager();

    }

    public void reorderGrades(String sort) {
        sortConfig = sort;
        grades.sort(new GradeComparator(sort));
    }

    public ArrayList<Grade> getGrades() {
        return grades;
    }

    public void addGrade(String emnekode, String karakter) {
        karakter = karakter.toUpperCase();
        emnekode = emnekode.toUpperCase();
        if (karakter.length() != 1 || (karakter.charAt(0) < 'A' || karakter.charAt(0) > 'F')) {
            new AlertCreator().alert("Ugyldig karakter", "Karakter må være en bokstav fra A-F.", "ERROR");
        } else {

            if (emnekode.length() < 3 || emnekode.length() > 10) {
                new AlertCreator().alert("Ugyldig emnekode", "Emnekode må være på 3-10 bokstaver/tall.", "ERROR");
            } else {
                for (int i = 0; i < emnekode.length(); i++) {
                    if (!Character.isLetterOrDigit(emnekode.charAt(i))) {
                        new AlertCreator().alert("Ugyldig emnekode", "Emnekode må være på 3-10 bokstaver/tall.",
                                "ERROR");
                        return;
                    }
                }
                for (Grade g : grades) {
                    if (g.getEmnekode().equals(emnekode)) {
                        grades.remove(g);
                        break;

                    }
                }
                grades.add(new Grade(emnekode, karakter.charAt(0)));
                reorderGrades(this.sortConfig);

            }
        }

    }

    public ArrayList<String> getResults() {

        GradeCalculator calc = new GradeCalculator(grades);
        String[] data = { Double.toString(calc.getMedian()), Double.toString(calc.getAverage()),
                calc.getBest(), calc.getWorst() };
        return new ArrayList<String>(Arrays.asList(data));
    }

    @Override
    public Iterator<Grade> iterator() {
        return new GradeManagerIterator(this);
    }

    public void deleteGrade(String id) {
        for (Grade g : this) {
            if (g.getEmnekode() == id) {
                grades.remove(g);
                break;
            }
        }

    }

    public boolean getFromFile() {
        grades = fm.getFromFile();
        reorderGrades(sortConfig);
        return true;
    }

    public void saveToFile() {
        fm.saveToFile(this);
    }
}
