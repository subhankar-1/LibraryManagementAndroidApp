package com.example.subhankar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class user extends AppCompatActivity {

    private TextView mTextMessage;
    private static Button button_sub;
    private static Button button_sub2;
    private static Button button_sub3;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                /*case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        OnClickButtonListener();
        //mValueField=(EditText) findViewById(R.id.editText5);
        button_sub=(Button)findViewById(R.id.booklist);
        button_sub2=(Button)findViewById(R.id.mybooks);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public  void OnClickButtonListener() {
        button_sub=(Button)findViewById(R.id.booklist);
        button_sub2=(Button)findViewById(R.id.mybooks);
        button_sub3=(Button)findViewById(R.id.mydetails);
       // button_sub3=(Button)findViewById(R.id.borrow);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".booklist");
                startActivity(intent);
            }
        });
        button_sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".borrowedbooks");
                startActivity(intent);
            }
        });
      button_sub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".md");
                startActivity(intent);
            }
        });



    }

}
