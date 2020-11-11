package agh.cs.lab1;

import java.util.LinkedList;
import java.util.List;

public class Animal {
    private Vector2d position;
    private MapDirection direction;
    private IWorldMap map;
    private List<IPositionChangeObserver> observers;

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
        this.direction = MapDirection.NORTH;
        this.observers = new LinkedList<>();
    }

    @Override
    public String toString() {
        if (direction == MapDirection.NORTH) return "^";
        if (direction == MapDirection.WEST) return ">";
        if (direction == MapDirection.SOUTH) return "v";
        if (direction == MapDirection.EAST) return "<";
//        this should never happen
        throw new IllegalStateException("Animal has a bad direction: " + direction);
    }

    public MapDirection getDirection() {
        return direction;
    }

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
        observers.forEach(observer -> observer.positionChanged(oldPosition, newPosition));
    }

    //    returns this to support chaining moves and other methods like:
    //    animal.move(a).move(b).getPosition();
    public Animal move(MoveDirection direction) {
        Vector2d moveResult;
        switch (direction) {
            case FORWARD:
                moveResult = this.position.add(this.direction.toUnitVector());
                if (map.canMoveTo(moveResult)) {
                    positionChanged(this.position, moveResult);
                    this.position = moveResult;
                }
                break;
            case BACKWARD:
                moveResult = this.position.subtract(this.direction.toUnitVector());
                if (map.canMoveTo(moveResult)) {
                    positionChanged(this.position, moveResult);
                    this.position = moveResult;
                }
                break;
            case RIGHT:
                this.direction = this.direction.next();
                break;
            case LEFT:
                this.direction = this.direction.previous();
                break;
        }
        return this;
    }
}
