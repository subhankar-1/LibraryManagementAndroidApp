package com.example.subhankar.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;


public class booklist extends AppCompatActivity {
    private  static  final  String TAG="booklist";
    private Firebase mRootRef;
    private FirebaseAuth mAuth;
    private  FirebaseAuth.AuthStateListener mAuthListener;
    private  String BOOKLIST;
    private ListView mListView;
    private String userID;
    private TextView tvName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRootRef=new Firebase("https://myproject-88e00.firebaseio.com/books");

        setContentView(R.layout.activity_booklist);
        mListView = (ListView) findViewById(R.id.listview);
        //TextView tvName = (TextView) findViewById(R.id.textView2);

        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //showing booklist
                showdata(dataSnapshot);

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });



    }
    private  void  showdata(DataSnapshot dataSnapshot){
        List<String> keys=new ArrayList<>();
        ArrayList<String> array= new ArrayList<>();
        array.add(" id          name        author      quantity");
        for(DataSnapshot ds:dataSnapshot.getChildren()){

            keys.add(ds.getKey());

            //bookList bookinfo=new bookList();
           // bookinfo.setBookid(ds.child("bookid").getValue().toString());
           // bookinfo.setBookname(ds.child("bookname").getValue().toString());



           //Log.d(TAG,"showData:bookid"+bookinfo.getBookid());
           // Log.d(TAG,"showData:name"+bookinfo.getBookname());

            array.add(ds.child("bookid").getValue().toString()+"        "+ds.child("bookname").getValue().toString()+"     "+ds.child("author").getValue().toString()+"          "+ds.child("count").getValue().toString());



        }
        ArrayAdapter<String>  adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,array);
        mListView.setAdapter(adapter);
    }
    private void toastMessage(String  message){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

}
