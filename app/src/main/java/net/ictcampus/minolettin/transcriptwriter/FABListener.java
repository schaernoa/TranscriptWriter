package net.ictcampus.minolettin.transcriptwriter;

import android.app.Activity;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class FABListener implements View.OnClickListener {

    private String folder_main;
    private Activity activity;

    public FABListener(String f, Activity act){
        folder_main = f;
        activity = act;
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(activity, "Safe no ni gspicheret (es würd)",Toast.LENGTH_SHORT).show();
    }
}
