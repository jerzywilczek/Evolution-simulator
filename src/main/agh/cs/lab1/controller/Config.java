package agh.cs.lab1.controller;

public class Config {
    public final int width, height, startEnergy, moveEnergy, plantEnergy, startAnimalAmount;
    public final double jungleRatio;
    public Config(){
        width = 200;
        height = 100;
        startEnergy = 10;
        moveEnergy = 1;
        plantEnergy = 5;
        jungleRatio = 0.1;
        startAnimalAmount = 10;
    }
}
