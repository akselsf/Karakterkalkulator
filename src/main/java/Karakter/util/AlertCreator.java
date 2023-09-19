package Karakter.util;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class AlertCreator {
    public void alert(String header, String message, String type) {
        switch (type) {
            case ("INFORMATION"):
                show(header, message, AlertType.INFORMATION);
                break;
            default:
                show(header, message, AlertType.ERROR);
                break;
        }

    }

    public void alert(String header, String message) {
        show(header, message, AlertType.ERROR);

    }

    private void show(String header, String message, AlertType type) {
        if (isTest()) {

            return;
        }
        Alert a = new Alert(type);
        a.setContentText(message);
        a.setHeaderText(header);
        a.show();

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
