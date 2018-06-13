package net.ictcampus.minolettin.transcriptwriter;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class AudioText extends AppCompatActivity {

    private TextView mTextMessage;
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

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, audioList);
        listView = (ListView) findViewById(R.id.audio_list);
        readDataName("/Audio/");
    }

    public void listUpdate() {
        listView.setAdapter(adapter);
    }

    public void readDataName(String item) {
        String path = Environment.getExternalStorageDirectory().toString() + mainPath + bundle.getString("foldername") + item;
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files.length != 0) {
            mTextMessage.setVisibility(View.INVISIBLE);
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
}
