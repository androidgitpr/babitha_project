package mmt.source.com.babitafuels.Activity;

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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.AddBunk;
import mmt.source.com.babitafuels.Service.AddPump;
import mmt.source.com.babitafuels.Service.GetAllBunk;
import mmt.source.com.babitafuels.Service.GetAllPump;

public class PetrolPumpInfoActivity extends SlidingBarActivity{

    private TextView mModuleTitle;
    private EditText Camera;
    int isBunkSel;
    public  static final int CAMERA_PIC_REQUEST = 1;//firstly define this
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.petrol_pump_info_activity, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Petrol Pump Information");

        isBunkSel = 0;

        FuelMnt fm_inst = FuelMnt.getInstance();
        fm_inst.getBunk().setAction("List");

        try {

            AsyncTask<String, Void, Integer> result = new GetAllBunk().execute();
            int code = result.get();
            if(code == 500) {
                Toast.makeText(PetrolPumpInfoActivity.this, "Failed get bunk List", Toast.LENGTH_LONG).show();
                return;
            }
            else {

            }
        }catch (Exception e) {

            Toast.makeText(PetrolPumpInfoActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
        }

        Spinner pumpSpinner = (Spinner)contentView.findViewById(R.id.spinnerpm);


        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();


        List<String> bunkList = new ArrayList<String>();
        bunkList.add("Petrol Bunk Name");

        for(int i = 0; i < fm_inst.getAllBunk().size(); i++) {
            bunkList.add(fm_inst.getAllBunk().get(i).getBunkName());
        }

        ArrayAdapter<String> pumpAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bunkList);
        pumpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pumpSpinner.setAdapter(pumpAdapter);

        pumpSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                isBunkSel = position;
                if(position != 0) {

                    try {
                        fm_inst.getPump().setAction("List");
                        AsyncTask<String, Void, Integer> result = new GetAllPump().execute();
                        int code = result.get();

                        if(code == 500) {
                            Toast.makeText(PetrolPumpInfoActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();

                            Spinner pumpSpinner1 = (Spinner) contentView.findViewById(R.id.spinnerpm1);
                            List<String> pumpList = new ArrayList<String>();
                            pumpList.add("No Pump ");

                            ArrayAdapter<String> pumpAdapter1 = new ArrayAdapter<String>(PetrolPumpInfoActivity.this, android.R.layout.simple_spinner_item, pumpList);
                            pumpAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            pumpSpinner1.setAdapter(pumpAdapter1);
                        }
                        else if(code == 200){

                            Spinner pumpSpinner1 = (Spinner) contentView.findViewById(R.id.spinnerpm1);
                            List<String> pumpList = new ArrayList<String>();

                            pumpList.add("Pump List");

                            for (int i = 0; i < fm_inst.getAllPump().size(); i++) {
                                System.out.println("shiva pump list "+fm_inst.getAllPump().get(i).getBunkId() +" compare to "+fm_inst.getAllBunk().get(position - 1).getBunkId());
                                if (fm_inst.getAllPump().get(i).getBunkId() == fm_inst.getAllBunk().get(position -1).getBunkId())
                                    pumpList.add(fm_inst.getAllPump().get(i).getPumpNum());

                            }

                            ArrayAdapter<String> pumpAdapter1 = new ArrayAdapter<String>(PetrolPumpInfoActivity.this, android.R.layout.simple_spinner_item, pumpList);
                            pumpAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            pumpSpinner1.setAdapter(pumpAdapter1);
                        }
                    }catch (Exception e) {

                        Spinner pumpSpinner1 = (Spinner) contentView.findViewById(R.id.spinnerpm1);
                        List<String> pumpList = new ArrayList<String>();
                        pumpList.add("No Pump ");

                        ArrayAdapter<String> pumpAdapter1 = new ArrayAdapter<String>(PetrolPumpInfoActivity.this, android.R.layout.simple_spinner_item, pumpList);
                        pumpAdapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        pumpSpinner1.setAdapter(pumpAdapter1);
                        Toast.makeText(PetrolPumpInfoActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
                    }


                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Toast.makeText(PersonalInfoActivity.this, "on not selected ", Toast.LENGTH_SHORT).show();
            }

        });
        Camera = contentView.findViewById(R.id.camerid);
        Camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo= new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(photo, CAMERA_PIC_REQUEST);
            }
        });

        Button submit = contentView.findViewById(R.id.submitBtn);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText pumpNum = findViewById(R.id.pumpNum);
                if(pumpNum.getText().toString().length() == 0) {
                    pumpNum.setError("Enter pump details");
                    return;
                }
                int value = 0;
                CheckBox petrol = findViewById(R.id.ptrradio);
                CheckBox diesel = findViewById(R.id.desradio);
                if(petrol.isChecked() == true) {
                    value += 1;
                }
                if(diesel.isChecked() == true) {
                    value += 2;
                }
                if(value == 0) {
                    Toast.makeText(PetrolPumpInfoActivity.this, "Select Fuel Type", Toast.LENGTH_LONG).show();
                    return;
                }

                if(isBunkSel == 0) {
                    Toast.makeText(PetrolPumpInfoActivity.this, "Select Bunk", Toast.LENGTH_LONG).show();
                    return;
                }
                fm_inst.getPump().setAction("Add");
                fm_inst.getPump().setBunkId(fm_inst.getAllBunk().get(isBunkSel -1).getBunkId());
                fm_inst.getPump().setPumpNum(pumpNum.getText().toString());
                fm_inst.getPump().setFuelType(String.valueOf(value));

                try {

                    AsyncTask<String, Void, Integer> result = new AddPump().execute();
                    int code = result.get();
                    if(code == 500) {
                        Toast.makeText(PetrolPumpInfoActivity.this, "Failed to Add Pump Info", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(code == 200){
                        Toast.makeText(PetrolPumpInfoActivity.this, "Successfully Added", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }catch (Exception e) {

                    Toast.makeText(PetrolPumpInfoActivity.this, "Fail to add Pump Info", Toast.LENGTH_SHORT).show();
                }

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
