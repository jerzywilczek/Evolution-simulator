package agh.cs.lab1.model.engine;


import agh.cs.lab1.model.animal.Animal;
import agh.cs.lab1.model.map.Vector2d;
import agh.cs.lab1.model.map.WorldMap;
import agh.cs.lab1.model.statistics.StatisticsTracker;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationEngine {
    private final Config config;
    private final WorldMap map;
    private final StatisticsTracker statisticsTracker;
    private long turnNumber = 0;
    private final List<IAnimalSpawnedObserver> animalSpawnedObservers = new LinkedList<>();

    public SimulationEngine() throws IOException, JsonSyntaxException, NumberFormatException {
        config = Config.loadFromJSON("parameters.json");
        map = new WorldMap(config.width, config.height, config.jungleRatio);
        map.getFreePlaces(config.startAnimalAmount).forEach(position -> {
            Animal animal = new Animal(position, config.startEnergy, map);
            map.place(animal);
            animalSpawned(animal);
        });
        statisticsTracker = new StatisticsTracker(map, this);
    }

    public void runTurn() {
        turnNumber++;
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        breedAnimals();
        map.growPlants();
        statisticsTracker.updateStatistics();
    }

    public WorldMap getMap() {
        return map;
    }

    public StatisticsTracker getStatisticsTracker() {
        return statisticsTracker;
    }

    private void removeDeadAnimals(){
        map.getAllAnimals()
                .stream()
                .filter(animal -> animal.getEnergy() <= 0)
                .collect(Collectors.toList())
                .forEach(animal -> {
                    animal.die(turnNumber);
                    map.removeAnimal(animal);
                });
    }

    private void moveAnimals(){
        map.getAllAnimals().forEach(animal -> animal.move(config.moveEnergy));
    }

    private void feedAnimals(){
        Map<Vector2d, Boolean> plants = map.getPlants();
        map.getStrongestAnimalsGroupedByFields()
                .stream()
                .filter(entry -> plants.get(entry.getKey()))
                .forEach(entry -> {
                    entry.getValue().forEach(animal -> animal.eat(config.plantEnergy / entry.getValue().size()));
                    map.plantEaten(entry.getKey());
                });
    }

    private void breedAnimals(){
        map.getBreedingCandidates()
                .stream()
                .filter(parents -> parents[0].getEnergy() > config.startEnergy / 2 && parents[1].getEnergy() > config.startEnergy / 2)
                .forEach(parents -> {
                    Animal child = new Animal(map.babySpawnPoint(parents[0].getPosition()), parents[0], parents[1]);
                    map.place(child);
                    animalSpawned(child);
                });
    }

    public void addAnimalSpawnedObserver(IAnimalSpawnedObserver observer){
        animalSpawnedObservers.add(observer);
    }

    public void removeAnimalSpawnedObserver(IAnimalSpawnedObserver observer){
        animalSpawnedObservers.remove(observer);
    }

    private void animalSpawned(Animal animal){
        animalSpawnedObservers.forEach(observer -> observer.animalSpawned(animal));
    }
}
