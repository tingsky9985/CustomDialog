package com.ting.scratch.customdialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ting on 16-8-25.
 */
public class DialogActivity extends Activity {
    private static final String TAG = DialogActivity.class.getSimpleName();
    public static final String ERROR_MESSAGE_ID_EXTRA = "error_message_id";

    private Dialog mGeneralErrorDialog;

    private TextView messTextView;
    private ImageView dismissImageView;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private void showGenericErrorDialog() {
        //int themeID = this.getResources().getIdentifier("androidhwext:style/Theme.Emui.Dialog.Alert", null, null);
        mGeneralErrorDialog = new AlertDialog.Builder(DialogActivity.this, R.style.ErrorDialog).create();
        mGeneralErrorDialog.setCancelable(false);
        mGeneralErrorDialog.show();
        mGeneralErrorDialog.getWindow().setContentView(R.layout.err_dialog);

        initWidget();
    }

    /**
     * init view
     */
    private void initWidget() {
        View layout = LayoutInflater.from(DialogActivity.this).inflate(R.layout.err_dialog, null);
        messTextView = (TextView) layout.findViewById(R.id.dialog_error_message);
        dismissImageView = (ImageView) layout.findViewById(R.id.dialog_error_dismiss);
        cancelButton = (Button) layout.findViewById(R.id.dialog_error_cancel);
    }

    public void cancelClick(View view){
        Log.d(TAG, "cancelClick click v : " + view.getId());
        if (mGeneralErrorDialog != null) {
            mGeneralErrorDialog.dismiss();
            mGeneralErrorDialog = null;
        }
        finish();
    }

    public void dismissClick(View view){
        Log.d(TAG, "dismissClick click v : " + view.getId());
        if (mGeneralErrorDialog != null) {
            mGeneralErrorDialog.dismiss();
            mGeneralErrorDialog = null;
        }
        finish();
    }

    /**
     * when some other activity covered on this dialog activity
     * we simply finish it to avoid the dialog flick.
     */
    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        // Don't show the return to previous task animation to avoid showing a black screen.
        // Just dismiss the dialog and undim the previous activity immediately.
        overridePendingTransition(0, 0);
    }

    ///M: ALPS01828565/ALPS01844901 this activity launch mode is singleInstance
    // reset intent onNewIntent()
    // call showErrorDialog() in onResume() instead of onCreate()
    // because of onCreate does not always be called,ex. press home key to exit, then start it again
    // @{
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mGeneralErrorDialog != null) {
            mGeneralErrorDialog.dismiss();
        }
        showGenericErrorDialog();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mGeneralErrorDialog != null) {
            mGeneralErrorDialog.dismiss();
            mGeneralErrorDialog = null;
        }
    }
}

