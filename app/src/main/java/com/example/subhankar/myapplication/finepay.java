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

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class finepay extends AppCompatActivity {

    private TextView mTextMessage;
    private  static  final  String TAG="returnbook";

    private Firebase mRootRef,mRootRef2,mRootRef3;
    private EditText mValueField1,mValueField2,mValueField3;
    private static Button button_sub,button_sub2,button_sub3;
    private TextView textView;
    private String i;
    private int c,x=1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
               /* case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;*/
                case R.id.navigation_dashboard:
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
        setContentView(R.layout.activity_finepay);

        mValueField1=(EditText) findViewById(R.id.fuid);
        mValueField2=(EditText) findViewById(R.id.fa);
        textView=(TextView) findViewById(R.id.ack);

        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/users");
        mRootRef2 = new Firebase("https://myproject-88e00.firebaseio.com/users" );
        button_sub=(Button)findViewById(R.id.fuidb);
        button_sub2=(Button)findViewById(R.id.fab);
        OnClickButtonListener();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public  void OnClickButtonListener() {
        button_sub = (Button) findViewById(R.id.fuidb);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=1;
                mRootRef2 = new Firebase("https://myproject-88e00.firebaseio.com/users");
                String value1 = mValueField1.getText().toString();
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
                toastMessage("UserID submitted");

            }
        });
        button_sub2 = (Button) findViewById(R.id.fab);
        button_sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value2 = mValueField2.getText().toString();

                if (new String(mRootRef.toString()).equals(mRootRef2.toString())) {


                    //toastMessage(mRootRef2.toString());
                    toastMessage("UserID invalid:User doesnot exists");
                    textView=(TextView) findViewById(R.id.ack);
                    textView.setText("");

                } else {
                    if (value2.isEmpty()){
                        toastMessage("Enter amount paid");
                        textView=(TextView) findViewById(R.id.ack);
                        textView.setText("");
                    }
                    else{

                    mRootRef2.child("fine").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            i =  dataSnapshot.getValue().toString();//getting previous fine

                            // do your stuff here with value

                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {

                        }
                    });
                    if(c<2)
                        toastMessage("Click again!");
                    if(c==2) {
                        //toastMessage(i);
                        Float initialfine = Float.parseFloat(i);
                         Float paid=Float.parseFloat(value2);
                       textView=(TextView) findViewById(R.id.ack);
                        if(initialfine-paid<0) {
                            mRootRef2.child("fine").setValue("0");//updaing after payment
                            textView.setText("Amount paid successfully  Rs." + value2 + "\nFine pending Rs.0.0");
                        }
                        else {
                            mRootRef2.child("fine").setValue(Float.toString(initialfine - paid));//updaing after payment
                            textView.setText("Amount paid successfully Rs." + value2 + "\nFine pending Rs." + Float.toString(initialfine - paid));
                        }

                    }

               c++; }
                }

            }
        });
    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
