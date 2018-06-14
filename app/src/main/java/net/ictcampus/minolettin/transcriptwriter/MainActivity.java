package net.ictcampus.minolettin.transcriptwriter;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static net.ictcampus.minolettin.transcriptwriter.AudioActivity.RequestPermissionCode;

public class MainActivity extends AppCompatActivity {

    /*Instanz variablen*/
    ArrayList<String> interviewNameList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listView;
    public static final String PFAD_MAIN = "/TranscriptWriter";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton ClickListener*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Neues InterviewDialog Objekt
                 * Parameter: ArrayList -> Interview Namen*/
                InterviewDialog dialog = new InterviewDialog(interviewNameList);
                /*Dialog anzeigen*/
                dialog.show(getFragmentManager(), "NoticeDialogFragment");
            }
        });

        /*Adapter erstellen und listView Objekt holen*/
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.listview_layout, interviewNameList);

        listView = (ListView) findViewById(R.id.interviewList);
        readDataName();

        if (checkPermission()) {
            File f = new File(Environment.getExternalStorageDirectory(), PFAD_MAIN);
            if (!f.exists()) {
                f.mkdirs();
                Toast.makeText(this, "Ordner erstellt",
                    Toast.LENGTH_LONG).show();

            }
            else {
                Toast.makeText(this, "Ordner wurde bereits erstellt",
                    Toast.LENGTH_LONG).show();
            }
        }
        else{
            requestPermission();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String foldername = interviewNameList.get(position);
                Toast.makeText(getApplicationContext(),  foldername, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(MainActivity.this, AudioText.class);
                myIntent.putExtra("foldername", foldername);
                startActivity(myIntent);
            }
        });
    }

    /*Die ListView wird aktualisiert*/
    public void listUpdate() {
        listView.setAdapter(adapter);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new
                String[]{WRITE_EXTERNAL_STORAGE, RECORD_AUDIO}, RequestPermissionCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case RequestPermissionCode:
                if (grantResults.length > 0) {
                    boolean StoragePermission = grantResults[0] ==
                            PackageManager.PERMISSION_GRANTED;
                    boolean RecordPermission = grantResults[1] ==
                            PackageManager.PERMISSION_GRANTED;

                    if (StoragePermission && RecordPermission) {
                        Toast.makeText(this, "Permission Granted",
                                Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(),
                WRITE_EXTERNAL_STORAGE);
        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(),
                RECORD_AUDIO);
        return result == PackageManager.PERMISSION_GRANTED &&
                result1 == PackageManager.PERMISSION_GRANTED;
    }

    public void readDataName() {
        String path = Environment.getExternalStorageDirectory().toString() + PFAD_MAIN;
        File directory = new File(path);
        File[] files = directory.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++)
            {
                String string = files[i].toString();
                string = string.substring(string.lastIndexOf("/") + 1);
                interviewNameList.add(string);
                listUpdate();
            }
        }
    }
}
