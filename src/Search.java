package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Search extends AppCompatActivity {


    ArrayList array = new ArrayList<String>();
    ArrayAdapter adapter;

    String  username = "", dt="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        DbConnection mydb = new DbConnection(this);
        final ListView list = findViewById(R.id.list);
        TextView empty = findViewById(R.id.empty);
        EditText getuser = findViewById(R.id.txt_edit_searchUser);
        empty.setVisibility(View.GONE);

        //Load available timestamps
        Cursor res= mydb.getColums(DbConnection.col_dt);

        //Adapter for adding the timestamps to the list
        adapter=new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                array);
        list.setAdapter(adapter);

        //Clear list and loading it again with dts
        adapter.clear();
        adapter.notifyDataSetChanged();

        //If db not empty, loading timestamps into the list
        if (res.getCount()==0){
            empty.setVisibility(View.VISIBLE);
            empty.setTextColor(Color.RED);

            list.setVisibility(View.GONE);
            findViewById(R.id.searchText1).setVisibility(View.GONE);
            findViewById(R.id.searchText2).setVisibility(View.GONE);
            findViewById(R.id.btn_search).setVisibility(View.GONE);
            findViewById(R.id.txt_edit_searchUser).setVisibility(View.GONE);
        }else {
            while (res.moveToNext()) {
                array.add(res.getString(res.getColumnIndex(DbConnection.col_dt)));
            }}
            adapter.notifyDataSetChanged();

            //Selecting timestamp from list
            list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedItem = (String) parent.getItemAtPosition(position);
                    Toast.makeText(Search.this, "Selected : " + selectedItem, Toast.LENGTH_SHORT).show();
                    dt = selectedItem;
                }
            });


            //Confirm search
            findViewById(R.id.btn_search).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    username = getuser.getText().toString();

                    if (username.isEmpty() && dt.isEmpty()) {
                        Toast.makeText(Search.this, "You have to enter a Username or a Timestamp in order to search the database.", Toast.LENGTH_SHORT).show();
                    } else {//If arguments were given, perform search

                        Intent intent = new Intent(getApplicationContext(), Results.class);
                        intent.putExtra("userID", username);
                        intent.putExtra("dt", dt);
                        startActivity(intent);


                    }

                }
            });

        //Go back
        findViewById(R.id.btn_goto_insertDB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

    }
}