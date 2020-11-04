package agh.cs.lab1;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AnimalTest {
    @Test
    public void moveTest(){
        RectangularMap map = new RectangularMap(4, 4);
        Animal animal = new Animal(map, new Vector2d(2, 2));
        map.place(animal);
//        direction
        assertEquals(MapDirection.EAST, animal.move(MoveDirection.RIGHT).getDirection());
        assertEquals(MapDirection.SOUTH, animal.move(MoveDirection.RIGHT).getDirection());
        assertEquals(MapDirection.EAST, animal.move(MoveDirection.LEFT).getDirection());
        map = new RectangularMap(4, 4);
        animal = new Animal(map, new Vector2d(2, 2));
        map.place(animal);
//        position
        assertEquals(new Vector2d(2,3), animal.move(MoveDirection.FORWARD).getPosition());
        assertEquals(new Vector2d(3, 3), animal.move(MoveDirection.RIGHT).move(MoveDirection.FORWARD).getPosition());
        assertEquals(new Vector2d(3, 2), animal.move(MoveDirection.LEFT).move(MoveDirection.BACKWARD).getPosition());
        map = new RectangularMap(4, 4);
        animal = new Animal(map, new Vector2d(2, 2));
        map.place(animal);
//        boundaries
        assertEquals(new Vector2d(2, 3), animal.move(MoveDirection.FORWARD).move(MoveDirection.FORWARD).move(MoveDirection.FORWARD).getPosition());
        assertEquals(new Vector2d(0, 3), animal.move(MoveDirection.LEFT).move(MoveDirection.FORWARD).move(MoveDirection.FORWARD).move(MoveDirection.FORWARD).getPosition());
    }
}
