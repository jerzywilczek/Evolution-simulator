package agh.cs.lab1.view.questionView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.stage.Window;

public class QuestionViewController {
    private boolean doubleMode;

    @FXML
    private void singleButtonPressed(ActionEvent event){
        doubleMode = false;
        closeWindow(event);
    }

    private void closeWindow(ActionEvent event) {
        Window w = ((Node) event.getSource()).getScene().getWindow();
        if (w instanceof Stage) {
            ((Stage) w).close();
        }
    }

    @FXML
    private void doubleButtonPressed(ActionEvent event){
        doubleMode = true;
        closeWindow(event);
    }

    public boolean isDoubleMode() {
        return doubleMode;
    }
}
