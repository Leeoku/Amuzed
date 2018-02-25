package com.mygdx.game.muse.listeners;

import android.os.Handler;
import android.util.Log;

import com.choosemuse.libmuse.Eeg;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseArtifactPacket;
import com.choosemuse.libmuse.MuseDataListener;
import com.choosemuse.libmuse.MuseDataPacket;
import com.choosemuse.libmuse.MuseDataPacketType;
import com.mygdx.game.muse.data.EegProcessingUtil;
import com.mygdx.game.muse.data.MuseDataStorage;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by isaac on 2018-02-25.
 */

public class FocusDataListener extends MuseDataListener {


    public static final int MAX_POLL_SECONDS = 3;

    private static final int EEG_STALE_DELAY_MS = 1000;

    private MuseDataStorage dataStorage;
    private boolean eegStale;
    private boolean firstPacketReceived;
    private Handler handler;
    private List<EegDataProcessingReceiver> receivers;

    public FocusDataListener(MuseDataStorage museDataStorage) {
        dataStorage = museDataStorage;
        handler = new Handler();
        receivers = new ArrayList<>();
    }

    @Override
    public void receiveMuseDataPacket(MuseDataPacket p, Muse muse) {

        if (!firstPacketReceived) {
            firstPacketReceived = true;
            handler.post(changeEegStale());
        }

        if (p.packetType() == MuseDataPacketType.EEG && !eegStale) {
            dataStorage.addToBuffer(p.getEegChannelValue(Eeg.EEG1));
            dataStorage.addToBuffer(p.getEegChannelValue(Eeg.EEG4));
        }
    }

    @Override
    public void receiveMuseArtifactPacket(MuseArtifactPacket p, Muse muse) {
        if (p.getJawClench()) {
            Log.d(getClass().getSimpleName(), "Jaw clench detected!");
        } else if (p.getBlink()) {
            Log.d(getClass().getSimpleName(), "Blink detected");
        }
    }

    public void addEegDataProcessingReceiver(EegDataProcessingReceiver eegDataProcessingReceiver) {
        receivers.add(eegDataProcessingReceiver);
    }

    private Runnable changeEegStale() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                eegStale = true;
                // Add average from samples
                dataStorage.addSecondValue(EegProcessingUtil.processBufferSamples(dataStorage.getBufferValues()));
                Log.d(FocusDataListener.this.getClass().getSimpleName(), "Processed buffer sample: " + EegProcessingUtil.processBufferSamples(dataStorage.getBufferValues()));
                dataStorage.clearBuffer();

                Log.d(FocusDataListener.this.getClass().getSimpleName(), "Sample averages seconds: " + dataStorage.getCurrentAverageSeconds());

                if (dataStorage.getCurrentAverageSeconds() == MAX_POLL_SECONDS) {
                    Log.d(FocusDataListener.this.getClass().getSimpleName(), "Is user calm? " + EegProcessingUtil.processSamplesAverage(dataStorage.getSampleAverages()));

                    // Send the updates to the receivers
                    int size = receivers.size();
                    for (int i = 0; i < size; i++) {
                        boolean userCalm = EegProcessingUtil.processSamplesAverage(dataStorage.getSampleAverages());
                        receivers.get(i).onEegDataProcessed(userCalm);
                    }
                    dataStorage.clearSecondValue();
                }
                eegStale = false;
                handler.postDelayed(changeEegStale(), EEG_STALE_DELAY_MS);
            }

        };
        return (runnable);
    }

    public void stopListening() {
        handler.removeCallbacksAndMessages(null);
    }

}
