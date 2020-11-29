package agh.cs.lab1.model;

public interface IPositionChangeObserver {
//    dodalem pole animal, poniewaz nie wiem jak bez niego aktualizowac pozycje zwierzecia w GrassField
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}