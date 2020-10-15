package agh.cs.lab1;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MapDirectionTest {
    @Test
    public void testNext() {
        MapDirection n = MapDirection.NORTH;
        MapDirection e = MapDirection.EAST;
        MapDirection s = MapDirection.SOUTH;
        MapDirection w = MapDirection.WEST;

        assertEquals(MapDirection.EAST, n.next());
        assertEquals(MapDirection.SOUTH, e.next());
        assertEquals(MapDirection.WEST, s.next());
        assertEquals(MapDirection.NORTH, w.next());
    }

    @Test
    public void testPrevious() {
        MapDirection n = MapDirection.NORTH;
        MapDirection e = MapDirection.EAST;
        MapDirection s = MapDirection.SOUTH;
        MapDirection w = MapDirection.WEST;

        assertEquals(MapDirection.WEST, n.previous());
        assertEquals(MapDirection.SOUTH, w.previous());
        assertEquals(MapDirection.EAST, s.previous());
        assertEquals(MapDirection.NORTH, e.previous());
    }

}
