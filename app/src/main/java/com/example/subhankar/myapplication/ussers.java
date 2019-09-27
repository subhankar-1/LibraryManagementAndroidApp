package com.example.subhankar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ussers extends AppCompatActivity {

    private TextView mTextMessage;
    private  static  final  String TAG="md";
    private Firebase mRootRef,mRootRef2;
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private ListView mListView;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
               /* case R.id.navigation_home:
                    Intent intent=new Intent(".MainActivity");
                    startActivity(intent);
                    return true;*/
                case R.id.navigation_dashboard:
                    Intent intent2=new Intent(".admin");
                    startActivity(intent2);

                    return true;
                /*case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    //return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ussers);
        mListView = (ListView) findViewById(R.id._u);
        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/users");
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                showdata(dataSnapshot);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private  void  showdata(DataSnapshot dataSnapshot){
        mListView = (ListView) findViewById(R.id._u);
        List<String> keys=new ArrayList<>();
        ArrayList<String> array= new ArrayList<>();
        array.add(" userID          username           fine");
        for(DataSnapshot ds:dataSnapshot.getChildren()){

//getting users list
            //toastMessage(ds.child("username").getValue().toString());
          array.add(ds.child("userid").getValue().toString()+"        "+ds.child("username").getValue().toString()+"        "+ds.child("fine").getValue().toString());


        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter);
    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
