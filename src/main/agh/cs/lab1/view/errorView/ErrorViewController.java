package agh.cs.lab1.view.errorView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ErrorViewController {
    @FXML
    private void buttonPressed(ActionEvent event){
        System.exit(1);
    }

    @FXML
    private Text text;

    public Text getText() {
        return text;
    }
}
