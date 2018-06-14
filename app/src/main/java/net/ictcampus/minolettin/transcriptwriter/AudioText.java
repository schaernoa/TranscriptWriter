package net.ictcampus.minolettin.transcriptwriter;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static net.ictcampus.minolettin.transcriptwriter.MainActivity.PFAD_MAIN;

public class AudioText extends AppCompatActivity {

    int i = 0;

    private TextView mTextMessage;

    private String foldername;
    private String audiopfad;
    private String filename;

    private MediaPlayer mediaPlayer;

    private ListView listView;
    private ArrayList<String> audioList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;
    private String mainPath = "/TranscriptWriter/";
    private Bundle bundle;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = new BottomNavigationView.OnNavigationItemSelectedListener() {

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_audio:
                listView.setVisibility(View.VISIBLE);
                return true;
            case R.id.navigation_text:
                listView.setVisibility(View.INVISIBLE);
                return true;
        }
        return false;
    }//BottomNavigationView end
};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_text);
        bundle = getIntent().getExtras();

        foldername = getIntent().getStringExtra("foldername");

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.listview_layout, audioList);
        listView = (ListView) findViewById(R.id.audio_list);

        readDataName("/Audio/");

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                filename = audioList.get(position);
                Log.d("file",filename);
                if (i % 2 == 0){
                    playAudio(filename);
                }
                else{
                    stopAudio();
                }
                i++;
            }
        });
    }

    public void listUpdate() {
        listView.setAdapter(adapter);
    }

    public void readDataName(String item) {
        String path = Environment.getExternalStorageDirectory().toString() + mainPath + bundle.getString("foldername") + item;
        Log.d("PFAD",path);
        Log.d("Folder",foldername);
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files.length != 0) {
            mTextMessage.setVisibility(View.INVISIBLE);
            Log.d("Status", "Files ok");
            for (int i = 0; i < files.length; i++)
            {
                String string = files[i].toString();
                string = string.substring(string.lastIndexOf("/") + 1);
                audioList.add(string);
                listUpdate();
            }
        }
        else {
            mTextMessage.setVisibility(View.VISIBLE);
        }
    }

    private void playAudio(String filename){
        audiopfad = Environment.getExternalStorageDirectory().getAbsolutePath() + PFAD_MAIN + "/" + foldername + "/Audio/" + filename;
        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audiopfad);
            mediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }

        mediaPlayer.start();
        Toast.makeText(this, "Recording playing",
                Toast.LENGTH_LONG).show();
    }

    private void stopAudio(){
        if (mediaPlayer != null) {
            Toast.makeText(this, "Recording stopped",Toast.LENGTH_SHORT).show();
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

}
