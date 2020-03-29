package com.example.audiovideo;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.VideoView;

import static com.example.audiovideo.R.id.myVideoView;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener {

    private VideoView myVideoView;
    private Button btnPlayVideo;
    private MediaController mediaController;

    private Button btnPlayMusic,btnPauseMusic;

    private MediaPlayer mediaPlayer;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myVideoView = findViewById(R.id.myVideoView);
        btnPlayVideo = findViewById(R.id.btnPlayVideo);

        mediaController = new MediaController(MainActivity.this);

        btnPlayMusic = findViewById(R.id.btnPlay);
        btnPauseMusic = findViewById(R.id.btnPause);

        btnPlayVideo.setOnClickListener(MainActivity.this);
        btnPlayMusic.setOnClickListener(MainActivity.this);
        btnPauseMusic.setOnClickListener(MainActivity.this);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);



        };

    @Override
    public void onClick(View buttonView) {

        switch (buttonView.getId()) {
            case R.id.btnPlayVideo:
                Uri videoUri = Uri.parse("android.resourse://" + getPackageName() + "/" + R.raw.video);

                myVideoView.setVideoURI(videoUri);
                myVideoView.setMediaController(mediaController);
                mediaController.setAnchorView(myVideoView);
                myVideoView.start();
                break;

            case  R.id.btnPlay:
                mediaPlayer.start();
                break;

            case  R.id.btnPause:
                mediaPlayer.pause();
                break;
        }

    }

}

