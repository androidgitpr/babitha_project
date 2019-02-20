package mmt.source.com.babitafuels.Activity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.HashMap;
import java.util.TimeZone;

import mmt.source.com.babitafuels.Model.Access;
import mmt.source.com.babitafuels.Model.Expenses;
import mmt.source.com.babitafuels.Model.FuelMnt;
import mmt.source.com.babitafuels.R;
import mmt.source.com.babitafuels.Service.GetAccess;
import mmt.source.com.babitafuels.Service.GetAllExpenses;
import mmt.source.com.babitafuels.util.DbHelper;

public class ExpenseReportActivity extends SlidingBarActivity {

    private int mDay;
    private int mMonth;
    private int mYear;
    private String[] selDate;
    private View view;
    private FuelMnt fm_Inst;
    private TextView dateSel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.fragment_expense_report, null, false);
        mDrawer.addView(contentView, 0);

        fm_Inst = FuelMnt.getInstance();
        try {

            fm_Inst.getExpenses().setAction("List");

            fm_Inst.getExpenses().setEmpId(fm_Inst.getUsrInfo().getUsrId());

            AsyncTask<String, Void, Integer> result = new GetAllExpenses().execute();
            int code = result.get();
            if (code == 500) {
                //Toast.makeText(HomeScreenActivity.this, "Fail to get access rights", Toast.LENGTH_LONG).show();

            } else if (code == 200) {

                System.out.println("shiva debug 1 "+fm_Inst.getGetMyExpenses().size());
                if(fm_Inst.getGetMyExpenses().size() != 0) {
                 //   String[] parseDate = fm_Inst.getGetMyExpenses().get(fm_Inst.getGetMyExpenses().size() - 1).getExpDate().split(" ");
                   // selDate = parseDate[0].split("-");
                    System.out.println("shiva debug 1");
                    updateValue();
                }
                else {
                    Toast.makeText(ExpenseReportActivity.this, "No Transaction Details ", Toast.LENGTH_LONG).show();
                }
            }
        }catch(Exception e){

            Toast.makeText(ExpenseReportActivity.this, "Fail to get expenses report", Toast.LENGTH_LONG).show();
        }

        TextView txtView = findViewById(R.id.add1);
        txtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v1) {
                Intent launchActivity1 = new Intent(ExpenseReportActivity.this, ExpencesActivity.class);
                startActivity(launchActivity1);
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

    void updateValue() {


        TableLayout stk = (TableLayout) findViewById(R.id.table_main);
        stk.removeAllViews();

        int amount = 0;
        System.out.println("shiva debug "+fm_Inst.getGetMyExpenses().size());
        for (int i = 0; i < fm_Inst.getGetMyExpenses().size(); i++) {
            TableRow tbrow = new TableRow(ExpenseReportActivity.this);
            //String[] dateParse = fm_Inst.getGetMyExpenses().get(i).getExpDate().split(" ");
           // String[] date1Parse = dateParse[0].split("-");

            System.out.println("shiva debug 2");
//            if (date1Parse[0].equals(selDate[0]) && date1Parse[1].equals(selDate[1]) && date1Parse[2].equals(selDate[2]))
              if(fm_Inst.getGetMyExpenses().get(i).getEmpId().equals(fm_Inst.getUsrInfo().getUsrId()) )
             {

                tbrow.setBackgroundResource(R.drawable.cell_border);
                TextView t1v = new TextView(ExpenseReportActivity.this);
                t1v.setText("\u20B9 "+fm_Inst.getGetMyExpenses().get(i).getAmount());
                t1v.setTextColor(Color.BLACK);
                t1v.setGravity(Gravity.CENTER);
                t1v.setPadding(10, 10, 10, 10);
                tbrow.addView(t1v);
                TextView t2v = new TextView(ExpenseReportActivity.this);
                t2v.setText(" " + fm_Inst.getGetMyExpenses().get(i).getExpType());
                t2v.setTextColor(Color.BLACK);
                t2v.setGravity(Gravity.CENTER);
                t2v.setPadding(10, 10, 10, 10);
                tbrow.addView(t2v);
                TextView t3v = new TextView(ExpenseReportActivity.this);
                t3v.setText("" + fm_Inst.getGetMyExpenses().get(i).getExpDate());
                t3v.setTextColor(Color.BLACK);
                t3v.setGravity(Gravity.CENTER);
                t3v.setPadding(10, 10, 10, 10);
                tbrow.addView(t3v);
                stk.addView(tbrow);

                amount += Integer.valueOf(fm_Inst.getGetMyExpenses().get(i).getAmount());
            }
        }
        //System.out.println("shiva debug 3");
        TextView txtView = findViewById(R.id.expv18);
        txtView.setText(""+amount);
    }
}

