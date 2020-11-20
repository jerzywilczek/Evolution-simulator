package agh.cs.lab1;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertThrows;

public class OptionsParserTest {
    @Test
    public void parseTest() {
        String[] s1 = {"n", "s", "se", "nw"};
        MapDirection[] r1 = {MapDirection.NORTH, MapDirection.SOUTH, MapDirection.SOUTHEAST, MapDirection.NORTHWEST};

        String[] s2 = {"abc", "f", "def", "backward", "123"};

        String[] s3 = {"forward", "BACKWARD", "\n", "324", "l", "r"};

        OptionsParser parser = new OptionsParser();

        assertArrayEquals(r1, parser.parse(s1));
        assertThrows(IllegalArgumentException.class, () -> parser.parse(s2));
        assertThrows(IllegalArgumentException.class, () -> parser.parse(s3));
    }
}
