package agh.cs.lab1;

import java.util.ArrayList;

public class OptionsParser {
    public static MoveDirection[] parse(String[] options) {
        ArrayList<MoveDirection> result = new ArrayList<>();
        for (String option : options) {
            MoveDirection moveDirection = parse(option);
            if (moveDirection != null) result.add(moveDirection);
        }
        return result.toArray(new MoveDirection[result.size()]);
    }

    private static MoveDirection parse(String option) { // można użyć switch
        if (option.equals("forward") || option.equals("f")) return MoveDirection.FORWARD;
        if (option.equals("backward") || option.equals("b")) return MoveDirection.BACKWARD;
        if (option.equals("right") || option.equals("r")) return MoveDirection.RIGHT;
        if (option.equals("left") || option.equals("l")) return MoveDirection.LEFT;
        return null;
    }
}
