package com.example.cooltimer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private SeekBar seekBar;
    private boolean isTimerOn;
    private Button button;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        textView = findViewById(R.id.textView);
        button = findViewById(R.id.button);

        seekBar.setMax(600);
        seekBar.setProgress(30);
        isTimerOn = false;

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                long progressInMillis = progress * 1000;
                updateTimer(progressInMillis);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void onClickStart(View view) {

        if (!isTimerOn) {
            button.setText("Stop");
            seekBar.setEnabled(false);
            isTimerOn = true;
            countDownTimer = new CountDownTimer(seekBar.getProgress() * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    updateTimer(millisUntilFinished);
                }

                @Override
                public void onFinish() {
                    MediaPlayer mediaPlayer = MediaPlayer.create(getApplicationContext(),
                            R.raw.bell_sound);
                    mediaPlayer.start();
                    resetTimer();
                }
            };
            countDownTimer.start();
        } else {
            resetTimer();
        }
    }

    private void updateTimer(long millisUntilFinished) {
        int minutes = (int) millisUntilFinished / 1000 / 60;
        int seconds = (int) millisUntilFinished / 1000 - (minutes * 60);

        String minutesString = "";
        String secondsString = "";

        if (minutes < 10) {
            minutesString = "0" + minutes;
        } else {
            minutesString = String.valueOf(minutes);
        }
        if (seconds < 10) {
            secondsString = "0" + seconds;
        } else {
            secondsString = String.valueOf(seconds);
        }
        textView.setText(minutesString + ":" + secondsString);
    }

    private void resetTimer() {
        countDownTimer.cancel();
        textView.setText("00:30");
        button.setText("Start");
        seekBar.setEnabled(true);
        seekBar.setProgress(30);
        isTimerOn = false;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.timer_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent openSettings = new Intent(this, SettingsActivity.class);
            startActivity(openSettings);
        } else if (id == R.id.action_about) {
            Intent openSettings = new Intent(this, AboutActivity.class);
            startActivity(openSettings);
        }

        return super.onOptionsItemSelected(item);
    }
}