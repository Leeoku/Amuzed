package com.mygdx.game.muse.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaac on 2018-02-25.
 */

public class MuseDataStorage {

    private List<Double> buffer;

    public MuseDataStorage() {
        buffer = new ArrayList<>();
    }

    public void addToBuffer(double eegValue) {
        buffer.add(eegValue);
    }

    public void clearBuffer() {
        buffer.clear();
    }

    public double[] getBufferValues() {
        int size = buffer.size();
        double[] values = new double[size];

        for (int i = 0; i < size; i++) {
            values[i] = buffer.get(i);
        }
        return (values);
    }

}
