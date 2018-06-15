package net.ictcampus.minolettin.transcriptwriter;

import android.app.Activity;
import android.content.Intent;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class FABListener implements View.OnClickListener {

    private Activity activity;
    private String foldername;

    public FABListener(Activity act, String fn){
        activity = act;
        foldername = fn;
    }

    @Override
    public void onClick(View v) {
        Intent myIntent = new Intent(activity, AudioText.class);
        myIntent.putExtra("foldername", foldername);
        activity.startActivity(myIntent);
    }
}
