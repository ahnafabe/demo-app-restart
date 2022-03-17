package com.example.demo_app_restart;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    private TextView register;
    private FirebaseAuth mAuth;
    private Button login;
    private EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);

        // Re-direct to register page

//        register.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
//            }
//        });

        register = (TextView) findViewById(R.id.needs_new_account);
        register.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        });

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        login = (Button) findViewById(R.id.login_button);
        login.setOnClickListener(view -> {
            loginUser();
        });
    }

    private void loginUser() {
        final String EmailAddress = email.getText().toString().trim();
        String Password = password.getText().toString().trim();

        //Check for email input
        if (EmailAddress.isEmpty()) {
            email.setError("Email is required (LOGIN)");
            email.requestFocus();
            return;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(EmailAddress).matches()) {
            email.setError("Please provide a valid email (LOGIN)"); //Check for valid form email
            email.requestFocus();
            return;
        }else if (Password.isEmpty()) {
            password.setError("Password is required (LOGIN)"); //Check for password input
            password.requestFocus();
            return;
        }else{
            mAuth.signInWithEmailAndPassword(EmailAddress,Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "User Logged in",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    }else{
                        Toast.makeText(LoginActivity.this, "Error:" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();

                    }
                }
            });
        }
    }
}