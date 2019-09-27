package com.example.subhankar.myapplication;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class adminlogin extends AppCompatActivity {

    private TextView mTextMessage;
    private Firebase mRootRef;
    private EditText mValueField1;
    private EditText mValueField2;
    private static Button button_sub;
    private FirebaseAuth mAuth;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
               /*  case R.id.navigation_home:
                    //mTextMessage.setText(R.string.title_home);
                    Intent intent2=new Intent(".booklist");
                    startActivity(intent2);
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
        setContentView(R.layout.activity_adminlogin);
        mValueField1=(EditText) findViewById(R.id.email);
        mValueField2=(EditText) findViewById(R.id.apass);
        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/");
        button_sub=(Button)findViewById(R.id.alogin);
        mAuth = FirebaseAuth.getInstance();
        OnClickButtonListener();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public  void OnClickButtonListener() {
        button_sub=(Button)findViewById(R.id.alogin);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String value1=mValueField1.getText().toString();
                 String value2=mValueField2.getText().toString();
                 // using firebase authentication
                 if(!value1.isEmpty() && !value2.isEmpty() && new String(value1).equals("shuvo23paul@gmail.com")) {

                     mAuth.signInWithEmailAndPassword(value1, value2).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                         @Override
                         public void onComplete(@NonNull Task<AuthResult> task) {
                             if (task.isSuccessful()) {
                                 toastMessage("Success");
                                 Intent intent = new Intent(".admin");
                                 startActivity(intent);

                             } else {
                                 toastMessage("Invalid");
                             }

                         }
                     });

                 }else toastMessage("Enter email & password");
            }
        });

    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
