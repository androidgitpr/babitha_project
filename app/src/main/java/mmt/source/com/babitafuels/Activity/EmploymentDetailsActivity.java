package mmt.source.com.babitafuels.Activity;

import android.app.DatePickerDialog;
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
import mmt.source.com.babitafuels.Service.GetAllUser;
import mmt.source.com.babitafuels.Service.Register;
import mmt.source.com.babitafuels.Service.SalaryUpdate;

public class EmploymentDetailsActivity extends SlidingBarActivity{

    Spinner mySpinner;
    private TextView mModuleTitle;
    private FuelMnt fm_inst;
    ArrayList<User> employeesList;
    int usrSel;
    int usrDesigSel;
    Calendar dateTime = Calendar.getInstance();
    EditText startDatePicker;

    ArrayList<String> desigList;
    int selDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.employment_details_activity, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Employement Details");

        usrSel = 0;
        usrDesigSel = 0;
        fm_inst = FuelMnt.getInstance();
        desigList = new ArrayList<String>();
        desigList.add("Select Designation");
        desigList.add("Admin");
        desigList.add("Manager");
        desigList.add("Employee");
        desigList.add("Cleaner");
        employeesList = new ArrayList<User>();
        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();

        fm_inst.getUsrInfo().setAction("List");
        try {
            if(fm_inst.getAllUsrInfo().size() == 0) {
                AsyncTask<String, Void, Integer> result = new GetAllUser().execute();
                int code = result.get();
                if (code == 500) {
                    Toast.makeText(EmploymentDetailsActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();

                } else {

                }
            }
        }catch (Exception e) {

            Toast.makeText(EmploymentDetailsActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
        }

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Employee Name");

        for(int i = 0; i < fm_inst.getAllUsrInfo().size(); i++) {
            //System.out.println("shiva emp typ received "+fm_inst.getAllUsrInfo().get(i).getRegType());
            if(fm_inst.getAllUsrInfo().get(i).getRegType().equals("Employee")) {
                categories.add(fm_inst.getAllUsrInfo().get(i).getUsrName());
                employeesList.add(fm_inst.getAllUsrInfo().get(i));
            }
        }

        mySpinner = findViewById(R.id.empList);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);

        startDatePicker =contentView.findViewById(R.id.doj);
        startDatePicker.setOnClickListener(view -> openDatePicker());


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                usrSel = position;
                if(position != 0) {
                    fm_inst.setCreditUsr(employeesList.get(position - 1));
                    EditText usrName = findViewById(R.id.nameId1);
                    usrName.setText(fm_inst.getCreditUsr().getMobileNum());
                    System.out.println("shiva designation "+fm_inst.getCreditUsr().getDesignation());
                    if(!fm_inst.getCreditUsr().getDesignation().equals("null")) {
                        Spinner grade = findViewById(R.id.Designation);
                        //find position
                        int pos = 0;
                        for(int i = 0; i < desigList.size(); i++) {
                            if(fm_inst.getCreditUsr().getDesignation().equals(desigList.get(i))) {
                                pos =i;
                                break;
                            }
                        }
                        grade.setSelection(pos);
                    }else {
                        Spinner grade = findViewById(R.id.Designation);
                        grade.setSelection(0);
                    }

                    if(!fm_inst.getCreditUsr().getPay().equals("null")) {
                        EditText pay = findViewById(R.id.Salary);
                        pay.setText(fm_inst.getCreditUsr().getPay());
                    }else {
                        EditText pay = findViewById(R.id.Salary);
                        pay.setText("");
                    }

                    if(!fm_inst.getCreditUsr().getDoJ().equals("null")) {
                        EditText doj = findViewById(R.id.doj);
                        doj.setText(fm_inst.getCreditUsr().getDoJ());
                        selDate = 1;
                    }
                    else {
                        EditText doj = findViewById(R.id.doj);
                        doj.setText("");
                        selDate = 0;
                    }

                    System.out.println("emp type "+fm_inst.getCreditUsr().getEmpType());
                    if(!fm_inst.getCreditUsr().getEmpType().equals("null")) {
                        RadioButton perm = findViewById(R.id.Permanent);
                        RadioButton con = findViewById(R.id.Contract);
                        if(fm_inst.getCreditUsr().getEmpType() == "1") {
                            perm.setChecked(true);
                            con.setChecked(false);
                        }
                        else {
                            perm.setChecked(false);
                            con.setChecked(true);
                        }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Toast.makeText(PersonalInfoActivity.this, "on not selected ", Toast.LENGTH_SHORT).show();
            }

        });


        Spinner myDesig = findViewById(R.id.Designation);
        ArrayAdapter<String> dataAdapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, desigList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        myDesig.setAdapter(dataAdapter1);


        myDesig.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                usrDesigSel = position;
                fm_inst.getCreditUsr().setDesignation(desigList.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Toast.makeText(PersonalInfoActivity.this, "on not selected ", Toast.LENGTH_SHORT).show();
            }

        });

        Button submit = findViewById(R.id.submitBtn);
        submit.setOnClickListener(view -> {

            fm_inst.getCreditUsr().setAction("Update");

            try {

                if(usrSel == 0) {
                    Toast.makeText(EmploymentDetailsActivity.this, "Select User", Toast.LENGTH_LONG).show();
                    return;
                }

                Spinner grade = findViewById(R.id.Designation);
                if(usrDesigSel == 0) {

                    Toast.makeText(EmploymentDetailsActivity.this, "Select Designation", Toast.LENGTH_LONG).show();
                    return;
                }

                EditText salary = findViewById(R.id.Salary);
                if(salary.getText().toString().length() == 0) {
                    salary.setError("Enter Salary Details");
                    Toast.makeText(EmploymentDetailsActivity.this, "Enter Designation", Toast.LENGTH_LONG).show();
                    return;
                }

                if(selDate == 0) {
                    Toast.makeText(EmploymentDetailsActivity.this, "Enter Date of Joining", Toast.LENGTH_LONG).show();
                    return;
                }


                fm_inst.getCreditUsr().setPay(salary.getText().toString());

                RadioButton perm = findViewById(R.id.Permanent);
                if(perm.isChecked() == true)
                    fm_inst.getCreditUsr().setEmpType("1");
                else
                    fm_inst.getCreditUsr().setEmpType("0");
                AsyncTask<String, Void, Integer> result = new SalaryUpdate().execute();
                int code = result.get();
                if(code == 500) {
                    Toast.makeText(EmploymentDetailsActivity.this, "Failed to add Salary Details", Toast.LENGTH_LONG).show();
                    return;
                }
                else {
                    Toast.makeText(EmploymentDetailsActivity.this, "Successfully Assigned", Toast.LENGTH_LONG).show();
                    finish();
                }
            }catch (Exception e) {

                Toast.makeText(EmploymentDetailsActivity.this, "Fail to add Salary Details", Toast.LENGTH_SHORT).show();
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
        String doj = sdf.format(dateTime.getTime());
        startDatePicker.setText(doj);
        fm_inst.getCreditUsr().setDoJ(doj);
        selDate = 1;
    }

    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        finish();
        return true;
    }

}
