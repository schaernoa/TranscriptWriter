package net.ictcampus.minolettin.transcriptwriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class FABListener implements View.OnClickListener {

    private String folder_main;
    private Activity activity;
    private String foldername;

    public FABListener(String f, Activity act, String fn){
        folder_main = f;
        activity = act;
        foldername = fn;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(activity, "gespeichert",Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(activity, AudioText.class);
        myIntent.putExtra("foldername", foldername);
        activity.startActivity(myIntent);
    }
}
