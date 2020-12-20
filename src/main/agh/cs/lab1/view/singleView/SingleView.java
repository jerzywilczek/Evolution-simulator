package agh.cs.lab1.view.singleView;

import agh.cs.lab1.model.animal.Animal;
import agh.cs.lab1.model.engine.SimulationEngine;
import agh.cs.lab1.model.map.Vector2d;
import agh.cs.lab1.view.AbstractSimulationView;
import agh.cs.lab1.view.MapDrawer;
import com.google.gson.JsonSyntaxException;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class SingleView extends AbstractSimulationView {
    private SimulationEngine engine;
    private final MapDrawer drawer;
    private final AnimationTimer animationTimer;
    private final SingleViewController singleViewController;


    public SingleView(Stage defaultStage) throws IOException {
        super(defaultStage);

        try {
            engine = new SimulationEngine();
        } catch (IOException | JsonSyntaxException | NumberFormatException exception) {
            showErrorWindowAndQuit(exception);
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SingeView.fxml"));
        Parent root = loader.load();
        singleViewController = loader.getController();

        getDefaultStage().setTitle("Simulation");
        getDefaultStage().setScene(new Scene(root, 1280, 720));

        drawer = new MapDrawer(engine.getMap(), singleViewController.getCanvas());

        animationTimer = new AnimationTimer() {
            private long previousSecond = 0;
            private int tps = 0;

            @Override
            public void handle(long time) {

                if (time / 1000000000 != previousSecond) {
                    previousSecond = time / 1000000000;
                    singleViewController.updateTPS(tps);
                    tps = 1;
                } else {
                    tps++;
                }

                engine.runTurn();
                drawer.draw();
                singleViewController.updateStatistics(engine.getStatisticsTracker());
            }
        };
    }

    private Optional<Animal> getAnimalByCanvasCoordinates(double x, double y) {
        double fieldWidth = (singleViewController.getCanvas().getWidth() / engine.getMap().getWidth());
        double fieldHeight = (singleViewController.getCanvas().getHeight() / engine.getMap().getHeight());
        return engine.getMap().getStrongestAnimalOnField(new Vector2d((int) (x / fieldWidth), (int) (y / fieldHeight)));
    }

    public void canvasClicked(double x, double y) {
        getAnimalByCanvasCoordinates(x, y).ifPresent(animal -> engine.getStatisticsTracker().setTrackedAnimal(animal));
        engine.getStatisticsTracker().updateStatistics();
        singleViewController.updateStatistics(engine.getStatisticsTracker());
    }

    public void highlightBestGenome(){
        drawer.highlightGenome(engine.getStatisticsTracker().getBestGenome());
    }

    @Override
    public AnimationTimer getAnimationTimer() {
        return animationTimer;
    }
}
