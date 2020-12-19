package agh.cs.lab1.view.singleView;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;


public class SingleViewController {
    @FXML
    private Canvas canvas;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    private void startButtonPressed(ActionEvent event){
        SingleView.getInstance().unpauseSimulation();
        startButton.setDisable(true);
        stopButton.setDisable(false);
    }

    @FXML
    private void stopButtonPressed(ActionEvent event){
        SingleView.getInstance().pauseSimulation();
        stopButton.setDisable(true);
        startButton.setDisable(false);
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
