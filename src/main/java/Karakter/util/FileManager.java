package Karakter.util;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import Karakter.app.Grade;
import Karakter.app.GradeManager;

public class FileManager {
    private final String filepath = "src/main/resources/Karakter/filer/grades.txt";

    public void saveToFile(GradeManager gradeManager) {
        try {

            FileWriter fileWriter = new FileWriter(filepath);

            for (Grade g : gradeManager) {
                String s = g.getEmnekode() + g.getBokstavKarakter();
                fileWriter.write(s + ":");

            }
            fileWriter.close();
            new AlertCreator().alert("Data lagret",
                    String.format("Lagret (%s stk) karakterer.", gradeManager.getGrades().size()), "INFORMATION");
        } catch (

        Exception exception) {
            System.out.println(exception);

            new AlertCreator().alert("Lagring feilet", "ERROR");
        }
    }

    public ArrayList<Grade> getFromFile() {
        ArrayList<Grade> res = new ArrayList<Grade>();
        try {

            Scanner scanner = new Scanner(new FileReader(filepath));
            ArrayList<String> line;
            if (scanner.hasNext()) {
                line = new ArrayList<String>(Arrays.asList(scanner.nextLine().split(":")));

                for (String grade : line) {

                    if (grade.length() > 3) {
                        res.add(new Grade(grade.substring(0, grade.length() - 1), grade.charAt(grade.length() - 1)));
                    }
                }
            }

            scanner.close();
        } catch (

        FileNotFoundException e) {
            System.out.println(e);

            new AlertCreator().alert("Henting av lagret data feilet",
                    "Ingen fil funnet. Opprett en ny fil ved å lagre nye karakterer.", "ERROR");

        } catch (Exception e) {
            new AlertCreator().alert("Henting av lagret data feilet", "Ukjent grunn, prøv igjen.", "ERROR");
        }
        return res;
    }

}
