package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button btn_submit = findViewById(R.id.btn_submit);
        final Button btn_goto_searchDB = findViewById(R.id.btn_goto_searchDB);
        final EditText get_user = findViewById(R.id.txt_edit_user);
        final EditText get_lng = findViewById(R.id.txt_edit_lng);
        final EditText get_lat = findViewById(R.id.txt_edit_lat);
        DbConnection mydb = new DbConnection(this);

        //Submit data to database, if inserted correctly.
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = get_user.getText().toString();
                float lng=0,lat=0;
             try {
                lng = Float.parseFloat(get_lng.getText().toString());
                lat = Float.parseFloat(get_lat.getText().toString());
             } catch(NumberFormatException nfe) {
                 Log.d("float","error");
             }

                //mydb.insertData("nikos",12,31,"31skd");

                //Getting the values from the editTexts
                if (user.isEmpty() || lng == 0 || lat == 0){
                   Toast.makeText(MainActivity.this, "You cannot leave a field empty", Toast.LENGTH_SHORT).show();
                } else {
                    long mils = Calendar.getInstance().getTimeInMillis();
                    Timestamp dt = new Timestamp(mils);

                    //Toast.makeText(MainActivity.this, dt.toString(), Toast.LENGTH_SHORT).show();
                    if( mydb.insertData(user,lng,lat,dt.toString()) == true) {


                        Toast.makeText(MainActivity.this, "Data was inserted to database", Toast.LENGTH_SHORT).show();
                        get_user.setText("");
                        get_lng.setText("0");
                        get_lat.setText("0");
                    }else{
                        Toast.makeText(MainActivity.this, "Unexpected error while inserting data to the database", Toast.LENGTH_SHORT).show();
                    }




                }
            }
        });


        //Go to search tab
        btn_goto_searchDB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(),Search.class);
                startActivity(intent);
            }
        });
















    }
}