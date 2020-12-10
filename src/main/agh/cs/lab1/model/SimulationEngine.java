package agh.cs.lab1.model;


import agh.cs.lab1.controller.Config;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SimulationEngine {
    private final Config config;
    private final WorldMap map;


    public SimulationEngine() {
        config = new Config();
        map = new WorldMap(config.width, config.height, config.jungleRatio);
        map.getFreePlaces(config.startAnimalAmount).forEach(position -> map.place(new Animal(position, config.startEnergy, map)));
    }

    public void runTurn() {

//        removing dead
        map.getAllAnimals()
                .filter(animal -> animal.getEnergy() <= 0)
                .collect(Collectors.toList())
                .forEach(map::removeAnimal);

//        moving
        map.getAllAnimals().forEach(animal -> animal.move(config.moveEnergy));

//        feeding
        Map<Vector2d, Boolean> plants = map.getPlants();
        map.getStrongestAnimalsGroupedByFields()
                .filter(entry -> plants.get(entry.getKey()))
                .forEach(entry -> {
                    entry.getValue().forEach(animal -> animal.eat(config.plantEnergy / entry.getValue().size()));
                    map.plantEaten(entry.getKey());
                });

//        breeding
        map.getBreedingCandidates()
                .filter(parents -> parents[0].getEnergy() > config.startEnergy / 2 && parents[1].getEnergy() > config.startEnergy / 2)
                .forEach(parents -> map.place(new Animal(map.babySpawnPoint(parents[0].getPosition()), parents[0], parents[1])));

//        growing plants
        map.growPlants();
    }

}
