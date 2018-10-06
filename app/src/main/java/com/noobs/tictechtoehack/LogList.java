package com.noobs.tictechtoehack;

import android.content.Intent;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.noobs.tictechtoehack.models.DataModel;
import com.noobs.tictechtoehack.service.ClipBoardMonitor;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class LogList extends AppCompatActivity {

    ArrayList<DataModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    public final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wordly";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Logs");
        setSupportActionBar(toolbar);

        Intent service = new Intent(getApplicationContext(), ClipBoardMonitor.class);
        startService(service);

        listView=(ListView)findViewById(R.id.list);

        dataModels = new ArrayList<>();
        File file = new File(path+"/logs.txt");
        String[] loadText = Load(file);

        for(int i=0; i<loadText.length; i++){
            dataModels.add(new DataModel(loadText[i]));
        }

        adapter= new CustomAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                DataModel dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getName(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

    public String[] Load(File file){
        FileInputStream fis = null;
        try{
            fis = new FileInputStream(file);
        }catch (Exception e){
            e.printStackTrace();
        }
        InputStreamReader isr = new InputStreamReader(fis);
        BufferedReader br = new BufferedReader(isr);

        String test;
        int count=0;

        try{
            while((test = br.readLine()) != null){
                count++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        try{
            fis.getChannel().position(0);
        }catch (Exception e){
            e.printStackTrace();
        }

        String[] arr=new String[count];
        String line;
        int i=0;

        try{
            while((line = br.readLine()) != null){
                arr[i] = line;
                i++;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return arr;
    }
}
