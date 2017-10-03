package com.cafe.decale.ledecale.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.cafe.decale.ledecale.R;

/**
 * Created by manut on 26/06/2017.
 */

public class AlertDialogManager {
    public AlertDialog alertDialog;

    public void showAlertDialog(Context context, String title, String message, Boolean status){
        alertDialog = new AlertDialog.Builder(context).create();

        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        if(status != null){
            alertDialog.setIcon((status) ? R.drawable.success : R.drawable.fail);
        }
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }


}