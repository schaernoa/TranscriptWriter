package net.ictcampus.minolettin.transcriptwriter;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import static net.ictcampus.minolettin.transcriptwriter.MainActivity.PFAD_MAIN;

public class AudioText extends AppCompatActivity {

    private int i = 0;
    private int k = 1;
    private TextView mTextMessage;
    private Button btnPlayAll;
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
            case R.id.navigation_audio://Navigation Audio
                listView.setVisibility(View.VISIBLE);
                mTextMessage.setVisibility(View.INVISIBLE);
                btnPlayAll.setVisibility(View.VISIBLE);
                return true;
            case R.id.navigation_text://Navigation Text
                listView.setVisibility(View.INVISIBLE);
                mTextMessage.setVisibility(View.VISIBLE);
                btnPlayAll.setVisibility(View.INVISIBLE);
                return true;
        }
        return false;
    }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_text);

        bundle = getIntent().getExtras();//temp
        foldername = getIntent().getStringExtra("foldername");
        mTextMessage = (TextView) findViewById(R.id.message);
        mTextMessage.setMovementMethod(new ScrollingMovementMethod());
        btnPlayAll = (Button) findViewById(R.id.btnPlayAll);
        btnPlayAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAll();
            }
        });

        /*Listener für Bottom Navigation*/
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        /*Adapter für ListView*/
        adapter = new ArrayAdapter<String>(getApplicationContext(),
                R.layout.listview_layout, audioList);
        listView = (ListView) findViewById(R.id.audio_list);


        readDataName("/Audio/");
        writeFile("öppis iche schriibe");//Temporär
        writeFile("no einisch öppis iche schriiibe kolleg");//Temporär
        readFile();

        /*Pfad von Text Datei*/
        String path = Environment.getExternalStorageDirectory().toString() + mainPath +
                foldername + "/Text/Text.txt";
        File file = new File(path);

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) break;
                mTextMessage.append(line + "\n");
            }
            inputStreamReader.close();
            fileInputStream.close();
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                filename = audioList.get(position);
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
            btnPlayAll.setEnabled(false);
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
        Toast.makeText(this, "Recording playing", Toast.LENGTH_SHORT).show();
    }

    private void stopAudio(){
        if (mediaPlayer != null) {
            Toast.makeText(this, "Recording stopped",Toast.LENGTH_SHORT).show();
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }

    private void playAll(){
        if (k <= listView.getCount()){
            if (k % 2 == 1){
                audiopfad = Environment.getExternalStorageDirectory().getAbsolutePath() + PFAD_MAIN + "/" + foldername + "/Audio/Person1_" + k + ".amr" ;
            }
            else {
                audiopfad = Environment.getExternalStorageDirectory().getAbsolutePath() + PFAD_MAIN + "/" + foldername + "/Audio/Person2_" + k + ".amr" ;
            }

            mediaPlayer = new MediaPlayer();
            try {
                mediaPlayer.setDataSource(audiopfad);
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mediaPlayer.start();
            k++;

            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    playAll();
                }
            });
        }
        else {
            mediaPlayer.stop();
            k = 1;
        }
    }

    private void writeFile(String content) {
        File dir = new File (Environment.getExternalStorageDirectory().toString() +
                mainPath + foldername + "/Text/");
        File file = new File(dir, "Text.txt");

        try {
            FileOutputStream f = new FileOutputStream(file, true);
            PrintWriter pw = new PrintWriter(f);
            pw.println(content);
            pw.println("");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFile() {
        /*Pfad von Text Datei*/
        String path = Environment.getExternalStorageDirectory().toString() + mainPath +
                foldername + "/Text/Text.txt";
        File file = new File(path);

        /*Datei Lesen*/
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader, 8192);
            String line;
            while (true) {
                line = bufferedReader.readLine();
                if (line == null) break;
                mTextMessage.append(line + "\n");
            }
            inputStreamReader.close();
            fileInputStream.close();
            bufferedReader.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
