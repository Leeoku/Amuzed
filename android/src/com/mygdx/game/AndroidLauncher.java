package com.mygdx.game;

import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionListener;
import com.choosemuse.libmuse.MuseDataListener;
import com.choosemuse.libmuse.MuseDataPacketType;
import com.choosemuse.libmuse.MuseListener;
import com.choosemuse.libmuse.MuseManagerAndroid;
import com.mygdx.game.muse.data.MuseDataStorage;
import com.mygdx.game.muse.listeners.EegDataProcessingReceiver;
import com.mygdx.game.muse.listeners.FocusDataListener;
import com.mygdx.game.muse.listeners.FocusListener;

public class AndroidLauncher extends AndroidApplication implements EegDataProcessingReceiver {

	private MuseManagerAndroid managerAndroid;
	private MuseListener listener;
	private MuseDataListener dataListener;
	private MuseConnectionListener connectionListener;
	private MuseDataStorage museDataStorage;
	private Muse muse;
	private Handler handler;
	MyGdxGame myGdxGame;
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Muse SDK only has a shared library for armeabi-v7a ABI's
		if (Build.SUPPORTED_ABIS[0].equals("armeabi-v7a")) {
			setMuseElements();
		}

		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		myGdxGame = new MyGdxGame();
		((FocusDataListener)dataListener).addEegDataProcessingReceiver(this);
		initialize(myGdxGame, config);
	}

	private void setMuseElements() {

		managerAndroid = MuseManagerAndroid.getInstance();
		managerAndroid.setContext(this);

		if (managerAndroid.getMuses() == null || managerAndroid.getMuses().isEmpty()) {
			showConnectDialog();
		} else {
			handler = new Handler();

			// Set the listeners
			listener = new FocusListener();
			museDataStorage = new MuseDataStorage();
			dataListener = new FocusDataListener(museDataStorage);

			managerAndroid.setMuseListener(listener);

			muse = managerAndroid.getMuses().get(0);

			Log.d(getClass().getSimpleName(), "Muse information: " + muse.getName());
			muse.registerDataListener(dataListener, MuseDataPacketType.EEG);
			muse.runAsynchronously();

		}
	}

	private void showConnectDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.connect_dialog_title);
		builder.setMessage(R.string.connect_dialog_content);
		builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				AndroidLauncher.this.finish();
			}
		});
		builder.show();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		if (muse != null) {
			Toast.makeText(this, R.string.muse_disconnection_toast, Toast.LENGTH_SHORT).show();
            ((FocusDataListener) dataListener).stopListening();
			muse.disconnect();
		}
	}

	@Override
	public void onEegDataProcessed(boolean isUserCalm) {
		myGdxGame.focusing(isUserCalm);
	}
}


