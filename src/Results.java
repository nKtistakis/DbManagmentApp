package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Results extends AppCompatActivity {

    //String dt = "", username = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        TableLayout table = findViewById(R.id.table);
        DbConnection mydb = new DbConnection(this);


        String username = getIntent().getStringExtra("userID");
        String dt = getIntent().getStringExtra("dt");

        Cursor res;
        if (username.isEmpty()) {//Search only with timestamp
            res = mydb.searchData(DbConnection.col_dt, dt);
        } else if (dt.isEmpty()) {//Search only with username
            res = mydb.searchData(DbConnection.col_userID, username);
        } else {//Search with both username and timestamp
            res = mydb.searchData(DbConnection.col_userID, username, DbConnection.col_dt, dt);
        }



        //res = mydb.searchData(DbConnection.col_userID, username);

        TextView no_res = findViewById(R.id.no_res);
        no_res.setVisibility(View.GONE);

        if (res.getCount() == 0) {
            table.setVisibility(View.GONE);
            no_res.setVisibility(View.VISIBLE);

        } else {
            while (res.moveToNext()) {
                table.setVisibility(View.VISIBLE);

                TableRow trow = new TableRow(Results.this);


                TextView txt1 = new TextView(Results.this);
                txt1.setText(res.getString(res.getColumnIndex(DbConnection.col_id)));
                txt1.setPadding(0, 0, 0, 0);
                txt1.setGravity(Gravity.CENTER);
                trow.addView(txt1);


                TextView txt2 = new TextView(Results.this);
                txt2.setText(res.getString(res.getColumnIndex(DbConnection.col_userID)));
                txt2.setPadding(0, 0, 0, 0);
                txt2.setGravity(Gravity.CENTER);
                trow.addView(txt2);


                TextView txt3 = new TextView(Results.this);
                txt3.setText(res.getString(res.getColumnIndex(DbConnection.col_lng)));
                txt3.setPadding(0, 0, 0, 0);
                txt3.setGravity(Gravity.CENTER);
                trow.addView(txt3);


                TextView txt4 = new TextView(Results.this);
                txt4.setText(res.getString(res.getColumnIndex(DbConnection.col_lat)));
                txt4.setPadding(0, 0, 0, 0);
                txt4.setGravity(Gravity.CENTER);
                trow.addView(txt4);

                TextView txt5 = new TextView(Results.this);
                txt5.setText(res.getString(res.getColumnIndex(DbConnection.col_dt)));
                txt5.setPadding(0, 0, 0, 0);
                txt5.setGravity(Gravity.CENTER);
                trow.addView(txt5);

                table.setColumnStretchable(1, true);

                table.addView(trow);



            }

        }
        //GO BACK
            findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                }
            });

    }
}