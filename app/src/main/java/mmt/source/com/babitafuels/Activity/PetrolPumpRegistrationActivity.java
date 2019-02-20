package mmt.source.com.babitafuels.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.GetAllBunk;
import mmt.source.com.babitafuels.Service.GetAllUser;
import mmt.source.com.babitafuels.util.Utils;

public class PetrolPumpRegistrationActivity extends SlidingBarActivity implements AdapterView.OnItemSelectedListener{
    private ImageView petrolPumpImg, userImg, petrolBunkImg, ccImg;
    private TextView mModuleTitle;
    Spinner pumpSpinner;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.petrol_pump_registration_activity, null, false);
        mDrawer.addView(contentView, 0);


        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Petrol Pump Registration");



        FuelMnt fm_inst = FuelMnt.getInstance();
        fm_inst.getBunk().setAction("List");

        try {

            AsyncTask<String, Void, Integer> result = new GetAllBunk().execute();
            int code = result.get();
            if(code == 500) {
                Toast.makeText(PetrolPumpRegistrationActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
                return;
            }
            else {

            }
        }catch (Exception e) {

            Toast.makeText(PetrolPumpRegistrationActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
        }

        pumpSpinner = (Spinner)contentView.findViewById(R.id.spinnerpm);

        petrolPumpImg = contentView.findViewById(R.id.ppImageView1);
        userImg = contentView.findViewById(R.id.userImageView1);
        petrolBunkImg = contentView.findViewById(R.id.pbImageView1);
        ccImg = contentView.findViewById(R.id.ccImageView1);


        userImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetrolPumpRegistrationActivity.this, PetrolPumpInfoActivity.class);
                startActivity(intent);
            }
        });

        petrolPumpImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetrolPumpRegistrationActivity.this, PetrolPumpInfoActivity.class);
                startActivity(intent);
            }
        });

        petrolBunkImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetrolPumpRegistrationActivity.this, PetrolPumpInfoActivity.class);
                startActivity(intent);
            }
        });

        ccImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PetrolPumpRegistrationActivity.this, PetrolPumpInfoActivity.class);
                startActivity(intent);
            }
        });




        pumpSpinner.setOnItemSelectedListener(this);
        List<String> bunkList = new ArrayList<String>();
        bunkList.add("Petrol Bunk Name");

        for(int i = 0; i < fm_inst.getAllBunk().size(); i++) {
            bunkList.add(fm_inst.getAllBunk().get(i).getBunkName());
        }

        ArrayAdapter<String> pumpAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, bunkList);
        pumpAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        pumpSpinner.setAdapter(pumpAdapter);

        Utils.setImageToImageView(this, petrolPumpImg, R.drawable.pump4);
        Utils.setImageToImageView(this, userImg, R.drawable.pump1);
        Utils.setImageToImageView(this, petrolBunkImg, R.drawable.pump3);
        Utils.setImageToImageView(this, ccImg, R.drawable.pump2);

        Fragment fragment = new FooterFragment();
        FragmentManager fm1 = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm1.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}


