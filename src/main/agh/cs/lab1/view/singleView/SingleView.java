package agh.cs.lab1.view.singleView;

import agh.cs.lab1.model.engine.SimulationEngine;
import agh.cs.lab1.view.AbstractSimulationView;
import agh.cs.lab1.view.MapDrawer;
import com.google.gson.JsonSyntaxException;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SingleView extends AbstractSimulationView {
    private SimulationEngine engine;
    private final MapDrawer drawer;
    private final AnimationTimer animationTimer;


    public SingleView(Stage defaultStage) throws IOException {
        super(defaultStage);

        try {
            engine = new SimulationEngine();
        } catch (IOException | JsonSyntaxException | NumberFormatException exception) {
            showErrorWindowAndQuit(exception);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SingeView.fxml"));
        Parent root = loader.load();
        SingleViewController singleViewController = loader.getController();

        getDefaultStage().setTitle("Simulation");
        getDefaultStage().setScene(new Scene(root, 1280, 720));

        drawer = new MapDrawer(engine.getMap(), singleViewController.getCanvas());
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                drawer.draw();
                engine.runTurn();
            }
        };
    }

    @Override
    public void runSimulation() {
        getDefaultStage().show();
        animationTimer.start();
    }

    @Override
    public void pauseSimulation() {
        animationTimer.stop();
    }

    @Override
    public void unpauseSimulation() {
        animationTimer.start();
    }

}
