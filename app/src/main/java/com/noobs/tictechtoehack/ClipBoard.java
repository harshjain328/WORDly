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
        File dir=new File(path);
        dir.mkdirs();

        //Toast.makeText(getApplicationContext(),"Application has started!",Toast.LENGTH_SHORT).show();
        Intent service = new Intent(getApplicationContext(), ClipBoardMonitor.class);
        startService(service);

        Intent log = new Intent(ClipBoard.this, LogList.class);
        startActivity(log);

        finish();
    }
}
