package net.ictcampus.minolettin.transcriptwriter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

@SuppressLint("ValidFragment")
public class InterviewDialog extends DialogFragment {

    /*Instanz Variablen*/
    ArrayList<String> interviewNameList = new ArrayList<String>();
    View dialogView;

    /*InterviewNameList (ArrayList) von MainActivity wird übergeben*/
    @SuppressLint("ValidFragment")
    public InterviewDialog(ArrayList<String> interviewNameList) {
        this.interviewNameList = interviewNameList;
    }

    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        /*AlertDialog builder erstellen*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /*LayoutInflater erstellen um Layout File zu verwenden */
        LayoutInflater inflater = getActivity().getLayoutInflater();
        /*Dialog Sichtbar machen*/
        dialogView = inflater.inflate(R.layout.activity_dialog, null);
        builder.setView(dialogView);

        /*Titel festlegen und OK Button erstellen*/
        builder.setMessage(R.string.interview_dialog)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*Wert des Eingabefelds holen und InterviewNameList Wert hinzufügen*/
                        EditText interviewName = (EditText) dialogView.findViewById(R.id.interview_name);

                        MainActivity a = (MainActivity) getActivity();

                        if (interviewName.getText().toString() != null){
                            String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/TranscriptWriter/";
                            File f = new File(path, interviewName.getText().toString());
                            if (!f.exists()) {
                                f.mkdirs();
                                File audioFile = new File(path + interviewName.getText().toString() + "/", "Audio");
                                audioFile.mkdirs();
                                File textFile = new File(path + interviewName.getText().toString() + "/", "Text");
                                textFile.mkdirs();
                                interviewNameList.add( interviewName.getText().toString() );
                                Intent myIntent = new Intent(a, AudioActivity.class);
                                myIntent.putExtra("foldername", interviewName.getText().toString());
                                startActivity(myIntent);
                            }
                            else {
                                Toast.makeText(a, "Ordner wurde bereits erstellt",
                                        Toast.LENGTH_LONG).show();
                            }
                        }
                        /*Aktuelle Activity zur MainActivity Casten und ein
                        Update der ListView durchführen*/
                        a.listUpdate();
                    }
                })
                /*CANCEL Button erstellen*/
                .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}

