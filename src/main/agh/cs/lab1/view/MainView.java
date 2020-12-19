package agh.cs.lab1.view;

import agh.cs.lab1.model.engine.SimulationEngine;
import agh.cs.lab1.view.errorView.ErrorViewController;
import agh.cs.lab1.view.mainView.MainViewController;
import agh.cs.lab1.view.mainView.MapDrawer;
import com.google.gson.JsonSyntaxException;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.stage.Stage;

import java.io.IOException;

public class MainView extends Application {
    private SimulationEngine engine;
    private AnimationTimer animationTimer;
    private MapDrawer mapDrawer;
    private static MainView instance;

    public AnimationTimer getAnimationTimer() {
        return animationTimer;
    }

    public static MainView getInstance() {
        return instance;
    }

    @Override
    public void start(Stage stage) throws Exception {
        instance = this;

        try{
            engine = new SimulationEngine();
        }catch (IOException | JsonSyntaxException | NumberFormatException exception){
            showErrorWindow(exception);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView/MainView.fxml"));
        Parent root = loader.load();
        MainViewController mainViewController =  loader.getController();

        stage.setTitle("Game");
        stage.setScene(new Scene(root, 1280, 720));
        stage.show();

        Canvas canvas = mainViewController.getCanvas();
        mapDrawer = new MapDrawer(engine.getMap(), canvas);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                mapDrawer.draw();
                engine.runTurn();
            }
        };
        animationTimer.start();
    }

    private void showErrorWindow(Exception exception) throws IOException {
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

    public static void main(String[] args){
        launch(args);
    }

}
