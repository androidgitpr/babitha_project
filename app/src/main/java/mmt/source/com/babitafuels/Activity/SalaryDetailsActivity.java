package mmt.source.com.babitafuels.Activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.Register;

public class SalaryDetailsActivity extends SlidingBarActivity implements AdapterView.OnItemSelectedListener {

    Spinner fromMSpinner, toMSpinner;
    private TextView mModuleTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.salary_details_activity, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Salary Details");

        fromMSpinner = (Spinner)contentView.findViewById(R.id.fromspinner);
        fromMSpinner.setOnItemSelectedListener(this);

        toMSpinner = (Spinner)contentView.findViewById(R.id.tospinner);
        toMSpinner.setOnItemSelectedListener(this);

        Fragment fragment = new FooterFragment();
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm1.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();


        // Spinner Drop down elements
        List<String> fromMonthList = new ArrayList<String>();
        fromMonthList.add("From Month");
        fromMonthList.add("JANUARY");
        fromMonthList.add("FEBRUARY");
        fromMonthList.add("MARCH");
        fromMonthList.add("APRIL");
        fromMonthList.add("MAY");
        fromMonthList.add("JUNE");
        fromMonthList.add("JULY");
        fromMonthList.add("AUGUST");
        fromMonthList.add("SEPTEMBER");
        fromMonthList.add("OCTOBER");
        fromMonthList.add("NOVEMBER");
        fromMonthList.add("DECEMBER");

        // Spinner Drop down elements
        List<String> toMonthList = new ArrayList<String>();
        toMonthList.add("To Month");
        toMonthList.add("JANUARY");
        toMonthList.add("FEBRUARY");
        toMonthList.add("MARCH");
        toMonthList.add("APRIL");
        toMonthList.add("MAY");
        toMonthList.add("JUNE");
        toMonthList.add("JULY");
        toMonthList.add("AUGUST");
        toMonthList.add("SEPTEMBER");
        toMonthList.add("OCTOBER");
        toMonthList.add("NOVEMBER");
        toMonthList.add("DECEMBER");


        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, fromMonthList);
        dataAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        fromMSpinner.setAdapter(dataAdapter1);

        ArrayAdapter<String> dataAdapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, toMonthList);
        dataAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        toMSpinner.setAdapter(dataAdapter2);

        Button submit = findViewById(R.id.submitBtn);


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
