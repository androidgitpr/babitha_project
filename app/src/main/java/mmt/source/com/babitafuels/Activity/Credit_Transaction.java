package mmt.source.com.babitafuels.Activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.Model.User;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.CreditTxn;
import mmt.source.com.babitafuels.Service.GetAllExpenses;
import mmt.source.com.babitafuels.Service.GetAllUser;

public class Credit_Transaction extends SlidingBarActivity implements AdapterView.OnItemSelectedListener {

    private TextView mModuleTitle;
    private Button upload;
    Spinner mySpinner;
    private static final int FILE_SELECT_CODE = 0;
    private FuelMnt fm_inst;
    ArrayList<User> creditUsrList;
    int selUser;
    int selDate;
    int selMode;
    Calendar dateTime = Calendar.getInstance();
    EditText startDatePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_credit__transaction, null, false);
        mDrawer.addView(contentView, 0);

        selUser = 0;
        selDate = 0;
        selMode = 0;
        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Credit Transaction");

        mySpinner = (Spinner)contentView.findViewById(R.id.spinnercr);
        // Spinner click listener
        mySpinner.setOnItemSelectedListener(this);

        fm_inst = FuelMnt.getInstance();
        creditUsrList = new ArrayList<User>();
        fm_inst.getUsrInfo().setAction("List");
        try {
            if(fm_inst.getAllUsrInfo().size() == 0) {
                AsyncTask<String, Void, Integer> result = new GetAllUser().execute();
                int code = result.get();
                if (code == 500) {
                    Toast.makeText(Credit_Transaction.this, "Failed get user List", Toast.LENGTH_LONG).show();

                } else {

                }
            }
        }catch (Exception e) {

            Toast.makeText(Credit_Transaction.this, "Failed get user List", Toast.LENGTH_LONG).show();
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


        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

        // Spinner Drop down elements
        List<String> payMode = new ArrayList<String>();
        payMode.add("payMode");
        payMode.add("Cash");
        payMode.add("Card");
        payMode.add("Credit");


        Spinner mySpinner1 = (Spinner)contentView.findViewById(R.id.modeTxn);


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, payMode);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mySpinner1.setAdapter(dataAdapter1);


        mySpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

             @Override
             public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                 selMode = position;
                 fm_inst.getCreditTxn().setModePay(payMode.get(position));
             }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });
        upload = findViewById(R.id.chalan_btn);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });



        startDatePicker =contentView.findViewById(R.id.pickDate);
        startDatePicker.setOnClickListener(view -> openDatePicker());

        Button details = findViewById(R.id.btn_txn);
        details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Credit_Transaction.this, CreditReportActivity.class);
                startActivity(intent);

            }
        });

        Button submitBtn = (Button) findViewById(R.id.btn_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                System.out.println("shiva debug 0");
                int isRecharge = 0;
                if(selUser == 0) {
                    Toast.makeText(Credit_Transaction.this, "Select User", Toast.LENGTH_LONG).show();
                    return;
                }

                if(selDate == 0) {
                    Toast.makeText(Credit_Transaction.this, "Select Date", Toast.LENGTH_LONG).show();
                    return;
                }
                EditText perName = findViewById(R.id.PersonName);
                fm_inst.getCreditTxn().setPersonName(perName.getText().toString());

                EditText vehNum = findViewById(R.id.VehicleNum);
                fm_inst.getCreditTxn().setVehNum(vehNum.getText().toString());
                System.out.println("shiva debug 0 0");
                EditText amountRev = findViewById(R.id.amtRev);

                if(amountRev.getText().toString().length() == 0) {

                }else {

                    isRecharge = 1;
                }

                if(isRecharge == 0) {
                    System.out.println("shiva debug 0 000");
                    EditText amountSpent = findViewById(R.id.amtSpent);
                    if (amountSpent.getText().toString().length() == 0) {
                        Toast.makeText(Credit_Transaction.this, "Enter Fuel Charge", Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (fm_inst.getCreditUsr().getStatus() == false) {
                        Toast.makeText(Credit_Transaction.this, "User Not active transcation not allowed", Toast.LENGTH_LONG).show();
                        return;
                    }

                    int fuleTy = 0;
                    CheckBox cb = findViewById(R.id.checkbox_petrol);
                    if (cb.isChecked())
                        fuleTy += 1;
                    cb = findViewById(R.id.checkbox_diesel);
                    if (cb.isChecked())
                        fuleTy += 2;
                    cb = findViewById(R.id.checkbox_oil);
                    if (cb.isChecked())
                        fuleTy += 4;

                    if (fuleTy == 0) {
                        Toast.makeText(Credit_Transaction.this, "Select Fuel Type", Toast.LENGTH_LONG).show();
                        return;
                    }
                    fm_inst.getCreditTxn().setFuelType(String.valueOf(fuleTy));
                    fm_inst.getCreditTxn().setAmountUsed(amountSpent.getText().toString());
                }
                if(selMode == 0) {
                    Toast.makeText(Credit_Transaction.this, "Select Payment Mode", Toast.LENGTH_LONG).show();
                    return;
                }


                fm_inst.getCreditTxn().setAction("Add");
                fm_inst.getCreditTxn().setUsrId(fm_inst.getCreditUsr().getUsrId());

                fm_inst.getCreditTxn().setAmountPaid(amountRev.getText().toString());


                try {
                    System.out.println("shiva debug 1");
                    fm_inst.getCreditTxn().setAction("Add");
                    if(fm_inst.getUsrInfo().getBunkId() == "null") {
                        Toast.makeText(Credit_Transaction.this, "Your Account is inactive ", Toast.LENGTH_LONG).show();
                        //return;
                    }else {
                    //    fm_inst.getCreditTxn().setBunkId(Integer.valueOf(fm_inst.getUsrInfo().getBunkId()));
                    }
                    System.out.println("shiva debug 2");
                    AsyncTask<String, Void, Integer> result = new CreditTxn().execute();
                    int code = result.get();
                    if (code == 500) {
                        //Toast.makeText(HomeScreenActivity.this, "Fail to get access rights", Toast.LENGTH_LONG).show();

                    } else if (code == 200) {
                        fm_inst.setCreditTxn(new mmt.source.com.babitafuels.Model.CreditTxn());
                        finish();
                    }
                }catch(Exception e){

                    Toast.makeText(Credit_Transaction.this, "Fail to get expenses report", Toast.LENGTH_LONG).show();
                }

            }
        });
    }



    private void showFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    FILE_SELECT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selUser = position;
        if(position != 0) {
            fm_inst.setCreditUsr(creditUsrList.get(position - 1));
            startDatePicker.setText("");
            selDate = 0;
            ImageView invalid = findViewById(R.id.img2);
            invalid.setVisibility(View.GONE);
            ImageView valid = findViewById(R.id.img1);
            valid.setVisibility(View.GONE);
            if(fm_inst.getCreditUsr().getStatus() == false) {

                invalid.setVisibility(View.VISIBLE);
            }
            if(fm_inst.getCreditUsr().getStatus() == true) {

                valid.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    DatePickerDialog.OnDateSetListener datePickerDialog = (view, year, monthOfYear, dayOfMonth) -> {
        dateTime.set(Calendar.YEAR, year);
        dateTime.set(Calendar.MONTH, monthOfYear);
        dateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        updateDate();
    };
    private void openDatePicker(){
        new DatePickerDialog(this, datePickerDialog, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void updateDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        String startDate = sdf.format(dateTime.getTime());
        startDatePicker.setText(startDate);
        selDate = 1;
        fm_inst.getCreditTxn().setTxnDate(startDate);
    }

}
