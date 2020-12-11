package agh.cs.lab1.model.map;

import agh.cs.lab1.model.animal.Animal;
import agh.cs.lab1.model.animal.IEnergyChangeObserver;
import agh.cs.lab1.model.animal.IPositionChangeObserver;
import org.jetbrains.annotations.NotNull;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class WorldMap implements IEnergyChangeObserver, IPositionChangeObserver {
    private final int width;
    private final int height;
    private final Vector2d jungleLowerLeft;
    private final Vector2d jungleUpperRight;
    private final Map<Vector2d, NavigableSet<AnimalSortingEntry>> animals;
    private final Map<Vector2d, Boolean> plants;
    private int junglePlants;
    private int nonJunglePlants;

    private static class AnimalSortingEntry implements Comparable<AnimalSortingEntry> {
        public final int energy;
        public final Animal animal;

        public AnimalSortingEntry(int energy, Animal animal) {
            this.energy = energy;
            this.animal = animal;
        }

        @Override
        public int compareTo(@NotNull WorldMap.AnimalSortingEntry other) {
            if (energy != other.energy) return Integer.compare(energy, other.energy);
            return Long.compare(animal.id, other.animal.id);
        }
    }

    public WorldMap(int width, int height, double jungleRatio) {
        this.width = width;
        this.height = height;
        jungleLowerLeft = new Vector2d((int) (width * (1 - jungleRatio) / 2), (int) (height * (1 - jungleRatio) / 2));
        jungleUpperRight = new Vector2d((int) (width * (1 + jungleRatio) / 2), (int) (height * (1 + jungleRatio) / 2));
        junglePlants = nonJunglePlants = 0;

        animals = new HashMap<>();
        IntStream.range(0, width)
                .forEach(x -> IntStream.range(0, height)
                        .forEach(y -> animals.put(new Vector2d(x, y), new TreeSet<>()))
                );

        plants = new HashMap<>();
        IntStream.range(0, width)
                .forEach(x -> IntStream.range(0, height)
                        .forEach(y -> plants.put(new Vector2d(x, y), false))
                );
    }

    public Map<Vector2d, NavigableSet<AnimalSortingEntry>> getAnimals() {
        return Collections.unmodifiableMap(animals);
    }

    /**
     * Returns a vector that fits on the map (in case the argument doesn't fit on the map it gets wrapped to the other side)
     *
     * @param vector a vector to be modularized
     * @return vector that is guaranteed to fit on the map
     */
    public Vector2d modularize(Vector2d vector) {
        return new Vector2d(
                ((vector.x % width) + width) % width,
                ((vector.y % height) + height) % height
        );
    }

    public void place(Animal animal) {
        animals.get(animal.getPosition()).add(new AnimalSortingEntry(animal.getEnergy(), animal));
        animal.addEnergyChangeObserver(this);
        animal.addPositionChangeObserver(this);
    }

    @Override
    public void energyChanged(int oldEnergy, int newEnergy, Animal animal) {
        animals.get(animal.getPosition()).remove(new AnimalSortingEntry(oldEnergy, animal));
        animals.get(animal.getPosition()).add(new AnimalSortingEntry(newEnergy, animal));
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal) {
        animals.get(oldPosition).remove(new AnimalSortingEntry(animal.getEnergy(), animal));
        animals.get(newPosition).add(new AnimalSortingEntry(animal.getEnergy(), animal));
    }

    /**
     * @param amount amount of items in the stream
     * @return stream of vectors pointing to free places on the map
     */
    public Stream<Vector2d> getFreePlaces(int amount) {
        List<Vector2d> list = animals.keySet()
                .parallelStream()
                .filter(key -> animals.get(key).isEmpty())
                .collect(Collectors.toList());
        Collections.shuffle(list);
        return list.subList(0, amount).stream();
    }

    /**
     * Gets all animals currently on the map
     * @return parallel stream containing all animals on the map
     */
    public Stream<Animal> getAllAnimals() {
        return animals
                .values()
                .parallelStream()
                .flatMap(SortedSet<AnimalSortingEntry>::stream)
                .map(animalSortingEntry -> animalSortingEntry.animal);
    }

    /**
     * Returns animals with highest energy grouped by fields
     * @return a parallel stream of entries, where <code>Entry.getKey()</code> contains a vector representing a certain field and <code>entry.getValue()</code> contains a list of animals
     */
    public Stream<AbstractMap.SimpleEntry<Vector2d, List<Animal>>> getStrongestAnimalsGroupedByFields() {
        return animals.entrySet().parallelStream()
                .filter(entry -> !entry.getValue().isEmpty())
                .map(entry -> new AbstractMap.SimpleEntry<>(
                                entry.getKey(),
                                entry.getValue().stream()
                                        .dropWhile(animalSortingEntry -> animalSortingEntry.energy < entry.getValue().last().energy)
                                        .map(animalSortingEntry -> animalSortingEntry.animal)
                                        .collect(Collectors.toList())
                        )
                );
    }

    public Stream<Animal[]> getBreedingCandidates() {
        return animals.values().parallelStream()
                .filter(set -> set.size() >= 2)
                .map(set -> {
//                    case 1: there are at least two animals with the highest energy
                    List<Animal> list = set.stream()
                            .dropWhile(animalSortingEntry -> animalSortingEntry.energy < set.last().energy)
                            .map(animalSortingEntry -> animalSortingEntry.animal)
                            .collect(Collectors.toList());
                    if (list.size() >= 2) {
                        Collections.shuffle(list);
                        return list.subList(0, 2).toArray(new Animal[2]);
                    }
//                    case 2: there is one animal with the highest energy
                    Iterator<AnimalSortingEntry> it = set.descendingIterator();
                    Animal a1 = it.next().animal;
                    List<AnimalSortingEntry> tempList = new LinkedList<>();
                    tempList.add(it.next());
                    while(it.hasNext()){
                        AnimalSortingEntry entry = it.next();
                        if(entry.animal.getEnergy() < tempList.get(0).animal.getEnergy()) break;
                        tempList.add(entry);
                    }
                    Collections.shuffle(tempList);
                    Animal a2 = tempList.get(0).animal;
                    return new Animal[]{a1, a2};
                });
    }

    public Vector2d babySpawnPoint(Vector2d parentsPosition) {
        List<Vector2d> possibleFields = IntStream.range(-1, 2).mapToObj(x ->
                IntStream.range(-1, 2).mapToObj(y ->
                        new Vector2d(x, y)
                ))
                .flatMap(v -> v)
                .map(v -> modularize(parentsPosition.add(v)))
                .filter(v -> !parentsPosition.equals(v))
                .collect(Collectors.toList());

        List<Vector2d> filteredFields = possibleFields.stream()
                .filter(v -> animals.get(v).isEmpty())
                .collect(Collectors.toList());

        if (filteredFields.size() > 0) {
            Collections.shuffle(filteredFields);
            return filteredFields.get(0);
        }
        Collections.shuffle(possibleFields);
        return possibleFields.get(0);
    }

    public void plantEaten(Vector2d position) {
        plants.put(position, false);
        if (position.follows(jungleLowerLeft) && position.precedes(jungleUpperRight)) junglePlants++;
        else nonJunglePlants++;
    }

    public void growPlants() {
        Random random = new Random();
        int x, y;
        int jungleArea = (jungleUpperRight.x - jungleLowerLeft.x) * (jungleUpperRight.y - jungleLowerLeft.y);
        if (nonJunglePlants < width * height - jungleArea) {
            do {
                x = random.nextInt(width);
                if (x < jungleLowerLeft.x && x > jungleUpperRight.x) {
                    y = random.nextInt(height);
                } else {
                    if (random.nextBoolean()) {
                        y = random.nextInt(jungleLowerLeft.y);
                    } else {
                        y = random.nextInt(jungleLowerLeft.y) + jungleUpperRight.y;
                    }
                }
            } while (plants.get(new Vector2d(x, y)));
            plants.put(new Vector2d(x, y), true);
            nonJunglePlants++;
        }
        if (junglePlants < jungleArea) {
            do {
                x = random.nextInt(jungleUpperRight.x - jungleLowerLeft.x) + jungleLowerLeft.x;
                y = random.nextInt();
            } while (plants.get(new Vector2d(x, y)));
            plants.put(new Vector2d(x, y), true);
            junglePlants++;
        }
    }

    public void removeAnimal(Animal animal) {
        animals.get(animal.getPosition()).remove(new AnimalSortingEntry(animal.getEnergy(), animal));
    }

    public Map<Vector2d, Boolean> getPlants() {
        return Collections.unmodifiableMap(plants);
    }
}
