package agh.cs.lab1.view;


import agh.cs.lab1.view.errorView.ErrorViewController;
import agh.cs.lab1.view.questionView.QuestionViewController;
import agh.cs.lab1.view.simulation.AbstractSimulationView;
import agh.cs.lab1.view.singleView.SingleView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        if(getIsDoubleMode()){
            AbstractSimulationView simulationView1 = new SingleView(stage);
            AbstractSimulationView simulationView2 = new SingleView(new Stage());
            simulationView1.runSimulation();
            simulationView2.runSimulation();
        }
        else {
            AbstractSimulationView simulationView = new SingleView(stage);
            simulationView.runSimulation();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

    private boolean getIsDoubleMode() throws IOException {
        FXMLLoader loader = new FXMLLoader(QuestionViewController.class.getResource("QuestionView.fxml"));
        Parent root = loader.load();
        QuestionViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Choose mode");
        stage.setScene(new Scene(root, 600, 200));
        stage.showAndWait();
        return controller.isDoubleMode();
    }
}
