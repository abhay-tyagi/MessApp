package com.example.abhay.messapp;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.time.LocalDate;

public class StudentViewActivity extends AppCompatActivity {

    private MessDBHandler messDBHandler;
    public static final String TAG = "com.example.abhay.messapp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String q = "SELECT * FROM USERS WHERE USERNAME = '" + getIntent().getStringExtra("studentName") + "';";
        Log.i(TAG, q);

        messDBHandler = new MessDBHandler(StudentViewActivity.this, null);

        Cursor c = messDBHandler.getResult(q);

        if(!c.isAfterLast()) {
            Log.i(TAG, "NOT EMPTY");

            TextView studentname = findViewById(R.id.studentname);
            TextView studentyear = findViewById(R.id.studentyear);
            TextView studentbranch = findViewById(R.id.studentbranch);
            TextView studentcourse = findViewById(R.id.studentcourse);
            TextView studentmess = findViewById(R.id.studentmess);

            studentname.setText(c.getString(c.getColumnIndex("USERNAME")));
            studentyear.setText(Integer.toString(c.getInt(c.getColumnIndex("YEAR"))));
            studentcourse.setText(c.getString(c.getColumnIndex("COURSE")));
            studentbranch.setText(c.getString(c.getColumnIndex("BRANCH")));

            q = "SELECT * FROM MESSES WHERE MESSID = '" + c.getString(c.getColumnIndex("MESS")) + "';";
            Log.i(TAG, q);

            Cursor cc = messDBHandler.getResult(q);

            if(!cc.isAfterLast()) {
                studentmess.setText(cc.getString(cc.getColumnIndex("MESSNAME")));
            }
            else {
                Log.i(TAG, "EMPTY CC");
            }

            q = "SELECT * FROM BILLS WHERE BILLYEAR = " + LocalDate.now().getYear() + " AND BILLMONTH = " + LocalDate.now().getMonthValue() + " AND USER = '" + getIntent().getStringExtra("studentName") + "'" + ";";
            Cursor ccc = messDBHandler.getResult(q);
            int totalBill = 0, totalExtra = 0;

            while(!ccc.isAfterLast()) {
                totalBill += ccc.getInt(ccc.getColumnIndex("MAINCOST"));
                totalExtra += ccc.getInt(ccc.getColumnIndex("EXTRASCOST"));

                ccc.moveToNext();
            }

            TextView studentbill = findViewById(R.id.studentbill);
            studentbill.setText(Integer.toString(totalBill));
            TextView studentextra = findViewById(R.id.studentextra);
            studentextra.setText(Integer.toString(totalExtra));
        }
        else {
            Log.i(TAG, "EMPTY");
        }


    }

}
