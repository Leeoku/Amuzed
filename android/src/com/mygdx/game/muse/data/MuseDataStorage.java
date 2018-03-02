package com.mygdx.game.muse.data;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Class used as a EEG data buffer
 *
 * @author A-Muse-D
 *
 */

public class MuseDataStorage {

    private List<Double> buffer;

    /**
     * Default constructor
     */
    public MuseDataStorage() {
        buffer = new ArrayList<>();
    }

    /**
     * Adds a value to the buffer
     *
     * @param eegValue the EEG value to the buffer
     */
    public void addToBuffer(double eegValue) {
        buffer.add(eegValue);
    }

    /**
     * Removes all the elements from the buffer and resets it
     */
    public void clearBuffer() {
        buffer.clear();
    }

    /**
     * Gets the values that has been accumulated in the buffer
     *
     * @return an array of all the values contained in the buffer
     */
    public double[] getBufferValues() {
        int size = buffer.size();
        double[] values = new double[size];

        for (int i = 0; i < size; i++) {
            values[i] = buffer.get(i);
        }
        return (values);
    }

}
