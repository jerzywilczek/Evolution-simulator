package agh.cs.lab1;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertSame;

public class GrassFieldIntegrationTest {
//    With the new IEngine interface there is no way to add individual animals to the map, run the simulation and then check whether animals present at expected positions actually are specific animals expected to be there.
//    Therefore, tests only checks whether there is an animal on an expected position and don't verify which one it is.
    @Test
    public void animalCollisionOnPlacementTest() {
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "f"});
        IWorldMap map = new GrassField(5);
        assertThrows(IllegalArgumentException.class, () ->
                new SimulationEngine(directions, map, new Vector2d[]{new Vector2d(0, 0), new Vector2d(0, 0)})
        );
    }
    @Test
    public void runTest(){
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new GrassField(0);
        IEngine engine = new SimulationEngine(directions, map, new Vector2d[]{new Vector2d(0, 0), new Vector2d(3, 4)});
        engine.run();
        assertTrue(map.objectAt(new Vector2d(1, -3)) instanceof Animal);
        assertTrue(map.objectAt(new Vector2d(2, 7)) instanceof Animal);
    }
    @Test
    public void moveTest(){
        IWorldMap map = new GrassField(10);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        animal1.move(MoveDirection.FORWARD);
        assertSame(animal1, map.objectAt(new Vector2d(0, 1)));
        animal1.move(MoveDirection.LEFT).move(MoveDirection.FORWARD).move(MoveDirection.BACKWARD);
        assertSame(animal1, map.objectAt(new Vector2d(0, 1)));
        Animal animal2 = new Animal(map);
        map.place(animal2);
        animal1.move(MoveDirection.LEFT).move(MoveDirection.FORWARD);
        assertSame(animal1, map.objectAt(new Vector2d(0, 1)));
    }
    @Test
    public void grassAmountTest(){
        GrassField grassField = new GrassField(10);
        int amount = 0;
        for(int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                if (grassField.objectAt(new Vector2d(i, j)) instanceof Grass) amount++;
        assertEquals(10, amount);
    }
}
