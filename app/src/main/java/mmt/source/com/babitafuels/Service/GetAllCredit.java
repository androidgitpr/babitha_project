package mmt.source.com.babitafuels.Service;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import mmt.source.com.babitafuels.Model.CreditTxn;
import mmt.source.com.babitafuels.Model.FuelMnt;

import static mmt.source.com.babitafuels.Model.Constants.url_base;

public class GetAllCredit extends AsyncTask<String, Void, Integer> {

    FuelMnt fm_inst = FuelMnt.getInstance();
    Integer result = 500;
    @Override
    protected Integer doInBackground(String... params) {
        BufferedReader reader = null;
        try {
            // Create Apache HttpClient
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost postRequest = new HttpPost(url_base+"Credit/transaction");

            Gson gson = new GsonBuilder().disableHtmlEscaping().create();
            //System.out.println("shiva exp api ");
            StringEntity stringEntity = new StringEntity(gson.toJson(fm_inst.getCreditTxn()));
            postRequest.setEntity(stringEntity);
            postRequest.setHeader("Content-Type", "application/json");
            HttpResponse httpResponse = httpclient.execute(postRequest);


            String line;
            reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
            if((line = reader.readLine())!=null){
                System.out.println("shiva get credit txn list response "+line);
            }

            int statusCode = httpResponse.getStatusLine().getStatusCode();

            if (statusCode == 200) {
                parseResult(line);
                result = 200;
            } else {
                System.out.println("shiva failure response received");
            }

        } catch (Exception e) {
            System.out.println("shiva "+e.getCause());
            e.printStackTrace();
            System.out.println("shiva  exception occurred ");
        }finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    private void parseResult(String inparam) {
        try {

            JSONArray post = new JSONArray(inparam);
            FuelMnt fm_inst = FuelMnt.getInstance();
            fm_inst.getAllCreditTxn().clear();
            for(int i = 0; i < post.length(); i++) {
                JSONObject obj = post.getJSONObject(i);
                CreditTxn item = new CreditTxn();
                item.setBunkId(obj.getInt("bunkId"));
                item.setModePay(obj.getString("modePay"));
                item.setUsrId(obj.getString("usrId"));
                item.setAmountPaid(obj.getString("amountPaid"));
                item.setAmountUsed(obj.getString("amountUsed"));
                item.setFuelType(obj.getString("fuelType"));
                item.setTxnDate(obj.getString("txnDate"));
                item.setPersonName(obj.getString("personName"));
                item.setVehNum(obj.getString("vehNum"));
                fm_inst.getAllCreditTxn().add(item);
            }
        }catch (Exception e) {
            System.out.println("shiva failed get credit txn list parse");
        }
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (result == 1) {
            //mGridAdapter.setGridData(mGridData);
        } else {
            // Toast.makeText(LocationViewActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}
