package agh.cs.lab1;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractWorldMap implements IWorldMap{
    protected List<Animal> animals;
    public AbstractWorldMap(){
        animals = new ArrayList<>();
    }

    @Override
    public boolean place(Animal animal) {
        if (!canMoveTo(animal.getPosition())) return false;
        animals.add(animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animals.stream()
                .filter(animal -> animal.getPosition().equals(position))
                .findAny().orElse(null);
    }

    @Override
    public String toString() {
        return new MapVisualizer(this).draw(getLowerLeft(), getUpperRight());
    }

    public abstract Vector2d getLowerLeft();
    public abstract Vector2d getUpperRight();
}
