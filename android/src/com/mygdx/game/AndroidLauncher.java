package com.mygdx.game;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.choosemuse.libmuse.Muse;
import com.choosemuse.libmuse.MuseConnectionListener;
import com.choosemuse.libmuse.MuseDataListener;
import com.choosemuse.libmuse.MuseDataPacketType;
import com.choosemuse.libmuse.MuseListener;
import com.choosemuse.libmuse.MuseManagerAndroid;
import com.mygdx.game.MyGdxGame;
import com.mygdx.game.muse.data.MuseDataStorage;
import com.mygdx.game.muse.listeners.FocusDataListener;
import com.mygdx.game.muse.listeners.FocusListener;

public class AndroidLauncher extends AndroidApplication {

	private MuseManagerAndroid managerAndroid;
	private MuseListener listener;
	private MuseDataListener dataListener;
	private MuseConnectionListener connectionListener;
	private MuseDataStorage museDataStorage;
	private Muse muse;
	private Handler handler;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMuseElements();
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		initialize(new MyGdxGame(), config);
	}

	private void setMuseElements() {

		managerAndroid = MuseManagerAndroid.getInstance();
		managerAndroid.setContext(this);

		if (managerAndroid.getMuses() == null || managerAndroid.getMuses().isEmpty()) {
			showConnectDialog();
		} else {
			handler = new Handler();
			final TextView eeg0 = findViewById(R.id.eeg_0_content);
			final TextView eeg2 = findViewById(R.id.eeg_2_content);

			// Set the listeners
			listener = new FocusListener();
			museDataStorage = new MuseDataStorage();
			dataListener = new FocusDataListener(museDataStorage);

			managerAndroid.setMuseListener(listener);


			muse = managerAndroid.getMuses().get(0);

			Log.d(getClass().getSimpleName(), "Muse information: " + muse.getName());
			muse.registerDataListener(dataListener, MuseDataPacketType.EEG);
			muse.runAsynchronously();

			handler.post(new Runnable() {
				@Override
				public void run() {
					eeg0.setText(Double.toString(museDataStorage.getLastEeg0Value()));
					eeg2.setText(Double.toString(museDataStorage.getLastEeg2Value()));
					handler.postDelayed(this, 1000);
				}
			});
		}
	}

	private void showConnectDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(R.string.connect_dialog_title);
		builder.setMessage(R.string.connect_dialog_content);
		builder.setPositiveButton(R.string.ok_text, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				AndroidLauncher.this.finish();
			}
		});
		builder.show();
	}
}
