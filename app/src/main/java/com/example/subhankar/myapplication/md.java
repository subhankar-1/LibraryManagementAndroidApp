package com.example.subhankar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class md extends AppCompatActivity {

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
                /*case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;*/
                case R.id.navigation_dashboard:
                    Intent intent=new Intent(".user");
                    startActivity(intent);
                    return true;
               /* case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md);
        mListView = (ListView) findViewById(R.id.pd);
        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        final Query query=mRootRef.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // showdata(dataSnapshot);
                ArrayList<String> array= new ArrayList<>();
                for (DataSnapshot data:dataSnapshot.getChildren()){

                    array.add("ID:    "+data.child("userid").getValue().toString());
                    array.add("name:    "+data.child("username").getValue().toString());
                    array.add("email:    "+data.child("email").getValue().toString());
                    array.add("password:    "+data.child("password").getValue().toString());
                    array.add("fine:    "+data.child("fine").getValue().toString());
                    showdata(array);

                }


            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }



        });


        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    private void showdata(ArrayList<String> array){
        mListView = (ListView) findViewById(R.id.pd);
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter);

    }

}
