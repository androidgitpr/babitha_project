package mmt.source.com.babitafuels.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.Access;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.AddAccess;
import mmt.source.com.babitafuels.Service.GetAccess;
import mmt.source.com.babitafuels.Service.GetAllUser;

public class AccessDetailsActivity extends SlidingBarActivity  {

    Spinner mySpinner;
    private TextView mModuleTitle;
    int usrSel = 0;
    ArrayList<TableRow> accessRow;
    ArrayList<Access> usrAccess;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.access_details_activity, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = (TextView) findViewById(R.id.product_title);
        mModuleTitle.setText("Access Details");

        mySpinner = (Spinner) contentView.findViewById(R.id.aspinner);


        FuelMnt fm_inst = FuelMnt.getInstance();
        fm_inst.getUsrInfo().setAction("List");

        usrAccess = new ArrayList<Access>();
        try {
            if (fm_inst.getAllUsrInfo().size() == 0) {
                AsyncTask<String, Void, Integer> result = new GetAllUser().execute();
                int code = result.get();
                if (code == 500) {
                    Toast.makeText(AccessDetailsActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
                    return;
                } else {

                }
            }
        } catch (Exception e) {

            Toast.makeText(AccessDetailsActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
        }
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select User Type");

        for (int i = 0; i < fm_inst.getAllUsrInfo().size(); i++) {
            categories.add(fm_inst.getAllUsrInfo().get(i).getUsrName());
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);

        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                usrSel = position;
                if (position != 0) {
                    fm_inst.getSetAccess().setUsrId(fm_inst.getAllUsrInfo().get(position - 1).getUsrId());
                    fm_inst.getSetAccess().setAction("Get");
                    for (int i = 0; i < fm_inst.getAccessList().size(); i++) {
                        TableRow tbrow = accessRow.get(i);
                        TextView accessName = (TextView) tbrow.getChildAt(0);
                        CheckBox read = (CheckBox) tbrow.getChildAt(1);
                        CheckBox write = (CheckBox) tbrow.getChildAt(2);
                        String firstText = accessName.getText().toString();

                        System.out.println("shiva debug " + firstText);
                        read.setChecked(false);
                        write.setChecked(false);
                    }
                        try {
                        AsyncTask<String, Void, Integer> result = new GetAccess().execute();
                        int code = result.get();
                        if(code == 500) {
                            Toast.makeText(AccessDetailsActivity.this, "Fail to update access rights", Toast.LENGTH_LONG).show();
                            return;
                        }
                        else if(code == 200){
                            //parse access
                            String[] accessList = fm_inst.getSetAccess().getAccessPage().split(":");

                            //System.out.println("shiva debug "+accessList.length);
                            for(int i = 0; i < accessList.length; i++) {
                                Access item = new Access();
                                String[] eachItem = accessList[i].split("-");
                                item.setAccessName(eachItem[0]);
                                //System.out.println("shiva debug "+item.getAccessName());
                                item.setPriv(Integer.valueOf(eachItem[1]));
                                usrAccess.add(item);
                            }
                            updatePrevSettings();
                        }
                    }catch (Exception e) {

                        Toast.makeText(AccessDetailsActivity.this, "Fail to update access rights", Toast.LENGTH_LONG).show();
                    }


                    EditText mobNum = findViewById(R.id.nameId);
                    mobNum.setText(fm_inst.getAllUsrInfo().get(position -1 ).getMobileNum());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Toast.makeText(PersonalInfoActivity.this, "on not selected ", Toast.LENGTH_SHORT).show();
            }
        });

        TableLayout stk = (TableLayout) findViewById(R.id.triptablid);
        accessRow = new ArrayList<TableRow>();

        for (int i = 0; i < fm_inst.getAccessList().size(); i++) {
            TableRow tbrow = new TableRow(getApplicationContext());

                TextView t1v = new TextView(getApplicationContext());
                t1v.setText("" + fm_inst.getAccessList().get(i).getAccessName());
                t1v.setTextColor(Color.BLACK);
                t1v.setGravity(Gravity.CENTER);
                t1v.setPadding(10, 10, 10, 10);
                tbrow.addView(t1v);
                CheckBox t2v = new CheckBox(getApplicationContext());

                t2v.setTextColor(Color.BLACK);
                t2v.setGravity(Gravity.CENTER);
                t2v.setPadding(10, 10, 10, 10);
                tbrow.addView(t2v);
                CheckBox t3v = new CheckBox(getApplicationContext());

                t3v.setTextColor(Color.BLACK);
                t3v.setGravity(Gravity.CENTER);
                t3v.setPadding(10, 10, 10, 10);
                tbrow.addView(t3v);
                stk.addView(tbrow);

                accessRow.add(tbrow);


        }

        Button submitBtn = findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {


                if(usrSel == 0) {
                    Toast.makeText(AccessDetailsActivity.this, "Please select User ", Toast.LENGTH_LONG).show();
                    return;
                }
                String Access = "";
                for (int i = 0; i < fm_inst.getAccessList().size(); i++) {
                    TableRow tbrow = accessRow.get(i);
                    TextView accessName = (TextView) tbrow.getChildAt(0);
                    CheckBox read = (CheckBox) tbrow.getChildAt(1);
                    CheckBox write = (CheckBox) tbrow.getChildAt(2);
                    String firstText = accessName.getText().toString();
                    int value = 0;
                    if(read.isChecked())
                        value += 1;
                    if(write.isChecked())
                        value += 2;
                    Access += firstText+"-"+value+":";
                }
                fm_inst.getSetAccess().setUsrProvider(fm_inst.getUsrInfo().getUsrId());

                fm_inst.getSetAccess().setAccessPage(Access);

                fm_inst.getSetAccess().setAction("Update");
            try {
                AsyncTask<String, Void, Integer> result = new AddAccess().execute();
                int code = result.get();
                if(code == 500) {
                    Toast.makeText(AccessDetailsActivity.this, "Fail to update access rights", Toast.LENGTH_LONG).show();
                    return;
                }
                else if(code == 200){
                    Toast.makeText(AccessDetailsActivity.this, "Access Details Updated Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }catch (Exception e) {

                Toast.makeText(AccessDetailsActivity.this, "Fail to update access rights", Toast.LENGTH_LONG).show();
            }

        }
        });
    }

    public void updatePrevSettings() {

        FuelMnt fm_inst = FuelMnt.getInstance();
        for (int i = 0; i < fm_inst.getAccessList().size(); i++) {
            TableRow tbrow = accessRow.get(i);
            TextView accessName = (TextView) tbrow.getChildAt(0);
            CheckBox read = (CheckBox) tbrow.getChildAt(1);
            CheckBox write = (CheckBox) tbrow.getChildAt(2);
            String firstText = accessName.getText().toString();

            System.out.println("shiva debug "+firstText);
            read.setChecked(false);
            write.setChecked(false);
            for (int j = 0; j < usrAccess.size(); j++) {


                if (usrAccess.get(j).getAccessName().equals(firstText)) {
                    System.out.println("shiva debug found "+firstText+" prev "+usrAccess.get(j).getPriv());
                    switch (usrAccess.get(j).getPriv()) {
                        case 1:
                            read.setChecked(true);
                            break;
                        case 2:
                            write.setChecked(true);
                            break;
                        case 3:
                            read.setChecked(true);
                            write.setChecked(true);
                            break;
                    }
                    break;
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        finish();
        return true;
    }
}

