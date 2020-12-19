package agh.cs.lab1.view.mainView;

import agh.cs.lab1.model.map.WorldMap;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Map;

public class MapDrawer {
    private final WorldMap map;
    private final Canvas canvas;

    private final double fieldWidth;
    private final double fieldHeight;

    private final Color groundColor = Color.rgb(179, 255, 153);
    private final Color grassColor = Color.rgb(0, 153, 0);
    private final Color animalColor = Color.rgb(153, 51, 0);

    public MapDrawer(WorldMap map, Canvas canvas) {
        this.map = map;
        this.canvas = canvas;
        fieldWidth = canvas.getWidth() / map.getWidth();
        fieldHeight = canvas.getHeight() / map.getHeight();
    }

    public void draw() {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

        drawBackground(graphicsContext);
        drawPlants(graphicsContext);
        drawAnimals(graphicsContext);
    }

    private void drawBackground(GraphicsContext graphicsContext){
        graphicsContext.setFill(groundColor);
        graphicsContext.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void drawAnimals(GraphicsContext graphicsContext){
        graphicsContext.setFill(animalColor);
        map
                .getStrongestAnimalsGroupedByFields()
                .forEach(entry ->
                        graphicsContext.fillOval(
                                entry.getKey().x * fieldWidth,
                                entry.getKey().y * fieldHeight,
                                fieldWidth,
                                fieldHeight
                        )
                );
    }

    private void drawPlants(GraphicsContext graphicsContext){
        graphicsContext.setFill(grassColor);
        map
                .getPlants()
                .entrySet()
                .stream()
                .filter(Map.Entry::getValue)
                .forEach(entry ->
                        graphicsContext.fillRect(
                                entry.getKey().x * fieldWidth,
                                entry.getKey().y * fieldHeight,
                                fieldWidth,
                                fieldHeight
                        )
                );
    }
}
