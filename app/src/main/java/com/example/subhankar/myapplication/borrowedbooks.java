package com.example.subhankar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;

public class borrowedbooks extends AppCompatActivity {
    private  static  final  String TAG="borrowedbooks";
    private Firebase mRootRef,mRootRef2;
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private  String BOOKLIST;
    private ListView mListView;
    private String userID;
    private TextView mTextMessage;
    private Button button_sub;

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
                /*case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/users");
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String emaill = user.getEmail();


        }
       final Query query=mRootRef.orderByChild("email").equalTo(user.getEmail());
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

               // showdata(dataSnapshot);
               for (DataSnapshot data:dataSnapshot.getChildren()){

                    String key=data.getKey();
                    mRootRef2=new Firebase("https://myproject-88e00.firebaseio.com/users/"+key);
                   OnClickButtonListener();

                }

            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }


        });

        setContentView(R.layout.activity_borrowedbooks);
        mListView = (ListView) findViewById(R.id.lv);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    public  void OnClickButtonListener() {
        button_sub=(Button)findViewById(R.id.vl);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

           mRootRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //toastMessage(dataSnapshot.toString());
                        showdata(dataSnapshot);





                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }


                });




            }
        });

    }
    private  void  showdata(DataSnapshot dataSnapshot){
        ArrayList<String> array= new ArrayList<>();
        array.add(" id  ");

        for(DataSnapshot ds:dataSnapshot.getChildren()){

            //toastMessage(ds.toString());

            if(ds.getKey().toString()!="email" && ds.getKey().toString()!="password" && ds.getKey().toString()!="username" && ds.getKey().toString()!="userid" && ds.getKey().toString()!="fine" ){
                array.add(ds.getValue().toString());
            }
            bookList bookinfo=new bookList();




        }

         ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter);
    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
