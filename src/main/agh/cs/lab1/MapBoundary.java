package agh.cs.lab1;

import java.util.SortedSet;
import java.util.TreeSet;

public class MapBoundary implements IPositionChangeObserver{
    private final SortedSet<IMapElement> xSet;
    private final SortedSet<IMapElement> ySet;
    public MapBoundary(){
        xSet = new TreeSet<>((o1, o2) -> {
            if(o1.getPosition().x != o2.getPosition().x) return o1.getPosition().x - o2.getPosition().x;
            if(o1.getPosition().y != o2.getPosition().y) return o1.getPosition().y - o2.getPosition().y;
            return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
        });
        ySet = new TreeSet<>((o1, o2) -> {
            if(o1.getPosition().y != o2.getPosition().y) return o1.getPosition().y - o2.getPosition().y;
            if(o1.getPosition().x != o2.getPosition().x) return o1.getPosition().x - o2.getPosition().x;
            return o1.getClass().getSimpleName().compareTo(o2.getClass().getSimpleName());
        });
    }

    void addMapElement(IMapElement mapElement){
        xSet.add(mapElement);
        ySet.add(mapElement);
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        if(oldPosition.x != newPosition.x){
            xSet.remove(animal);
            xSet.add(animal);
        }
        if(oldPosition.y != newPosition.y){
            ySet.remove(animal);
            ySet.add(animal);
        }
    }

    public Vector2d getLowerLeft(){
        return new Vector2d(xSet.first().getPosition().x, ySet.first().getPosition().y);
    }

    public Vector2d getUpperRight(){
        return new Vector2d(xSet.last().getPosition().x, ySet.last().getPosition().y);
    }
}
