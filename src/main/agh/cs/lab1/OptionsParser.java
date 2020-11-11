package agh.cs.lab1;

import java.util.Arrays;

public class OptionsParser {
    public MoveDirection[] parse(String[] options) {
        return Arrays
                .stream(options)
                .map(OptionsParser::parse)
                .toArray(MoveDirection[]::new);
    }

    private static MoveDirection parse(String option) throws IllegalArgumentException {
        if (option.equals("forward") || option.equals("f")) return MoveDirection.FORWARD;
        if (option.equals("backward") || option.equals("b")) return MoveDirection.BACKWARD;
        if (option.equals("right") || option.equals("r")) return MoveDirection.RIGHT;
        if (option.equals("left") || option.equals("l")) return MoveDirection.LEFT;
        throw new IllegalArgumentException(option + " is not a valid direction");
    }
}
