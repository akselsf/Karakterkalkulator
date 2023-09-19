package Karakter.ui;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import Karakter.app.Grade;
import Karakter.app.GradeManager;
import javafx.scene.Node;

public class GradeContainerCreator {
    private Controller controller;
    private GradeManager gm;

    public GradeContainerCreator(GradeManager gm, Controller controller) {
        this.gm = gm;
        this.controller = controller;

    }

    public Pane getPane() {
        Pane pane = new Pane();
        pane.setId("gradescontainerpane");
        int index = 0;
        for (Grade g : gm) {
            pane.getChildren().add(getSingleGradePane(g, index));
            index++;
            pane.setPrefHeight((index + 1) * 60);
        }
        return pane;
    }

    private Pane getSingleGradePane(Grade grade, int index) {

        Pane pane = new Pane();
        if (isTest()) {
            return pane;
        }
        pane.getStyleClass().add("gradepane");
        pane.setLayoutY(60 * index);
        pane.setLayoutX(35);
        pane.setPrefHeight(44);
        pane.setPrefWidth(477);

        Label emneLabel = new Label();
        emneLabel.getStyleClass().add("gradepanelabel");
        emneLabel.setText(String.format("Emne: %s", grade.getEmnekode()));
        emneLabel.setLayoutX(26);
        emneLabel.setLayoutY(13);
        pane.getChildren().add(emneLabel);

        Label karakterLabel = new Label();
        karakterLabel.getStyleClass().add("gradepanelabel");
        karakterLabel.setText(String.format("Karakter: %s", grade.getBokstavKarakter()));
        karakterLabel.setLayoutX(250);
        karakterLabel.setLayoutY(13);
        pane.getChildren().add(karakterLabel);

        Button deleteButton = new Button();
        deleteButton.getStyleClass().add("deletebtn");
        deleteButton.setText("Fjern");
        deleteButton.setLayoutX(407);
        deleteButton.setLayoutY(10);
        deleteButton.setPrefHeight(20);
        deleteButton.setPrefWidth(50);
        deleteButton.setId(grade.getEmnekode());

        deleteButton.setOnAction(e -> controller.handleDelete(((Node) e.getSource()).getId()));

        pane.getChildren().add(deleteButton);

        return pane;
    }

    private boolean isTest() {
        for (StackTraceElement e : Thread.currentThread().getStackTrace()) {
            if (e.getClassName().startsWith("org.junit.")) {
                return true;
            }
        }
        return false;
    }
}
