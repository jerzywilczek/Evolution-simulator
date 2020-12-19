package agh.cs.lab1.view;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;


public class MainViewController {
    @FXML
    private Canvas canvas;

    @FXML
    private Button startButton;

    @FXML
    private Button stopButton;

    @FXML
    public void startButtonPressed(ActionEvent event){
        MainView.getInstance().getAnimationTimer().start();
        startButton.setDisable(true);
        stopButton.setDisable(false);
    }

    @FXML
    public void stopButtonPressed(ActionEvent event){
        MainView.getInstance().getAnimationTimer().stop();
        stopButton.setDisable(true);
        startButton.setDisable(false);
    }


    public Canvas getCanvas() {
        return canvas;
    }
}
