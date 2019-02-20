package mmt.source.com.babitafuels.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Camera;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.Login;
import mmt.source.com.babitafuels.Service.Register;

import static mmt.source.com.babitafuels.Activity.PetrolPumpInfoActivity.CAMERA_PIC_REQUEST;

public class CreditCustomerRegistrationActivity extends SlidingBarActivity {

    private TextView mModuleTitle, govtText1,govtText2,govtText3;
    public  static final int CAMERA_PIC_REQUEST = 1;//firstly define this
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.creadit_customer_reg_activity, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Credit Customer Registration");

        govtText1=contentView.findViewById(R.id.govt1);
        govtText2=contentView.findViewById(R.id.govt2);
        govtText3=contentView.findViewById(R.id.govt3);

        govtText1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo= new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(photo, CAMERA_PIC_REQUEST);
            }
        });

        govtText2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo= new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(photo, CAMERA_PIC_REQUEST);
            }
        });

        govtText3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photo= new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(photo, CAMERA_PIC_REQUEST);
            }
        });

        Fragment fragment = new FooterFragment();
        FragmentManager fm = getFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.framelayout, fragment);
        fragmentTransaction.commit();




        Button submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)
            {
                EditText usrName = findViewById(R.id.custnameId);
                EditText usrMobile = findViewById(R.id.contactNoId);
                EditText usrBusNam = findViewById(R.id.busineNameId);
                EditText usrEmail = findViewById(R.id.cremailId);
                EditText usrBusMobile = findViewById(R.id.busicontactNoId);
                EditText usrOffAddr = findViewById(R.id.offaddId);

                if(usrName.getText().toString().length() == 0) {
                    usrName.setError("Enter Valid Name");
                    return;
                }


                if(usrMobile.getText().toString().length() != 10) {
                    usrMobile.setError("Mobile Number should be 10 digit");
                    return;
                }


                if(usrBusNam.getText().toString().length() == 0) {
                    usrBusNam.setError("Enter Valid Business Name");
                    return;
                }

                //
                if(usrEmail.getText().toString().length() == 0) {
                    usrEmail.setError("Enter Valid Email");
                    return;
                }


                if(usrBusMobile.getText().toString().length() != 10) {
                    usrBusMobile.setError("Mobile number should be 10 digit");
                    return;
                }


                if(usrOffAddr.getText().toString().length() == 0) {
                    usrOffAddr.setError("Enter Valid Office Address");
                    return;
                }


                try {
                    System.out.println("shiva calling credit calling api");
                    FuelMnt fm_inst = FuelMnt.getInstance();
                    fm_inst.getUsrInfo().setAction("Add");
                    fm_inst.getUsrInfo().setRegType("Credit");
                    fm_inst.getUsrInfo().setUsrName(usrName.getText().toString());
                    fm_inst.getUsrInfo().setMobileNum(usrMobile.getText().toString());
                    fm_inst.getUsrInfo().setBusName(usrBusNam.getText().toString());
                    fm_inst.getUsrInfo().setEmailId(usrEmail.getText().toString());
                    fm_inst.getUsrInfo().setEmgMobileNum(usrBusMobile.getText().toString());
                    fm_inst.getUsrInfo().setEmgAddr(usrOffAddr.getText().toString());

                    AsyncTask<String, Void, Integer> result = new Register().execute();
                    int code = result.get();
                    if(code == 500) {
                        Toast.makeText(CreditCustomerRegistrationActivity.this, "Fail to Register", Toast.LENGTH_LONG).show();
                        return;
                    }
                    else if(code == 200){
                        finish();
                    }
                }catch (Exception e) {

                    Toast.makeText(CreditCustomerRegistrationActivity.this, "fail to Register", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

}

