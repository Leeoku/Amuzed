package com.mygdx.game.muse.data;

/**
 * Created by isaac on 2018-02-25.
 */

public class EegProcessingUtil {

    private static final int EEG_CALM_THRESHOLD = 80;

    /**
     * Processes the buffer samples from an array of raw EEG data defined on a fixed period of
     * time by averaging it with the total number of samples.
     *
     * @param samples
     * @return
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
     * @param averages
     * @return
     */
    public static boolean processSamplesAverage(double[] averages) {

        // Take the current average value and compare to initial to see if change is calm.
        // We need to define calm as positive or negative???
        // Compare the absolute of difference to threshold
        double difference = averages[averages.length - 1] - averages[0];
        if (difference < 0) {
            double thresholdComparison = Math.abs(difference);
            return (thresholdComparison >= EEG_CALM_THRESHOLD);
        } else {
            return (false);
        }
    }

    public static double findMinInArray(double[] values) {

        double min = 0;

        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                min = values[i];
            }

            if (values[i] < min) {
                min = values[i];
            }
        }
        return (min);
    }


    public static double findMaxInArray(double[] values) {
        double max = 0;

        for (int i = 0; i < values.length; i++) {
            if (i == 0) {
                max = values[i];
            }

            if (values[i] > max) {
                max = values[i];
            }
        }
        return (max);
    }


}
