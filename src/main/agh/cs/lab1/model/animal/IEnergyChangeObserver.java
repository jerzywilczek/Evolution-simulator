package agh.cs.lab1.model.animal;

public interface IEnergyChangeObserver {
    void energyChanged(int oldEnergy, int newEnergy, Animal animal);
}
