package agh.cs.lab1.view;


import agh.cs.lab1.view.singleView.SingleView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        AbstractSimulationView simulationView = new SingleView(stage);
        simulationView.runSimulation();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
