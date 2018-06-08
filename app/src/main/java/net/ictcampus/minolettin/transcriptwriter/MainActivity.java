package net.ictcampus.minolettin.transcriptwriter;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    /*Instanz variablen*/
    ArrayList<String> interviewNameList = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        File file = new File(getApplicationContext().getFilesDir(), "TranscriptWriter.txt");
        String path = getApplicationContext().getFilesDir().getAbsolutePath();

        Toast.makeText(getApplicationContext(), path, Toast.LENGTH_LONG).show();
        Log.d("pfad", path);


       /* AudioSavePathInDevice = Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + "Person1_" + person + ".3gp";*/

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

        /*Strings dem Array hinzufügen*/
        interviewNameList.add("erster String");//temp
        interviewNameList.add("zweiter String");//temp
        interviewNameList.add("dritter Sting");//temp
        listUpdate();
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
}
