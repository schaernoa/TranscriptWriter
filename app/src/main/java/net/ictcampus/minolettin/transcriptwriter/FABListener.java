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
        if (folder_main != null){
            File f = new File(Environment.getExternalStorageDirectory(), folder_main);
            if (!f.exists()) {
                f.mkdirs();
                Toast.makeText(activity, "Ordner erstellt",
                        Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(activity, "Ordner wurde bereits erstellt",
                        Toast.LENGTH_LONG).show();
            }
        }
    }
}
