package agh.cs.lab1;

public class Animal {
    private Vector2d position;
    private MapDirection direction;
    private IWorldMap map;

    /*public Animal() {
        this.position = new Vector2d(2, 2);
        this.direction = MapDirection.NORTH;
    }*/

    /**
     * Initializes the animal with position (0, 0) and a given map.
     * Doesn't actually place the animal on the map, you need to call <code>IWorldMap.place(Animal)</code> for an animal to be placed.
     * @param map
     */
    public Animal(IWorldMap map) {
        this(map, new Vector2d(0, 0));
    }

    /**
     * Initializes the animal with specified position and a given map.
     * Doesn't actually place the animal on the map, you need to call <code>IWorldMap.place(Animal)</code> for an animal to be placed.
     * @param map
     * @param initialPosition
     */
    public Animal(IWorldMap map, Vector2d initialPosition) {
        this.map = map;
        this.position = initialPosition;
        this.direction = MapDirection.NORTH;
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

    //    returns this to support chaining moves and other methods like:
    //    animal.move(a).move(b).getPosition();
    public Animal move(MoveDirection direction) {
        Vector2d moveResult;
        switch (direction) {
            case FORWARD:
                moveResult = this.position.add(this.direction.toUnitVector());
                if (map.canMoveTo(moveResult))
                    this.position = moveResult;
                break;
            case BACKWARD:
                moveResult = this.position.subtract(this.direction.toUnitVector());
                if (map.canMoveTo(moveResult))
                    this.position = moveResult;
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
