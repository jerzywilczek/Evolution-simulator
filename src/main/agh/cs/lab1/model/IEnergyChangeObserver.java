package agh.cs.lab1.model;

public interface IEnergyChangeObserver {
    void energyChanged(int oldEnergy, int newEnergy, Animal animal);
}
