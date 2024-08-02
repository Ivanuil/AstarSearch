package edu.ivanuil.field;

import edu.ivanuil.util.Coordinates;

public interface Field {

    double getDifficulty(double longitude, double latitude);

    default double getDifficulty(Coordinates coordinates) {
        return getDifficulty(coordinates.longitude(), coordinates.latitude());
    }

}
