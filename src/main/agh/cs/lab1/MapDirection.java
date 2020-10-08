package agh.cs.lab1;

import javax.sound.midi.Soundbank;

public enum MapDirection {
    NORTH,
    SOUTH,
    WEST,
    EAST;

    @Override
    public String toString(){
        switch(this){
            case NORTH:
                return "Polnoc";
            case SOUTH:
                return "Poludnie";
            case EAST:
                return "Wschod";
            case WEST:
                return "Zachod";
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public MapDirection next(){
        switch(this){
            case NORTH:
                return EAST;
            case EAST:
                return SOUTH;
            case SOUTH:
                return WEST;
            case WEST:
                return NORTH;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public MapDirection previous(){
        switch(this){
            case NORTH:
                return WEST;
            case EAST:
                return NORTH;
            case SOUTH:
                return EAST;
            case WEST:
                return SOUTH;
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }

    public Vector2d toUnitVector(){
        switch (this){
            case NORTH:
                return new Vector2d(0, 1);
            case EAST:
                return new Vector2d(1, 0);
            case WEST:
                return new Vector2d(-1, 0);
            case SOUTH:
                return new Vector2d(0, -1);
            default:
                throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}
