package com.example.subhankar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class uid extends AppCompatActivity {

    private TextView mTextMessage;
    private EditText mValueField1;
    private static Button button_sub,button_sub2;
    private Firebase mRootRef,mRootRef2;
    private ListView mListView;
    public boolean click=FALSE;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                /*case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;*/
                case R.id.navigation_dashboard:
                    Intent intent=new Intent(".admin");
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
        setContentView(R.layout.activity_uid);
        mValueField1=(EditText) findViewById(R.id.uid);
        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/users");

        mListView = (ListView) findViewById(R.id.x);
        OnClickButtonListener();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public  void OnClickButtonListener() {
        button_sub = (Button) findViewById(R.id.c);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 click=TRUE;
                mRootRef2=new Firebase("https://myproject-88e00.firebaseio.com/users");
                String value1 = mValueField1.getText().toString();
                if(!value1.isEmpty()) {
                    final Query query = mRootRef.orderByChild("userid").equalTo(value1);
                    query.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            //toastMessage(dataSnapshot.toString());
                            for (DataSnapshot data : dataSnapshot.getChildren()) {

                                String key = data.getKey();

                                mRootRef2 = new Firebase("https://myproject-88e00.firebaseio.com/users/" + key);



                            }


                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }


                    });
                    toastMessage("UserID submitted click viewList to see books borrowed");

                }
                else toastMessage("Enter userID");


            }
        });

        button_sub2 = (Button) findViewById(R.id.but);
        button_sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value1 = mValueField1.getText().toString();

               // final Query query = mRootRef2.orderByChild("userid").equalTo(value1);



                if(click==FALSE){
                    toastMessage("Submit userID first");
                    show();
                }
                else if(click && new String(mRootRef.toString()).equals(mRootRef2.toString()) ){
                    toastMessage("Invalid userID :User Doesnot exist");
                    click=FALSE;
                    show();

                }
                else{
                mRootRef2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        //toastMessage(dataSnapshot.toString());
                        click=FALSE;
                        showdata(dataSnapshot);


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }


                });
            }
            }


        });
    }

    private  void  showdata(DataSnapshot dataSnapshot){
        ArrayList<String> array= new ArrayList<>();

        array.add("LIST ");
        for(DataSnapshot ds:dataSnapshot.getChildren()){

            //toastMessage(ds.toString());

            if(ds.getKey().toString()!="email" && ds.getKey().toString()!="password" && ds.getKey().toString()!="username" && ds.getKey().toString()!="userid" && ds.getKey().toString()!="" && ds.getKey().toString()!="fine" ){
                
                array.add(ds.getValue().toString());
            }



        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter);
    }
    private void show(){
        ArrayList<String> array= new ArrayList<>();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter);

    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
