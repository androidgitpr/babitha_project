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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.AddBunk;
import mmt.source.com.babitafuels.Service.Register;

public class PetrolBunkRegistrationActivity extends SlidingBarActivity {

    private TextView mModuleTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.petrol_bunk_registr_activity, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Petrol Bunk Registration");

        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();

        EditText bunkName = findViewById(R.id.pbnameId);
        EditText bunkArea = findViewById(R.id.anameId);
        EditText bunkAddr = findViewById(R.id.addessId);
        EditText bunkPin = findViewById(R.id.pinId);
        EditText bunkMngName = findViewById(R.id.mangrNameId);
        EditText bunkMngMobile = findViewById(R.id.mangrContactId);
        EditText bunkOffice = findViewById(R.id.usrnameId);

        Button submit = findViewById(R.id.submitBtn);
        submit.setOnClickListener(view -> {
            FuelMnt fm_inst = FuelMnt.getInstance();
            if(bunkName.getText().toString().length() == 0) {
                bunkName.setError("Enter Details");
                return;
            }
            fm_inst.getBunk().setBunkName(bunkName.getText().toString());

            if(bunkArea.getText().toString().length() == 0) {
                bunkArea.setError("Enter Details");
                return;
            }
            fm_inst.getBunk().setBunkArea(bunkArea.getText().toString());

            if(bunkAddr.getText().toString().length() == 0) {
                bunkAddr.setError("Enter Details");
                return;
            }
            fm_inst.getBunk().setBunkAddr(bunkAddr.getText().toString());


            if(bunkPin.getText().toString().length() == 0) {
                bunkPin.setError("Enter Details");
                return;
            }
            fm_inst.getBunk().setBunkPincode(bunkPin.getText().toString());


            if(bunkMngName.getText().toString().length() == 0) {
                bunkMngName.setError("Enter Details");
                return;
            }
            fm_inst.getBunk().setBunkMngName(bunkMngName.getText().toString());


            if(bunkMngMobile.getText().toString().length() == 0) {
                bunkMngMobile.setError("Enter Details");
                return;
            }
            fm_inst.getBunk().setBunkMngMobile(bunkMngMobile.getText().toString());


            if(bunkOffice.getText().toString().length() == 0) {
                bunkOffice.setError("Enter Details");
                return;
            }
            fm_inst.getBunk().setBunkOfficeNum(bunkOffice.getText().toString());


            fm_inst.getBunk().setAction("Add");

            try {

                AsyncTask<String, Void, Integer> result = new AddBunk().execute();
                int code = result.get();
                if(code == 500) {
                    Toast.makeText(PetrolBunkRegistrationActivity.this, "Failed to Add Bunk Info", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    Toast.makeText(PetrolBunkRegistrationActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                    finish();
                }
            }catch (Exception e) {

                Toast.makeText(PetrolBunkRegistrationActivity.this, "Fail to Register", Toast.LENGTH_SHORT).show();
            }
        });


    }



    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        finish();
        return true;
    }
}

