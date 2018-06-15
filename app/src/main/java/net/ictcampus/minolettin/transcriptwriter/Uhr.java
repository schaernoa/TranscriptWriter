package net.ictcampus.minolettin.transcriptwriter;

import android.widget.TextView;
import java.util.TimerTask;

public class Uhr extends TimerTask {

    private final android.os.Handler handler = new android.os.Handler();
    private int count;
    private TextView txtV;

    public Uhr(TextView tv){
        txtV = tv;
    }

    // Stellt die Zeit als "Stoppuhr" dar
    @Override
    public void run() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                count++;
                if (count < 3600){
                    int min = (int) count / 60;
                    int sec = count % 60;
                    String formatted = String.format("%02d:%02d", min, sec);
                    txtV.setText(formatted);
                }
                else{
                    txtV.setText("out of range");
                }
            }
        });
    }
}