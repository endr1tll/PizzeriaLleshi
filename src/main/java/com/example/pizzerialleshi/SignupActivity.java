package com.example.pizzerialleshi;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {

    private static final int RC_SIGN_IN = 9001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private EditText emailEditText, passwordEditText;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Initialize UI components
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        Button signupButton = findViewById(R.id.signupConfirmButton);
        ImageButton googleSignUpButton = findViewById(R.id.googleSignInButton);

        // Initialize progress dialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        // Email/Password Signup
        signupButton.setOnClickListener(v -> attemptEmailSignup());

        // Google Sign-Up
        googleSignUpButton.setOnClickListener(v -> signInWithGoogle());
    }

    private void attemptEmailSignup() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        // Validate email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Enter valid email");
            emailEditText.requestFocus();
            return;
        }

        // Validate password
        if (password.length() < 8) {
            passwordEditText.setError("Password must be ≥8 characters");
            passwordEditText.requestFocus();
            return;
        }

        progressDialog.setMessage("Creating account...");
        progressDialog.show();

        // Create user with email/password
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        navigateToMain();
                    } else {
                        showError("Signup failed: " + task.getException().getMessage());
                    }
                });
    }

    // The following methods are identical to LoginActivity
    private void signInWithGoogle() { /* Same as login */ }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { /* Same */
        super.onActivityResult(requestCode, resultCode, data);
    }
    private void firebaseAuthWithGoogle(String idToken) { /* Same */ }
    private void navigateToMain() { /* Same */ }
    private void showError(String message) { /* Same */ }
    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}