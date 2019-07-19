package com.example.mini_douyin2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.Arrays;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class RecordVideoActivity extends AppCompatActivity {

    private VideoView videoView;
    private String[] PermissionsArray = new String[]{CAMERA,WRITE_EXTERNAL_STORAGE,RECORD_AUDIO};
    private int[] grantResults = new int[]{};
    private static final int REQUEST_VIDEO_CAPTURE = 1;
    private static final int REQUEST_PERMISSION =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);

        videoView = findViewById(R.id.video);

        findViewById(R.id.bt_to_upload).setOnClickListener(view -> {
            startActivity(new Intent(RecordVideoActivity.this, UploadActivity.class));
        });

        findViewById(R.id.bt_take_video).setOnClickListener(v -> {
            Log.d("bt","click bt_take_video");
            /*
            if (ContextCompat.checkSelfPermission(RecordVideoActivity.this,
                    Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(RecordVideoActivity.this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED||
                    ContextCompat.checkSelfPermission(RecordVideoActivity.this,
                    Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
            ) {
                Log.d("permission","click check_permission");
                if (!checkPermissionAllGranted(PermissionsArray)) {
                    Log.d("permission","click not_all_permission");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        Log.d("permission","click SDK bigger");
                        requestPermissions(PermissionsArray, REQUEST_PERMISSION);

                    }
                }
                onRequestPermissionsResult(REQUEST_PERMISSION, PermissionsArray, grantResults);
            }
            */
                //todo 打开相机拍摄
                recordVideo();

        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (videoView.isPlaying()){
                    videoView.pause();
                }
                else {
                    videoView.start();
                }
                return false;
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (requestCode == REQUEST_VIDEO_CAPTURE && resultCode == RESULT_OK) {
            //todo 播放刚才录制的视频
            Uri videoUri = intent.getData();
            videoView.setVideoURI(videoUri);
            videoView.start();

        }
    }


    /*
    private boolean checkPermissionAllGranted(String[] permissions) {
        // 6.0以下
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        for (String permission : permissions) {
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false

                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1){
            Toast.makeText(this, "已授权" + Arrays.toString(permissions), Toast.LENGTH_LONG).show();
        }
    }
    */

    private void recordVideo(){
        Intent recordVideoIntent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if (recordVideoIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(recordVideoIntent, REQUEST_VIDEO_CAPTURE);
        }
    }


}
