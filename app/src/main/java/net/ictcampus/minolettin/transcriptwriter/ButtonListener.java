package net.ictcampus.minolettin.transcriptwriter;

import android.app.Activity;
import android.view.View;
import android.widget.Button;

public class ButtonListener implements View.OnClickListener {

    private Button btn;
    private AudioActivity aActivity;

    public ButtonListener(Button b, Activity activity){
        btn = b;
        aActivity = (AudioActivity) activity;
    }


    @Override
    public void onClick(View v) {
        if(btn.getText().equals("Starten")){
            aActivity.startRecording("Starten");
        }
        else if (btn.getText().equals("Stoppen")){
            aActivity.startRecording("Stoppen");
        }
    }
}

