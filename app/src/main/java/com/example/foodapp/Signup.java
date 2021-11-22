package com.example.foodapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signup extends AppCompatActivity {

    EditText editName, editAddress, editContact, editEmail, editPassword;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        editName = findViewById(R.id.editName);
        editAddress = findViewById(R.id.editAddress);
        editContact = findViewById(R.id.editContact);
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);

        mAuth = FirebaseAuth.getInstance();

    }

    public void create(View view) {
        String name, address, contact, email, password;

        name=editName.getText().toString().trim();
        address=editAddress.getText().toString().trim();
        contact=editContact.getText().toString().trim();
        email=editEmail.getText().toString().trim();
        password=editPassword.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            Users users = new Users(name, address, contact, email);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(users).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){
                                            Intent intent = new Intent(getApplicationContext(), Thanks.class);
                                            startActivity(intent);
                                        Toast.makeText(Signup.this, "Creating Account Successful", Toast.LENGTH_LONG).show();
                                    }else Toast.makeText(Signup.this, "Error. Please try again.", Toast.LENGTH_LONG).show();

                                }
                            });
                        }else Toast.makeText(Signup.this, "Error. Please try again.", Toast.LENGTH_LONG).show();
                    }
                });
    }



}