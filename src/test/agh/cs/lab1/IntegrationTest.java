package agh.cs.lab1;


import org.junit.Test;

import static java.lang.System.out;
import static org.junit.Assert.*;

public class IntegrationTest {  // bardzo ubogi test
    @Test
    public void animalCollisionOnPlacementTest() {
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "z", "f"});
        RectangularMap map = new RectangularMap(4, 4);
        map.place(new Animal(map));
        map.place(new Animal(map));
        map.run(directions);
        assertNotNull(map.objectAt(new Vector2d(0, 2)));    // sprawdzenie, że coś jest na tej pozycji, to trochę mało; jeszcze sprawdźmy co
        assertNull(map.objectAt(new Vector2d(0, 1)));
        assertNull(map.objectAt(new Vector2d(0, 0)));
    }
    @Test
    public void generalTest(){
        MoveDirection[] directions = new OptionsParser().parse(new String[]{"f", "b", "r", "l", "f", "f", "r", "r", "f", "f", "f", "f", "f", "f", "f", "f"});
        IWorldMap map = new RectangularMap(10, 5);
        map.place(new Animal(map));
        map.place(new Animal(map,new Vector2d(3,4)));
        map.run(directions);
        assertNotNull(map.objectAt(new Vector2d(1, 0)));    // j.w.
        assertNotNull(map.objectAt(new Vector2d(2, 5)));
    }
}
