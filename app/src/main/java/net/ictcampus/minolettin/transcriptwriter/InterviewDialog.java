package net.ictcampus.minolettin.transcriptwriter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;

public class InterviewDialog extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle saveInstanceState) {
        /*Create ArtDialog*/
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /*Create LayoutInflater to use the layout file for the Dialog*/
        LayoutInflater inflater = getActivity().getLayoutInflater();
        /*setView of the dialog*/
        builder.setView(inflater.inflate(R.layout.activity_dialog, null));

        builder.setMessage(R.string.interview_dialog)
                .setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
}
