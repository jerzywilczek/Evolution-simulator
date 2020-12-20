package agh.cs.lab1.model.animal;

public interface IAnimalDiedObserver {
    void animalDied(Animal animal, long deathTurn);
}
