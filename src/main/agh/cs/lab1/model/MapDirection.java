package agh.cs.lab1.model;

import java.util.Random;

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


    /**
     * Returns MapDirection rotated by 45deg * rotationAmount clockwise
     *
     * @param rotationAmount amount of 45 degree turns to be applied to the MapDirection. Passing negative values results in a counter-clockwise turn instead.
     * @return this rotated by 45deg * rotationAmount
     */
    public MapDirection rotate(int rotationAmount) {
        int n = MapDirection.values().length;
        return MapDirection.values()[(((this.ordinal() + rotationAmount) % n) + n) % n];
    }

    public Vector2d toUnitVector() {
        return UNIT_VECTOR;
    }

    public static MapDirection randomDirection(){
        return MapDirection.values()[new Random().nextInt(MapDirection.values().length)];
    }
}
