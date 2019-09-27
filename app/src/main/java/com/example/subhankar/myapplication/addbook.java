package com.example.subhankar.myapplication;
import  java.lang.*;
import java.util.HashMap;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
public class addbook extends AppCompatActivity {

    private TextView mTextMessage;
    private Firebase mRootRef;
    private EditText mValueField1;
    private EditText mValueField2;
    private EditText mValueField3;
    private EditText mValueField4;
    private static Button button_sub;
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
        setContentView(R.layout.activity_addbook);
        mValueField1=(EditText) findViewById(R.id.bookid);
        mValueField2=(EditText) findViewById(R.id.bookname);
        mValueField3=(EditText) findViewById(R.id.count);
        mValueField4=(EditText) findViewById(R.id.aut);
        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/books");
        button_sub=(Button)findViewById(R.id.buttonaddbook);
        OnClickButtonListener();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public  void OnClickButtonListener() {
        button_sub=(Button)findViewById(R.id.buttonaddbook);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value1=mValueField1.getText().toString();
                String value2=mValueField2.getText().toString();
                String value3=mValueField3.getText().toString();
                String value4=mValueField4.getText().toString();
//adding book to database
                if(!value1.isEmpty() && !value2.isEmpty() && !value3.isEmpty() && !value4.isEmpty() ){
                HashMap<String,String> datamap=new HashMap<String, String>();
                datamap.put("bookid",value1);
                datamap.put("bookname",value2);
                datamap.put("author",value4);
                datamap.put("count",value3);
                mRootRef.push().setValue(datamap);
                toastMessage("Book added successfully");

                Intent intent=new Intent(".admin");
                startActivity(intent);
                }
                else{
                    toastMessage("Enter all values");
                }

            }
        });

    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}


