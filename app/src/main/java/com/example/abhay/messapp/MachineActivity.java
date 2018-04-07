package com.example.abhay.messapp;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;


public class MachineActivity extends AppCompatActivity {

    public static final String TAG = "com.example.abhay.messapp";
    MessDBHandler messDBHandler;
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machine);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        messDBHandler = new MessDBHandler(MachineActivity.this, null);
    }

    public void scanQR(View v) {
        try {
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(MachineActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                String format = intent.getStringExtra("SCAN_RESULT_FORMAT");
                Toast toast = Toast.makeText(this, "Content:" + contents + " Format:" + format, Toast.LENGTH_LONG);
                toast.show();

                SharedPreferences sharedPref = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                sharedPref.getString("username", "");


                if(true) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    Date now = new Date();
                    String strDate = sdf.format(now);
                    String[] dateFull = strDate.split("/");

                    SimpleDateFormat stf = new SimpleDateFormat("HH:mm:ss");
                    String strTime = stf.format(now);
                    String[] timeFull = strTime.split(":");

                    ScanInstance scanInstance = new ScanInstance();
                    scanInstance.setUserScanner(contents);
                    scanInstance.setScanDate(new Date(Integer.parseInt(dateFull[0]), Integer.parseInt(dateFull[1]), Integer.parseInt(dateFull[0])));
                    scanInstance.setScanTime(Integer.parseInt(timeFull[0]));

                    String q = "SELECT * FROM USERS WHERE USERNAME" + "=" + contents + ";";
                    Log.i(TAG, q);
                    Cursor c = messDBHandler.getResult(q);

                    Log.i(TAG, "MESS IS " + Integer.toString(c.getInt(c.getColumnIndex("MESS"))));

                    q = "SELECT * FROM MESSES WHERE MESSID" + "=\"" + Integer.toString(c.getInt(c.getColumnIndex("MESS"))) + "\";";
                    c = messDBHandler.getResult(q);

                    Log.i(TAG, "CAPACITY IS " + Integer.toString(c.getInt(c.getColumnIndex("CAPACITY"))));

                    Mess m = new Mess();
                    m.setCapacity(c.getInt(c.getColumnIndex("CAPACITY")));
                    m.setNonvegCost(c.getInt(c.getColumnIndex("NONVEGCOST")));
                    m.setDailyCost(c.getInt(c.getColumnIndex("DAILYCOST")));
                    m.setMessHead(c.getString(c.getColumnIndex("MESSHEAD")));
                    m.setMessName(c.getString(c.getColumnIndex("MESSNAME")));
                    m.setMessId(c.getInt(c.getColumnIndex("MESSID")));

                    scanInstance.setMess(m);
                    messDBHandler.addScanInstance(scanInstance);

                    String timePeriod;

                    if(scanInstance.getScanTime() >= 6 && scanInstance.getScanTime() <= 9)
                        timePeriod = "BREAKFASTTOTAL";
                    else if(scanInstance.getScanTime() >= 11 && scanInstance.getScanTime() <= 14)
                        timePeriod = "LUNCHTOTAL";
                    else if(scanInstance.getScanTime() >= 6 && scanInstance.getScanTime() <= 21)
                        timePeriod = "DINNERTOTAL";
                    else
                        timePeriod = "";

                    q = "SELECT * FROM MESSINSTANCES WHERE MIDATE = " + strDate + " AND MESS" + "=\"" + Integer.toString(m.getMessId()) + "\";";
                    c = messDBHandler.getResult(q);


                    q = "UPDATE MESSINSTANCES SET " + timePeriod + " = " + timePeriod + "+1 WHERE MIDATE = " + Integer.toString(c.getInt(c.getColumnIndex(timePeriod))) + " AND MESS" + "=\"" + Integer.toString(m.getMessId()) + "\";";
                    messDBHandler.runUpdate(q);

                    q = "UPDATE BILLS SET MAINCOST = MAINCOST+" + Integer.toString(m.getDailyCost()) + " WHERE BILLYEAR = " + dateFull[0] + " AND BILLMONTH = " + dateFull[1] + " AND BILLDAY = " + dateFull[2] + ";";
                    messDBHandler.runUpdate(q);
                }
                else {
                    Toast.makeText(this, "Wrong Code", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}
