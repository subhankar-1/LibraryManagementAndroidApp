package com.example.subhankar.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class adduser extends AppCompatActivity {
    private  static  final  String TAG="adduser";
    private TextView mTextMessage;
    private FirebaseAuth mAuth;
    private EditText mValueField1;
    private EditText mValueField2;
    private EditText mValueField3,mValueField4,mValueField5;
    private static Button button_sub;
    private Firebase mRootRef;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
               /* case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;*/
                case R.id.navigation_dashboard:
                   // mTextMessage.setText(R.string.title_dashboard);
                    Intent intent=new Intent(".admin");
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
        setContentView(R.layout.activity_adduser);
        mValueField1=(EditText) findViewById(R.id.usrname);
        mValueField2=(EditText) findViewById(R.id.userpass);
        mValueField3=(EditText) findViewById(R.id.useremail);
        mValueField4=(EditText) findViewById(R.id.uid);
        mValueField5=(EditText) findViewById(R.id.f);
        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/users");
        button_sub=(Button)findViewById(R.id.buttonadduser);
        mTextMessage = (TextView) findViewById(R.id.message);
        mAuth = FirebaseAuth.getInstance();
        String value1=mValueField1.getText().toString();
        String value2=mValueField2.getText().toString();
        OnClickButtonListener();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public  void OnClickButtonListener() {
        button_sub=(Button)findViewById(R.id.buttonadduser);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value1=mValueField1.getText().toString();
                String value2=mValueField2.getText().toString();
                String value3=mValueField3.getText().toString();
                String value4=mValueField4.getText().toString();
                String value5=mValueField5.getText().toString();


                if(!value1.isEmpty() && !value2.isEmpty() && !value3.isEmpty() && !value4.isEmpty() && !value5.isEmpty()){
                mAuth.createUserWithEmailAndPassword(value3,value2).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        if(task.isSuccessful()){
                            toastMessage("Success");
                            Intent intent=new Intent(".admin");
                            startActivity(intent);

                        }
                        else{
                            toastMessage("Invalid");
                        }

                    }
                });
                //adding user to database
                HashMap<String,String> datamap=new HashMap<String, String>();
                datamap.put("username",value1);
                datamap.put("password",value2);
                datamap.put("email",value3);
                datamap.put("userid",value4);
                datamap.put("fine",value5);
                mRootRef.push().setValue(datamap);
                toastMessage("User added successfully");
                    Intent intent=new Intent(".admin");
                    startActivity(intent);}
                else
                    toastMessage("Enter all fields");

            }


        });


    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
