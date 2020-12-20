package agh.cs.lab1.view.simulation;

import agh.cs.lab1.view.errorView.ErrorViewController;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class AbstractSimulationView {
    private static AbstractSimulationView instance;
    private final Stage defaultStage;

    public AbstractSimulationView(Stage defaultStage){
        if (instance != null){
            throw new IllegalStateException("There can only exist one simulation view");
        }
        this.defaultStage = defaultStage;
        instance = this;
    }


    public void runSimulation(){
        defaultStage.show();
        getAnimationTimer().start();
    }

    protected Stage getDefaultStage() {
        return defaultStage;
    }

    protected void showErrorWindowAndQuit(Exception exception) throws IOException {
        FXMLLoader loader = new FXMLLoader(ErrorViewController.class.getResource("ErrorView.fxml"));
        Parent root = loader.load();
        loader.<ErrorViewController>getController().getText().setText("Error:\n" + exception.getMessage());

        exception.printStackTrace();

        Stage stage = new Stage();
        stage.setTitle("Error!");
        stage.setScene(new Scene(root, 600, 200));
        stage.showAndWait();

        System.exit(1);
    }

    public static AbstractSimulationView getInstance(){
        return instance;
    }

    public void pauseSimulation(){
        getAnimationTimer().stop();
    }

    public void unpauseSimulation(){
        getAnimationTimer().start();
    }

    public abstract AnimationTimer getAnimationTimer();
}
