package agh.cs.lab1.view.simulation;

import agh.cs.lab1.model.animal.Animal;
import agh.cs.lab1.model.engine.IStatisticsListener;
import agh.cs.lab1.model.engine.SimulationEngine;
import agh.cs.lab1.model.map.Vector2d;
import com.google.gson.JsonSyntaxException;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

public class SimulationInstance {
    private final Canvas canvas;
    private final SimulationEngine engine = new SimulationEngine();
    private final MapDrawer drawer;

    public SimulationInstance(Canvas canvas, IStatisticsListener... listeners) throws IOException, JsonSyntaxException, NumberFormatException {
        this.canvas = canvas;
        drawer = new MapDrawer(engine.getMap(), canvas);
        Arrays.stream(listeners).forEach(listener -> engine.getStatisticsTracker().addStatisticsListener(listener));
    }

    public void canvasClicked(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY) {
            getAnimalByCanvasCoordinates(mouseEvent.getX(), mouseEvent.getY()).ifPresent(animal -> engine.getStatisticsTracker().setTrackedAnimal(animal));
            engine.getStatisticsTracker().updateStatistics();
        }
    }

    private Optional<Animal> getAnimalByCanvasCoordinates(double x, double y) {
        double fieldWidth = (canvas.getWidth() / engine.getMap().getWidth());
        double fieldHeight = (canvas.getHeight() / engine.getMap().getHeight());
        return engine.getMap().getStrongestAnimalOnField(new Vector2d((int) (x / fieldWidth), (int) (y / fieldHeight)));
    }

    public void runTurn(){
        engine.runTurn();
        drawer.draw();
    }

    public void highlightBestGenome(){
        drawer.highlightGenome(engine.getStatisticsTracker().getBestGenome());
    }
}
