package mmt.source.com.babitafuels.Activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.AddExpenses;
import mmt.source.com.babitafuels.Service.SalaryUpdate;

public class ExpencesActivity extends SlidingBarActivity {

    private Button btn_submit;
    private EditText mDate;
    private EditText mTime;
    private int mDay;
    private int mMonth;
    private int mYear;
    private Button mupdate;
    EditText startDatePicker;
    private Boolean isDob = false;
    private TextView mModuleTitle;
    Calendar dateTime = Calendar.getInstance();
    FuelMnt fm_inst;
    int selDate;
    int selExpType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_expences, null, false);
        mDrawer.addView(contentView, 0);


        selDate = 0;
        selExpType = 0;
        Spinner mySpinner = (Spinner)contentView.findViewById(R.id.crdcustspinner1);

        fm_inst = FuelMnt.getInstance();
        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Expences");

        mDate = findViewById(R.id.date);
        mTime = findViewById(R.id.time);
        mupdate = findViewById(R.id.update);


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Select Expenses Type");
        categories.add("Food\\Beverage");
        categories.add("Festival");
        categories.add("BirthDay");
        categories.add("Other");

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);

        startDatePicker = findViewById(R.id.date);
        startDatePicker.setOnClickListener(view -> openDatePicker());


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selExpType = position;

                fm_inst.getExpenses().setExpType(categories.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Toast.makeText(PersonalInfoActivity.this, "on not selected ", Toast.LENGTH_SHORT).show();
            }

        });

        mupdate.setOnClickListener(view -> {

            fm_inst.getCreditUsr().setAction("Update");

            try {

                if(selDate == 0) {
                    Toast.makeText(ExpencesActivity.this, "Select Date", Toast.LENGTH_LONG).show();
                    return;
                }


                if(selExpType == 0) {

                    Toast.makeText(ExpencesActivity.this, "Select Expenses Type", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText amount = findViewById(R.id.amount);
                if(amount.getText().toString().length() == 0) {
                    amount.setError("Enter amount");
                    Toast.makeText(ExpencesActivity.this, "Enter Amount", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText desc = findViewById(R.id.remark);
                System.out.println("shiva debug 1");
                fm_inst.getExpenses().setAmount(amount.getText().toString());
                System.out.println("shiva debug 2");
                fm_inst.getExpenses().setDescription(desc.getText().toString());
                System.out.println("shiva debug 2");
                //fm_inst.getExpenses().setBunkId(Integer.valueOf(fm_inst.getUsrInfo().getBunkId()));
                System.out.println("shiva debug 3");
                fm_inst.getExpenses().setEmpId(fm_inst.getUsrInfo().getUsrId());

                fm_inst.getExpenses().setAction("Add");
                AsyncTask<String, Void, Integer> result = new AddExpenses().execute();
                int code = result.get();
                if(code == 500) {
                    Toast.makeText(ExpencesActivity.this, "Failed to add Expenses Details", Toast.LENGTH_LONG).show();

                }
                else if(code == 200){
                    Toast.makeText(ExpencesActivity.this, "Expenses Added Successfully", Toast.LENGTH_LONG).show();
                    finish();
                }
            }catch (Exception e) {

                Toast.makeText(ExpencesActivity.this, " fatal Fail to add Expenses Details", Toast.LENGTH_SHORT).show();
            }
        });

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
        fm_inst.getExpenses().setExpDate(startDate);
        selDate = 1;
    }
}
