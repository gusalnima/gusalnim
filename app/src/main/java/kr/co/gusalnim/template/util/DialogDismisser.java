package kr.co.gusalnim.template.util;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

public class DialogDismisser {
    public static void dismiss(DialogInterface d) {
        if(d == null)
            return;

        try {
            if(d instanceof AlertDialog) {
                if(((AlertDialog) d).isShowing())
                    ((AlertDialog)d).dismiss();

                return;
            }

            if(d instanceof ProgressDialog) {
                if(((ProgressDialog) d).isShowing())
                    ((ProgressDialog)d).dismiss();

                return;
            }

            if(d instanceof Dialog) {
                if(((Dialog) d).isShowing())
                    ((Dialog)d).dismiss();

                return;
            }
        } catch(Exception e) {
        }
    }

    public static void dismiss(DialogInterface d1, DialogInterface d2) {
        dismiss(d1);
        dismiss(d2);
    }
}