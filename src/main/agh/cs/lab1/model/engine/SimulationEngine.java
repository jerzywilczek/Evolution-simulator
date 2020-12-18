package agh.cs.lab1.model.engine;


import agh.cs.lab1.model.animal.Animal;
import agh.cs.lab1.model.map.Vector2d;
import agh.cs.lab1.model.map.WorldMap;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationEngine {
    private final Config config;
    private final WorldMap map;


    public SimulationEngine() throws IOException{
        config = Config.loadFromJSON("parameters.json");
        map = new WorldMap(config.width, config.height, config.jungleRatio);
        map.getFreePlaces(config.startAnimalAmount).forEach(position -> map.place(new Animal(position, config.startEnergy, map)));
    }

    public void runTurn() {
        removeDeadAnimals();
        moveAnimals();
        feedAnimals();
        breedAnimals();
        map.growPlants();
    }

    public WorldMap getMap() {
        return map;
    }

    private void removeDeadAnimals(){
        map.getAllAnimals()
                .stream()
                .filter(animal -> animal.getEnergy() <= 0)
                .collect(Collectors.toList())
                .forEach(map::removeAnimal);
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
                .forEach(parents -> map.place(new Animal(map.babySpawnPoint(parents[0].getPosition()), parents[0], parents[1])));
    }
}
