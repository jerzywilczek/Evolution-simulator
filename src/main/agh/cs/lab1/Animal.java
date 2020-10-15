package agh.cs.lab1;

public class Animal {
    private Vector2d position;
    private MapDirection direction;

    public Animal() {
        this.position = new Vector2d(2, 2);
        this.direction = MapDirection.NORTH;
    }

    @Override
    public String toString() {
        return "pozycja: " + position + ", kierunek: " + direction;
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
                if (World.isInsideWorldBoundaries(moveResult))
                    this.position = moveResult;
                break;
            case BACKWARD:
                moveResult = this.position.subtract(this.direction.toUnitVector());
                if (World.isInsideWorldBoundaries(moveResult))
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
