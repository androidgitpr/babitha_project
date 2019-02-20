package mmt.source.com.babitafuels.Activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import mmt.source.com.babitafuels.Adapter.HomeScreenAdapter;
import mmt.source.com.babitafuels.Fragment.FooterFragment;
import mmt.source.com.babitafuels.Model.Access;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.Model.HomeIcons;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.GetAccess;
import mmt.source.com.babitafuels.dao.UserDetailsDao;
import mmt.source.com.babitafuels.util.DbHelper;

public class HomeScreenNewActivity extends SlidingBarActivity {

    GridView androidGridView;
    TextView mModuleTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_home_screen_new, null, false);
        mDrawer.addView(contentView, 0);

        mModuleTitle = (TextView)findViewById(R.id.product_title);
        mModuleTitle.setText("Home");


        FuelMnt fm_inst = FuelMnt.getInstance();
        fm_inst.getSetAccess().setAction("Get");
        fm_inst.getSetAccess().setUsrId(fm_inst.getUsrInfo().getUsrId());



        DbHelper.createVendorDbHelper(HomeScreenNewActivity.this);
        UserDetailsDao vendorDetailsDao = new UserDetailsDao();
        fm_inst.getSetAccess().setAccessPage(vendorDetailsDao.getAccessCtrl());
        if (fm_inst.getSetAccess().getAccessPage().equals("-1")) {
            try {
            //System.out.println("shiva get my access");
                AsyncTask<String, Void, Integer> result = new GetAccess().execute();
                int code = result.get();
                if (code == 500) {
                    //Toast.makeText(HomeScreenActivity.this, "Fail to get access rights", Toast.LENGTH_LONG).show();

                } else if (code == 200) {

                    DbHelper.createVendorDbHelper(HomeScreenNewActivity.this);
                    vendorDetailsDao.addAccessCtrl(fm_inst.getSetAccess().getAccessPage());

                    fm_inst.getMyAccess().clear();
                    //parse access
                    String[] accessList = fm_inst.getSetAccess().getAccessPage().split(":");

                    //System.out.println("shiva debug "+accessList.length);
                    for (int i = 0; i < accessList.length; i++) {
                        Access item = new Access();
                        String[] eachItem = accessList[i].split("-");
                        item.setAccessName(eachItem[0]);
                        //System.out.println("shiva debug "+item.getAccessName());
                        item.setPriv(Integer.valueOf(eachItem[1]));
                        fm_inst.getMyAccess().add(item);
                    }
                }
            }catch(Exception e){

                Toast.makeText(HomeScreenNewActivity.this, "Fail to update access rights", Toast.LENGTH_LONG).show();
            }
        }
        else {
                //parse access
            String[] accessList = fm_inst.getSetAccess().getAccessPage().split(":");
            fm_inst.getMyAccess().clear();
            //System.out.println("shiva debug "+accessList.length);
            for (int i = 0; i < accessList.length; i++) {
                Access item = new Access();
                String[] eachItem = accessList[i].split("-");
                item.setAccessName(eachItem[0]);
                //System.out.println("shiva debug "+item.getAccessName());
                item.setPriv(Integer.valueOf(eachItem[1]));
                fm_inst.getMyAccess().add(item);
            }
        }

        fm_inst.getMyPages().clear();
        for(int i = 0; i < fm_inst.getMyAccess().size(); i++) {
            switch (i) {
                case 0:
                    if(fm_inst.getMyAccess().get(i).getPriv() != 0) {
                        HomeIcons item = new HomeIcons();
                        item.setResPath(R.drawable.price1);
                        item.setFunctionName("PRICE");
                        item.setPos(0);
                        fm_inst.getMyPages().add(item);
                    }
                    break;
                case 1:
                    if(fm_inst.getMyAccess().get(i).getPriv() != 0) {
                        HomeIcons item = new HomeIcons();
                        item.setResPath(R.drawable.stock1);
                        item.setFunctionName("STOCK");
                        item.setPos(1);
                        fm_inst.getMyPages().add(item);
                    }

                    break;
                case 2:
                    if(fm_inst.getMyAccess().get(i).getPriv() != 0) {
                        HomeIcons item = new HomeIcons();
                        item.setResPath(R.drawable.collection1);
                        item.setFunctionName("COLLECTION");
                        item.setPos(2);
                        fm_inst.getMyPages().add(item);
                    }

                    break;
                case 3:
                    if(fm_inst.getMyAccess().get(i).getPriv() != 0) {
                        HomeIcons item = new HomeIcons();
                        item.setResPath(R.drawable.expences1);
                        item.setFunctionName("EXPENSES");
                        item.setPos(3);
                        fm_inst.getMyPages().add(item);
                    }

                    break;
                case 4:
                    if(fm_inst.getMyAccess().get(i).getPriv() != 0) {
                        HomeIcons item = new HomeIcons();
                        item.setResPath(R.drawable.credittrans);
                        item.setFunctionName("CREDIT_TXN");
                        item.setPos(4);
                        fm_inst.getMyPages().add(item);
                    }

                    break;
                case 5:
                    if(fm_inst.getMyAccess().get(i).getPriv() != 0) {
                        HomeIcons item = new HomeIcons();
                        item.setResPath(R.drawable.credistup);
                        item.setFunctionName("CREDIT_SETUP");
                        item.setPos(5);
                        fm_inst.getMyPages().add(item);
                    }

                    break;
                case 6:
                    if(fm_inst.getMyAccess().get(i).getPriv() != 0) {
                        HomeIcons item = new HomeIcons();
                        item.setResPath(R.drawable.register);
                        item.setFunctionName("REGISTRATION");
                        item.setPos(6);
                        fm_inst.getMyPages().add(item);
                    }

                    break;
                case 7:
                    if(fm_inst.getMyAccess().get(i).getPriv() != 0) {
                        HomeIcons item = new HomeIcons();
                        item.setResPath(R.drawable.price1);
                        item.setFunctionName("ACCESS");
                        item.setPos(7);
                        fm_inst.getMyPages().add(item);
                    }

                    break;
                case 8:
                    if(fm_inst.getMyAccess().get(i).getPriv() != 0)
                    {
                        HomeIcons item = new HomeIcons();
                        item.setResPath(R.drawable.price1);
                        item.setFunctionName("EMPLOYEMENT");
                        item.setPos(8);
                        fm_inst.getMyPages().add(item);
                    }
                    break;
            }

        }

        HomeScreenAdapter adapter = new HomeScreenAdapter(HomeScreenNewActivity.this, fm_inst.getMyPages());
        androidGridView=(GridView)findViewById(R.id.grid_view_image_text);
        androidGridView.setAdapter(adapter);

        androidGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                switch (fm_inst.getMyPages().get(position).getPos()) {
                    case 0:
                        Intent intent = new Intent(HomeScreenNewActivity.this, PriceActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(HomeScreenNewActivity.this, StockActivity.class);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(HomeScreenNewActivity.this, CollectionsActivity.class);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(HomeScreenNewActivity.this, ExpenseReportActivity.class);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(HomeScreenNewActivity.this, Credit_Transaction.class);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(HomeScreenNewActivity.this, CreditSetupActivityNew.class);
                        startActivity(intent);
                        break;
                    case 6:
                        intent = new Intent(HomeScreenNewActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                        break;
                    case 7:
                        intent = new Intent(HomeScreenNewActivity.this, AccessDetailsActivity.class);
                        startActivity(intent);
                        break;
                    case 8:
                        intent = new Intent(HomeScreenNewActivity.this, EmploymentDetailsActivity.class);
                        startActivity(intent);
                        break;
                }
            }
        });

    }
}
