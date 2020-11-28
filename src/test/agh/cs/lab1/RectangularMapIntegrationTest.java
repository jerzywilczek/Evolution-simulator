package agh.cs.lab1;

import agh.cs.lab1.model.*;
import org.junit.Test;
import static org.junit.Assert.*;

public class RectangularMapIntegrationTest {
//    With the new IEngine interface there is no way to add individual animals to the map, run the simulation and then check whether animals present at expected positions actually are specific animals expected to be there.
//    Therefore, tests only checks whether there is an animal on an expected position and don't verify which one it is.
    @Test
    public void animalCollisionOnPlacementTest() {
        assertThrows(IllegalArgumentException.class, () -> new OptionsParser().parse(new String[]{"f", "z", "f"}));
        MapDirection[] directions = new OptionsParser().parse(new String[]{"n", "n"});
        IWorldMap map = new RectangularMap(4, 4);
        IEngine engine = new SimulationEngine(directions, map, new Vector2d[]{new Vector2d(0, 0)});
//        Collision with an animal
        assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map)));
//        Outside of boundaries
        assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(4, 0))));
        assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(4, 4))));
        assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(-1, 0))));
        engine.run();
        assertTrue(map.objectAt(new Vector2d(0, 2)) instanceof Animal);
        assertNull(map.objectAt(new Vector2d(0, 1)));
        assertNull(map.objectAt(new Vector2d(0, 0)));
    }
    @Test
    public void runTest(){
        MapDirection[] directions = new OptionsParser().parse(new String[]{"n", "s", "e", "w", "s", "n", "s", "n", "s", "n", "s", "n"});
        IWorldMap map = new RectangularMap(10, 5);
        IEngine engine = new SimulationEngine(directions, map, new Vector2d[]{new Vector2d(0, 0), new Vector2d(3, 4)});
        engine.run();
        assertTrue(map.objectAt(new Vector2d(1, 0)) instanceof Animal);
        assertTrue(map.objectAt(new Vector2d(2, 4)) instanceof Animal);
    }
    @Test
    public void moveTest(){
        IWorldMap map = new RectangularMap(4, 4);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        animal1.move(MapDirection.NORTH);
        assertSame(animal1, map.objectAt(new Vector2d(0, 1)));
        animal1.move(MapDirection.WEST).move(MapDirection.EAST);
        assertSame(animal1, map.objectAt(new Vector2d(1, 1)));
        Animal animal2 = new Animal(map);
        map.place(animal2);
        animal1.move(MapDirection.SOUTHWEST);
        assertSame(animal1, map.objectAt(new Vector2d(1, 1)));
    }
}
