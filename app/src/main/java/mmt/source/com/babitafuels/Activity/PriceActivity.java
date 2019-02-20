package mmt.source.com.babitafuels.Activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import mmt.source.com.babitafuels.Model.Bunk;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.Model.Price;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.AddPrice;
import mmt.source.com.babitafuels.Service.AddPump;
import mmt.source.com.babitafuels.Service.CreditTxn;
import mmt.source.com.babitafuels.Service.GetAllBunk;
import mmt.source.com.babitafuels.Service.GetAllPrice;
import mmt.source.com.babitafuels.Service.GetAllUser;

public class PriceActivity extends SlidingBarActivity {

    private Button btn_submit;
    private EditText mDate;
    private EditText mTime;
    private int mDay;
    private int mMonth;
    private int mYear;
    private Button mupdate;

    private ArrayList<Bunk> allBunk;
    int selBunk = 0;
    FuelMnt fm_inst;
    private Boolean isDob = false;
    private TextView mModuleTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_price, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Price");


        fm_inst = FuelMnt.getInstance();
        allBunk = new ArrayList<Bunk>();
        fm_inst.getBunk().setAction("List");
        try {

            AsyncTask<String, Void, Integer> result = new GetAllBunk().execute();
            int code = result.get();
            if (code == 500) {
                Toast.makeText(PriceActivity.this, "Failed get bunk List", Toast.LENGTH_LONG).show();

            } else {

            }

        }catch (Exception e) {

            Toast.makeText(PriceActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
        }


        try {

            fm_inst.getTodayPrice().setAction("List");
            AsyncTask<String, Void, Integer> result = new GetAllPrice().execute();
            int code = result.get();
            if (code == 500) {
                Toast.makeText(PriceActivity.this, "Fail to get price Details", Toast.LENGTH_LONG).show();

            } else if (code == 200) {
            }
        }catch(Exception e){

            Toast.makeText(PriceActivity.this, "Fatal Fail to get price details", Toast.LENGTH_LONG).show();
        }


        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();



        List<String> bunkList = new ArrayList<String>();
        bunkList.add("Select Bunk");


        for(int i = 0; i < fm_inst.getAllBunk().size(); i++) {
            bunkList.add(fm_inst.getAllBunk().get(i).getBunkName());
            allBunk.add(fm_inst.getAllBunk().get(i));
        }

        Spinner mySpinner = findViewById(R.id.bunkSel);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bunkList);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // attaching data adapter to spinner
        mySpinner.setAdapter(dataAdapter);

        btn_submit = findViewById(R.id.update);
        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PriceActivity.this, "Update Clicked", Toast.LENGTH_SHORT).show();
            }
        });

/*
        mDate = findViewById(R.id.date);
        mTime = findViewById(R.id.time);
        mDate.setText(formattedDate);
        mTime.setText(localTime);
*/
        mupdate = findViewById(R.id.update);

        mupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selBunk == 0) {
                    Toast.makeText(PriceActivity.this, "select bunk", Toast.LENGTH_SHORT).show();
                    return;
                }

                EditText ptrlPrice = findViewById(R.id.petPrice);
                EditText dieselPrice = findViewById(R.id.dieselPrice);
                EditText oilPrice = findViewById(R.id.oilPrice);
                if(ptrlPrice.getText().toString().length() == 0) {
                    Toast.makeText(PriceActivity.this, "Enter Petrol Price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(dieselPrice.getText().toString().length() == 0) {
                    Toast.makeText(PriceActivity.this, "Enter Diesel Price", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(oilPrice.getText().toString().length() == 0) {
                    Toast.makeText(PriceActivity.this, "Enter Oil Price", Toast.LENGTH_SHORT).show();
                    return;
                }

                fm_inst.getTodayPrice().setAction("Add");
                fm_inst.getTodayPrice().setUsrId(fm_inst.getUsrInfo().getUsrId());
                fm_inst.getTodayPrice().setPetrolPrice(ptrlPrice.getText().toString());
                fm_inst.getTodayPrice().setDieselPrice(dieselPrice.getText().toString());
                fm_inst.getTodayPrice().setOilPrice(oilPrice.getText().toString());



                try {

                    AsyncTask<String, Void, Integer> result = new AddPrice().execute();
                    int code = result.get();
                    if (code == 500) {
                        Toast.makeText(PriceActivity.this, "Fail to add price Details", Toast.LENGTH_LONG).show();

                    } else if (code == 200) {
                        Toast.makeText(PriceActivity.this, "Price Updated Succesfully", Toast.LENGTH_LONG).show();
                        fm_inst.setTodayPrice(new Price());
                        finish();
                    }
                }catch(Exception e){

                    Toast.makeText(PriceActivity.this, "Fail to add price details", Toast.LENGTH_LONG).show();
                }
            }
        });


        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selBunk = position;
                if(position != 0) {
                    fm_inst.getTodayPrice().setBunkId(String.valueOf(allBunk.get(position - 1).getBunkId()));

                    Date c = Calendar.getInstance().getTime();
                    System.out.println("Current time => " + c);
                    Calendar dateTime = Calendar.getInstance();
                    dateTime.set(Calendar.YEAR, dateTime.get(Calendar.YEAR));
                    dateTime.set(Calendar.MONTH, dateTime.get(Calendar.MONTH));
                    dateTime.set(Calendar.DAY_OF_MONTH, dateTime.get(Calendar.DAY_OF_MONTH));

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
                    //String startDate = sdf.format(dateTime.getTime());

                    String localTime = sdf.format(dateTime.getTime());

                    String[] todayDate = localTime.split("-");

                    //System.out.println("shiva last record date is "+fm_inst.getAllPrice().get(0).getCreateDate() +" today "+todayDate[0]);
                    String[] lastRecord1 = fm_inst.getAllPrice().get(0).getCreateDate().split(" ");
                    String[] lastRecord2 = lastRecord1[0].split("-");
                    if(todayDate[0].equals(lastRecord2[0]) && todayDate[1].equals(lastRecord2[1]) && todayDate[2].equals(lastRecord2[2]) && fm_inst.getAllPrice().get(0).getBunkId().equals(String.valueOf(allBunk.get(position - 1).getBunkId()))) {
                       // System.out.println("shiva found matching date records");
                        mupdate = findViewById(R.id.update);

                        mupdate.setText("UPDATE");

                        EditText ptrlPrice = findViewById(R.id.petPrice);
                        EditText dieselPrice = findViewById(R.id.dieselPrice);
                        EditText oilPrice = findViewById(R.id.oilPrice);
                        ptrlPrice.setText(fm_inst.getAllPrice().get(0).getPetrolPrice());
                        dieselPrice.setText(fm_inst.getAllPrice().get(0).getDieselPrice());
                        oilPrice.setText(fm_inst.getAllPrice().get(0).getOilPrice());
                    }
                    else {

                        EditText ptrlPrice = findViewById(R.id.petPrice);
                        EditText dieselPrice = findViewById(R.id.dieselPrice);
                        EditText oilPrice = findViewById(R.id.oilPrice);
                        ptrlPrice.setText("");
                        dieselPrice.setText("");
                        oilPrice.setText("");
                        mupdate = findViewById(R.id.update);
                        mupdate.setText("ADD");
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Toast.makeText(PersonalInfoActivity.this, "on not selected ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
