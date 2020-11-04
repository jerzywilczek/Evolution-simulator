package agh.cs.lab1;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertSame;

public class GrassFieldIntegrationTest {
    @Test
    public void animalCollisionOnPlacementTest() {
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "z", "f"});
        IWorldMap map = new GrassField(5);
        Animal animal = new Animal(map);
        map.place(animal);
//        Collision with an animal
        assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map)));
        map.run(directions);
        assertSame(animal, map.objectAt(new Vector2d(0, 2)));
        assertFalse(map.objectAt(new Vector2d(0, 1)) instanceof Animal);
        assertFalse(map.objectAt(new Vector2d(0, 0)) instanceof Animal);
    }
    @Test
    public void runTest(){
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new GrassField(0);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        Animal animal2 = new Animal(map, new Vector2d(3, 4));
        map.place(animal2);
        map.run(directions);
        assertSame(animal1, map.objectAt(new Vector2d(1, -3)));
        assertSame(animal2, map.objectAt(new Vector2d(2, 7)));
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
