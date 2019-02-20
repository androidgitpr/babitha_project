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
import android.util.Patterns;
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
import java.util.regex.Matcher;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.Register;

public class UserRegistrationActivity extends SlidingBarActivity implements AdapterView.OnItemSelectedListener {

    Spinner mySpinner;
    private TextView mModuleTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.user_registration_activity, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Personal Information");

        mySpinner = (Spinner)contentView.findViewById(R.id.spinner1);
        // Spinner click listener
        mySpinner.setOnItemSelectedListener(this);
        Fragment fragment = new FooterFragment();
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm1.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select User Type");
        categories.add("A");
        categories.add("B");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);

        EditText usrName = findViewById(R.id.nameId);
        EditText usrMobile = findViewById(R.id.contactId);
        EditText usrEmail = findViewById(R.id.emailId);
        EditText usrAddr = findViewById(R.id.addrId);
        EditText usrBlood = findViewById(R.id.bloodId);
        EditText emgMobile = findViewById(R.id.emergId);
        EditText password = findViewById(R.id.passwordId);

        Button submit = findViewById(R.id.submitBtn);
        submit.setOnClickListener(view -> {
            FuelMnt fm = FuelMnt.getInstance();
            if(usrName.getText().toString().length() == 0) {
                usrName.setError("Enter Name");
                return;
            }
            fm.getUsrInfo().setUsrName(usrName.getText().toString());

            if(usrMobile.getText().toString().length() == 0) {
                usrMobile.setError("Enter Mobile Number");
                return;
            }
            fm.getUsrInfo().setMobileNum(usrMobile.getText().toString());


            Matcher matcher = Patterns.EMAIL_ADDRESS.matcher(usrEmail.getText().toString());
            if(!matcher.matches())
            {
                usrEmail.setError("Invalid Email Address");
                return;
            }
            fm.getUsrInfo().setEmailId(usrEmail.getText().toString());

            if(usrAddr.getText().toString().length() == 0) {
                usrAddr.setError("Enter Address");
                return;
            }
            fm.getUsrInfo().setAddr(usrAddr.getText().toString());

            if(usrBlood.getText().toString().length() == 0) {
                usrBlood.setError("Enter Blood Group");
                return;
            }
            fm.getUsrInfo().setBloodG(usrBlood.getText().toString());

            if(emgMobile.getText().toString().length() == 0) {
                emgMobile.setError("Enter Emergency Mobile Number");
                return;
            }

            fm.getUsrInfo().setEmgMobileNum(emgMobile.getText().toString());

            if(password.getText().toString().length() == 0) {
                password.setError("Enter Password");
                return;
            }
            fm.getUsrInfo().setPassword(password.getText().toString());

            fm.getUsrInfo().setAction("Add");
            fm.getUsrInfo().setRegType("Employee");
            try {

                AsyncTask<String, Void, Integer> result = new Register().execute();
                int code = result.get();
                if(code == 500) {
                    Toast.makeText(UserRegistrationActivity.this, "Failed to Register", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                /*    DbHelper.createVendorDbHelper(this);
                    UserDetailsDao vendorDetailsDao = new UserDetailsDao();
                    System.out.println("shiva calling add login ");
                    vendorDetailsDao.addUsrDetails();
*/
                    finish();
                }
            }catch (Exception e) {

                Toast.makeText(UserRegistrationActivity.this, "Fail to Register", Toast.LENGTH_SHORT).show();
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
