package mmt.source.com.babitafuels.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.GetAllBunk;
import mmt.source.com.babitafuels.Service.GetAllUser;
import mmt.source.com.babitafuels.Service.Register;

public class AssignPBAllocationActivity extends SlidingBarActivity implements AdapterView.OnItemSelectedListener {

    Spinner mySpinner1, mySpinner2, mySpinner3;
    ArrayAdapter<String> userAdapter, usernameAdapter,petrolBkAdapter;
    TextView mModuleTitle;
    TextView mobileNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.assign_pb_allocation_activity, null, false);
        mDrawer.addView(contentView, 0);

        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Assign Petrol Bunk");

        mySpinner1 = (Spinner)contentView.findViewById(R.id.spinner2);
        mobileNum = (TextView) contentView.findViewById(R.id.spinner3);
        mySpinner3 = (Spinner)contentView.findViewById(R.id.spinner4);
        // Spinner click listener
        mySpinner1.setOnItemSelectedListener(this);


        FuelMnt fm_inst = FuelMnt.getInstance();
        fm_inst.getUsrInfo().setAction("List");

        try {
            if(fm_inst.getAllUsrInfo().size() == 0) {
                AsyncTask<String, Void, Integer> result = new GetAllUser().execute();
                int code = result.get();
                if (code == 500) {
                    Toast.makeText(AssignPBAllocationActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
                    return;
                } else {

                }
            }
        }catch (Exception e) {

            Toast.makeText(AssignPBAllocationActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
        }
        // Spinner Drop down elements
        List<String> userType = new ArrayList<String>();
        userType.add("User Type");

        for(int i = 0; i < fm_inst.getAllUsrInfo().size(); i++) {
            userType.add(fm_inst.getAllUsrInfo().get(i).getUsrName());
        }

        List<String> userName = new ArrayList<String>();
        userName.add("User Name");
        userName.add("A");
        userName.add("B");


        try {

            AsyncTask<String, Void, Integer> result = new GetAllBunk().execute();
            int code = result.get();
            if(code == 500) {
                Toast.makeText(AssignPBAllocationActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
                return;
            }
            else {

            }
        }catch (Exception e) {

            Toast.makeText(AssignPBAllocationActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
        }
        List<String> petrlBunk = new ArrayList<String>();
        petrlBunk.add("Petrol Bunk");

        for(int i = 0; i < fm_inst.getAllBunk().size(); i++) {
            petrlBunk.add(fm_inst.getAllBunk().get(i).getBunkName());
        }

        userAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userType);
        userAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner1.setAdapter(userAdapter);

        usernameAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userName);
        usernameAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //mySpinner2.setAdapter(usernameAdapter);


        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position != 0) {
                    mobileNum.setText(fm_inst.getAllUsrInfo().get(position - 1).getMobileNum());
                    fm_inst.setUsrInfo(fm_inst.getAllUsrInfo().get(position - 1));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Toast.makeText(PersonalInfoActivity.this, "on not selected ", Toast.LENGTH_SHORT).show();
            }

        });


        petrolBkAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, petrlBunk);
        petrolBkAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner3.setAdapter(petrolBkAdapter);


        mySpinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position != 0)
                    fm_inst.getUsrInfo().setBunkId(String.valueOf(fm_inst.getAllBunk().get(position - 1).getBunkId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Toast.makeText(PersonalInfoActivity.this, "on not selected ", Toast.LENGTH_SHORT).show();
            }

        });


        Button Assign = findViewById(R.id.assignBtn);
        Assign.setOnClickListener(view -> {

            fm_inst.getUsrInfo().setAction("Update");

            try {

                AsyncTask<String, Void, Integer> result = new Register().execute();
                int code = result.get();
                if(code == 500) {
                    Toast.makeText(AssignPBAllocationActivity.this, "Failed to Assign", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    Toast.makeText(AssignPBAllocationActivity.this, "Successfully Assigned", Toast.LENGTH_LONG).show();
                    finish();
                }
            }catch (Exception e) {

                Toast.makeText(AssignPBAllocationActivity.this, "Fail to Assign", Toast.LENGTH_SHORT).show();
            }
        });


    }


    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
