package agh.cs.lab1;

import java.util.*;

import static java.lang.Math.*;

public class GrassField extends AbstractWorldMap {
    private final int grassAmount;
    private final int grassBound;
    private final Map<Vector2d, Grass> grassMap;
    private final MapBoundary mapBoundary;

//    Wedlug mnie dodawanie interfejsu IMapElement lub klasy AbstractWordlMapElement na tym etapie projektu (z obecnym kodem i bez wiedzy o tym co ma byc dodane pozniej) tylko utrudniloby implementacje.

    /**
     * @param grassAmount - amount of grass on the map
     */
    public GrassField(int grassAmount) {
        super();
        mapBoundary = new MapBoundary();
        this.grassAmount = grassAmount;
        grassBound = (int) sqrt(grassAmount * 10);

//        gen distinct random grass positions as ints until we have the specified amount
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        while (set.size() < grassAmount) {
            set.add(random.nextInt(grassBound * grassBound));
        }

//        convert ints into Grass
        grassMap = new HashMap<>();
        set.stream()
                .map(i -> new Grass(new Vector2d(i % grassBound, i / grassBound)))
                .forEach(grass -> {
                    grassMap.put(grass.getPosition(), grass);
                    mapBoundary.addMapElement(grass);
                });
    }

    @Override
    public boolean place(Animal animal) throws IllegalArgumentException {
        mapBoundary.addMapElement(animal);
        return super.place(animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object result = super.objectAt(position);
        if (result == null) result = grassMap.get(position);
        return result;
    }

    @Override
    public Vector2d getLowerLeft() {
        return mapBoundary.getLowerLeft();
    }

    @Override
    public Vector2d getUpperRight() {
        return mapBoundary.getUpperRight();
    }
}
