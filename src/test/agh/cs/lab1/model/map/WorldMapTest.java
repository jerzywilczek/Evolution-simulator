package agh.cs.lab1.model.map;

import agh.cs.lab1.model.animal.Animal;
import org.junit.Before;
import org.junit.Test;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class WorldMapTest {

    WorldMap map;
    List<Animal> animals;

    @Before
    public void setup() {
        map = new WorldMap(5, 5, 0.2);
        animals = List.of(
                new Animal(new Vector2d(0, 0), 2, map),
                new Animal(new Vector2d(0, 0), 3, map),
                new Animal(new Vector2d(0, 0), 3, map),
                new Animal(new Vector2d(5, 5), 3, map),
                new Animal(new Vector2d(1, 1), 3, map),
                new Animal(new Vector2d(1, 1), 4, map),
                new Animal(new Vector2d(1, 2), 4, map),
                new Animal(new Vector2d(2, 2), 0, map),
                new Animal(new Vector2d(2, 2), 0, map),
                new Animal(new Vector2d(2, 2), 0, map),
                new Animal(new Vector2d(2, 2), 0, map),
                new Animal(new Vector2d(2, 2), 0, map),
                new Animal(new Vector2d(2, 2), 0, map)
        );
        animals.forEach(map::place);
    }

    @Test
    public void testGetAllAnimals() {
        int[] expected = animals.stream()
                .mapToInt(Animal::hashCode)
                .sorted()
                .toArray();
        int[] actual = map.getAllAnimals()
                .stream()
                .mapToInt(Animal::hashCode)
                .sorted()
                .toArray();
        assertArrayEquals(expected, actual);
    }

    @Test
    public void testGetStrongestAnimalsGroupedByFields() {
        Map<Vector2d, List<Animal>> result = map
                .getStrongestAnimalsGroupedByFields()
                .stream()
                .collect(Collectors.toMap(
                        AbstractMap.SimpleEntry::getKey,
                        AbstractMap.SimpleEntry::getValue
                ));
        assertEquals(3, result.get(new Vector2d(0, 0)).size());
        assertEquals(1, result.get(new Vector2d(1, 1)).size());
        assertEquals(1, result.get(new Vector2d(1, 2)).size());
        assertEquals(6, result.get(new Vector2d(2, 2)).size());
    }

    @Test
    public void testGetBreedingCandidates() {
        List<Animal[]> result = map.getBreedingCandidates();
        result.stream().mapToInt(a -> a.length).forEach(len -> assertEquals(2, len));
        assertEquals(3, result.size());
    }

    @Test
    public void testBabySpawnPoint() {
        Set<Vector2d> possiblePositions = Set.of(
                new Vector2d(4, 1),
                new Vector2d(4, 0),
                new Vector2d(4, 4),
                new Vector2d(0, 1),
                new Vector2d(0, 4),
                new Vector2d(1, 1),
                new Vector2d(1, 0),
                new Vector2d(1, 4)
        );
        IntStream.range(0, 20)
                .mapToObj(i -> map.babySpawnPoint(new Vector2d(0, 0)))
                .forEach(vector -> assertTrue(possiblePositions.contains(vector)));
    }

    @Test
    public void testRemovingDead(){
        map.getAllAnimals()
                .stream()
                .filter(animal -> animal.getEnergy() <= 0)
                .collect(Collectors.toList())
                .forEach(map::removeAnimal);
        assertEquals(7, map.getAllAnimals().size());
    }

    @Test
    public void testAnimalMoving(){
        map.getAllAnimals().forEach(animal -> animal.move(0));
        assertEquals(animals.size(), map.getAllAnimals().size());
    }
}