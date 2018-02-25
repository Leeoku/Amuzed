package com.mygdx.game.muse.data;

import com.mygdx.game.muse.listeners.FocusDataListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaac on 2018-02-25.
 */

public class MuseDataStorage {


    private List<Double> buffer;
    private double[] roundedValueSeconds;
    private int seconds;

    public MuseDataStorage() {
        buffer = new ArrayList<>();
        roundedValueSeconds = new double[FocusDataListener.MAX_POLL_SECONDS];
    }

    public void addToBuffer(double eegValue) {
        buffer.add(eegValue);
    }

    public void clearBuffer() {
        buffer.clear();
    }

    public void addSecondValue(double eegValue) {
        roundedValueSeconds[seconds] = eegValue;
        seconds++;
    }

    public void clearSecondValue() {
        roundedValueSeconds = new double[FocusDataListener.MAX_POLL_SECONDS];
        seconds = 0;
    }

    public int getCurrentAverageSeconds() {
        return (seconds);
    }

    public double[] getSampleAverages() {
        return (roundedValueSeconds);
    }

    public double getLastEeg0Value() {
        return (!buffer.isEmpty() ? buffer.get(buffer.size() - 2) : 0);
    }

    public double getLastEeg2Value() {
        return (!buffer.isEmpty() ? buffer.get(buffer.size() - 1) : 0);
    }

    public int getBufferSize() {
        return (buffer.size());
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
