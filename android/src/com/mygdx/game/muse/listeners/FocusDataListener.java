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

    private static final int EEG_STALE_DELAY_MS = 1000;

    private MuseDataStorage dataStorage;
    private boolean alphaStale;
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
            handler.post(changeAlphaStale());
        }

        if (p.packetType() == MuseDataPacketType.ALPHA_RELATIVE && !alphaStale) {
            dataStorage.addToBuffer(p.getEegChannelValue(Eeg.EEG1));
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

    private Runnable changeAlphaStale() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                alphaStale = true;
                Double alpha = EegProcessingUtil.processBufferSamples(dataStorage.getBufferValues());
                Log.d(getClass().getSimpleName(), "alpha: " + alpha);
                dataStorage.clearBuffer();

                // Send the updates to the receivers
                int size = receivers.size();
                for (int i = 0; i < size; i++) {
                    boolean userCalm = EegProcessingUtil.processSamplesAverage(alpha);
                    receivers.get(i).onEegDataProcessed(userCalm);
                }

                alphaStale = false;
                handler.postDelayed(changeAlphaStale(), EEG_STALE_DELAY_MS);
            }

        };
        return (runnable);
    }

    public void stopListening() {
        handler.removeCallbacksAndMessages(null);
    }

}
