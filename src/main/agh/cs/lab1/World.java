package agh.cs.lab1;

import static java.lang.System.out;

public class World {
    public static final Vector2d LOWER_LEFT_BOUNDARY = new Vector2d(0, 0);
    public static final Vector2d UPPER_RIGHT_BOUNDARY = new Vector2d(4, 4);

    public static boolean isInsideWorldBoundaries(Vector2d vector) {
        return vector.follows(World.LOWER_LEFT_BOUNDARY) && vector.precedes(World.UPPER_RIGHT_BOUNDARY);
    }

    public static void main(String[] args) {
        MoveDirection[] directions = new OptionsParser().parse(args);
        IWorldMap map = new GrassField(10);
        map.place(new Animal(map));
        map.place(new Animal(map,new Vector2d(3,4)));
        map.run(directions);
        out.print(map.toString());
    }

}
