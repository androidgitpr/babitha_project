package mmt.source.com.babitafuels.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.Model.User;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.GetAllCredit;
import mmt.source.com.babitafuels.Service.GetAllExpenses;
import mmt.source.com.babitafuels.Service.GetAllUser;

public class CreditReportActivity extends SlidingBarActivity {

    private int mDay;
    private int mMonth;
    private int mYear;
    private String[] selDate;
    private View view;
    private FuelMnt fm_Inst;
    private TextView dateSel;
    ArrayList<User> creditUsrList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.credit_txn_details, null, false);
        mDrawer.addView(contentView, 0);

        fm_Inst = FuelMnt.getInstance();
        creditUsrList = new ArrayList<User>();
        try {

            fm_Inst.getCreditTxn().setAction("List");

            AsyncTask<String, Void, Integer> result = new GetAllCredit().execute();
            int code = result.get();
            if (code == 500) {
                //Toast.makeText(HomeScreenActivity.this, "Fail to get access rights", Toast.LENGTH_LONG).show();

            } else if (code == 200) {

                System.out.println("shiva debug 1 "+fm_Inst.getAllCreditTxn().size());
                if(fm_Inst.getAllCreditTxn().size() != 0) {
                 //   String[] parseDate = fm_Inst.getGetMyExpenses().get(fm_Inst.getGetMyExpenses().size() - 1).getExpDate().split(" ");
                   // selDate = parseDate[0].split("-");


                }
                else {
                    Toast.makeText(CreditReportActivity.this, "No Transaction Details ", Toast.LENGTH_LONG).show();
                }
            }
        }catch(Exception e){

            Toast.makeText(CreditReportActivity.this, "Fail to get expenses report", Toast.LENGTH_LONG).show();
        }


        Spinner mySpinner = (Spinner)contentView.findViewById(R.id.spinnercr);

        fm_Inst.getUsrInfo().setAction("List");

        try {
            if(fm_Inst.getAllUsrInfo().size() == 0) {
                AsyncTask<String, Void, Integer> result = new GetAllUser().execute();
                int code = result.get();
                if (code == 500) {
                    Toast.makeText(CreditReportActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();

                } else {

                }
            }
        }catch (Exception e) {

            Toast.makeText(CreditReportActivity.this, "Failed get user List", Toast.LENGTH_LONG).show();
        }


        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Credit Customer Name");

        for(int i = 0; i < fm_Inst.getAllUsrInfo().size(); i++) {
            if(fm_Inst.getAllUsrInfo().get(i).getRegType().equals("Credit")) {
                categories.add(fm_Inst.getAllUsrInfo().get(i).getUsrName());
                creditUsrList.add(fm_Inst.getAllUsrInfo().get(i));
            }
        }

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, categories);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        mySpinner.setAdapter(dataAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if(position != 0)
                updateValue(creditUsrList.get(position -1).getUsrId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                //Toast.makeText(PersonalInfoActivity.this, "on not selected ", Toast.LENGTH_SHORT).show();
            }
        });

/*
        dateSel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                calendar.setTimeZone(TimeZone.getTimeZone("UTC"));

                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(ExpenseReportActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            public void onDateSet(DatePicker view, int selectedYear,
                                                  int selectedMonth, int selectedDay) {
                                mDay = selectedDay;
                                selectedMonth++;
                                mMonth = selectedMonth;
                                mYear = selectedYear;
                                StringBuilder Date = new StringBuilder("");
                                String conver = Integer.toString(selectedYear);
                                Date.append(conver);
                                Date.append("-");
                                conver = Integer.toString(selectedMonth);
                                //conver = selectedMonth <= 9 ? "0"+selectedMonth : String.valueOf(selectedMonth);
                                //conver = Integer.toString(selectedMonth);
                                Date.append(conver);
                                Date.append("-");
                                //conver = selectedDay <= 9 ? "0"+selectedDay : String.valueOf(selectedDay);
                                conver = Integer.toString(selectedDay);
                                Date.append(conver);

                                dateSel.setText("Date : " + Date.toString());
                                selDate = Date.toString().split("-");
                                updateValue();

                            }
                        }, mDay, mMonth, mYear);

                datePickerDialog.updateDate(mYear,mMonth,mDay);
                datePickerDialog.setTitle("Select Date");
                datePickerDialog.show();
            }
        });
*/
    }

    void updateValue(String usrId) {


        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        stk.removeAllViews();

        int amount = 0;
        System.out.println("shiva debug "+fm_Inst.getAllCreditTxn().size());
        for (int i = 0; i < fm_Inst.getAllCreditTxn().size(); i++) {
            TableRow tbrow = new TableRow(CreditReportActivity.this);
            //String[] dateParse = fm_Inst.getGetMyExpenses().get(i).getExpDate().split(" ");
           // String[] date1Parse = dateParse[0].split("-");

            System.out.println("shiva debug 2");
//            if (date1Parse[0].equals(selDate[0]) && date1Parse[1].equals(selDate[1]) && date1Parse[2].equals(selDate[2]))
              if(fm_Inst.getAllCreditTxn().get(i).getUsrId().equals(usrId) )
             {

                tbrow.setBackgroundResource(R.drawable.cell_border);
                TextView t1v = new TextView(CreditReportActivity.this);
                t1v.setText(""+fm_Inst.getAllCreditTxn().get(i).getTxnDate());
                t1v.setTextColor(Color.BLACK);
                t1v.setGravity(Gravity.CENTER);
                t1v.setPadding(10, 10, 10, 10);
                tbrow.addView(t1v);
                TextView t2v = new TextView(CreditReportActivity.this);
                t2v.setText(" " + fm_Inst.getAllCreditTxn().get(i).getAmountPaid());
                t2v.setTextColor(Color.BLACK);
                t2v.setGravity(Gravity.CENTER);
                t2v.setPadding(10, 10, 10, 10);
                tbrow.addView(t2v);
                TextView t3v = new TextView(CreditReportActivity.this);
                t3v.setText("" + fm_Inst.getAllCreditTxn().get(i).getAmountUsed());
                t3v.setTextColor(Color.BLACK);
                t3v.setGravity(Gravity.CENTER);
                t3v.setPadding(10, 10, 10, 10);
                tbrow.addView(t3v);
                stk.addView(tbrow);

                //amount += Integer.valueOf(fm_Inst.getGetMyExpenses().get(i).getAmount());
            }
        }
        //System.out.println("shiva debug 3");
        TextView txtView = findViewById(R.id.expv18);
        txtView.setText(""+amount);
    }
}

