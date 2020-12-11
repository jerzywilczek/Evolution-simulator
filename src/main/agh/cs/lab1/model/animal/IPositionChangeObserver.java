package agh.cs.lab1.model.animal;

import agh.cs.lab1.model.map.Vector2d;

public interface IPositionChangeObserver {
    void positionChanged(Vector2d oldPosition, Vector2d newPosition, Animal animal);
}
