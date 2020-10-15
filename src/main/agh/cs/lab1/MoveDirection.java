package agh.cs.lab1;

public enum MoveDirection {
    FORWARD("f"),
    BACKWARD("b"),
    RIGHT("r"),
    LEFT("l");

    private final String STRING;

    MoveDirection(String string) {
        this.STRING = string;
    }

    @Override
    public String toString() {
        return STRING;
    }
}
