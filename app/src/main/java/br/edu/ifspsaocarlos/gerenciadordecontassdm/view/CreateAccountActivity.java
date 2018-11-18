package br.edu.ifspsaocarlos.gerenciadordecontassdm.view;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.edu.ifspsaocarlos.gerenciadordecontassdm.R;

public class CreateAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText accountNameEditText;
    private EditText valueEditText;
    private Button cancelButton;
    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        // Layout reference
        accountNameEditText = findViewById(R.id.accountName);
        valueEditText = findViewById(R.id.initialValueAccount);
        cancelButton = findViewById(R.id.cancelButton);
        saveButton = findViewById(R.id.saveButton);

        // Button Listeners
        cancelButton.setOnClickListener(this);
        saveButton.setOnClickListener(this);
    }

    // Interface implementations (OnClickListener)
    @Override
    public void onClick(View v) {
        // Advertise alertview
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();

        // Load field values
        String name = accountNameEditText.getText().toString();
        String value = valueEditText.getText().toString();

        switch (v.getId()) {
            case R.id.cancelButton:
                // Behavior if the user taps to cancel
                if (!name.matches("") || !value.matches("")) {

                    String title = this.getString(R.string.title_alertView);
                    String message = this.getString(R.string.title_alertView_cancel_message);
                    String okbuttonText = this.getString(R.string.ok_button_text)   ;
                    String cancelButtonText = this.getString(R.string.cancel_button_text);

                    alertDialog.setTitle(title);
                    alertDialog.setMessage(message);
                    alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, okbuttonText,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    finish();
                                }
                            });
                    alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, cancelButtonText,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    alertDialog.show();
                } else {
                    finish();
                }

                break;
            case R.id.saveButton:
                // Behavior if the user taps to save
                if (name.matches("") || value.matches("")) {

                    String errorTitle = this.getString(R.string.error_save_title);
                    String messageError = this.getString(R.string.error_save_message);

                    alertDialog.setTitle(errorTitle);
                    alertDialog.setMessage(messageError);
                    alertDialog.show();
                } else {
                    finish();
                }

                break;
        }
    }
}
