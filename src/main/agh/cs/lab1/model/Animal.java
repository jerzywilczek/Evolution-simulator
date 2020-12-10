package agh.cs.lab1.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Animal implements IMapElement {
    private Vector2d position;
    private MapDirection previousMove;
    private int energy;
    public final long id;
    private final Genome genome;
    private final WorldMap map;
    private final List<IPositionChangeObserver> positionChangeObservers;
    private final List<IEnergyChangeObserver> energyChangeObservers;
    private static long amount = 0;

    public int getEnergy() {
        return energy;
    }

    private Animal(Vector2d position, int startEnergy, Genome genome, WorldMap map) {
        this.map = map;
        this.positionChangeObservers = new LinkedList<>();
        this.energyChangeObservers = new LinkedList<>();
        this.previousMove = MapDirection.randomDirection();
        this.genome = genome;
        this.position = map.modularize(position);
        this.energy = startEnergy;
        this.id = amount++;
    }

    /**
     * Create an animal without parents
     *
     * @param position    starting position
     * @param startEnergy energy animal starts with
     * @param map         map animal exists on
     */
    public Animal(Vector2d position, int startEnergy, WorldMap map) {
        this(position, startEnergy, new Genome(), map);
    }

    /**
     * Breed two parents to create a new animal.
     * This takes some of parents energy for the child and generates a genome out of their genomes.
     * The child exists on the same map as its parents
     *
     * @param position starting position
     * @param parent1  first parent
     * @param parent2  second parent
     */
    public Animal(Vector2d position, Animal parent1, Animal parent2) {
        this(
                position,
                (int) (0.25 * parent1.energy) + (int) (0.25 * parent2.energy),
                new Genome(parent1.genome, parent2.genome),
                parent1.map
        );
        int oldEnergy = parent1.energy;
        int newEnergy = parent1.energy -= (int) (0.25 * parent1.energy);
        parent1.energyChanged(oldEnergy, newEnergy);
        oldEnergy = parent2.energy;
        newEnergy = parent2.energy -= (int) (0.25 * parent2.energy);
        parent2.energyChanged(oldEnergy, newEnergy);
    }

    @Override
    public String toString() {
        return "A";
    }

    @Override
    public Vector2d getPosition() {
        return position;
    }

    public void addPositionChangeObserver(IPositionChangeObserver observer) {
        positionChangeObservers.add(observer);
    }

    public void removePositionChangeObserver(IPositionChangeObserver observer) {
        positionChangeObservers.remove(observer);
    }

    public void addEnergyChangeObserver(IEnergyChangeObserver observer){
        energyChangeObservers.add(observer);
    }

    public void removeEnergyChangeObserver(IEnergyChangeObserver observer){
        energyChangeObservers.remove(observer);
    }

    public void energyChanged(int oldEnergy, int newEnergy){
        energyChangeObservers.forEach(observer -> observer.energyChanged(oldEnergy, newEnergy, this));
    }

    private void positionChanged(Vector2d oldPosition, Vector2d newPosition) {
        positionChangeObservers.forEach(observer -> observer.positionChanged(oldPosition, newPosition, this));
    }

    /**
     * Moves animal in a direction randomly chosen based on genome and previous move direction
     *
     * @param energyLoss how much energy decreases after move
     */
    public void move(int energyLoss) {
        MapDirection direction = previousMove.rotate(genome.chooseRotation());
        Vector2d moveResult = map.modularize(this.position.add(direction.toUnitVector()));
        Vector2d oldPosition = position;
        position = moveResult;
        previousMove = direction;
        positionChanged(oldPosition, position);
        int oldEnergy = energy;
        energy -= energyLoss;
        energyChanged(oldEnergy, energy);
    }

    public void eat(int energyGain){
        int oldEnergy = energy;
        energy += energyGain;
        energyChanged(oldEnergy, energy);
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(!(obj instanceof Animal)) return false;
        return this.id == ((Animal) obj).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
