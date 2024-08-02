package edu.ivanuil.util;

public record Rectangle(
        Coordinates leftBottom,
        Coordinates rightTop) {

    public boolean isInside(Coordinates point) {
        double longitude = point.longitude();
        double latitude = point.latitude();
        return longitude > leftBottom().longitude() && latitude > leftBottom().longitude()
                && longitude < rightTop().longitude() && latitude < rightTop().latitude();
    }

}
