package com.stranskymarek.chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    //Widgets
    EditText userET, passET, emailET;
    Button registerBtn;

    //Firebase
    FirebaseAuth auth;
    DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initilizing Widgets;
        userET = findViewById(R.id.userNameEditText);
        passET = findViewById(R.id.passwordEditText);
        emailET = findViewById(R.id.emailEditText);
        registerBtn = findViewById(R.id.registerBtn);

        //Firebase Auth
        auth = FirebaseAuth.getInstance();

        //Adding Event Listener to Button Register
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username_txt = userET.getText().toString();
                String email_txt = emailET.getText().toString();
                String pass_txt = passET.getText().toString();

                if (TextUtils.isEmpty(username_txt)
                        || TextUtils.isEmpty(email_txt)
                        || TextUtils.isEmpty(pass_txt)) {
                    Toast.makeText(RegisterActivity.this, "Please Fill All Fields", Toast.LENGTH_SHORT).show();
                } else {
                    RegisterNow(username_txt, email_txt, pass_txt);
                }
            }
        });
    }

    private void RegisterNow(final String username, String email, String password ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            FirebaseUser firebaseUser = auth.getCurrentUser();
                            String userid = firebaseUser.getUid();

                            myRef = FirebaseDatabase.getInstance().getReference("MyUsers")
                                    .child(userid);

                            //HashMap
                            HashMap<String, String> hashMap = new HashMap<>();
                            hashMap.put("id", userid);
                            hashMap.put("username", username);
                            hashMap.put("imageURL", "default");
                            hashMap.put("status", "offline");

                            //Opening the Main Activity after successful reg // onComplete listener was not called
                            myRef.setValue(hashMap);

                            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(RegisterActivity.this, "Invalid Email or Password ", Toast.LENGTH_SHORT).show();
                        }
                }
            });
    }
}