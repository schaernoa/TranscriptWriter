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
    String pfad_main = "/TranscriptWriter";

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
                android.R.layout.simple_list_item_1, interviewNameList);
        listView = (ListView) findViewById(R.id.interviewList);

        Log.d("CHECK","vorher");
        if (checkPermission()) {
            Log.d("CHECK","CHECKED");
            File f = new File(Environment.getExternalStorageDirectory(), pfad_main);
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
                Toast.makeText(getApplicationContext(), "Gugus element " + foldername, Toast.LENGTH_LONG).show();
                Intent myIntent = new Intent(MainActivity.this, AudioActivity.class);
                myIntent.putExtra("foldername", foldername);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    public void listf(String directoryName, ArrayList<File> files) {
        File directory = new File(directoryName);

        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                files.add(file);
            } else if (file.isDirectory()) {
                listf(file.getAbsolutePath(), files);
            }
        }
    }
}
