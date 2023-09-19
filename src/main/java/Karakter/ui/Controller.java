package Karakter.ui;

import java.util.ArrayList;

import Karakter.app.GradeManager;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class Controller {
    @FXML
    private TextField emnekodeinput, karakterinput;

    @FXML
    private Label avgGrade, medianGrade, bestGrade, worstGrade;

    @FXML
    private ScrollPane gradesContainer;

    @FXML
    private ChoiceBox<String> sortChoice;

    private GradeManager gradeManager;

    public Controller() {
        gradeManager = new GradeManager();

    }

    @FXML
    public void handleAddGradeClick() {
        String emnekode = emnekodeinput.getText();
        String karakter = karakterinput.getText();
        gradeManager.addGrade(emnekode, karakter);
        updateGrades();
        updateResult();
    }

    public void handleDelete(String id) {
        gradeManager.deleteGrade(id);
        updateResult();
        updateGrades();

    }

    private void updateGrades() {
        gradesContainer.setContent(new GradeContainerCreator(gradeManager, this).getPane());
    }

    private void updateResult() {
        ArrayList<String> res = gradeManager.getResults();

        medianGrade.setText(res.get(0));
        avgGrade.setText(res.get(1));
        bestGrade.setText(res.get(2));
        worstGrade.setText(res.get(3));
    }

    @FXML
    public void saveToFile() {
        gradeManager.saveToFile();
    }

    @FXML
    public void getFromFile() {

        if (gradeManager.getFromFile()) {
            updateGrades();
            updateResult();
        }
    }

    @FXML
    public void changeSort() {
        String value = (String) sortChoice.getValue();
        String converted;
        switch (value) {
            case ("Emnekode stigende"):
                converted = "a+";
                break;
            case ("Emnekode synkende"):
                converted = "a-";

                break;
            case ("Karakter stigende"):
                converted = "g+";
                break;
            case ("Karakter synkende"):
                converted = "g-";
                break;
            default:
                converted = "";
                break;
        }

        gradeManager.reorderGrades(converted);
        updateGrades();

    }
}
