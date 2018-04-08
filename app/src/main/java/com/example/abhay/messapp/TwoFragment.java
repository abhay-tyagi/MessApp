package com.example.abhay.messapp;

import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;


public class TwoFragment extends Fragment{

    ArrayList<String> messes = new ArrayList<String>();

    public static final String TAG = "com.example.abhay.messapp";
    MessDBHandler messDBHandler;
    private TextView textView1, textView2, textView3;
    private CalendarView calendarView;

    private static final String tableName = "MESSES";

    public TwoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_two, container, false);

        messDBHandler = new MessDBHandler(this.getActivity(), null);
        String query = "SELECT * FROM " + tableName + ";";
        Cursor c = messDBHandler.getResult(query);

        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex("MESSNAME")) != null) {
                messes.add(c.getString(c.getColumnIndex("MESSNAME")));
            }
            c.moveToNext();
        }

        calendarView = v.findViewById(R.id.messCalendar);

        final Spinner spinner = v.findViewById(R.id.messNameSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, messes);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        Date now = new Date();
//        String strDate = sdf.format(now);
//        String[] dateFull = strDate.split("/");

        try {
            setNumbersData("IFC B", v, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
        }catch (Exception e) {}
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//                        String selectedDate = sdf.format(new Date(calendarView.getDate()));
//                        String [] dateTopass = selectedDate.split("/");

                        try {
                            setNumbersData(parent.getSelectedItem().toString(), v, LocalDate.now().getYear(), LocalDate.now().getMonthValue(), LocalDate.now().getDayOfMonth());
                        } catch (Exception e) {}
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                }
        );

        calendarView.setOnDateChangeListener(
                new CalendarView.OnDateChangeListener() {
                    @Override
                    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                        try {
                            setNumbersData(spinner.getSelectedItem().toString(), v, year, month + 1, dayOfMonth);
                        }catch (Exception e) {}
                    }
                }
        );

        return v;
    }

    void setNumbersData(String mess, View v, int year, int month, int date) {
//        String s = LocalDate.of(year, month, date).toString();
//        Log.i(TAG, s);

        try {
        String query = "SELECT * FROM MESSES WHERE MESSNAME" + "=\"" + mess + "\";";
        Cursor c = messDBHandler.getResult(query);
        int messId = c.getInt(c.getColumnIndex("MESSID"));

        Log.i(TAG, (LocalDate.of(year, month, date)).toString());

            query = "SELECT * FROM MESSINSTANCES WHERE MESS =" + messId + " AND MIDATE" + "=\"" + LocalDate.of(year, month, date) + "\";";
            c = messDBHandler.getResult(query);


            Log.i(TAG, "Cursor: ");
            Log.i(TAG, Integer.toString(c.getInt(c.getColumnIndex("BREAKFASTTOTAL"))));

            String s1 = Integer.toString(c.getInt(c.getColumnIndex("BREAKFASTTOTAL")));
            String s2 = Integer.toString(c.getInt(c.getColumnIndex("LUNCHTOTAL")));
            String s3 = Integer.toString(c.getInt(c.getColumnIndex("DINNERTOTAL")));

            Log.i(TAG, s1);
            Log.i(TAG, s2);
            Log.i(TAG, s3);

            textView2 = v.findViewById(R.id.lnhNum);
            textView1 = v.findViewById(R.id.brkNum);
            textView3 = v.findViewById(R.id.dinNum);
            textView1.setText(s1);
            textView2.setText(s2);
            textView3.setText(s3);
        }
        catch(CursorIndexOutOfBoundsException e) {
            Log.i(TAG, e.getMessage());
        }
    }

}