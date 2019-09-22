package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UsersActivity extends AppCompatActivity {

    private ListView listView;
    private DatabaseReference userListDataRef;
    private FirebaseAuth mAuth;
    private ArrayAdapter arrayAdapter;
    private ArrayList<String> arrayList;
    private ArrayList<String> keysArrayList;
    private ArrayList<Users> usersList;
    //  private List list;

    // Instance Variable
    public static final String FRIEND_ID = "FRIEND_ID";
    public static final String CLIENT_NAME = "CLIENT_NAME";
    private String clientUserID;
    public static String clientName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        listView = findViewById(R.id.listView);
        arrayList = new ArrayList<>();
        keysArrayList = new ArrayList<>();
        usersList = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(UsersActivity.this, android.R.layout.simple_list_item_1, arrayList);

        mAuth = FirebaseAuth.getInstance();
        clientUserID = mAuth.getCurrentUser().getUid();

        userListDataRef = FirebaseDatabase.getInstance().getReference().child("Users");

        userListDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {





                for (DataSnapshot snapshot : dataSnapshot.getChildren()){

                   // String name = snapshot.getValue().toString();
                   // name = name.substring(6,name.length()-1);
                    String key = snapshot.getKey();
                    String name = dataSnapshot.child(key).child("Name").getValue().toString();
                    Log.i("NAME", name);

                    if (!key.equals(clientUserID)) {
                        arrayList.add(name);
                        keysArrayList.add(key);
                    }else {
                        clientName = name;
                    }


                }
                arrayAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        listView.setAdapter(arrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Toast.makeText(UsersActivity.this, keysArrayList.get(position), Toast.LENGTH_SHORT).show();

                Intent gotoChatIntent = new Intent(UsersActivity.this , MainActivity.class);
                String friend = keysArrayList.get(position);
                if (friend != ""){


                    gotoChatIntent.putExtra(FRIEND_ID,friend);
                    gotoChatIntent.putExtra(CLIENT_NAME,clientName);

                    startActivity(gotoChatIntent);
                }else{
                    Log.i("friend Is Null" , friend+"");
                }
            }
        });

    }
}



        /**/



      /*  FirebaseListOptions<Users> listOptions = new FirebaseListOptions.Builder<Users>().setLayout(android.R.layout.simple_list_item_1).setQuery(userListDataRef , Users.class)
                .build();
        FirebaseListAdapter<Users> listAdapter = new FirebaseListAdapter<Users>(listOptions) {
            @Override
            protected void populateView(@NonNull View view, @NonNull Users model, int position) {

*/


//                TextView textViewName = findViewById(R.id.txtUserName);
//                textViewName.setText(model.getUserName());
        //    }
    //    }

      //  listView.setAdapter(listAdapter);


