package agh.cs.lab1.model.map;

import agh.cs.lab1.model.map.MapDirection;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapDirectionTest {
    @Test
    public void testRotate(){
        MapDirection n = MapDirection.NORTH;

        assertEquals(MapDirection.NORTHEAST, n.rotate(1));
        assertEquals(MapDirection.EAST, n.rotate(2));
        assertEquals(MapDirection.SOUTHEAST, n.rotate(3));
        assertEquals(MapDirection.NORTHWEST, n.rotate(-1));
        assertEquals(MapDirection.WEST, n.rotate(-2));
    }
}
