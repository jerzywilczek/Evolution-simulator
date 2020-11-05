package agh.cs.lab1;

import java.util.*;

public class RectangularMap extends AbstractWorldMap {
    private final Vector2d lowerLeft;
    private final Vector2d upperRight;

    public RectangularMap(int width, int height) {
        super();
        lowerLeft = new Vector2d(0, 0);
        upperRight = new Vector2d(width - 1, height - 1);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return position.follows(lowerLeft) && position.precedes(upperRight) && !isOccupied(position);
    }

    @Override
    public Vector2d getLowerLeft() {
        return lowerLeft;
    }

    @Override
    public Vector2d getUpperRight() {
        return upperRight;
    }
}
