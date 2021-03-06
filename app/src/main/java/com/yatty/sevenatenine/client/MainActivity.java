package com.yatty.sevenatenine.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private boolean mShouldMusicStay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button logInButton = findViewById(R.id.button_log_in);
        logInButton.setOnClickListener((view) -> {
            Intent logInIntent = LogInActivity.getStartIntent(this);
            startActivity(logInIntent);
            mShouldMusicStay = true;
            finish();
        });

        final Button settingsButton = findViewById(R.id.button_setting);
        settingsButton.setOnClickListener((view) -> {
            Intent settingIntent = SettingsActivity.getStartIntent(this);
            startActivity(settingIntent);
            mShouldMusicStay = true;
        });

        final Button exitButton = findViewById(R.id.button_exit);
        exitButton.setOnClickListener((view) -> {
            mShouldMusicStay = false;
            finish();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (ApplicationSettings.isMusicEnabled(this)) {
            startService(BackgroundMusicService.getIntent(getApplicationContext()));
        }
        View rootView = findViewById(android.R.id.content);
        rootView.setBackground(ApplicationSettings.getBackgroundPicture(this));
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!mShouldMusicStay) {
            stopService(BackgroundMusicService.getIntent(getApplicationContext()));
        }
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}
