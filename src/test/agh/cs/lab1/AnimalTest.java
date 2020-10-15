package agh.cs.lab1;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class AnimalTest {
    @Test
    public void moveTest(){
        Animal animal = new Animal();
//        direction
        assertEquals(MapDirection.EAST, animal.move(MoveDirection.RIGHT).getDirection());
        assertEquals(MapDirection.SOUTH, animal.move(MoveDirection.RIGHT).getDirection());
        assertEquals(MapDirection.EAST, animal.move(MoveDirection.LEFT).getDirection());
        animal = new Animal();
//        position
        assertEquals(new Vector2d(2,3), animal.move(MoveDirection.FORWARD).getPosition());
        assertEquals(new Vector2d(3, 3), animal.move(MoveDirection.RIGHT).move(MoveDirection.FORWARD).getPosition());
        assertEquals(new Vector2d(3, 2), animal.move(MoveDirection.LEFT).move(MoveDirection.BACKWARD).getPosition());
        animal = new Animal();
//        boundaries
        assertEquals(new Vector2d(2, 4), animal.move(MoveDirection.FORWARD).move(MoveDirection.FORWARD).move(MoveDirection.FORWARD).getPosition());
        assertEquals(new Vector2d(0, 4), animal.move(MoveDirection.LEFT).move(MoveDirection.FORWARD).move(MoveDirection.FORWARD).move(MoveDirection.FORWARD).getPosition());

    }
}
