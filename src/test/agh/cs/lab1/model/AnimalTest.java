package agh.cs.lab1.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AnimalTest {
    @Test
    public void moveTest(){
        RectangularMap map = new RectangularMap(4, 4);
        Animal animal = new Animal(map, new Vector2d(2, 2));
        map.place(animal);
//        position
        assertEquals(new Vector2d(2,3), animal.move(MapDirection.NORTH).getPosition());
        assertEquals(new Vector2d(3, 3), animal.move(MapDirection.EAST).getPosition());
        assertEquals(new Vector2d(3, 2), animal.move(MapDirection.SOUTH).getPosition());
        map = new RectangularMap(4, 4);
        animal = new Animal(map, new Vector2d(2, 2));
        map.place(animal);
//        boundaries
        assertEquals(new Vector2d(2, 3), animal.move(MapDirection.NORTH).move(MapDirection.NORTH).move(MapDirection.NORTH).getPosition());
        assertEquals(new Vector2d(0, 3), animal.move(MapDirection.WEST).move(MapDirection.WEST).move(MapDirection.WEST).move(MapDirection.WEST).getPosition());
    }
}
