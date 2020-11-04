package agh.cs.lab1;

import org.junit.Test;
import static org.junit.Assert.*;

public class RectangularMapIntegrationTest {
    @Test
    public void animalCollisionOnPlacementTest() {
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "z", "f"});
        IWorldMap map = new RectangularMap(4, 4);
        Animal animal = new Animal(map);
        map.place(animal);
//        Collision with an animal
        assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map)));
//        Outside of boundaries
        assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(4, 0))));
        assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(4, 4))));
        assertThrows(IllegalArgumentException.class, () -> map.place(new Animal(map, new Vector2d(-1, 0))));
        map.run(directions);
        assertSame(animal, map.objectAt(new Vector2d(0, 2)));
        assertNull(map.objectAt(new Vector2d(0, 1)));
        assertNull(map.objectAt(new Vector2d(0, 0)));
    }
    @Test
    public void runTest(){
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(10, 5);
        Animal animal1 = new Animal(map);
        map.place(animal1);
        Animal animal2 = new Animal(map, new Vector2d(3, 4));
        map.place(animal2);
        map.run(directions);
        assertSame(animal1, map.objectAt(new Vector2d(1, 0)));
        assertSame(animal2, map.objectAt(new Vector2d(2, 4)));
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
