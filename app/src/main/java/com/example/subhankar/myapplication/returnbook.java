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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.text.ParseException;
import java.util.List;

import static java.lang.Boolean.FALSE;

public class returnbook extends AppCompatActivity {
    private  static  final  String TAG="returnbook";
    private TextView mTextMessage;
    private Firebase mRootRef,mRootRef2,mRootRef3;
    private EditText mValueField1,mValueField2,mValueField3;
    private static Button button_sub,button_sub2,button_sub3;
    private TextView textView;
    public  int result,c=1;
    public  float initialfine;
    public Firebase statusRef,statusRef2;
    public String str,str1,i;
    public int x=1;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
              /*  case R.id.navigation_home:
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
        setContentView(R.layout.activity_returnbook);
        mValueField1=(EditText) findViewById(R.id.us);
        mValueField2=(EditText) findViewById(R.id.bi);
        mValueField3=(EditText) findViewById(R.id.rd);
        textView=(TextView) findViewById(R.id.TV);

        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/users");
        mRootRef2 = new Firebase("https://myproject-88e00.firebaseio.com/users" );
        button_sub=(Button)findViewById(R.id.su);
        button_sub2=(Button)findViewById(R.id.rb);
        button_sub3=(Button)findViewById(R.id.sb);
        OnClickButtonListener();
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }
    public  void OnClickButtonListener() {
        button_sub = (Button) findViewById(R.id.su);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mRootRef2 = new Firebase("https://myproject-88e00.firebaseio.com/users" );
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
        button_sub3 = (Button) findViewById(R.id.sb);
        button_sub3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(new String(mRootRef.toString()).equals(mRootRef2.toString())){


                    //toastMessage(mRootRef2.toString());
                    toastMessage("UserID invalid:User doesnot exists");


                }
                else{
                    c++;
                    if(c>3)
                        c=3;
                String value1 = mValueField1.getText().toString();
                mValueField2=(EditText) findViewById(R.id.bi);
                mRootRef3 = new Firebase("https://myproject-88e00.firebaseio.com/books/");
                final String value2=mValueField2.getText().toString();
                final Query query = mRootRef3.orderByChild("bookid").equalTo(value2);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        //toastMessage(dataSnapshot.toString());
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            final String v=data.child("count").getValue().toString();

                           statusRef = data.child("count").getRef();
                            result = Integer.parseInt(v);
                            result++;
                            str = Integer.toString(result);


                        }//toastMessage(Integer.toString(c));


                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }


                });

                if(c==3 ){

                toastMessage("bookID submitted");
                if(x==1)
                statusRef.setValue(str);
                x=0;
                mRootRef3=mRootRef;
                    }
                    if(c<3) {
                toastMessage("click again !");
                }
                }

            }
        });



        button_sub2=(Button)findViewById(R.id.rb);
        button_sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!new String(mRootRef.toString()).equals(mRootRef3.toString()))
                    toastMessage("Submit bookID first");
                else{

               final String value2=mValueField2.getText().toString();
                final String value3=mValueField3.getText().toString();
                Query applesQuery = mRootRef2.orderByChild("bookid").equalTo(value2);
                // String i=mRootRef2.child("fine").toString();
                mRootRef2.child("fine").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        i =  dataSnapshot.getValue().toString();

                        // do your stuff here with value

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        SimpleDateFormat myFormat = new SimpleDateFormat("dd MM yyyy");

                        ArrayList<String> array= new ArrayList<>();
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                           final String v=appleSnapshot.child("issue_date").getValue().toString();

                            //toastMessage(i);
                           // statusRef2=appleSnapshot.child("fine").getRef();

                            initialfine = Float.parseFloat(i);

                           try {
                               fine f=new fine();
                               Date dateBefore = myFormat.parse(v);
                               Date dateAfter = myFormat.parse(value3);
                               if(!value3.equals(myFormat.format(dateAfter)))
                                   toastMessage("Enter a valid date format");
                               else{
                                   long difference = dateAfter.getTime() - dateBefore.getTime();
                               f.setId(v);
                               f.setRd(value3);
                               str1 = Float.toString(initialfine);
                               float daysBetween = (difference / (1000*60*60*24));
                               if(daysBetween>15.0) {
                                   float fine = (daysBetween - 15) * 5;
                                   initialfine+=fine;
                                   String numberAsString = Float.toString(daysBetween-15);
                                   f.setD(numberAsString);
                                   String fin = Float.toString(fine);
                                   array.add("Fine @ Rs.5/day:" + fin);
                                  str1 = Float.toString(initialfine);

                               //toastMessage(numberAsString);
                               Log.d(TAG,"showData:fin"+fin);
                               f.setFin(fin);
                               textView=(TextView) findViewById(R.id.TV);
                               textView.setText("issue date:     "+v+"\nNo. of extra days:   "+numberAsString+ "\nFine @ Rs.5/day:    Rs." +fin+"\nYour Total fine:   "+str1);
                               }
                               else{
                                   textView=(TextView) findViewById(R.id.TV);
                                   textView.setText("Hurray!!No extra days hence no fine."+"\nYour Total fine:   "+str1);
                               }



                                   appleSnapshot.getRef().removeValue();
                                   // toastMessage(str1);
                                   // statusRef2.setValue(str1);
                                   toastMessage(str1);
                                   //calculating fine automatically
                                   mRootRef2.child("fine").setValue(str1); }
                           }
                            catch (ParseException e) {
                                e.printStackTrace();
                            }






                        }



                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }}
        });

    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }


}
