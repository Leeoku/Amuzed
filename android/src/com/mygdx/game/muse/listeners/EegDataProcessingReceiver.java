package com.mygdx.game.muse.listeners;

/**
 *
 * Interface in which a user might want to update some of it's data based on EEG processing
 * events.
 *
 * @author A-Muse-D
 */

public interface EegDataProcessingReceiver {
    /**
     * Callback used when the EEG data has been processed
     *
     * @param isUserCalm is true if the user is calm.
     */
    void onEegDataProcessed(boolean isUserCalm);
}
