package com.noobs.tictechtoehack.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.noobs.tictechtoehack.ClipBoard;
import com.noobs.tictechtoehack.R;
import com.noobs.tictechtoehack.models.Meaning;
import com.noobs.tictechtoehack.models.Word;
import com.noobs.tictechtoehack.rest.ApiClient;
import com.noobs.tictechtoehack.rest.ApiInterface;
import java.io.File;
import java.io.FileOutputStream;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClipBoardMonitor extends Service {
    private ClipboardManager clipboardManager;
    public final String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/wordly";
    @Override
    public void onCreate() {
        clipboardManager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.addPrimaryClipChangedListener(onPrimaryClipChanged);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private ClipboardManager.OnPrimaryClipChangedListener onPrimaryClipChanged = new ClipboardManager.OnPrimaryClipChangedListener(){
        @Override
        public void onPrimaryClipChanged() {
            ClipData clip = clipboardManager.getPrimaryClip();
            try{
                final String test = clip.getItemAt(0).getText().toString();
                final File file=new File(path+"/logs.txt");
                ApiInterface service = ApiClient.getClient().create(ApiInterface.class);
                Word word = new Word(test);
                final Call<Meaning> getDefCall = service.getDef(word);
                getDefCall.enqueue(new Callback<Meaning>() {
                    @Override
                    public void onResponse(Call<Meaning> call, Response<Meaning> response) {
                        if(response.isSuccessful()){
                            if(response.body().getError() == null) {
                                String definition = response.body().getDefinition();
                                Toast.makeText(getApplicationContext(), definition, Toast.LENGTH_SHORT).show();
                                addNotification(response.body().getText(),response.body().getExample());
                                Save(file, test + " = " + definition);
                            }
                            else
                                Toast.makeText(getApplicationContext(), "Select an appropriate word", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Meaning> call, Throwable t) {
                        Log.e("Failure","Error");
                    }
                });
            }
            catch (Exception e){
                Toast.makeText(getApplicationContext(),"Select text",Toast.LENGTH_SHORT).show();
            }
        }
    };

    private void Save(File file,String data){
        FileOutputStream fos=null;
        try{
            fos=new FileOutputStream(file,true);
        }catch(Exception e) {
            e.printStackTrace();
        }
        try {
            fos.write(data.getBytes());
            fos.write("\n".getBytes());
        }catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                fos.close();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    private void addNotification(String title,String content) {
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.read)
                        .setContentTitle(title)
                        .setContentText(content);

        Intent notificationIntent = new Intent(this, ClipBoardMonitor.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
