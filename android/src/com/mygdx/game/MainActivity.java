package com.mygdx.game;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * First screen which the user see upon opening the application. He/she will have the choice between
 * going to {@link ConnectActivity} and {@link AndroidLauncher}
 *
 * @author A-Muse-D
 */

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button connectButton = findViewById(R.id.connect_button);

        connectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ConnectActivity.class);
                startActivity(intent);
            }
        });

        Button playButton = findViewById(R.id.play_button);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AndroidLauncher.class);
                startActivity(intent);
                finish();
            }
        });
    }

}
