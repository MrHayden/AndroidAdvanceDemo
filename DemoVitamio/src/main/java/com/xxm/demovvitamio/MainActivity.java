package com.xxm.demovvitamio;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        (!LibsChecker.checkVitamioLibs(this));
        setContentView(R.layout.activity_main);

        initView();
        initData();
    }

    private void initView() {
        videoView = findViewById(R.id.video_view);
    }

    private void initData() {
        videoView.setVideoPath("http://gslb.miaopai.com/stream/3D~8BM-7CZqjZscVBEYr5g__.mp4");//设置播放地址
        videoView.setMediaController(new MediaController(this));//绑定控制器
        videoView.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//设置播放画质 高画质
        videoView.requestFocus();////取得焦点
        //视频播放器的准备,此时播放器已经准备好了,此处可以设置一下播放速度,播放位置等等
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setPlaybackSpeed(1.0f);
            }
        });
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.seekTo(0);   //转到第一帧
                mp.start();     //开始播放
            }
        });
    }
}
