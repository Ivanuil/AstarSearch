package edu.ivanuil.util;

import org.jetbrains.annotations.NotNull;

public record Coordinates(
        double longitude,
        double latitude) {

    public double getDistance(@NotNull Coordinates secondPoint) {
        double longitudeDistance = longitude - secondPoint.longitude;
        double latitudeDistance = latitude - secondPoint.latitude;
        return Math.sqrt(longitudeDistance * longitudeDistance + latitudeDistance * latitudeDistance);
    }

    public Coordinates add(@NotNull Coordinates secondPoint) {
        return new Coordinates(
                this.longitude() + secondPoint.longitude(),
                this.latitude() + secondPoint.latitude());
    }

    public Coordinates subtract(@NotNull Coordinates secondPoint) {
        return new Coordinates(
                this.longitude() - secondPoint.longitude(),
                this.latitude() - secondPoint.latitude());
    }

}
