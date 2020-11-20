package agh.cs.lab1;

public enum MapDirection {
    NORTH(new Vector2d(0, 1)),
    NORTHEAST(new Vector2d(1, 1)),
    EAST(new Vector2d(1, 0)),
    SOUTHEAST(new Vector2d(1, -1)),
    SOUTH(new Vector2d(0, -1)),
    SOUTHWEST(new Vector2d(-1, -1)),
    WEST(new Vector2d(-1, 0)),
    NORTHWEST(new Vector2d(-1, 1));

    private final Vector2d UNIT_VECTOR;

    MapDirection(Vector2d unitVector) {
        this.UNIT_VECTOR = unitVector;
    }


    public MapDirection rotate(int rotationAmount){
        return MapDirection.values()[(this.ordinal() + rotationAmount + MapDirection.values().length) % MapDirection.values().length];
    }

    @Deprecated
    public MapDirection next() {
        return this.rotate(2);
    }

    @Deprecated
    public MapDirection previous() {
        return this.rotate(-2);
    }

    public Vector2d toUnitVector() {
        return UNIT_VECTOR;
    }
}
