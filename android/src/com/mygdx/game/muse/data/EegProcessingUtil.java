package com.mygdx.game.muse.data;

/**
 * Created by isaac on 2018-02-25.
 */

public class EegProcessingUtil {

    /**
     * Alpha relative value used to measure the "calmness" of somebody
     */
    private static final double EEG_CALM_THRESHOLD = 0.25;

    /**
     * Processes the buffer samples from an array of raw EEG data defined on a fixed period of
     * time by averaging it with the total number of samples.
     *
     * @param samples the samples containing the raw EEG data
     * @return the mean value of all the raw EEG data
     */
    public static double processBufferSamples(double[] samples) {

        double total = 0;

        for (int i = 0; i < samples.length; i++) {
            total += samples[i];
        }

        return (total/samples.length);
    }

    /**
     * Processes the average samples by taking the difference of the min and max of them and saying
     * if the difference is considerate based on {@link #EEG_CALM_THRESHOLD}.
     *
     * @param value the average EEG data based from computed samples
     * @return true if the user is considered calm
     */
    public static boolean processSamplesAverage(double value) {
        return (value >= EEG_CALM_THRESHOLD);
    }
}
