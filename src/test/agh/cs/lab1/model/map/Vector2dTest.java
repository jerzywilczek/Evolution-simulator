package agh.cs.lab1.model.map;

import org.junit.Test;

import static org.junit.Assert.*;

public class Vector2dTest {
    @Test
    public void testEquals(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(0, 1);
        Vector2d v3 = new Vector2d(1, 0);
        Vector2d v4 = new Vector2d(1, 1);
        Vector2d v5 = new Vector2d(0, 0);

        assertEquals(v1, v1);
        assertNotEquals(v1, v2);
        assertNotEquals(v1, v3);
        assertNotEquals(v1, v4);
        assertEquals(v1, v5);
        assertNotEquals(0, v1);
    }

    @Test
    public void testToString(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(0, 1);
        Vector2d v3 = new Vector2d(1, 0);
        Vector2d v4 = new Vector2d(1, 1);

        assertEquals("(0,0)", v1.toString());
        assertEquals("(0,1)", v2.toString());
        assertEquals("(1,0)", v3.toString());
        assertEquals("(1,1)", v4.toString());
    }

    @Test
    public void testPrecedes(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(-1, -1);
        Vector2d v3 = new Vector2d(-1, 0);
        Vector2d v4 = new Vector2d(0, -1);
        Vector2d v5 = new Vector2d(0, 1);
        Vector2d v6 = new Vector2d(1, 0);
        Vector2d v7 = new Vector2d(1, 1);

        assertTrue(v1.precedes(v1));
        assertTrue(v2.precedes(v1));
        assertTrue(v3.precedes(v1));
        assertTrue(v4.precedes(v1));
        assertFalse(v5.precedes(v1));
        assertFalse(v6.precedes(v1));
        assertFalse(v7.precedes(v1));
    }

    @Test
    public void testFollows(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(-1, -1);
        Vector2d v3 = new Vector2d(-1, 0);
        Vector2d v4 = new Vector2d(0, -1);
        Vector2d v5 = new Vector2d(0, 1);
        Vector2d v6 = new Vector2d(1, 0);
        Vector2d v7 = new Vector2d(1, 1);

        assertTrue(v1.follows(v1));
        assertFalse(v2.follows(v1));
        assertFalse(v3.follows(v1));
        assertFalse(v4.follows(v1));
        assertTrue(v5.follows(v1));
        assertTrue(v6.follows(v1));
        assertTrue(v7.follows(v1));
    }

    @Test
    public void testUpperRight(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(-1, -1);
        Vector2d v3 = new Vector2d(-1, 0);
        Vector2d v4 = new Vector2d(0, -1);
        Vector2d v5 = new Vector2d(0, 1);
        Vector2d v6 = new Vector2d(1, 0);
        Vector2d v7 = new Vector2d(1, 1);

        assertEquals(new Vector2d(0, 0), v1.upperRight(v2));
        assertEquals(new Vector2d(0, 0), v3.upperRight(v4));
        assertEquals(new Vector2d(1, 1), v5.upperRight(v6));
        assertEquals(new Vector2d(1, 1), v1.upperRight(v7));
    }

    @Test
    public void testLowerLeft(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(-1, -1);
        Vector2d v3 = new Vector2d(-1, 0);
        Vector2d v4 = new Vector2d(0, -1);
        Vector2d v5 = new Vector2d(0, 1);
        Vector2d v6 = new Vector2d(1, 0);
        Vector2d v7 = new Vector2d(1, 1);

        assertEquals(new Vector2d(-1, -1), v1.lowerLeft(v2));
        assertEquals(new Vector2d(-1, -1), v3.lowerLeft(v4));
        assertEquals(new Vector2d(0, 0), v5.lowerLeft(v6));
        assertEquals(new Vector2d(0, 0), v1.lowerLeft(v7));
    }

    @Test
    public void testAdd(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(-1, -1);
        Vector2d v3 = new Vector2d(-1, 0);
        Vector2d v4 = new Vector2d(0, -1);
        Vector2d v5 = new Vector2d(0, 1);
        Vector2d v6 = new Vector2d(1, 0);
        Vector2d v7 = new Vector2d(1, 1);

        assertEquals(new Vector2d(-1, -1), v1.add(v2));
        assertEquals(new Vector2d(-1, -1), v3.add(v4));
        assertEquals(new Vector2d(1, 1), v5.add(v6));
        assertEquals(new Vector2d(1, 1), v1.add(v7));
    }

    @Test
    public void testSubtract(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(-1, -1);
        Vector2d v3 = new Vector2d(-1, 0);
        Vector2d v4 = new Vector2d(0, -1);
        Vector2d v5 = new Vector2d(0, 1);
        Vector2d v6 = new Vector2d(1, 0);
        Vector2d v7 = new Vector2d(1, 1);

        assertEquals(new Vector2d(1, 1), v1.subtract(v2));
        assertEquals(new Vector2d(-1, 1), v3.subtract(v4));
        assertEquals(new Vector2d(-1, 1), v5.subtract(v6));
        assertEquals(new Vector2d(-1, -1), v1.subtract(v7));
    }

    @Test
    public void testOpposite(){
        Vector2d v1 = new Vector2d(0, 0);
        Vector2d v2 = new Vector2d(-1, -1);
        Vector2d v3 = new Vector2d(-1, 0);
        Vector2d v4 = new Vector2d(0, -1);
        Vector2d v5 = new Vector2d(0, 1);
        Vector2d v6 = new Vector2d(1, 0);
        Vector2d v7 = new Vector2d(1, 1);

        assertEquals(new Vector2d(0, 0), v1.opposite());
        assertEquals(new Vector2d(1, 1), v2.opposite());
        assertEquals(new Vector2d(1, 0), v3.opposite());
        assertEquals(new Vector2d(0, 1), v4.opposite());
        assertEquals(new Vector2d(0, -1), v5.opposite());
        assertEquals(new Vector2d(-1, 0), v6.opposite());
        assertEquals(new Vector2d(-1, -1), v7.opposite());
    }


}
