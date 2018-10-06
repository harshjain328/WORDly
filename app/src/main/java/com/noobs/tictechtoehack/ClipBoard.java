package com.noobs.tictechtoehack;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.noobs.tictechtoehack.service.ClipBoardMonitor;

import java.io.File;

public class ClipBoard extends AppCompatActivity {

    public final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wordly";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clip_board);
        
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {
            File dir=new File(path);
            dir.mkdirs();
            Intent service = new Intent(getApplicationContext(), ClipBoardMonitor.class);
            startService(service);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
            finish();
        }

        Intent log = new Intent(ClipBoard.this, LogList.class);
        startActivity(log);

        finish();
    }
}
