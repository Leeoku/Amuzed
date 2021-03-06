package com.mygdx.game.muse.listeners;

import android.util.Log;

import com.choosemuse.libmuse.ConnectionState;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionListener;
import com.choosemuse.libmuse.MuseConnectionPacket;

/**
 *
 * Listener used to determine the state of a Muse device
 *
 * @author A-Muse-D
 *
 */

public class FocusConnectionListener extends MuseConnectionListener {

    public void receiveMuseConnectionPacket(MuseConnectionPacket p, Muse muse) {
        final ConnectionState current = p.getCurrentConnectionState();
        Log.d(getClass().getSimpleName(), "Status: " + p.toString());

        if (current == ConnectionState.CONNECTED) {
            Log.d(getClass().getSimpleName(), "Muse connected");
        } else if (current == ConnectionState.DISCONNECTED) {
            Log.d(getClass().getSimpleName(), "Muse disconnected");
        }
    }

}
