package mmt.source.com.babitafuels.Activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.Model.User;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.CreditSetup;
import mmt.source.com.babitafuels.Service.GetAllUser;

public class CreditSetupActivityNew extends SlidingBarActivity implements AdapterView.OnItemSelectedListener{
    Spinner mySpinner;
    private TextView mModuleTitle;
    View rootView;
    Calendar dateTime = Calendar.getInstance();
    Calendar dateTime1 = Calendar.getInstance();
    EditText startDatePicker, endDatePicker;
    ArrayList<User> creditUsrList;
    String startDate, endDate;
    FuelMnt fm_inst;
    int userSelect = 0;

    DatePickerDialog.OnDateSetListener datePickerDialog = (view, year, monthOfYear, dayOfMonth) -> {
        dateTime.set(Calendar.YEAR, year);
        dateTime.set(Calendar.MONTH, monthOfYear);
        dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDate();
    };

    DatePickerDialog.OnDateSetListener datePickerDialog1 = (view, year, monthOfYear, dayOfMonth) -> {
        dateTime1.set(Calendar.YEAR, year);
        dateTime1.set(Calendar.MONTH, monthOfYear);
        dateTime1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDate1();
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.credit_setup_activity_new, null, false);
        mDrawer.addView(contentView, 0);

        creditUsrList = new ArrayList<User>();
        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Credit Customer Setup");

        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();

        mySpinner = (Spinner)contentView.findViewById(R.id.crdcustspinner1);

        mySpinner.setOnItemSelectedListener(this);
        fm_inst = FuelMnt.getInstance();
        fm_inst.getUsrInfo().setAction("List");


        try {
            if(fm_inst.getAllUsrInfo().size() == 0) {
                AsyncTask<String, Void, Integer> result = new GetAllUser().execute();
                int code = result.get();
                if (code == 500) {
                    Toast.makeText(CreditSetupActivityNew.this, "Failed get user List", Toast.LENGTH_LONG).show();

                } else {

                }
            }
        }catch (Exception e) {

            Toast.makeText(CreditSetupActivityNew.this, "Failed get user List", Toast.LENGTH_LONG).show();
        }


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Credit Customer Name");

        for(int i = 0; i < fm_inst.getAllUsrInfo().size(); i++) {
            if(fm_inst.getAllUsrInfo().get(i).getRegType().equals("Credit")) {
                categories.add(fm_inst.getAllUsrInfo().get(i).getUsrName());
                creditUsrList.add(fm_inst.getAllUsrInfo().get(i));
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);

        startDatePicker =contentView.findViewById(R.id.startDateId);
        endDatePicker=contentView.findViewById(R.id.endDateId);
        startDatePicker.setOnClickListener(view -> openDatePicker());
        endDatePicker.setOnClickListener(view -> openDatePicker1());


        /*viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);*/

        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {

                try {
                    int fuelTyep = 0;

                    System.out.println("shiva updated calling credit calling api");
                    FuelMnt fm_inst = FuelMnt.getInstance();
                    if(userSelect == 0) {
                        Toast.makeText(CreditSetupActivityNew.this, "Select User", Toast.LENGTH_LONG).show();
                        return;
                    }
                    fm_inst.getCreditUsr().setAction("Update");

                    System.out.println("shiva update calling credit calling api 1");
                    fm_inst.getCreditUsr().setStartDate(startDate);
                    fm_inst.getCreditUsr().setEndDate(endDate);
                    EditText limit = findViewById(R.id.noltrid);

                    if(limit.getText().toString().length() == 0) {
                        limit.setError("Enter Limit Information");
                        return;
                    }
                    //System.out.println("shiva update calling credit calling api 2");
                    fm_inst.getCreditUsr().setCreditLmt(limit.getText().toString());

                    EditText advPaid = findViewById(R.id.advpaidid);
                    if(advPaid.getText().toString().length() == 0) {
                        advPaid.setError("Enter Limit Information");
                        return;
                    }
                    fm_inst.getCreditUsr().setAdvPaid(advPaid.getText().toString());

                    CheckBox cb = findViewById(R.id.petrold1);
                    if(cb.isChecked())
                        fuelTyep += 1;
                    cb = findViewById(R.id.dieselid1);
                    if(cb.isChecked())
                        fuelTyep += 2;
                    cb = findViewById(R.id.oilid1);
                    if(cb.isChecked())
                        fuelTyep += 4;

                    fm_inst.getCreditUsr().setFuelType(String.valueOf(fuelTyep));
                    if(fuelTyep == 0) {
                        Toast.makeText(CreditSetupActivityNew.this, "Select Fuel Type", Toast.LENGTH_LONG).show();
                        return;
                    }
                    RadioButton active = findViewById(R.id.radio1);

                    fm_inst.getCreditUsr().setStatus(active.isChecked());
                    System.out.println("shiva active status "+active.isChecked());
                    AsyncTask<String, Void, Integer> result = new CreditSetup().execute();
                    int code = result.get();
                    if(code == 500) {
                        Toast.makeText(CreditSetupActivityNew.this, "Fail to setup Credit Information", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(code == 200){
                        finish();
                    }
                }catch (Exception e) {

                    Toast.makeText(CreditSetupActivityNew.this, "Fail to setup Credit Information", Toast.LENGTH_LONG).show();
                }

            }
        });
    }


    private void openDatePicker(){
        new DatePickerDialog(this, datePickerDialog, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void openDatePicker1(){
        new DatePickerDialog(this, datePickerDialog1, dateTime1.get(Calendar.YEAR),dateTime1.get(Calendar.MONTH),dateTime1.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        startDate = sdf.format(dateTime.getTime());
        startDatePicker.setText(startDate);
    }

    private void updateDate1(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        endDate = sdf.format(dateTime1.getTime());
        System.out.println("end time "+endDate);
        endDatePicker.setText(endDate);
    }

    /*private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new CreditSetupFragment(), "Credit Setup");
        adapter.addFragment(new CreditTransactionFragment(), "Credit Transaction");
        viewPager.setAdapter(adapter);
    }*/

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        finish();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0) {
            fm_inst.setCreditUsr(creditUsrList.get(position - 1));
            if(!fm_inst.getCreditUsr().getStartDate().equals("null")) {
                startDatePicker.setText(fm_inst.getCreditUsr().getStartDate());
            }else {
                startDatePicker.setText("");
            }
            if(!fm_inst.getCreditUsr().getEndDate().equals("null")) {
                endDatePicker.setText(fm_inst.getCreditUsr().getEndDate());
            }else {
                endDatePicker.setText("");
            }
            userSelect = position;
            CheckBox cb;
            cb = findViewById(R.id.petrold1);
            cb.setChecked(false);
            cb = findViewById(R.id.dieselid1);
            cb.setChecked(false);
            cb = findViewById(R.id.oilid1);
            cb.setChecked(false);
         //   System.out.println("shiva fuel type "+fm_inst.getCreditUsr().getFuelType());
            switch (fm_inst.getCreditUsr().getFuelType()) {
                case "1":
                    cb = findViewById(R.id.petrold1);
                    cb.setChecked(true);
                    break;
                case "2":
                    cb = findViewById(R.id.dieselid1);
                    cb.setChecked(true);
                    break;
                case "3":
                    cb = findViewById(R.id.petrold1);
                    cb.setChecked(true);
                    cb = findViewById(R.id.dieselid1);
                    cb.setChecked(true);
                    break;
                case "4":
                    cb = findViewById(R.id.oilid1);
                    cb.setChecked(true);
                    break;
                case "5":
                    cb = findViewById(R.id.petrold1);
                    cb.setChecked(true);
                    cb = findViewById(R.id.oilid1);
                    cb.setChecked(true);
                    break;
                case "6":
                    cb = findViewById(R.id.dieselid1);
                    cb.setChecked(true);
                    cb = findViewById(R.id.oilid1);
                    cb.setChecked(true);
                    break;
                case "7":
                    cb = findViewById(R.id.petrold1);
                    cb.setChecked(true);
                    cb = findViewById(R.id.dieselid1);
                    cb.setChecked(true);
                    cb = findViewById(R.id.oilid1);
                    cb.setChecked(true);
                    break;
            }

            EditText limit = findViewById(R.id.noltrid);
            if(!fm_inst.getCreditUsr().getCreditLmt().equals("null")) {
                limit.setText("" + fm_inst.getCreditUsr().getCreditLmt());
            }else {
                limit.setText("");
            }
            EditText advPaid = findViewById(R.id.advpaidid);
            if(!fm_inst.getCreditUsr().getAdvPaid().equals("null")) {
                advPaid.setText("" + fm_inst.getCreditUsr().getAdvPaid());
            }else {
                advPaid.setText("");
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


