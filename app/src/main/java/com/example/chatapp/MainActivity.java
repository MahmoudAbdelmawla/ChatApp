package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseArray;
import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtMessage;
    private Button btnSend;
    private RecyclerView recMessage;
    private MessageAdapter adapter;
    private DatabaseReference mDatabase;
    private DatabaseReference fDatabase;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser mUser;
    private DatabaseReference mDatabaseUser;


    private ArrayList<String> sendersList;
    private String name;
    private String userID;
    private String friendID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Intent intent = new Intent();

        friendID = getIntent().getStringExtra(UsersActivity.FRIEND_ID);
        if (friendID.equals("")){
            Log.i("NULL" , "  firebase null");
        }


        edtMessage = findViewById(R.id.edtMessage);
        btnSend = findViewById(R.id.btnSend);

        mAuth = FirebaseAuth.getInstance();
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null){
                    startActivity(new Intent(MainActivity.this, SignUpActivity.class));
                }
            }
        };

        mUser = mAuth.getCurrentUser();
        userID = mUser.getUid();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("MESSAGES").child(userID).child(friendID);
        fDatabase = FirebaseDatabase.getInstance().getReference().child("MESSAGES").child(friendID).child(userID);
//        mUser = mAuth.getCurrentUser();
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                name = dataSnapshot.child("Name").getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        sendersList = new ArrayList<>();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                    String name = snapshot.getValue(Message.class).getName();
                    sendersList.add(name);


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnSend.setOnClickListener(MainActivity.this);

        recMessage = findViewById(R.id.recMessage);
        LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        recMessage.setLayoutManager(linearLayoutManager);


        FirebaseRecyclerOptions<Message> options =
                new FirebaseRecyclerOptions.Builder<Message>()
                        .setQuery(FirebaseDatabase.getInstance().getReference().child("MESSAGES").child(userID).child(friendID), Message.class)
                        .build();

        adapter = new MessageAdapter(options , sendersList );
        recMessage.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
        adapter.startListening();
    }

    @Override
    public void onClick(View v) {

        String messageValue = edtMessage.getText().toString();
        if (!TextUtils.isEmpty(messageValue)){

            String id = mDatabase.push().getKey();
            String fmid = fDatabase.push().getKey();
            String time = new SimpleDateFormat("HH:mm  aa").format(Calendar.getInstance().getTime());
            Message message = new Message(messageValue , time , name);
            mDatabase.child(id).setValue(message);
            fDatabase.child(fmid).setValue(message);
            edtMessage.setText("");


        }
    }



}
