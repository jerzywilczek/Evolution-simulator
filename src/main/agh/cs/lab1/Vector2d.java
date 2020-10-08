package agh.cs.lab1;

import java.util.Objects;

public class Vector2d {
    public final int x, y;
    public Vector2d(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString(){
        return String.format("(%d,%d)", x, y);
    }

    public boolean precedes(Vector2d other){
        if((x <= other.x) && (y <= other.y)) return true;
        return false;
    }

    public boolean follows(Vector2d other){
        if(x >= other.x && y >= other.y) return true;
        return false;
    }

    public Vector2d upperRight(Vector2d other){
        return new Vector2d(Math.max(x, other.x), Math.max(y, other.y));
    }

    public Vector2d lowerLeft(Vector2d other){
        return new Vector2d(Math.min(x, other.x), Math.min(y, other.y));
    }

    public Vector2d add(Vector2d other){
        return new Vector2d(x + other.x, y + other.y);
    }

    public Vector2d subtract(Vector2d other){
        return new Vector2d(x - other.x, y - other.y);
    }

    public Vector2d opposite(){
        return new Vector2d(-x, -y);
    }

    @Override
    public boolean equals(Object other){
        if(this == other) return true;
        if(!(other instanceof Vector2d)) return false;
        Vector2d v = (Vector2d) other;
        if((x == v.x) && (y == v.y)) return true;
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
