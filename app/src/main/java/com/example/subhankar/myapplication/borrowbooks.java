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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class borrowbooks extends AppCompatActivity {
    public String key,str1;
    private int result=0,c=1;
    public Firebase statusRef;
    private TextView mTextMessage;
  private Firebase mRootRef,mRootRef2,mRootRef3;
    private EditText mValueField1,mValueField2,mValueField3;
    private static Button button_sub,button_sub2;
    private boolean click=FALSE,click2=FALSE;
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
        setContentView(R.layout.activity_borrowbooks);
        mValueField1=(EditText) findViewById(R.id.bookID);
        mValueField2=(EditText) findViewById(R.id.id);
        mValueField3=(EditText) findViewById(R.id.u);
        mRootRef2 = new Firebase("https://myproject-88e00.firebaseio.com/users" );
        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/users");
        mRootRef3=new Firebase("https://myproject-88e00.firebaseio.com/books");
        button_sub=(Button)findViewById(R.id.borrow);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        OnClickButtonListener();

        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

    }
    public  void OnClickButtonListener() {
        button_sub2 = (Button) findViewById(R.id.ub);
        button_sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c=1;
                String value1 = mValueField3.getText().toString();
                if(value1.isEmpty())
                    toastMessage("Enter userID");
                else{
              //  mRootRef2 = new Firebase("https://myproject-88e00.firebaseio.com/users/" );
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
                    click2=TRUE;
                    toastMessage("UserID submitted");}





            }
        });



        button_sub=(Button)findViewById(R.id.borrow);
        button_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //toastMessage(mRootRef2.toString());
                if(click2==FALSE) {
                    toastMessage("Submit userID first");
                }
                else{
//verifying user
                if(new String(mRootRef.toString()).equals(mRootRef2.toString())){


                    //toastMessage(mRootRef2.toString());
                    toastMessage("UserID invalid:User doesnot exists");
                    click=FALSE;

                }
                else{
                   // click=TRUE;

                String value1=mValueField1.getText().toString();
                String value2=mValueField2.getText().toString();


                final Query query = mRootRef3.orderByChild("bookid").equalTo(value1);
                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String value1=mValueField1.getText().toString();
                        String value2=mValueField2.getText().toString();


                        //toastMessage(dataSnapshot.toString());
                        for (DataSnapshot data : dataSnapshot.getChildren()) {

                            final String v=data.child("count").getValue().toString();
                             statusRef = data.child("count").getRef();
                             result = Integer.parseInt(v);



                        }



                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }



                });

//checking availability of book
               if (result>0 ){
                    result--;
                    str1 = Integer.toString(result);
                    //toastMessage(str1);
                    //statusRef.setValue(str1);
                   SimpleDateFormat sdf = new SimpleDateFormat("dd MM yyyy");
                   try {

                       Date date = sdf.parse(value2);

                         if(!value2.equals(sdf.format(date))) {

                           toastMessage("Enter valid date format");
                       }
//if available pushing the book id in user as foreign key to represent books borrowed
                       else{
                           HashMap<String,String> datamap=new HashMap<String, String>();
                           datamap.put("bookid",value1);
                           datamap.put("issue_date",value2);
                           mRootRef2.push().setValue(datamap);




                           statusRef.setValue(str1);
                           toastMessage("book Successfully borrowed");
                           Intent intent=new Intent(".admin");
                           startActivity(intent);
                       }

                   } catch (ParseException ex) {
                       ex.printStackTrace();
                   }


                }
               else if(c==2){
                    toastMessage("SORRY!Book out of stock!");
                   Intent intent=new Intent(".admin");
                   startActivity(intent);
               }

                else
                    toastMessage("Click again!");







            c++;
               }
                }
            }
        });

    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
