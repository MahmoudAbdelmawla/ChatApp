package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtUserEmailSign;
    private EditText edtUserPasswordSign;
    private Button btnSignIn;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtUserEmailSign = findViewById(R.id.edtUserEmailSign);
        edtUserPasswordSign = findViewById(R.id.edtUserPasswordSign);
        btnSignIn = findViewById(R.id.btnSignIn);
        mAuth = FirebaseAuth.getInstance();
        btnSignIn.setOnClickListener(SignInActivity.this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.btnSignIn:
                String email = edtUserEmailSign.getText().toString();
                String password = edtUserPasswordSign.getText().toString();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)) {

                    mAuth.signInWithEmailAndPassword(email , password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                Toast.makeText(SignInActivity.this, "Sign In Successfully", Toast.LENGTH_SHORT).show();
                               // Intent intent = new Intent(SignInActivity.this , MainActivity.class);
                                Intent intent = new Intent(SignInActivity.this , UsersActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                }
                break;
        }

    }
}
