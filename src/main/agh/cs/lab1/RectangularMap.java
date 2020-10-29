package agh.cs.lab1;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

public class RectangularMap implements IWorldMap {

    private final Vector2d lowerLeft;
    private final Vector2d upperRight;
    private List<Animal> animals;

    public RectangularMap(int width, int height) {
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width, height);   // width - 1
        animals = new ArrayList<>();
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight) && !isOccupied(position);
    }

    @Override
    public boolean place(Animal animal) {
        if (isOccupied(animal.getPosition())) return false; // czy isOccupied jest właściwą metodą?
        animals.add(animal);
        return true;
    }

    @Override
    public void run(MoveDirection[] directions) {
        if (animals.size() == 0) return;
        for (int i = 0; i < directions.length; i++) {
            animals.get(i % animals.size()).move(directions[i]);
        }
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) return true; // można skorzystać z objectAt
        }
        return false;
    }

    @Override
    public Object objectAt(Vector2d position) {
        for (Animal animal : animals) {
            if (animal.getPosition().equals(position)) return animal;
        }
        return null;
    }

    @Override
    public String toString() {
        return new MapVisualizer(this).draw(lowerLeft, upperRight);
    }
}
