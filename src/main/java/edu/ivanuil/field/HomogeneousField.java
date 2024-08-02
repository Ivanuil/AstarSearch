package edu.ivanuil.field;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class HomogeneousField implements Field {

    private final double difficulty;

    @Override
    public double getDifficulty(double longitude, double latitude) {
        return difficulty;
    }

}
