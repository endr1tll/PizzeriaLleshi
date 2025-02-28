package com.example.pizzerialleshi;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Button;
import androidx.appcompat.app.AlertDialog;

public class SignupActivity extends BaseActivity {
    private Button signupConfirmButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        signupConfirmButton = findViewById(R.id.signupConfirmButton);
        signupConfirmButton.setOnClickListener(v -> {
            if (!NetworkUtils.isInternetConnected(SignupActivity.this)) {
                showInternetRequiredDialog();
                return;
            }

            // Add your signup logic here
            // startActivity(...);
        });
    }

    private void showInternetRequiredDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Internet Required")
                .setMessage("You need an internet connection to sign up")
                .setCancelable(false)
                .setPositiveButton("Settings", (dialog, which) -> {
                    startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                })
                .setNegativeButton("Not Now", (dialog, which) -> {
                    dialog.dismiss();
                })
                .show();
    }
}