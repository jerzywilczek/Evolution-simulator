package agh.cs.lab1;

import static java.lang.System.out;

public class World {
    public static void main(String[] args) {
        try {
            MoveDirection[] directions = new OptionsParser().parse(args);
            IWorldMap map = new GrassField(10);
            IEngine engine = new SimulationEngine(directions, map, new Vector2d[]{new Vector2d(0, 0), new Vector2d(3, 4)});
            engine.run();
            out.print(map.toString());
        } catch (IllegalArgumentException e) {
            out.println(e.getMessage());
        }
    }
}
