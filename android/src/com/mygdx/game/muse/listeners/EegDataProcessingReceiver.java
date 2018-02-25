package com.mygdx.game.muse.listeners;

/**
 * Created by isaac on 2018-02-25.
 *
 * Interface in which a user might want to update some of it's data based on EEG processing
 * events.
 *
 */

public interface EegDataProcessingReceiver {
    void onEegDataProcessed(boolean isUserCalm);
}
