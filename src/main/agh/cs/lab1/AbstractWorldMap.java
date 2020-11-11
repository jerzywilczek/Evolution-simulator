package agh.cs.lab1;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver{
    protected Map<Vector2d, Animal> animalMap;

    public AbstractWorldMap(){
        animalMap = new HashMap<>();
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        if (!canMoveTo(animal.getPosition()))
            throw new IllegalArgumentException("Can't place animal at " + animal.getPosition());
        animal.addObserver(this);
        animalMap.put(animal.getPosition(), animal);
        return true;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position) {
        return animalMap.get(position);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        Animal animal = animalMap.remove(oldPosition);
        animalMap.put(newPosition, animal);
    }

    @Override
    public String toString() {
        return new MapVisualizer(this).draw(getLowerLeft(), getUpperRight());
    }


    public abstract Vector2d getLowerLeft();
    public abstract Vector2d getUpperRight();
}
