package net.ictcampus.minolettin.transcriptwriter;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Timer;

import static net.ictcampus.minolettin.transcriptwriter.MainActivity.PFAD_MAIN;

public class AudioActivity extends AppCompatActivity {

    private TextView txtTime, txtPerson1, txtPerson2, txtInfo, txtInfoPerson;
    private Button btnStarten;

    private Timer timer;
    private Uhr uhr;

    MediaRecorder mediaRecorder;
    public static final int RequestPermissionCode = 1;
    private FloatingActionButton fabSafe;

    private int person = 1;
    String AudioSavePathInDevice;
    private String folder_main = "TranscriptWriter";

    private String foldername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        txtTime = (TextView) findViewById(R.id.txtTime);
        txtPerson1 = (TextView) findViewById(R.id.txtPerson1);
        txtPerson2 = (TextView) findViewById(R.id.txtPerson2);
        txtInfo = (TextView) findViewById(R.id.txtInfo);
        txtInfoPerson = (TextView) findViewById(R.id.txtInfoPerson);
        btnStarten = (Button) findViewById(R.id.btnStarten);
        fabSafe = (FloatingActionButton) findViewById(R.id.fabSafe);

        btnStarten.setText("Starten");
        txtPerson1.setBackgroundColor(Color.GREEN);
        txtPerson2.setBackgroundColor(Color.LTGRAY);

        Intent intent = getIntent();
        //Zusatzinformation aus dem Internet laden
        foldername = intent.getStringExtra("foldername");

        btnStarten.setOnClickListener(new ButtonListener(btnStarten,this));
        fabSafe.setOnClickListener(new FABListener(folder_main,this, foldername));
    }

    public void startRecording(String button) {
        if (button.equals("Starten")) {
            if (person % 2 == 1) {
                AudioSavePathInDevice = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + PFAD_MAIN + "/"
                        + foldername + "/Audio/Person1_" + person + ".amr";
            }
            else {
                AudioSavePathInDevice = Environment.getExternalStorageDirectory()
                        .getAbsolutePath() + PFAD_MAIN + "/"
                        + foldername +  "/Audio/Person2_" + person + ".amr";
            }

            MediaRecorderReady();

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            timer = new Timer(false);
            uhr = new Uhr(txtTime);
            timer.schedule(uhr, 1000, 1000);

            btnStarten.setText("Stoppen");
            if (person % 2 == 1) {
                txtInfoPerson.setText("Person1 ist dran");
                txtInfo.setText("'Stoppen' um Aufnahme zu beenden");
            }
            else {
                txtInfoPerson.setText("Person2 ist dran");
                txtInfo.setText("'Stoppen' um Aufnahme zu beenden");
            }
            Toast.makeText(this, "Recording started",
                    Toast.LENGTH_SHORT).show();

        }
        else{
            mediaRecorder.stop();

            timer.cancel();
            btnStarten.setText("Starten");
            txtTime.setText("00:00");

            if (person % 2 == 0) {
                txtInfoPerson.setText("Person1 ist dran");
                txtInfo.setText("'Starten' um Audio aufzunehmen");
                txtPerson1.setBackgroundColor(Color.GREEN);
                txtPerson2.setBackgroundColor(Color.LTGRAY);
            }
            else {
                txtInfoPerson.setText("Person2 ist dran");
                txtInfo.setText("'Starten' um Audio aufzunehmen");
                txtPerson1.setBackgroundColor(Color.LTGRAY);
                txtPerson2.setBackgroundColor(Color.GREEN);
            }
            Toast.makeText(this, "Recording finished",
                    Toast.LENGTH_SHORT).show();
            person++;
        }
    }

    private void MediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        mediaRecorder.setOutputFile(AudioSavePathInDevice);
    }
}
