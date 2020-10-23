package agh.cs.lab1;

public enum MapDirection {
    NORTH("Polnoc", new Vector2d(0, 1)),
    SOUTH("Poludnie", new Vector2d(0, -1)),
    WEST("Zachod", new Vector2d(-1, 0)),
    EAST("Wschod", new Vector2d(1, 0));

    private final String STRING;
    private final Vector2d UNIT_VECTOR;

    MapDirection(String string, Vector2d unitVector) {
        this.STRING = string;
        this.UNIT_VECTOR = unitVector;
    }

    @Override
    public String toString() {
        return STRING;
    }

    public MapDirection next() {
        switch (this) {
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public MapDirection previous() {
        switch (this) {
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Vector2d toUnitVector() {
        return UNIT_VECTOR;
    }
}
