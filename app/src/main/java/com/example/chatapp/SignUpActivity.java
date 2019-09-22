package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edtUserEmail;
    private EditText edtUserName;
    private EditText edtUserPassword;
    private Button btnSignUp;
    private TextView txtHaveAccount;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtUserEmail = findViewById(R.id.edtUserEmail);
        edtUserName = findViewById(R.id.edtUserName);
        edtUserPassword = findViewById(R.id.edtUserPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        txtHaveAccount = findViewById(R.id.txtHaveAccount);
        btnSignUp.setOnClickListener(SignUpActivity.this);
        txtHaveAccount.setOnClickListener(SignUpActivity.this);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignUp:
                String email = edtUserEmail.getText().toString();
                final String name = edtUserName.getText().toString();
                String password = edtUserPassword.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)&& !TextUtils.isEmpty(name)){
                    mAuth.createUserWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                String userID = mAuth.getCurrentUser().getUid();
                                mDatabase.child(userID);
                                DatabaseReference currentUserDatabase = mDatabase.child(userID);
                                currentUserDatabase.child("Name").setValue(name);

                                Toast.makeText(SignUpActivity.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(SignUpActivity.this , UsersActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                break;
            case R.id.txtHaveAccount:
                Intent intent = new Intent(SignUpActivity.this , SignInActivity.class);
                startActivity(intent);
                break;
        }

    }
}
