package agh.cs.lab1;

import java.util.*;
import java.util.stream.Collectors;

import static java.lang.Math.*;

public class GrassField extends AbstractWorldMap {
    private int grassAmount;
    private List<Grass> grassList;
    private int grassBound;

//    Wedlug mnie dodawanie interfejsu IMapElement lub klasy AbstractWordlMapElement na tym etapie projektu (z obecnym kodem i bez wiedzy o tym co ma byc dodane pozniej) tylko utrudniloby implementacje.

    /**
     * @param grassAmount - amount of grass on the map
     */
    public GrassField(int grassAmount) {
        super();
        this.grassAmount = grassAmount;
        grassBound = (int) sqrt(grassAmount * 10);

//        gen distinct random grass positions as ints until we have the specified amount
        Set<Integer> set = new HashSet<>();
        Random random = new Random();
        while (set.size() < grassAmount) {
            set.add(random.nextInt(grassBound * grassBound));
        }

//        convert ints into Grass
        grassList = new ArrayList<>(
                set.stream()
                        .map(i -> new Grass(new Vector2d(i % grassBound, i / grassBound)))
                        .collect(Collectors.toList())
        );
    }

    @Override
    public boolean canMoveTo(Vector2d position) {
        return !(objectAt(position) instanceof Animal);
    }

    @Override
    public Object objectAt(Vector2d position) {
        Object result = super.objectAt(position);
        if (result == null) result = grassList.stream()
                .filter(grass -> grass.getPosition().equals(position))
                .findAny().orElse(null);
        return result;
    }

    @Override
    public Vector2d getLowerLeft() {
        int minX = animals.stream()
                .map(animal -> animal.getPosition().x)
                .min(Integer::compareTo).orElse(0);
        int minY = animals.stream()
                .map(animal -> animal.getPosition().y)
                .min(Integer::compareTo).orElse(0);
        return new Vector2d(min(0, minX), min(0, minY));
    }

    @Override
    public Vector2d getUpperRight() {
        int maxX = animals.stream()
                .map(animal -> animal.getPosition().x)
                .max(Integer::compareTo).orElse(0);
        int maxY = animals.stream()
                .map(animal -> animal.getPosition().y)
                .max(Integer::compareTo).orElse(0);
        return new Vector2d(max(maxX, grassBound), max(maxY, grassBound));
    }
}
