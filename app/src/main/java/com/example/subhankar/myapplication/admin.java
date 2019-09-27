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

import com.firebase.client.Firebase;

public class admin extends AppCompatActivity {
    private static Button button_sub;
    private static Button button_sub2;
    private static Button button_sub3,button_sub4,button_sub5,button_sub6,button_sub7,button_sub8;
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            /*switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);
                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
                    return true;
            }*/
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        button_sub=(Button)findViewById(R.id.adduser);
        button_sub2=(Button)findViewById(R.id.addbook);
        button_sub3=(Button)findViewById(R.id.cbooklist);
        mTextMessage = (TextView) findViewById(R.id.message);

        OnClickButtonListener();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public  void OnClickButtonListener() {
        button_sub=(Button)findViewById(R.id.adduser);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".adduser");
                startActivity(intent);
            }
        });
        button_sub2=(Button)findViewById(R.id.addbook);
        button_sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".addbook");
                startActivity(intent);
            }
        });
        button_sub3=(Button)findViewById(R.id.cbooklist);
        button_sub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".booklist");
                startActivity(intent);
            }
        });
        button_sub4=(Button)findViewById(R.id.booksborrowed);
        button_sub4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".uid");
                startActivity(intent);
            }
        });
        button_sub5=(Button)findViewById(R.id.rb);
        button_sub5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".returnbook");
                startActivity(intent);
            }
        });
        button_sub6=(Button)findViewById(R.id.borrow);
        button_sub6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".borrowbooks");
                startActivity(intent);
            }
        });
        button_sub7=(Button)findViewById(R.id.ul);
        button_sub7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".ussers");
                startActivity(intent);
            }
        });
        button_sub8=(Button)findViewById(R.id.fp);
        button_sub8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(".finepay");
                startActivity(intent);
            }
        });

    }

}
