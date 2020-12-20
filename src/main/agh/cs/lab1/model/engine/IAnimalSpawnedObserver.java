package agh.cs.lab1.model.engine;

import agh.cs.lab1.model.animal.Animal;

public interface IAnimalSpawnedObserver {
    void animalSpawned(Animal animal);
}
