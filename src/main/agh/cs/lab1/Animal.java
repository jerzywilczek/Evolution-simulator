package agh.cs.lab1;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class Animal implements IMapElement{
    private Vector2d position;
    private final IWorldMap map;
    private final List<IPositionChangeObserver> observers;

    /**
     * Initializes the animal with position (0, 0) and a given map.
     * Doesn't actually place the animal on the map, you need to call <code>IWorldMap.place(Animal)</code> for an animal to be placed.
     */
    public Animal(IWorldMap map) {
        this(map, new Vector2d(0, 0));
    }

    /**
     * Initializes the animal with specified position and a given map.
     * Doesn't actually place the animal on the map, you need to call <code>IWorldMap.place(Animal)</code> for an animal to be placed.
     */
    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
        this.observers = new LinkedList<>();
    }

    @Override
    public String toString() {
        return "A";
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public void addObserver(IPositionChangeObserver observer) {
        observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer) {
        observers.remove(observer);
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        observers.forEach(observer -> observer.positionChanged(oldPosition, newPosition, this));
    }

    //    returns this to support chaining moves and other methods like:
    //    animal.move(a).move(b).getPosition();
    public Animal move(@NotNull MapDirection direction) {
        Vector2d moveResult = this.position.add(direction.toUnitVector());
        if(map.canMoveTo(moveResult)){
            Vector2d oldPosition = position;
            position = moveResult;
            positionChanged(oldPosition, position);
        }
        return this;
    }

}
