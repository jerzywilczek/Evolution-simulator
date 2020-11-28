package agh.cs.lab1.model;

import java.util.Arrays;

public class OptionsParser {
    public MapDirection[] parse(String[] options) {
        return Arrays
                .stream(options)
                .map(OptionsParser::parse)
                .toArray(MapDirection[]::new);
    }

    private static MapDirection parse(String option) throws IllegalArgumentException {
        return switch (option) {
            case "n" -> MapDirection.NORTH;
            case "ne" -> MapDirection.NORTHEAST;
            case "e" -> MapDirection.EAST;
            case "se" -> MapDirection.SOUTHEAST;
            case "s" -> MapDirection.SOUTH;
            case "sw" -> MapDirection.SOUTHWEST;
            case "w" -> MapDirection.WEST;
            case "nw" -> MapDirection.NORTHWEST;
            default -> throw new IllegalArgumentException(option + " is not a valid direction");
        };
    }
}
