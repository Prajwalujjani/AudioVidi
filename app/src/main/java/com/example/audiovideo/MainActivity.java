package com.example.audiovideo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Timer;
import java.util.TimerTask;

import static com.example.audiovideo.R.id.myVideoView;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener, SeekBar.OnSeekBarChangeListener, MediaPlayer.OnCompletionListener {
//   UI components
    private VideoView myVideoView;
    private Button btnPlayVideo;
    private MediaController mediaController;

    private Button btnPlayMusic,btnPauseMusic;

    private MediaPlayer mediaPlayer;

    private SeekBar seekBarVolume,seekBarMoveBackAndForth;
    private AudioManager audioManager;
    private Timer timer;




    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myVideoView = findViewById(R.id.myVideoView);
        btnPlayVideo = findViewById(R.id.btnPlayVideo);


        mediaController = new MediaController(MainActivity.this);

        btnPlayMusic = findViewById(R.id.btnPlay);
        btnPauseMusic = findViewById(R.id.btnPause);
        seekBarVolume = findViewById(R.id.seekBarVolume);
        seekBarMoveBackAndForth = findViewById(R.id.seekBarMove);

        btnPlayVideo.setOnClickListener(MainActivity.this);
        btnPlayMusic.setOnClickListener(MainActivity.this);
        btnPauseMusic.setOnClickListener(MainActivity.this);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);

        //For volume change using seek bar
        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);
        int maximumVolumeOfUserDevice = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        seekBarVolume.setMax(maximumVolumeOfUserDevice);
        seekBarVolume.setProgress(currentVolume);

        //  When user simply tapped on seekBar volume going to change
        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if (fromUser){
                   // Toast.makeText(MainActivity.this, Integer.toString(progress), Toast.LENGTH_SHORT).show();
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarMoveBackAndForth.setOnSeekBarChangeListener(this); //to progress duration of music
        seekBarMoveBackAndForth.setMax(mediaPlayer.getDuration()); // idu music yav time alli odtide anta set madodu
        // and max duration ge karkond ogalla

        mediaPlayer.setOnCompletionListener(this);

        };

    @Override
    public void onClick(View buttonView) {

        switch (buttonView.getId()) {
            case R.id.btnPlayVideo:
                Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video);

                myVideoView.setVideoURI(videoUri);
                myVideoView.setMediaController(mediaController);
                mediaController.setAnchorView(myVideoView);
                myVideoView.start();
                break;

            case  R.id.btnPlay:
                mediaPlayer.start();
                timer = new Timer();
                timer.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        seekBarMoveBackAndForth.setProgress(mediaPlayer.getCurrentPosition());
                    } // it is used for display current time of the music
                }, 0, 1000);
                break;

            case  R.id.btnPause:
                mediaPlayer.pause();
                timer.cancel();  //When the music is pause thread will stop so it will reduce battery comsumption

                break;
        }

    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        if(fromUser){
           // Toast.makeText(this,Integer.toString(progress), Toast.LENGTH_SHORT).show();
        mediaPlayer.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

        mediaPlayer.pause(); //seek Bar duration change madbekadre music stop agutte

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

        mediaPlayer.start(); //when we stop interacting with the seekBar

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
      timer.cancel();
      Toast.makeText(this, "Music is ended",Toast.LENGTH_SHORT).show();
    }
}

