package com.example.abhay.messapp;

import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class OneFragment extends Fragment{

    String[] tables = { "Mess", "User", "Bill", "Mess Instance", "Scan Instance"};
    Button button1, button2;
    MessDBHandler messDBHandler;
    public static final String TAG = "com.example.abhay.messapp";

    public OneFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        messDBHandler = new MessDBHandler(this.getActivity(), null);

        View v = inflater.inflate(R.layout.fragment_one, container, false);

        final Spinner spinner1 = v.findViewById(R.id.objectSpinner1);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, tables);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner1.setAdapter(adapter);

        button1 = v.findViewById(R.id.addButton);
        button1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String table = spinner1.getSelectedItem().toString();

                        if(table.equals("User"))
                            callAddDialog("USER");
                        else if(table.equals("Bill"))
                            callAddDialog("BILL");
                        else if(table.equals("Mess"))
                            callAddDialog("MESS");
                        else if(table.equals("Mess Instance"))
                            callAddDialog("MESSINSTANCE");
                        else if(table.equals("Scan Instance"))
                            callAddDialog("SCANINSTANCE");
                    }
                }
        );

        final Spinner spinner2 = v.findViewById(R.id.objectSpinner2);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner2.setAdapter(adapter);

        button2 = v.findViewById(R.id.viewButton);
        button2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String table = spinner2.getSelectedItem().toString();

                        if(table.equals("User"))
                            callViewDialog("USER");
                        else if(table.equals("Bill"))
                            callViewDialog("BILL");
                        else if(table.equals("Mess"))
                            callViewDialog("MESS");
                        else if(table.equals("Mess Instance"))
                            callViewDialog("MESSINSTANCE");
                        else if(table.equals("Scan Instance"))
                            callViewDialog("SCANINSTANCE");
                    }
                }
        );

        return v;
    }

    private void callViewDialog(String table) {
        String content = messDBHandler.getEntireTable(table);

        Intent i = new Intent(getActivity(), TableViewActivity.class);
        i.putExtra("content", content);
        i.putExtra("Table", table);
        startActivity(i);
    }

    private void callAddDialog(String table) {
        final Dialog myDialog = new Dialog(this.getActivity());

        if(table.equals("MESS"))
            myDialog.setContentView(R.layout.mess_form);
        else if(table.equals("BILL"))
            myDialog.setContentView(R.layout.bill_form);
        else if(table.equals("USER"))
            myDialog.setContentView(R.layout.user_form);
        else if(table.equals("MESSINSTANCE"))
            myDialog.setContentView(R.layout.messinstance_form);
        else if(table.equals("SCANINSTANCE"))
            myDialog.setContentView(R.layout.scaninstance_form);

        myDialog.setCancelable(true);

        final Button btn;

        if(table.equals("MESS"))
            btn = myDialog.findViewById(R.id.addMess);
        else if(table.equals("BILL"))
            btn = myDialog.findViewById(R.id.addBill);
        else if(table.equals("USER"))
            btn = myDialog.findViewById(R.id.addUser);
        else if(table.equals("MESSINSTANCE"))
            btn = myDialog.findViewById(R.id.addMessInstance);
        else if(table.equals("SCANINSTANCE"))
            btn = myDialog.findViewById(R.id.addScanInstance);
        else
            btn = null;

        myDialog.show();

        btn.setOnClickListener(new View.OnClickListener() {

            EditText messName, messHead, messCost, messCap, nonveg;
            EditText userName, userPass, userYear, userCourse, userBranch, userMess;
            EditText instanceMess, breakfast, lunch, dinner;
            EditText scanMess, userScanner;
            EditText billUser, billMainCost, billExtrasCost, billDate;

            @Override
            public void onClick(View v) {
                if(btn.getId() == R.id.addMess) {
                    messName = v.getRootView().findViewById(R.id.messName);
                    messHead = v.getRootView().findViewById(R.id.messHead);
                    messCost = v.getRootView().findViewById(R.id.messCost);
                    messCap = v.getRootView().findViewById(R.id.messCap);
                    nonveg = v.getRootView().findViewById(R.id.nonveg);

                    String name = messName.getText().toString().trim();
                    String head = messHead.getText().toString().trim();
                    String cost = messCost.getText().toString().trim();
                    String cap = messCap.getText().toString().trim();
                    String nvg = nonveg.getText().toString().trim();

                    if (!name.equals("") && !head.equals("") && !cost.equals("") && !cap.equals("") && !nvg.equals("")) {
                        Mess mess = new Mess();
                        mess.setMessName(name);
                        mess.setMessHead(head);
                        mess.setCapacity(Integer.parseInt(cap));
                        mess.setDailyCost(Integer.parseInt(cost));
                        mess.setNonvegCost(Integer.parseInt(nvg));

                        messDBHandler.addMess(mess);
                        myDialog.dismiss();
                    } else {
                        Toast.makeText(v.getContext(), "Fill the details", Toast.LENGTH_LONG).show();
                    }
                }
                else if(btn.getId() == R.id.addBill) {
                    billUser = v.getRootView().findViewById(R.id.billUser);
                    billMainCost = v.getRootView().findViewById(R.id.billMainCost);
                    billExtrasCost = v.getRootView().findViewById(R.id.billExtrasCost);
                    billDate = v.getRootView().findViewById(R.id.billDate);

                    String user = billUser.getText().toString().trim();
                    String mainCost = billMainCost.getText().toString().trim();
                    String extrasCost = billExtrasCost.getText().toString().trim();
                    String date = billDate.getText().toString().trim();

                    if (!user.equals("") && !mainCost.equals("") && !extrasCost.equals("") && !date.equals("")) {
                        String q = "SELECT * FROM USERS WHERE USERNAME" + "=\"" + user + "\";";
                        Cursor c = messDBHandler.getResult(q);

                        User u = new User();

                        while (!c.isAfterLast()) {
                            if (c.getString(c.getColumnIndex("USERNAME")).equals(user)) {
                                u.setUsername(user);
                                u.setPassword(c.getString(c.getColumnIndex("PASSWORD")));
                                u.setYear(c.getInt(c.getColumnIndex("YEAR")));
                                u.setCourse(c.getString(c.getColumnIndex("COURSE")));

                                String mess = c.getString(c.getColumnIndex("MESS"));

                                String qu = "SELECT * FROM MESSES WHERE MESSNAME" + "=\"" + mess + "\";";
                                Cursor cu = messDBHandler.getResult(qu);

                                Mess m = new Mess();

                                while (!cu.isAfterLast()) {
                                    if (cu.getString(c.getColumnIndex("MESSNAME")).equals(mess)) {
                                        m.setMessName(mess);
                                        m.setMessHead(c.getString(c.getColumnIndex("MESSHEAD")));
                                        m.setDailyCost(c.getInt(c.getColumnIndex("DAILYCOST")));
                                        m.setMessId(c.getInt(c.getColumnIndex("MESSID")));
                                        m.setNonvegCost(c.getInt(c.getColumnIndex("NONVEGCOST")));
                                        m.setCapacity(c.getInt(c.getColumnIndex("CAPACITY")));
                                        break;
                                    }
                                    cu.moveToNext();
                                }

                                u.setMess(m);
                                u.setBranch(c.getString(c.getColumnIndex("BRANCH")));

                                break;
                            }
                            c.moveToNext();
                        }

                        Bill bill = new Bill();

                        String[] dateFull = date.split("/");

                        bill.setExtrasCost(Integer.parseInt(extrasCost));
                        bill.setMainCost(Integer.parseInt(mainCost));
                        bill.setBillDay(Integer.parseInt(dateFull[2]));
                        bill.setBillMonth(Integer.parseInt(dateFull[1]));
                        bill.setBillYear(Integer.parseInt(dateFull[0]));
                        bill.setBillUser(u);

                        messDBHandler.addBill(bill);
                        myDialog.dismiss();

                    }
                    else {
                        Toast.makeText(v.getContext(), "Fill the details", Toast.LENGTH_LONG).show();
                    }
                }
                else if(btn.getId() == R.id.addUser) {
                    userName = v.getRootView().findViewById(R.id.userName);
                    userPass = v.getRootView().findViewById(R.id.userPass);
                    userYear = v.getRootView().findViewById(R.id.userYear);
                    userMess = v.getRootView().findViewById(R.id.Mess);
                    userBranch = v.getRootView().findViewById(R.id.userBranch);
                    userCourse = v.getRootView().findViewById(R.id.userCourse);

                    String name = userName.getText().toString().trim();
                    String pass = userPass.getText().toString().trim();
                    String year = userYear.getText().toString().trim();
                    String mess = userMess.getText().toString().trim();
                    String branch = userBranch.getText().toString().trim();
                    String course = userCourse.getText().toString().trim();

                    if (!name.equals("") && !pass.equals("") && !year.equals("") && !mess.equals("") && !branch.equals("") && !course.equals("")) {
                        String q = "SELECT * FROM MESSES WHERE MESSNAME" + "=\"" + mess + "\";";
                        Cursor c = messDBHandler.getResult(q);

                        Mess m = new Mess();

                        while (!c.isAfterLast()) {
                            if (c.getString(c.getColumnIndex("MESSNAME")).equals(mess)) {
                                m.setMessName(mess);
                                m.setMessHead(c.getString(c.getColumnIndex("MESSHEAD")));
                                m.setDailyCost(c.getInt(c.getColumnIndex("DAILYCOST")));
                                m.setMessId(c.getInt(c.getColumnIndex("MESSID")));
                                m.setNonvegCost(c.getInt(c.getColumnIndex("NONVEGCOST")));
                                m.setCapacity(c.getInt(c.getColumnIndex("CAPACITY")));
                                break;
                            }
                            c.moveToNext();
                        }

                        User user = new User();
                        user.setMess(m);
                        user.setUsername(name);
                        user.setPassword(pass);
                        user.setBranch(branch);
                        user.setCourse(course);
                        user.setYear(Integer.parseInt(year));

                        messDBHandler.addUser(user);
                        myDialog.dismiss();

                    } else {
                        Toast.makeText(v.getContext(), "Fill the details", Toast.LENGTH_LONG).show();
                    }
                }
                else if(btn.getId() == R.id.addMessInstance) {
                    instanceMess = v.getRootView().findViewById(R.id.instanceMess);
                    breakfast = v.getRootView().findViewById(R.id.breakfast);
                    lunch = v.getRootView().findViewById(R.id.lunch);
                    dinner = v.getRootView().findViewById(R.id.dinner);

                    String mess = instanceMess.getText().toString().trim();
                    String brk = breakfast.getText().toString().trim();
                    String lnh = lunch.getText().toString().trim();
                    String din = dinner.getText().toString().trim();

                    if (!mess.equals("") && !brk.equals("") && !lnh.equals("") && !din.equals("")) {
                        String q = "SELECT * FROM MESSES WHERE MESSNAME" + "=\"" + mess + "\";";
                        Cursor c = messDBHandler.getResult(q);

                        Mess m = new Mess();

                        while (!c.isAfterLast()) {
                            if (c.getString(c.getColumnIndex("MESSNAME")).equals(mess)) {
                                m.setMessName(mess);
                                m.setMessHead(c.getString(c.getColumnIndex("MESSHEAD")));
                                m.setDailyCost(c.getInt(c.getColumnIndex("DAILYCOST")));
                                m.setMessId(c.getInt(c.getColumnIndex("MESSID")));
                                m.setNonvegCost(c.getInt(c.getColumnIndex("NONVEGCOST")));
                                m.setCapacity(c.getInt(c.getColumnIndex("CAPACITY")));
                                break;
                            }
                            c.moveToNext();
                        }

                        MessInstance messInstance = new MessInstance();
                        messInstance.setMess(m);
                        messInstance.setBreakfastTotal(Integer.parseInt(brk));
                        messInstance.setLunchTotal(Integer.parseInt(lnh));
                        messInstance.setDinnerTotal(Integer.parseInt(din));

                        messDBHandler.addMessInstance(messInstance);
                        myDialog.dismiss();

                        Log.i(TAG, "Done adding Mess Instance");
                    } else {
                        Toast.makeText(v.getContext(), "Fill the details", Toast.LENGTH_LONG).show();
                    }
                }
                else if(btn.getId() == R.id.addScanInstance) {
                    scanMess = v.getRootView().findViewById(R.id.scanMess);
                    userScanner = v.getRootView().findViewById(R.id.userScanner);

                    String mess = scanMess.getText().toString().trim();
                    String user = userScanner.getText().toString().trim();

                    if (!mess.equals("") && !user.equals("")) {
                        String q = "SELECT * FROM MESSES WHERE MESSNAME" + "=\"" + mess + "\";";
                        Cursor c = messDBHandler.getResult(q);

                        Mess m = new Mess();

                        while (!c.isAfterLast()) {
                            if (c.getString(c.getColumnIndex("MESSNAME")).equals(mess)) {
                                m.setMessName(mess);
                                m.setMessHead(c.getString(c.getColumnIndex("MESSHEAD")));
                                m.setDailyCost(c.getInt(c.getColumnIndex("DAILYCOST")));
                                m.setMessId(c.getInt(c.getColumnIndex("MESSID")));
                                m.setNonvegCost(c.getInt(c.getColumnIndex("NONVEGCOST")));
                                m.setCapacity(c.getInt(c.getColumnIndex("CAPACITY")));
                                break;
                            }
                            c.moveToNext();
                        }

                        ScanInstance scanInstance = new ScanInstance();
                        scanInstance.setMess(m);
                        scanInstance.setUserScanner(user);

                        messDBHandler.addScanInstance(scanInstance);
                        myDialog.dismiss();

                        Log.i(TAG, "Done adding Mess Instance");
                    } else {
                        Toast.makeText(v.getContext(), "Fill the details", Toast.LENGTH_LONG).show();
                    }
                }
            }
        });
    }
}