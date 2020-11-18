package agh.cs.lab1;

public class Grass implements IMapElement{
    private final Vector2d position;

    public Grass(Vector2d position){
        this.position = position;
    }

    @Override
    public String toString(){
        return "*";
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }
}
