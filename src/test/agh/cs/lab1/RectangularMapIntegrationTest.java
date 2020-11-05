package agh.cs.lab1;

import org.junit.Test;
import static org.junit.Assert.*;

public class RectangularMapIntegrationTest {
//    With the new IEngine interface there is no way to add individual animals to the map, run the simulation and then check whether animals present at expected positions actually are specific animals expected to be there.
//    Therefore, tests only checks whether there is an animal on an expected position and don't verify which one it is.
    @Test
    public void animalCollisionOnPlacementTest() {
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "z", "f"});
        IWorldMap map = new RectangularMap(4, 4);
        IEngine engine = new SimulationEngine(directions, map, new Vector2d[]{new Vector2d(0, 0)});
//        Collision with an animal
        assertFalse(map.place(new Animal(map)));
//        Outside of boundaries
        assertFalse(map.place(new Animal(map, new Vector2d(4, 0))));
        assertFalse(map.place(new Animal(map, new Vector2d(4, 4))));
        assertFalse(map.place(new Animal(map, new Vector2d(-1, 0))));
        engine.run();
        assertTrue(map.objectAt(new Vector2d(0, 2)) instanceof Animal);
        assertNull(map.objectAt(new Vector2d(0, 1)));
        assertNull(map.objectAt(new Vector2d(0, 0)));
    }
    @Test
    public void runTest(){
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
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
        animal1.move(MoveDirection.FORWARD);
        assertSame(animal1, map.objectAt(new Vector2d(0, 1)));
        animal1.move(MoveDirection.LEFT).move(MoveDirection.FORWARD).move(MoveDirection.BACKWARD);
        assertSame(animal1, map.objectAt(new Vector2d(1, 1)));
        Animal animal2 = new Animal(map);
        map.place(animal2);
        animal1.move(MoveDirection.LEFT).move(MoveDirection.FORWARD).move(MoveDirection.RIGHT).move(MoveDirection.FORWARD);
        assertSame(animal1, map.objectAt(new Vector2d(1, 0)));
    }
}
