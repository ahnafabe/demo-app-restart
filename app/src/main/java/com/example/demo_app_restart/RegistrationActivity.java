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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrationActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private EditText editTextEmail, editTextPassword, editTextUserName;
    private Button signUpButton;
    private TextView prevMenu;

    FirebaseDatabase rootNode;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //Email input field
        editTextEmail = (EditText) findViewById(R.id.register_email);

        //Password input field
        editTextPassword = (EditText) findViewById(R.id.register_password);

        //Username input field
        editTextUserName = (EditText) findViewById(R.id.register_name);

        mAuth = FirebaseAuth.getInstance();

        signUpButton = (Button) findViewById(R.id.register_button);
        signUpButton.setOnClickListener(view -> {
            registerUser();

        });
        prevMenu = (TextView) findViewById(R.id.homepage);
        prevMenu.setOnClickListener(view -> {
            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user == null){
            startActivity(new Intent(this, LoginActivity.class));
        }
    }

    private void registerUser () {
        final String EmailAddress = editTextEmail.getText().toString().trim();
        String Password = editTextPassword.getText().toString().trim();
        final String PersonName = editTextUserName.getText().toString().trim();

        //Check for email input
        if (EmailAddress.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }else if (!Patterns.EMAIL_ADDRESS.matcher(EmailAddress).matches()) {
            editTextEmail.setError("Please provide a valid email"); //Check for valid form email
            editTextEmail.requestFocus();
            return;
        }else if (Password.isEmpty()) {
            editTextPassword.setError("Password is required"); //Check for password input
            editTextPassword.requestFocus();
            return;
        }else if (Password.length() < 6) {
            editTextPassword.setError("Min password length should be 6 characters"); //Check password length
            editTextPassword.requestFocus();
            return;
        }else if (PersonName.isEmpty()) {
            editTextUserName.setError("Username is required"); //Check username input
            editTextUserName.requestFocus();
            return;
        } else {

            rootNode = FirebaseDatabase.getInstance(); //instance of database root
            reference = rootNode.getReference("users"); //referencing databse of users

            User userData = new User(EmailAddress, Password); //creating user instance
            reference.child(mAuth.getUid()).setValue(userData); //pushing data to database and organizing users by ID's

            mAuth.createUserWithEmailAndPassword(EmailAddress, Password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(RegistrationActivity.this, "User Created",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                    }else{
                        Toast.makeText(RegistrationActivity.this, "Error:" + task.getException().getMessage(),Toast.LENGTH_SHORT).show();


                    }
                }
            });
        }
    }
}
