package mmt.source.com.babitafuels.Fragment;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import mmt.source.com.babitafuels.Activity.HomeScreenNewActivity;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.Model.User;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.CreditSetup;
import mmt.source.com.babitafuels.Service.GetAllUser;

public class CreditSetupFragment extends Fragment implements AdapterView.OnItemSelectedListener{
    View rootView;
    Calendar dateTime = Calendar.getInstance();
    Calendar dateTime1 = Calendar.getInstance();
    EditText startDatePicker, endDatePicker;
    Spinner mySpinner;
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

    public CreditSetupFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_credit_setup, container, false);


        creditUsrList = new ArrayList<User>();
        fm_inst = FuelMnt.getInstance();
        fm_inst.getUsrInfo().setAction("List");

        try {
            if(fm_inst.getAllUsrInfo().size() == 0) {
                AsyncTask<String, Void, Integer> result = new GetAllUser().execute();
                int code = result.get();
                if (code == 500) {
                    Toast.makeText(getActivity(), "Failed get user List", Toast.LENGTH_LONG).show();

                } else {

                }
            }
        }catch (Exception e) {

            Toast.makeText(getActivity(), "Failed get user List", Toast.LENGTH_LONG).show();
        }

        mySpinner = (Spinner)rootView.findViewById(R.id.crdcustspinner);

        mySpinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Credit Customer Name");

        for(int i = 0; i < fm_inst.getAllUsrInfo().size(); i++) {
            if(fm_inst.getAllUsrInfo().get(i).getRegType().equals("Credit")) {
                categories.add(fm_inst.getAllUsrInfo().get(i).getUsrName());
                creditUsrList.add(fm_inst.getAllUsrInfo().get(i));
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);

        startDatePicker =rootView.findViewById(R.id.startDateId);
        endDatePicker=rootView.findViewById(R.id.endDateId);
        startDatePicker.setOnClickListener(view -> openDatePicker());
        endDatePicker.setOnClickListener(view -> openDatePicker1());

        Button submitBtn = (Button) rootView.findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {

                try {
                    int fuelTyep = 0;

                    System.out.println("shiva updated calling credit calling api");
                    FuelMnt fm_inst = FuelMnt.getInstance();
                    if(userSelect == 0) {
                        Toast.makeText(getActivity(), "Select User", Toast.LENGTH_LONG).show();
                        return;
                    }
                    fm_inst.getCreditUsr().setAction("Update");

                    System.out.println("shiva update calling credit calling api 1");
                    fm_inst.getCreditUsr().setStartDate(startDate);
                    fm_inst.getCreditUsr().setEndDate(endDate);
                    EditText limit = rootView.findViewById(R.id.noltrid);

                    if(limit.getText().toString().length() == 0) {
                        limit.setError("Enter Limit Information");
                        return;
                    }
                    //System.out.println("shiva update calling credit calling api 2");
                    fm_inst.getCreditUsr().setCreditLmt(limit.getText().toString());

                    EditText advPaid = rootView.findViewById(R.id.advpaidid);
                    if(advPaid.getText().toString().length() == 0) {
                        advPaid.setError("Enter Limit Information");
                        return;
                    }
                    fm_inst.getCreditUsr().setAdvPaid(advPaid.getText().toString());

                    CheckBox cb = rootView.findViewById(R.id.petrold1);
                    if(cb.isChecked())
                        fuelTyep += 1;
                    cb = rootView.findViewById(R.id.dieselid1);
                    if(cb.isChecked())
                        fuelTyep += 2;
                    cb = rootView.findViewById(R.id.oilid1);
                    if(cb.isChecked())
                        fuelTyep += 4;

                    fm_inst.getCreditUsr().setFuelType(String.valueOf(fuelTyep));
                    if(fuelTyep == 0) {
                        Toast.makeText(getActivity(), "Select Fuel Type", Toast.LENGTH_LONG).show();
                        return;
                    }
                    AsyncTask<String, Void, Integer> result = new CreditSetup().execute();
                    int code = result.get();
                    if(code == 500) {
                        Toast.makeText(getActivity(), "Fail to setup Credit Information", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(code == 200){
                        Intent i = new Intent(getActivity(),HomeScreenNewActivity.class);
                        startActivity(i);
                    }
                }catch (Exception e) {

                    Toast.makeText(getActivity(), "Fail to setup Credit Information", Toast.LENGTH_LONG).show();
                }

            }
        });

        return rootView;
    }
    private void openDatePicker(){
        new DatePickerDialog(getContext(), datePickerDialog, dateTime.get(Calendar.YEAR),dateTime.get(Calendar.MONTH),dateTime.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void openDatePicker1(){
        new DatePickerDialog(getContext(), datePickerDialog1, dateTime1.get(Calendar.YEAR),dateTime1.get(Calendar.MONTH),dateTime1.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        startDate = sdf.format(dateTime.getTime());
        startDatePicker.setText(startDate);

    }

    private void updateDate1(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        endDate = sdf.format(dateTime.getTime());
        endDatePicker.setText(endDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if(position != 0) {
            fm_inst.setCreditUsr(creditUsrList.get(position - 1));
            userSelect = position;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}