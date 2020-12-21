package agh.cs.lab1.view.simulation;

import agh.cs.lab1.model.animal.Animal;
import agh.cs.lab1.model.animal.Genome;
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
    private final Color animalColor = Color.rgb(255, 175, 0);
    private final Color highlightedColor = Color.rgb(255, 0, 255);

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

    private void drawAnimal(GraphicsContext graphicsContext, Animal animal, Color color){
        graphicsContext.setFill(color);
        graphicsContext.fillOval(
                animal.getPosition().x * fieldWidth,
                animal.getPosition().y * fieldHeight,
                fieldWidth,
                fieldHeight
        );
    }

    private void drawAnimals(GraphicsContext graphicsContext) {
        graphicsContext.setFill(animalColor);
        map
                .getStrongestAnimalsGroupedByFields()
                .forEach(entry -> {
                    Color paint = Color.rgb(
                            (int)(255 * Math.abs((animalColor.getRed() * (Math.atan(entry.getValue().get(0).getEnergy())/(Math.PI/2))))),
                            (int)(255 * Math.abs((animalColor.getGreen() * (Math.atan(entry.getValue().get(0).getEnergy())/(Math.PI/2))))),
                            (int) (255 * Math.abs((animalColor.getBlue() * (Math.atan(entry.getValue().get(0).getEnergy())/(Math.PI/2)))))
                    );
                    drawAnimal(graphicsContext, entry.getValue().get(0), paint);
                });
    }

    private void drawPlants(GraphicsContext graphicsContext) {
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

    public void highlightGenome(Genome genome){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        map
                .getAnimalsByGenome(genome)
                .forEach(animal -> drawAnimal(graphicsContext, animal, highlightedColor));
    }
}
