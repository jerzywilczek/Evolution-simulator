package agh.cs.lab1.view.simulation;

import agh.cs.lab1.view.errorView.ErrorViewController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractSimulationView {
    private final Stage defaultStage;

    public AbstractSimulationView(Stage defaultStage){
        this.defaultStage = defaultStage;
    }


    public void runSimulation(){
        defaultStage.show();
        getAnimationTimer().start();
    }

    protected Stage getDefaultStage() {
        return defaultStage;
    }

    protected void showErrorWindowAndQuit(Exception exception) {
        showErrorWindow(exception);
        System.exit(1);
    }

    public void showErrorWindow(Exception exception) {
        FXMLLoader loader = new FXMLLoader(ErrorViewController.class.getResource("ErrorView.fxml"));
        Parent root = null;
        try {
            root = loader.load();
        } catch (IOException e){
            e.printStackTrace();
            System.exit(1);
        }
        loader.<ErrorViewController>getController().getText().setText("Error:\n" + exception.getMessage());

        exception.printStackTrace();

        Stage stage = new Stage();
        stage.setTitle("Error!");
        stage.setScene(new Scene(root, 600, 200));
        stage.showAndWait();
    }

    public void pauseSimulation(){
        getAnimationTimer().stop();
    }

    public void unpauseSimulation(){
        getAnimationTimer().start();
    }

    public abstract AnimationTimer getAnimationTimer();
}
